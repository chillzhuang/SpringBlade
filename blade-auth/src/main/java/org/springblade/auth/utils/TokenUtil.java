/**
 * Copyright (c) 2018-2099, Chill Zhuang 庄骞 (bladejava@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.auth.utils;

import org.springblade.common.cache.CacheNames;
import org.springblade.core.launch.constant.TokenConstant;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.AuthInfo;
import org.springblade.core.secure.TokenInfo;
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tool.utils.*;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.entity.UserInfo;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证工具类
 *
 * @author Chill
 */
public class TokenUtil {

	public final static String CAPTCHA_HEADER_KEY = "Captcha-Key";
	public final static String CAPTCHA_HEADER_CODE = "Captcha-Code";
	public final static String CAPTCHA_NOT_CORRECT = "验证码不正确";
	public final static String TENANT_HEADER_KEY = "Tenant-Id";
	public final static String DEFAULT_TENANT_ID = "000000";
	public final static String USER_TYPE_HEADER_KEY = "User-Type";
	public final static String DEFAULT_USER_TYPE = "web";
	public final static String USER_NOT_FOUND = "用户名或密码错误";
	public final static String HEADER_KEY = "Authorization";
	public final static String HEADER_PREFIX = "Basic ";
	public final static String ENCRYPT_PREFIX = "04";
	public final static String USER_HAS_TOO_MANY_FAILS = "用户登录失败次数过多";
	public final static String IP_HAS_TOO_MANY_FAILS = "用户登录失败次数过多，请稍后再试";
	public final static String DEFAULT_AVATAR = "https://bladex.cn/images/logo.png";

	/**
	 * 创建认证token
	 *
	 * @param userInfo 用户信息
	 * @return token
	 */
	public static AuthInfo createAuthInfo(UserInfo userInfo) {
		User user = userInfo.getUser();

		//设置jwt参数
		Map<String, String> param = new HashMap<>(16);
		param.put(TokenConstant.TOKEN_TYPE, TokenConstant.ACCESS_TOKEN);
		param.put(TokenConstant.TENANT_ID, user.getTenantId());
		param.put(TokenConstant.OAUTH_ID, userInfo.getOauthId());
		param.put(TokenConstant.USER_ID, Func.toStr(user.getId()));
		param.put(TokenConstant.ROLE_ID, user.getRoleId());
		param.put(TokenConstant.DEPT_ID, user.getDeptId());
		param.put(TokenConstant.ACCOUNT, user.getAccount());
		param.put(TokenConstant.USER_NAME, user.getAccount());
		param.put(TokenConstant.ROLE_NAME, Func.join(userInfo.getRoles()));

		TokenInfo accessToken = SecureUtil.createJWT(param, "audience", "issuser", TokenConstant.ACCESS_TOKEN);
		AuthInfo authInfo = new AuthInfo();
		authInfo.setUserId(user.getId());
		authInfo.setTenantId(user.getTenantId());
		authInfo.setOauthId(userInfo.getOauthId());
		authInfo.setAccount(user.getAccount());
		authInfo.setUserName(user.getRealName());
		authInfo.setAuthority(Func.join(userInfo.getRoles()));
		authInfo.setAccessToken(accessToken.getToken());
		authInfo.setExpiresIn(accessToken.getExpire());
		authInfo.setRefreshToken(createRefreshToken(userInfo).getToken());
		authInfo.setTokenType(TokenConstant.BEARER);
		authInfo.setLicense(TokenConstant.LICENSE_NAME);

		return authInfo;
	}

	/**
	 * 创建refreshToken
	 *
	 * @param userInfo 用户信息
	 * @return refreshToken
	 */
	private static TokenInfo createRefreshToken(UserInfo userInfo) {
		User user = userInfo.getUser();
		Map<String, String> param = new HashMap<>(16);
		param.put(TokenConstant.TOKEN_TYPE, TokenConstant.REFRESH_TOKEN);
		param.put(TokenConstant.USER_ID, Func.toStr(user.getId()));
		return SecureUtil.createJWT(param, "audience", "issuser", TokenConstant.REFRESH_TOKEN);
	}

	/**
	 * 解析国密sm2加密密码
	 *
	 * @param rawPassword 请求时提交的原密码
	 * @param publicKey   公钥
	 * @param privateKey  私钥
	 * @return 解密后的密码
	 */
	public static String decryptPassword(String rawPassword, String publicKey, String privateKey) {
		// 其中有空则匹配失败
		if (StringUtil.isAnyBlank(publicKey, privateKey)) {
			return StringPool.EMPTY;
		}
		// 处理部分工具类加密不带04前缀的情况
		if (!StringUtil.startsWithIgnoreCase(rawPassword, ENCRYPT_PREFIX)) {
			rawPassword = ENCRYPT_PREFIX + rawPassword;
		}
		// 解密密码
		String decryptPassword = SM2Util.decrypt(rawPassword, privateKey);
		// 签名校验
		boolean isVerified = SM2Util.verify(decryptPassword, SM2Util.sign(decryptPassword, privateKey), publicKey);
		if (!isVerified) {
			return StringPool.EMPTY;
		}
		return decryptPassword;
	}

	/**
	 * 失败次数上限
	 */
	public final static Integer FAIL_COUNT = 5;

	/**
	 * 检查账号和IP是否被锁定
	 *
	 * @param bladeRedis Redis缓存
	 * @param tenantId   租户ID
	 * @param account    账号
	 */
	public static void checkAccountAndIpLock(BladeRedis bladeRedis, String tenantId, String account) {
		String ip = WebUtil.getIP();

		// 检查账号锁定
		int userFailCount = Func.toInt(bladeRedis.get(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, account)), 0);
		if (userFailCount >= FAIL_COUNT) {
			throw new ServiceException(USER_HAS_TOO_MANY_FAILS);
		}

		// 检查IP锁定
		int ipFailCount = Func.toInt(bladeRedis.get(CacheNames.IP_FAIL_KEY + ip), 0);
		if (ipFailCount >= FAIL_COUNT) {
			throw new ServiceException(IP_HAS_TOO_MANY_FAILS);
		}
	}

	/**
	 * 处理登录失败，增加失败次数
	 *
	 * @param bladeRedis Redis缓存
	 * @param tenantId   租户ID
	 * @param account    账号
	 */
	public static void handleLoginFailure(BladeRedis bladeRedis, String tenantId, String account) {
		String ip = WebUtil.getIP();

		// 增加账号错误锁定次数
		int userFailCount = Func.toInt(bladeRedis.get(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, account)), 0);
		bladeRedis.setEx(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, account), userFailCount + 1, Duration.ofMinutes(30));

		// 增加IP错误锁定次数
		int ipFailCount = Func.toInt(bladeRedis.get(CacheNames.IP_FAIL_KEY + ip), 0);
		bladeRedis.setEx(CacheNames.IP_FAIL_KEY + ip, ipFailCount + 1, Duration.ofMinutes(30));
	}

	/**
	 * 处理登录成功，清除失败缓存
	 *
	 * @param bladeRedis Redis缓存
	 * @param tenantId   租户ID
	 * @param account    账号
	 */
	public static void handleLoginSuccess(BladeRedis bladeRedis, String tenantId, String account) {
		String ip = WebUtil.getIP();

		// 清除账号登录失败缓存
		bladeRedis.del(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, account));

		// 清除IP登录失败缓存
		bladeRedis.del(CacheNames.IP_FAIL_KEY + ip);
	}

}
