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
import org.springblade.core.mp.base.TenantEntity;

import java.io.Serial;

/**
 * 顶部菜单表实体类
 *
 * @author BladeX
 */
@Data
@TableName("blade_top_menu")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "顶部菜单表")
public class TopMenu extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 顶部菜单编号
	 */
	@Schema(description = "顶部菜单编号")
	private String code;
	/**
	 * 顶部菜单名
	 */
	@Schema(description = "顶部菜单名")
	private String name;
	/**
	 * 顶部菜单资源
	 */
	@Schema(description = "顶部菜单资源")
	private String source;
	/**
	 * 顶部菜单路由
	 */
	@Schema(description = "顶部菜单路由")
	private String path;
	/**
	 * 顶部菜单排序
	 */
	@Schema(description = "顶部菜单排序")
	private Integer sort;


}
