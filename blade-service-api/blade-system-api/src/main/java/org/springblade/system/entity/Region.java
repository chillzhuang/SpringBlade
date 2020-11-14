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
