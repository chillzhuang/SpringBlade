/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
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

import org.springblade.core.launch.constant.TokenConstant;
import org.springblade.core.secure.AuthInfo;
import org.springblade.core.secure.TokenInfo;
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.entity.UserInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证工具类
 *
 * @author Chill
 */
public class TokenUtil {

	public final static String TENANT_HEADER_KEY = "Tenant-Id";
	public final static String DEFAULT_TENANT_ID = "000000";
	public final static String USER_TYPE_HEADER_KEY = "User-Type";
	public final static String DEFAULT_USER_TYPE = "web";
	public final static String USER_NOT_FOUND = "用户名或密码错误";
	public final static String HEADER_KEY = "Authorization";
	public final static String HEADER_PREFIX = "Basic ";
	public final static String DEFAULT_AVATAR = "https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png";

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
		param.put(TokenConstant.USER_ID, Func.toStr(user.getId()));
		param.put(TokenConstant.ROLE_ID, user.getRoleId());
		param.put(TokenConstant.ACCOUNT, user.getAccount());
		param.put(TokenConstant.USER_NAME, user.getAccount());
		param.put(TokenConstant.ROLE_NAME, Func.join(userInfo.getRoles()));

		TokenInfo accessToken = SecureUtil.createJWT(param, "audience", "issuser", TokenConstant.ACCESS_TOKEN);
		AuthInfo authInfo = new AuthInfo();
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

}
