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
package org.springblade.modules.system.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tool.jackson.BladeView;
import org.springblade.core.tool.jackson.Views;
import org.springblade.modules.system.entity.User;

import java.io.Serial;

/**
 * 视图实体类
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "UserVO对象")
public class UserVO extends User {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 角色名
	 */
	@BladeView(Views.Summary.class)
	private String roleName;

	/**
	 * 部门名
	 */
	@BladeView(Views.Summary.class)
	private String deptName;

	/**
	 * 岗位名
	 */
	@BladeView(Views.Detail.class)
	private String postName;

	/**
	 * 性别
	 */
	@BladeView(Views.Detail.class)
	private String sexName;
}
