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
package org.springblade.auth.granter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.auth.enums.BladeUserEnum;
import org.springblade.auth.utils.TokenUtil;
import org.springblade.common.cache.CacheNames;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.props.BladeAuthProperties;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DigestUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.WebUtil;
import org.springblade.system.user.entity.UserInfo;
import org.springblade.system.user.feign.IUserClient;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * PasswordTokenGranter
 *
 * @author Chill
 */
@Slf4j
@Component
@AllArgsConstructor
public class PasswordTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "password";
	public static final Integer FAIL_COUNT = 5;

	private IUserClient userClient;
	private BladeRedis bladeRedis;

	private BladeAuthProperties authProperties;

	@Override
	public UserInfo grant(TokenParameter tokenParameter) {
		String tenantId = tokenParameter.getArgs().getStr("tenantId");
		String account = tokenParameter.getArgs().getStr("account");
		String password = tokenParameter.getArgs().getStr("password");

		// 判断登录是否锁定
		int cnt = Func.toInt(bladeRedis.get(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, account)), 0);
		if (cnt >= FAIL_COUNT) {
			log.error("用户登录失败次数过多, 账号:{}, IP:{}", account, WebUtil.getIP());
			throw new ServiceException(TokenUtil.USER_HAS_TOO_MANY_FAILS);
		}

		UserInfo userInfo = null;
		if (Func.isNoneBlank(account, password)) {
			// 获取用户类型
			String userType = tokenParameter.getArgs().getStr("userType");
			// 解密密码
			String decryptPassword = TokenUtil.decryptPassword(password, authProperties.getPublicKey(), authProperties.getPrivateKey());
			// 定义返回结果
			R<UserInfo> result;
			// 根据不同用户类型调用对应的接口返回数据，用户可自行拓展
			if (userType.equals(BladeUserEnum.WEB.getName())) {
				result = userClient.userInfo(tenantId, account, DigestUtil.encrypt(decryptPassword));
			} else if (userType.equals(BladeUserEnum.APP.getName())) {
				result = userClient.userInfo(tenantId, account, DigestUtil.encrypt(decryptPassword));
			} else {
				result = userClient.userInfo(tenantId, account, DigestUtil.encrypt(decryptPassword));
			}
			userInfo = result.isSuccess() ? result.getData() : null;
		}

		if (userInfo == null || userInfo.getUser() == null) {
			// 增加错误锁定次数
			bladeRedis.setEx(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, account), cnt + 1, Duration.ofMinutes(30));
		} else {
			// 成功则清除登录缓存
			bladeRedis.del(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, account));
		}
		return userInfo;
	}

}
