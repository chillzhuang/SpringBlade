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

package org.springblade.gateway.config;

import com.github.xiaoymin.knife4j.spring.gateway.Knife4jGatewayProperties;
import com.github.xiaoymin.knife4j.spring.gateway.discover.router.DiscoverClientRouteServiceConvert;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * knife4j自动聚合配置
 *
 * @author BladeX
 */
@Configuration(proxyBeanMethods = false)
public class ReactiveDiscoveryConfiguration {

	@Bean
	@ConditionalOnProperty(name = {"spring.cloud.gateway.server.webflux.discovery.locator.enabled"})
	public DiscoverClientRouteServiceConvert discoverClientRouteServiceConvert(DiscoveryClientRouteDefinitionLocator discoveryClient,
																			   Knife4jGatewayProperties knife4jGatewayProperties) {
		return new DiscoverClientRouteServiceConvert(discoveryClient, knife4jGatewayProperties);
	}

}
