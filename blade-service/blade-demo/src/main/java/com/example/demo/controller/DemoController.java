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
package com.example.demo.controller;

import com.example.demo.props.DemoProperties;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo控制器
 *
 * @author Chill
 */
@RefreshScope
@RestController
@RequestMapping("demo")
@Api(value = "配置接口", tags = "即时刷新配置")
public class DemoController {

	@Value("${demo.name}")
	private String name;

	private final DemoProperties properties;

	public DemoController(DemoProperties properties) {
		this.properties = properties;
	}


	@GetMapping("name")
	public String getName() {
		return name;
	}

	@GetMapping("name-by-props")
	public String getNameByProps() {
		return properties.getName();
	}

}
