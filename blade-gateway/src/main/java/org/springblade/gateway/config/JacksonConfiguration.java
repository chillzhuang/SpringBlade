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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 网关 Jackson 配置
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
public class JacksonConfiguration {

	/**
	 * 为网关鉴权与全局异常处理提供写响应所需的 Jackson 2 ObjectMapper。
	 * 该 Bean 在 Boot 4 下仅随 spring-boot-jackson2 自动装配，而网关刻意不引入该模块（以免其编解码器干扰 WebFlux），故在此手动声明。
	 */
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}
