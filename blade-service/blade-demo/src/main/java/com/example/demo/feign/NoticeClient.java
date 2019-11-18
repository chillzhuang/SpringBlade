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

import com.example.demo.entity.Notice;
import com.example.demo.mapper.NoticeMapper;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Notice Feign
 *
 * @author Chill
 */
@ApiIgnore()
@RestController
@AllArgsConstructor
public class NoticeClient implements INoticeClient {

	private NoticeMapper mapper;

	@Override
	@GetMapping(TOP)
	public R<List<Notice>> top(Integer number) {
		return R.data(mapper.topList(number));
	}

}
