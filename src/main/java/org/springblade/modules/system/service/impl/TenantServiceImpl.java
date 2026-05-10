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
package org.springblade.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tenant.TenantId;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.utils.DigestUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.entity.*;
import org.springblade.modules.system.mapper.DeptMapper;
import org.springblade.modules.system.mapper.RoleMapper;
import org.springblade.modules.system.mapper.TenantMapper;
import org.springblade.modules.system.mapper.UserMapper;
import org.springblade.modules.system.service.IPostService;
import org.springblade.modules.system.service.ITenantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
@AllArgsConstructor
public class TenantServiceImpl extends BaseServiceImpl<TenantMapper, Tenant> implements ITenantService {

	private final TenantId tenantId;
	private final RoleMapper roleMapper;
	private final DeptMapper deptMapper;
	private final IPostService postService;
	private final UserMapper userMapper;

	@Override
	public IPage<Tenant> selectTenantPage(IPage<Tenant> page, Tenant tenant) {
		return page.setRecords(baseMapper.selectTenantPage(page, tenant));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveTenant(Tenant tenant) {
		if (Func.isEmpty(tenant.getId())) {
			List<Tenant> tenants = baseMapper.selectList(Wrappers.<Tenant>query().lambda().eq(Tenant::getIsDeleted, BladeConstant.DB_NOT_DELETED));
			List<String> codes = tenants.stream().map(Tenant::getTenantId).collect(Collectors.toList());
			String tenantId = getTenantId(codes);
			tenant.setTenantId(tenantId);
			// 新建租户对应的默认角色
			Role role = new Role();
			role.setTenantId(tenantId);
			role.setParentId(0L);
			role.setRoleName("管理员");
			role.setRoleAlias("admin");
			role.setSort(2);
			role.setIsDeleted(0);
			roleMapper.insert(role);
			// 新建租户对应的默认部门
			Dept dept = new Dept();
			dept.setTenantId(tenantId);
			dept.setParentId(0L);
			dept.setDeptName(tenant.getTenantName());
			dept.setFullName(tenant.getTenantName());
			dept.setSort(2);
			dept.setIsDeleted(0);
			deptMapper.insert(dept);
			// 新建租户对应的默认岗位
			Post post = new Post();
			post.setTenantId(tenantId);
			post.setCategory(1);
			post.setPostCode("ceo");
			post.setPostName("首席执行官");
			post.setSort(1);
			postService.save(post);
			// 新建租户对应的默认管理用户
			User user = new User();
			user.setTenantId(tenantId);
			user.setName("admin");
			user.setRealName("admin");
			user.setAccount("admin");
			user.setPassword(DigestUtil.encrypt("admin"));
			user.setRoleId(String.valueOf(role.getId()));
			user.setDeptId(String.valueOf(dept.getId()));
			user.setPostId(String.valueOf(post.getId()));
			user.setBirthday(new Date());
			user.setSex(1);
			user.setIsDeleted(BladeConstant.DB_NOT_DELETED);
			userMapper.insert(user);
		}
		return super.saveOrUpdate(tenant);
	}

	private String getTenantId(List<String> codes) {
		String code = tenantId.generate();
		if (codes.contains(code)) {
			return getTenantId(codes);
		}
		return code;
	}

	@Override
	public Tenant getDetail(Tenant tenant) {
		QueryWrapper<Tenant> queryWrapper = Condition.getQueryWrapper(tenant);
		applyTenantScope(queryWrapper);
		return getOne(queryWrapper);
	}

	@Override
	public IPage<Tenant> selectPage(Map<String, Object> tenant, Query query) {
		QueryWrapper<Tenant> queryWrapper = Condition.getQueryWrapper(tenant, Tenant.class);
		applyTenantScope(queryWrapper);
		return page(Condition.getPage(query), queryWrapper);
	}

	@Override
	public List<Tenant> selectList(Tenant tenant) {
		QueryWrapper<Tenant> queryWrapper = Condition.getQueryWrapper(tenant);
		applyTenantScope(queryWrapper);
		return list(queryWrapper);
	}

	/**
	 * 统一的租户范围过滤：超管放行，其他用户强制限定为当前会话租户。
	 */
	private void applyTenantScope(QueryWrapper<Tenant> queryWrapper) {
		if (!SecureUtil.isAdministrator()) {
			queryWrapper.lambda().eq(Tenant::getTenantId, SecureUtil.getTenantId());
		}
	}

}
