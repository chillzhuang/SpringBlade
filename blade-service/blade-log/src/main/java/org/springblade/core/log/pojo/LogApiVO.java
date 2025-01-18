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
package org.springblade.core.log.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.log.model.LogApi;

import java.io.Serial;

/**
 * LogApiVO
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogApiVO extends LogApi {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 操作提交的数据
	 */
	@JsonIgnore
	private String params;


}
