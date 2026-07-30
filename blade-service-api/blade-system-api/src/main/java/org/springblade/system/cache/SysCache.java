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
package org.springblade.system.cache;

import org.springblade.core.cache.constant.CacheConstant;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.system.entity.Dept;
import org.springblade.system.entity.Role;
import org.springblade.system.entity.Tenant;
import org.springblade.system.feign.ISysClient;

import java.util.List;

/**
 * 系统缓存工具类
 * <p>
 * 通过 {@link SpringUtil} 在运行时懒加载 Feign 客户端，供各服务复用系统级数据缓存。
 * 部门、岗位、角色、租户等数据统一缓存在 {@link CacheConstant#SYS_CACHE} 域中，
 * 对应数据变更时由各 Service 清理该缓存域。
 *
 * @author Chill
 */
public class SysCache {

	private static final String DEPT_ID = "dept:id:";
	private static final String DEPT_IDS = "dept:ids:";
	private static final String DEPT_NAMES = "dept:names:";
	private static final String POST_IDS = "post:ids:";
	private static final String POST_NAMES = "post:names:";
	private static final String ROLE_ID = "role:id:";
	private static final String ROLE_IDS = "role:ids:";
	private static final String ROLE_NAMES = "role:names:";
	private static final String TENANT_ID = "tenant:id:";
	private static final String TENANT_TENANT_ID = "tenant:tenantId:";

	private static ISysClient sysClient;

	private static ISysClient getSysClient() {
		if (sysClient == null) {
			sysClient = SpringUtil.getBean(ISysClient.class);
		}
		return sysClient;
	}

	/**
	 * 获取部门
	 *
	 * @param id 主键
	 * @return Dept
	 */
	public static Dept getDept(Long id) {
		return CacheUtil.get(CacheConstant.SYS_CACHE, DEPT_ID, id, () -> getSysClient().getDept(id));
	}

	/**
	 * 获取部门主键集合
	 *
	 * @param tenantId  租户id
	 * @param deptNames 部门名称集合
	 * @return 部门主键集合
	 */
	public static String getDeptIds(String tenantId, String deptNames) {
		return CacheUtil.get(CacheConstant.SYS_CACHE, DEPT_IDS, tenantId + StringPool.DASH + deptNames, () -> getSysClient().getDeptIds(tenantId, deptNames));
	}

	/**
	 * 获取部门名称集合
	 *
	 * @param deptIds 部门主键集合
	 * @return 部门名称集合
	 */
	public static List<String> getDeptNames(String deptIds) {
		return CacheUtil.get(CacheConstant.SYS_CACHE, DEPT_NAMES, deptIds, () -> getSysClient().getDeptNames(deptIds));
	}

	/**
	 * 获取岗位主键集合
	 *
	 * @param tenantId  租户id
	 * @param postNames 岗位名称集合
	 * @return 岗位主键集合
	 */
	public static String getPostIds(String tenantId, String postNames) {
		return CacheUtil.get(CacheConstant.SYS_CACHE, POST_IDS, tenantId + StringPool.DASH + postNames, () -> getSysClient().getPostIds(tenantId, postNames));
	}

	/**
	 * 获取岗位名称集合
	 *
	 * @param postIds 岗位主键集合
	 * @return 岗位名称集合
	 */
	public static List<String> getPostNames(String postIds) {
		return CacheUtil.get(CacheConstant.SYS_CACHE, POST_NAMES, postIds, () -> getSysClient().getPostNames(postIds));
	}

	/**
	 * 获取角色
	 *
	 * @param id 主键
	 * @return Role
	 */
	public static Role getRole(Long id) {
		return CacheUtil.get(CacheConstant.SYS_CACHE, ROLE_ID, id, () -> getSysClient().getRole(id));
	}

	/**
	 * 获取角色主键集合
	 *
	 * @param tenantId  租户id
	 * @param roleNames 角色名称集合
	 * @return 角色主键集合
	 */
	public static String getRoleIds(String tenantId, String roleNames) {
		return CacheUtil.get(CacheConstant.SYS_CACHE, ROLE_IDS, tenantId + StringPool.DASH + roleNames, () -> getSysClient().getRoleIds(tenantId, roleNames));
	}

	/**
	 * 获取角色名称集合
	 *
	 * @param roleIds 角色主键集合
	 * @return 角色名称集合
	 */
	public static List<String> getRoleNames(String roleIds) {
		return CacheUtil.get(CacheConstant.SYS_CACHE, ROLE_NAMES, roleIds, () -> getSysClient().getRoleNames(roleIds));
	}

	/**
	 * 获取租户
	 *
	 * @param id 主键
	 * @return Tenant
	 */
	public static Tenant getTenant(Long id) {
		return CacheUtil.get(CacheConstant.SYS_CACHE, TENANT_ID, id, () -> getSysClient().getTenant(id).getData());
	}

	/**
	 * 获取租户
	 *
	 * @param tenantId 租户编号
	 * @return Tenant
	 */
	public static Tenant getTenant(String tenantId) {
		return CacheUtil.get(CacheConstant.SYS_CACHE, TENANT_TENANT_ID, tenantId, () -> getSysClient().getTenant(tenantId).getData());
	}

}
