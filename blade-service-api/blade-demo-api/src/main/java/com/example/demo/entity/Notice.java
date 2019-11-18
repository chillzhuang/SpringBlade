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
package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.util.Date;

/**
 * 实体类
 *
 * @author Chill
 */
@Data
@TableName("blade_notice")
@EqualsAndHashCode(callSuper = true)
public class Notice extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "主键id")
	private Integer id;

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题")
	private String title;

	/**
	 * 通知类型
	 */
	@ApiModelProperty(value = "通知类型")
	private Integer category;

	/**
	 * 发布日期
	 */
	@ApiModelProperty(value = "发布日期")
	private Date releaseTime;

	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容")
	private String content;


}
