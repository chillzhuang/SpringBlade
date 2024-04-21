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
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springblade.core.tool.utils.Func;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * 实体类
 *
 * @author Chill
 */
@Data
@TableName("blade_menu")
@Schema(description = "Menu对象")
public class Menu implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 菜单父主键
	 */
	@Schema(description = "父主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;

	/**
	 * 菜单编号
	 */
	@Schema(description = "菜单编号")
	private String code;

	/**
	 * 菜单名称
	 */
	@Schema(description = "菜单名称")
	private String name;

	/**
	 * 菜单别名
	 */
	@Schema(description = "菜单别名")
	private String alias;

	/**
	 * 请求地址
	 */
	@Schema(description = "请求地址")
	private String path;

	/**
	 * 菜单资源
	 */
	@Schema(description = "菜单资源")
	private String source;

	/**
	 * 排序
	 */
	@Schema(description = "排序")
	private Integer sort;

	/**
	 * 菜单类型
	 */
	@Schema(description = "菜单类型")
	private Integer category;

	/**
	 * 操作按钮类型
	 */
	@Schema(description = "操作按钮类型")
	private Integer action;

	/**
	 * 是否打开新页面
	 */
	@Schema(description = "是否打开新页面")
	private Integer isOpen;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remark;

	/**
	 * 是否已删除
	 */
	@TableLogic
	@Schema(description = "是否已删除")
	private Integer isDeleted;


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		Menu other = (Menu) obj;
		return Func.equals(this.getId(), other.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, parentId, code);
	}

}
