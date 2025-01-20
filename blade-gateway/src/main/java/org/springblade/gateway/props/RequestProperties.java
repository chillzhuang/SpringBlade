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

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Request配置类
 *
 * @author Chill
 */
@Data
@ConfigurationProperties("blade.request")
public class RequestProperties {

	/**
	 * 开启自定义request
	 */
	private Boolean enabled = true;

	/**
	 * 放行url
	 */
	private List<String> skipUrl = new ArrayList<>();

	/**
	 * 禁用url
	 */
	private List<String> blockUrl = new ArrayList<>();

	/**
	 * 白名单，支持通配符，例如：10.20.0.8*、10.20.0.*
	 */
	private List<String> whiteList = new ArrayList<>();

	/**
	 * 黑名单，支持通配符，例如：10.20.0.8*、10.20.0.*
	 */
	private List<String> blackList = new ArrayList<>();

}
