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
package org.springblade.system.user.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 *
 * @author Chill
 */
@Data
@ApiModel(description = "用户信息")
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户基础信息
	 */
	@ApiModelProperty(value = "用户")
	private User user;

	/**
	 * 权限标识集合
	 */
	@ApiModelProperty(value = "权限集合")
	private List<String> permissions;

	/**
	 * 角色集合
	 */
	@ApiModelProperty(value = "角色集合")
	private List<String> roles;

	/**
	 * 第三方授权id
	 */
	@ApiModelProperty(value = "第三方授权id")
	private String oauthId;

}
