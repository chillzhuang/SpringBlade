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
package com.example.demo.service;

import com.example.demo.entity.Notice;
import org.springblade.core.mp.base.BaseService;

import java.util.List;

/**
 * 服务类
 *
 * @author Chill
 */
public interface IDynamicService extends BaseService<Notice> {

	/**
	 * master数据源的列表
	 *
	 * @return
	 */
	List<Notice> masterList();

	/**
	 * slave数据源的列表
	 *
	 * @return
	 */
	List<Notice> slaveList();

}
