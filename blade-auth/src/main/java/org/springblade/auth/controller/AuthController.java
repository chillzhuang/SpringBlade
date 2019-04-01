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
package org.springblade.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.core.launch.constant.TokenConstant;
import org.springblade.core.secure.AuthInfo;
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DigestUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.entity.UserInfo;
import org.springblade.system.user.feign.IUserClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证模块
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@Api(value = "用户授权认证", tags = "授权接口")
public class AuthController {

	private IUserClient client;

	@PostMapping("token")
	@ApiOperation(value = "获取认证token", notes = "传入租户编号:tenantCode,账号:account,密码:password")
	public R<AuthInfo> token(@ApiParam(value = "租户编号", required = true) @RequestParam(defaultValue = "000000", required = false) String tenantCode,
							 @ApiParam(value = "账号", required = true) @RequestParam String account,
							 @ApiParam(value = "密码", required = true) @RequestParam String password) {

		if (Func.hasEmpty(account, password)) {
			return R.fail("接口调用不合法");
		}

		R<UserInfo> res = client.userInfo(tenantCode, account, DigestUtil.encrypt(password));

		User user = res.getData().getUser();

		//验证用户
		if (Func.isEmpty(user.getId())) {
			return R.fail("用户名或密码不正确");
		}

		//设置jwt参数
		Map<String, String> param = new HashMap<>(16);
		param.put(TokenConstant.USER_ID, Func.toStr(user.getId()));
		param.put(TokenConstant.ROLE_ID, user.getRoleId());
		param.put(TokenConstant.TENANT_CODE, user.getTenantCode());
		param.put(TokenConstant.ACCOUNT, user.getAccount());
		param.put(TokenConstant.USER_NAME, user.getRealName());
		param.put(TokenConstant.ROLE_NAME, Func.join(res.getData().getRoles()));

		//拼装accessToken
		String accessToken = SecureUtil.createJWT(param, "audience", "issuser", true);

		//返回accessToken
		AuthInfo authInfo = new AuthInfo();
		authInfo.setAccount(user.getAccount());
		authInfo.setUserName(user.getRealName());
		authInfo.setAuthority(Func.join(res.getData().getRoles()));
		authInfo.setAccessToken(accessToken);
		authInfo.setTokenType(TokenConstant.BEARER);
		//设置token过期时间
		authInfo.setExpiresIn(SecureUtil.getExpire());
		return R.data(authInfo);

	}

}
