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
package org.springblade.system.feign;

import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.tool.api.R;
import org.springblade.system.entity.Dept;
import org.springblade.system.entity.Role;
import org.springblade.system.entity.Tenant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Feign接口类
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_SYSTEM_NAME,
	fallback = ISysClientFallback.class
)
public interface ISysClient {

	String API_PREFIX = "/sys";


	/**
	 * 获取部门
	 *
	 * @param id 主键
	 * @return Dept
	 */
	@GetMapping(API_PREFIX + "/getDept")
	Dept getDept(@RequestParam("id") Long id);

	/**
	 * 获取部门名
	 *
	 * @param id 主键
	 * @return 部门名
	 */
	@GetMapping(API_PREFIX + "/getDeptName")
	String getDeptName(@RequestParam("id") Long id);

	/**
	 * 获取部门id
	 *
	 * @param tenantId  租户id
	 * @param deptNames 部门名
	 * @return 部门id
	 */
	@GetMapping(API_PREFIX + "/getDeptIds")
	String getDeptIds(@RequestParam("tenantId") String tenantId, @RequestParam("deptNames") String deptNames);

	/**
	 * 获取部门名
	 *
	 * @param deptIds 主键
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getDeptNames")
	List<String> getDeptNames(@RequestParam("deptIds") String deptIds);

	/**
	 * 获取岗位id
	 *
	 * @param tenantId  租户id
	 * @param postNames 岗位名
	 * @return 岗位id
	 */
	@GetMapping(API_PREFIX + "/getPostIds")
	String getPostIds(@RequestParam("tenantId") String tenantId, @RequestParam("postNames") String postNames);

	/**
	 * 获取岗位名
	 *
	 * @param postIds 主键
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getPostNames")
	List<String> getPostNames(@RequestParam("postIds") String postIds);


	/**
	 * 获取角色
	 *
	 * @param id 主键
	 * @return Role
	 */
	@GetMapping(API_PREFIX + "/getRole")
	Role getRole(@RequestParam("id") Long id);

	/**
	 * 获取角色id
	 *
	 * @param tenantId  租户id
	 * @param roleNames 角色名
	 * @return 角色id
	 */
	@GetMapping(API_PREFIX + "/getRoleIds")
	String getRoleIds(@RequestParam("tenantId") String tenantId, @RequestParam("roleNames") String roleNames);

	/**
	 * 获取角色名
	 *
	 * @param id 主键
	 * @return 角色名
	 */
	@GetMapping(API_PREFIX + "/getRoleName")
	String getRoleName(@RequestParam("id") Long id);

	/**
	 * 获取角色名
	 *
	 * @param roleIds 主键
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getRoleNames")
	List<String> getRoleNames(@RequestParam("roleIds") String roleIds);

	/**
	 * 获取角色别名
	 *
	 * @param id 主键
	 * @return 角色别名
	 */
	@GetMapping(API_PREFIX + "/getRoleAlias")
	String getRoleAlias(@RequestParam("id") Long id);

	/**
	 * 获取租户
	 *
	 * @param id 主键
	 * @return Tenant
	 */
	@GetMapping(API_PREFIX + "/tenant")
	R<Tenant> getTenant(@RequestParam("id") Long id);

	/**
	 * 获取租户
	 *
	 * @param tenantId 租户id
	 * @return Tenant
	 */
	@GetMapping(API_PREFIX + "/tenant-id")
	R<Tenant> getTenant(@RequestParam("tenantId") String tenantId);

}
