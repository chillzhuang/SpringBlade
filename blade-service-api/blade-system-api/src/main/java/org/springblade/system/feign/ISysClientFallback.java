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

import org.springblade.core.tool.api.R;
import org.springblade.system.entity.Dept;
import org.springblade.system.entity.Role;
import org.springblade.system.entity.Tenant;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Feign失败配置
 *
 * @author Chill
 */
@Component
public class ISysClientFallback implements ISysClient {

	@Override
	public Dept getDept(Long id) {
		return null;
	}

	@Override
	public String getDeptName(Long id) {
		return null;
	}

	@Override
	public String getDeptIds(String tenantId, String deptNames) {
		return null;
	}

	@Override
	public List<String> getDeptNames(String deptIds) {
		return null;
	}

	@Override
	public String getPostIds(String tenantId, String postNames) {
		return null;
	}

	@Override
	public List<String> getPostNames(String postIds) {
		return null;
	}

	@Override
	public Role getRole(Long id) {
		return null;
	}

	@Override
	public String getRoleIds(String tenantId, String roleNames) {
		return null;
	}

	@Override
	public String getRoleName(Long id) {
		return null;
	}

	@Override
	public List<String> getRoleNames(String roleIds) {
		return null;
	}

	@Override
	public String getRoleAlias(Long id) {
		return null;
	}

	@Override
	public R<Tenant> getTenant(Long id) {
		return null;
	}

	@Override
	public R<Tenant> getTenant(String tenantId) {
		return null;
	}
}
