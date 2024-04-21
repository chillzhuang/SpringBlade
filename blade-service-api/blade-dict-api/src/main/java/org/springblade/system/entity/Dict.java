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

import java.io.Serial;
import java.io.Serializable;

/**
 * 实体类
 *
 * @author Chill
 * @since 2018-12-24
 */
@Data
@TableName("blade_dict")
@Schema(description = "Dict对象")
public class Dict implements Serializable {

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
	 * 父主键
	 */
	@Schema(description = "父主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;

	/**
	 * 字典码
	 */
	@Schema(description = "字典码")
	private String code;

	/**
	 * 字典值
	 */
	@Schema(description = "字典值")
	private Integer dictKey;

	/**
	 * 字典名称
	 */
	@Schema(description = "字典名称")
	private String dictValue;

	/**
	 * 排序
	 */
	@Schema(description = "排序")
	private Integer sort;

	/**
	 * 字典备注
	 */
	@Schema(description = "字典备注")
	private String remark;

	/**
	 * 是否已删除
	 */
	@TableLogic
	@Schema(description = "是否已删除")
	private Integer isDeleted;


}
