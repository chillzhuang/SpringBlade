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
package org.springblade.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 行政区划表实体类
 *
 * @author Chill
 */
@Data
@TableName("blade_region")
@ApiModel(value = "Region对象", description = "行政区划表")
public class Region implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 区划编号
	 */
	@TableId(value = "code", type = IdType.INPUT)
	@ApiModelProperty(value = "区划编号")
	private String code;
	/**
	 * 父区划编号
	 */
	@ApiModelProperty(value = "父区划编号")
	private String parentCode;
	/**
	 * 祖区划编号
	 */
	@ApiModelProperty(value = "祖区划编号")
	private String ancestors;
	/**
	 * 区划名称
	 */
	@ApiModelProperty(value = "区划名称")
	private String name;
	/**
	 * 省级区划编号
	 */
	@ApiModelProperty(value = "省级区划编号")
	private String provinceCode;
	/**
	 * 省级名称
	 */
	@ApiModelProperty(value = "省级名称")
	private String provinceName;
	/**
	 * 市级区划编号
	 */
	@ApiModelProperty(value = "市级区划编号")
	private String cityCode;
	/**
	 * 市级名称
	 */
	@ApiModelProperty(value = "市级名称")
	private String cityName;
	/**
	 * 区级区划编号
	 */
	@ApiModelProperty(value = "区级区划编号")
	private String districtCode;
	/**
	 * 区级名称
	 */
	@ApiModelProperty(value = "区级名称")
	private String districtName;
	/**
	 * 镇级区划编号
	 */
	@ApiModelProperty(value = "镇级区划编号")
	private String townCode;
	/**
	 * 镇级名称
	 */
	@ApiModelProperty(value = "镇级名称")
	private String townName;
	/**
	 * 村级区划编号
	 */
	@ApiModelProperty(value = "村级区划编号")
	private String villageCode;
	/**
	 * 村级名称
	 */
	@ApiModelProperty(value = "村级名称")
	private String villageName;
	/**
	 * 层级
	 */
	@ApiModelProperty(value = "层级")
	private Integer level;
	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private Integer sort;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;


}
