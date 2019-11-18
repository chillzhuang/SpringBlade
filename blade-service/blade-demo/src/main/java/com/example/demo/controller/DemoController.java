/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
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
