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

import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.system.entity.Dept;
import org.springblade.system.entity.Role;
import org.springblade.system.entity.Tenant;
import org.springblade.system.service.IDeptService;
import org.springblade.system.service.IPostService;
import org.springblade.system.service.IRoleService;
import org.springblade.system.service.ITenantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 系统服务Feign实现类
 *
 * @author Chill
 */
@ApiIgnore
@RestController
@AllArgsConstructor
public class SysClient implements ISysClient {

	private IDeptService deptService;

	private IPostService postService;

	private IRoleService roleService;

	private ITenantService tenantService;

	@Override
	@GetMapping(API_PREFIX + "/getDept")
	public Dept getDept(Long id) {
		return deptService.getById(id);
	}

	@Override
	@GetMapping(API_PREFIX + "/getDeptName")
	public String getDeptName(Long id) {
		return deptService.getById(id).getDeptName();
	}

	@Override
	public String getDeptIds(String tenantId, String deptNames) {
		return deptService.getDeptIds(tenantId, deptNames);
	}

	@Override
	public List<String> getDeptNames(String deptIds) {
		return deptService.getDeptNames(deptIds);
	}

	@Override
	public String getPostIds(String tenantId, String postNames) {
		return postService.getPostIds(tenantId, postNames);
	}

	@Override
	public List<String> getPostNames(String postIds) {
		return postService.getPostNames(postIds);
	}

	@Override
	@GetMapping(API_PREFIX + "/getRole")
	public Role getRole(Long id) {
		return roleService.getById(id);
	}

	@Override
	public String getRoleIds(String tenantId, String roleNames) {
		return roleService.getRoleIds(tenantId, roleNames);
	}

	@Override
	@GetMapping(API_PREFIX + "/getRoleName")
	public String getRoleName(Long id) {
		return roleService.getById(id).getRoleName();
	}

	@Override
	public List<String> getRoleNames(String roleIds) {
		return roleService.getRoleNames(roleIds);
	}

	@Override
	@GetMapping(API_PREFIX + "/getRoleAlias")
	public String getRoleAlias(Long id) {
		return roleService.getById(id).getRoleAlias();
	}

	@Override
	@GetMapping(API_PREFIX + "/tenant")
	public R<Tenant> getTenant(Long id) {
		return R.data(tenantService.getById(id));
	}

	@Override
	@GetMapping(API_PREFIX + "/tenant-id")
	public R<Tenant> getTenant(String tenantId) {
		return R.data(tenantService.getByTenantId(tenantId));
	}
}
