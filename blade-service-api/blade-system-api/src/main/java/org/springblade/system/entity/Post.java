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
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.TenantEntity;

import java.io.Serial;

/**
 * 岗位表实体类
 *
 * @author Chill
 */
@Data
@TableName("blade_post")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Post对象")
public class Post extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 类型
	 */
	@Schema(description = "类型")
	private Integer category;
	/**
	 * 岗位编号
	 */
	@Schema(description = "岗位编号")
	private String postCode;
	/**
	 * 岗位名称
	 */
	@Schema(description = "岗位名称")
	private String postName;
	/**
	 * 岗位排序
	 */
	@Schema(description = "岗位排序")
	private Integer sort;
	/**
	 * 岗位描述
	 */
	@Schema(description = "岗位描述")
	private String remark;


}
