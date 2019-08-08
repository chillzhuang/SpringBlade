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
package org.springblade.auth.granter;

import lombok.AllArgsConstructor;
import org.springblade.auth.enums.BladeUserEnum;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DigestUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.user.entity.UserInfo;
import org.springblade.system.user.feign.IUserClient;
import org.springframework.stereotype.Component;

/**
 * PasswordTokenGranter
 *
 * @author Chill
 */
@Component
@AllArgsConstructor
public class PasswordTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "password";

	private IUserClient userClient;

	@Override
	public UserInfo grant(TokenParameter tokenParameter) {
		String tenantId = tokenParameter.getArgs().getStr("tenantId");
		String account = tokenParameter.getArgs().getStr("account");
		String password = tokenParameter.getArgs().getStr("password");
		UserInfo userInfo = null;
		if (Func.isNoneBlank(account, password)) {
			// 获取用户类型
			String userType = tokenParameter.getArgs().getStr("userType");
			R<UserInfo> result;
			// 根据不同用户类型调用对应的接口返回数据，用户可自行拓展
			if (userType.equals(BladeUserEnum.WEB.getName())) {
				result = userClient.userInfo(tenantId, account, DigestUtil.encrypt(password));
			} else if (userType.equals(BladeUserEnum.APP.getName())) {
				result = userClient.userInfo(tenantId, account, DigestUtil.encrypt(password));
			} else {
				result = userClient.userInfo(tenantId, account, DigestUtil.encrypt(password));
			}
			userInfo = result.isSuccess() ? result.getData() : null;
		}
		return userInfo;
	}

}
