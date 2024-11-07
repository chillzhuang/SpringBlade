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
package org.springblade.gateway.props;

import io.jsonwebtoken.JwtException;
import lombok.Data;
import org.springblade.core.launch.constant.TokenConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWT配置
 *
 * @author Chill
 */
@Data
@ConfigurationProperties("blade.token")
public class JwtProperties {

	/**
	 * token是否有状态
	 */
	private Boolean state = Boolean.FALSE;

	/**
	 * 是否只可同时在线一人
	 */
	private Boolean single = Boolean.FALSE;

	/**
	 * token签名
	 */
	private String signKey = "";

	/**
	 * 获取签名规则
	 */
	public String getSignKey() {
		if (this.signKey.length() < TokenConstant.SIGN_KEY_LENGTH) {
			throw new JwtException("请配置 blade.token.sign-key 的值, 长度32位以上");
		}
		return this.signKey;
	}

}
