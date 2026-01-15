/**
 * Copyright (c) 2018-2099, Chill Zhuang 庄骞 (bladejava@qq.com).
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
package org.springblade.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.io.Serial;

/**
 * 实体类
 *
 * @author BladeX
 */
@Data
@TableName("blade_scope_api")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "ApiScope对象")
public class ApiScope extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 菜单主键
	 */
	@Schema(description = "菜单主键")
	private Long menuId;
	/**
	 * 资源编号
	 */
	@Schema(description = "资源编号")
	private String resourceCode;
	/**
	 * 接口权限名称
	 */
	@Schema(description = "接口权限名称")
	private String scopeName;
	/**
	 * 接口权限字段
	 */
	@Schema(description = "接口权限字段")
	private String scopePath;
	/**
	 * 接口权限类型
	 */
	@Schema(description = "接口权限类型")
	private Integer scopeType;
	/**
	 * 接口权限备注
	 */
	@Schema(description = "接口权限备注")
	private String remark;


}
