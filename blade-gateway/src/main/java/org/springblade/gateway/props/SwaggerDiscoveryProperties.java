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

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 接口文档聚合发现配置
 *
 * @author Chill
 */
@Getter
@Setter
@ConfigurationProperties("blade.swagger.discovery")
public class SwaggerDiscoveryProperties {

	/**
	 * 各微服务 OpenAPI 文档路径
	 */
	private String apiDocsPath = "/v3/api-docs";

	/**
	 * 聚合下拉项刷新间隔(毫秒)
	 */
	private long refreshInterval = 30000L;

	/**
	 * 不纳入聚合的服务，网关自身与无业务文档的服务应排除
	 */
	private List<String> excludedServices = new ArrayList<>(List.of("blade-gateway", "blade-admin", "blade-log"));

}
