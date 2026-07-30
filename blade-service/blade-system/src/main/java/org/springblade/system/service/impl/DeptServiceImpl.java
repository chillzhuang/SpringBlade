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
package org.springblade.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tenant.TenantGuard;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.system.entity.Dept;
import org.springblade.system.mapper.DeptMapper;
import org.springblade.system.service.IDeptService;
import org.springblade.system.vo.DeptVO;
import org.springblade.system.wrapper.DeptWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springblade.core.tenant.TenantGuard.EntityType.DEPT;
import static org.springblade.core.tenant.TenantGuard.EntityType.DEPT_PARENT;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

	@Override
	public IPage<DeptVO> selectDeptPage(IPage<DeptVO> page, DeptVO dept) {
		return page.setRecords(baseMapper.selectDeptPage(page, dept));
	}

	@Override
	public List<DeptVO> tree(String tenantId) {
		String resolvedTenantId = SecureUtil.isAdministrator()
			? Func.toStr(tenantId, SecureUtil.getTenantId())
			: SecureUtil.getTenantId();
		return ForestNodeMerger.merge(baseMapper.tree(resolvedTenantId));
	}

	@Override
	public List<DeptVO> selectList(Map<String, Object> dept) {
		QueryWrapper<Dept> queryWrapper = Condition.getQueryWrapper(dept, Dept.class);
		if (!SecureUtil.isAdministrator()) {
			queryWrapper.lambda().eq(Dept::getTenantId, SecureUtil.getTenantId());
		}
		return DeptWrapper.build().listNodeVO(list(queryWrapper));
	}

	@Override
	public String getDeptIds(String tenantId, String deptNames) {
		List<Dept> deptList = baseMapper.selectList(Wrappers.<Dept>query().lambda().eq(Dept::getTenantId, tenantId).in(Dept::getDeptName, Func.toStrList(deptNames)));
		if (deptList != null && !deptList.isEmpty()) {
			return deptList.stream().map(dept -> Func.toStr(dept.getId())).distinct().collect(Collectors.joining(","));
		}
		return null;
	}

	@Override
	public List<String> getDeptNames(String deptIds) {
		return baseMapper.getDeptNames(Func.toLongArray(deptIds));
	}

	/**
	 * 新增或修改部门（带租户归属校验）
	 * <p>
	 * 顶级部门通过 {@link TenantGuard#bindTenant} 绑定当前会话 tenantId；
	 * 子部门强制继承父节点 tenantId，且<strong>显式禁止跨租户迁移</strong>——
	 * 源部门与目标父部门必须同租户，否则抛业务异常。
	 * <p>
	 * 跨租户迁移会让子部门、子部门下的用户/角色/数据权限范围全部失配，
	 * 应通过专门的"租户迁移"工单流程而非常规修改入口完成。
	 */
	@Override
	public boolean submit(Dept dept) {
		CacheUtil.clear(CacheUtil.SYS_CACHE);
		if (Func.isEmpty(dept.getParentId())) {
			// 顶级部门：bindTenant 统一处理新增 / 修改路径的租户绑定
			TenantGuard.bindTenant(this, dept, DEPT);
			dept.setParentId(BladeConstant.TOP_PARENT_ID);
			dept.setAncestors(String.valueOf(BladeConstant.TOP_PARENT_ID));
		} else {
			// 子部门：先校验自身归属（修改路径），再继承父节点 tenantId
			if (Func.toLong(dept.getParentId()) == Func.toLong(dept.getId())) {
				throw new ServiceException("父节点不可选择自身!");
			}
			Dept self = Func.isNotEmpty(dept.getId())
				? TenantGuard.verify(this, dept.getId(), DEPT) : null;
			Dept parent = TenantGuard.verify(this, dept.getParentId(), DEPT_PARENT);
			if (parent == null) {
				throw new ServiceException("上级部门不存在!");
			}
			// 显式禁止跨租户迁移：保护现有子树的数据一致性
			if (self != null && Func.isNotEmpty(self.getTenantId())
				&& !self.getTenantId().equals(parent.getTenantId())) {
				throw new ServiceException("不允许跨租户迁移部门，请联系运维处理");
			}
			dept.setTenantId(parent.getTenantId());
			dept.setAncestors(parent.getAncestors() + StringPool.COMMA + dept.getParentId());
		}
		dept.setIsDeleted(BladeConstant.DB_NOT_DELETED);
		return saveOrUpdate(dept);
	}

	@Override
	public boolean remove(List<Long> ids) {
		CacheUtil.clear(CacheUtil.SYS_CACHE);
		TenantGuard.verifyBatch(this, ids, DEPT);
		return removeByIds(ids);
	}

}
