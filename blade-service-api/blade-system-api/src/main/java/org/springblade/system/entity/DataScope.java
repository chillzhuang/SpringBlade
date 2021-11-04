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
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

/**
 * 实体类
 *
 * @author BladeX
 */
@Data
@TableName("blade_scope_data")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DataScope对象", description = "DataScope对象")
public class DataScope extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 菜单主键
	 */
	@ApiModelProperty(value = "菜单主键")
	private Long menuId;
	/**
	 * 资源编号
	 */
	@ApiModelProperty(value = "资源编号")
	private String resourceCode;
	/**
	 * 数据权限名称
	 */
	@ApiModelProperty(value = "数据权限名称")
	private String scopeName;
	/**
	 * 数据权限可见字段
	 */
	@ApiModelProperty(value = "数据权限可见字段")
	private String scopeField;
	/**
	 * 数据权限类名
	 */
	@ApiModelProperty(value = "数据权限类名")
	private String scopeClass;
	/**
	 * 数据权限字段
	 */
	@ApiModelProperty(value = "数据权限字段")
	private String scopeColumn;
	/**
	 * 数据权限类型
	 */
	@ApiModelProperty(value = "数据权限类型")
	private Integer scopeType;
	/**
	 * 数据权限值域
	 */
	@ApiModelProperty(value = "数据权限值域")
	private String scopeValue;
	/**
	 * 数据权限备注
	 */
	@ApiModelProperty(value = "数据权限备注")
	private String remark;


}
