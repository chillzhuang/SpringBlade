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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.gateway.props.SwaggerDiscoveryProperties;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties.SwaggerUrl;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * 接口文档聚合配置
 *
 * <p>
 * 从 注册中心 拉取在册服务动态生成聚合下拉列表，无需手工维护 文档路径；各服务文档经网关同源路由 /{serviceId}/v3/api-docs
 * 暴露、规避跨域。swagger-config 端点每次请求都会重新拷贝 SwaggerUiConfigProperties 的 文档路径，故运行期整表替换即在
 * 下一次拉取时生效。
 * </p>
 *
 * @author Chill
 */
@Slf4j
@EnableScheduling
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SwaggerDiscoveryProperties.class)
@ConditionalOnProperty(value = "springdoc.api-docs.enabled", matchIfMissing = true)
public class SwaggerAggregationConfiguration {

	private final ReactiveDiscoveryClient discoveryClient;
	private final SwaggerUiConfigProperties swaggerUiConfigProperties;
	private final SwaggerDiscoveryProperties swaggerDiscoveryProperties;

	/**
	 * 启动完成后首刷，填补首个定时周期前的空窗
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		refresh();
	}

	/**
	 * 定时从 注册中心 拉取服务列表，重建聚合文档下拉项
	 */
	@Scheduled(fixedDelayString = "${blade.swagger.discovery.refresh-interval:30000}", initialDelay = 30000)
	public void refresh() {
		// getServices 由 注册中心 反应式实现移到 boundedElastic 执行，不占用网关事件循环
		discoveryClient.getServices()
			.filter(this::isIncluded)
			.map(this::toSwaggerUrl)
			.collect(Collectors.toCollection(LinkedHashSet::new))
			// 整表替换路径引用，读侧每请求拷贝快照，避免就地增删引发的并发修改
			.subscribe(swaggerUiConfigProperties::setUrls, error -> log.error("刷新聚合接口文档失败", error));
	}

	private boolean isIncluded(String serviceId) {
		return swaggerDiscoveryProperties.getExcludedServices().stream()
			.noneMatch(excluded -> excluded.equalsIgnoreCase(serviceId));
	}

	private SwaggerUrl toSwaggerUrl(String serviceId) {
		String url = "/" + serviceId + swaggerDiscoveryProperties.getApiDocsPath();
		return new SwaggerUrl(serviceId, url, serviceId);
	}

}
