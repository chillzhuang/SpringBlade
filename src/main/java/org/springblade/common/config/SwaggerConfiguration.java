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
package org.springblade.common.config;

import lombok.AllArgsConstructor;
import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.swagger.EnableSwagger;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置类
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@EnableSwagger
@AllArgsConstructor
@ConditionalOnProperty(value = "swagger.enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerConfiguration {

	@Bean
	public GroupedOpenApi authApi() {
		return GroupedOpenApi.builder()
			.group("授权模块")
			.packagesToScan(AppConstant.BASE_PACKAGES + ".modules.auth")
			.build();
	}

	@Bean
	public GroupedOpenApi sysApi() {
		return GroupedOpenApi.builder()
			.group("系统模块")
			.packagesToScan(AppConstant.BASE_PACKAGES + ".modules.system", AppConstant.BASE_PACKAGES + ".modules.resource")
			.build();
	}

	@Bean
	public GroupedOpenApi deskApi() {
		// 创建并返回GroupedOpenApi对象
		return GroupedOpenApi.builder()
			.group("工作台模块")
			.packagesToScan(AppConstant.BASE_PACKAGES + ".modules.desk")
			.build();
	}

}
