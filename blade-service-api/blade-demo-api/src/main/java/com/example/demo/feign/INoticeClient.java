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
package com.example.demo.feign;

import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.tool.api.R;
import com.example.demo.entity.Notice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Notice Feign接口类
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_DESK_NAME
)
public interface INoticeClient {

	String API_PREFIX = "/client";
	String TOP = API_PREFIX + "/top";

	/**
	 * 获取notice列表
	 *
	 * @param number
	 * @return
	 */
	@GetMapping(TOP)
	R<List<Notice>> top(@RequestParam("number") Integer number);

}
