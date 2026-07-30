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
package org.springblade.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.spring.service.IService;
import org.springblade.system.entity.Role;
import org.springblade.system.vo.RoleVO;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * 服务类
 *
 * @author Chill
 */
public interface IRoleService extends IService<Role> {

	/**
	 * 自定义分页
	 *
	 * @param page 分页参数
	 * @param role 角色查询条件
	 * @return 角色分页数据
	 */
	IPage<RoleVO> selectRolePage(IPage<RoleVO> page, RoleVO role);

	/**
	 * 树形结构（含超管判定 + 当前租户隔离）
	 * <p>
	 * 仅超级管理员可指定任意 tenantId 查询；其他用户传入的 tenantId 一律被忽略，
	 * 强制使用当前会话租户。
	 *
	 * @param tenantId 入参 tenantId（仅超管生效）
	 * @return 角色树
	 */
	List<RoleVO> tree(String tenantId);

	/**
	 * 通过 roleId 获取对应租户的角色树（含超管路由判定）
	 * <p>
	 * 仅超级管理员可通过任意 roleId 跳转到对应租户的角色树（用于跨租户管理）；
	 * 其他用户忽略 roleId 参数，强制返回自身租户的角色树。
	 *
	 * @param roleId 角色 ID（仅超管生效，用于反推 tenantId）
	 * @return 角色树
	 */
	List<RoleVO> treeById(Long roleId);

	/**
	 * 列表（含超管判定 + 当前租户隔离）
	 *
	 * @param role 查询条件 Map
	 * @return 列表 VO，非超管时仅命中当前会话租户
	 */
	List<RoleVO> selectList(Map<String, Object> role);

	/**
	 * 权限配置
	 *
	 * @param roleIds      角色id集合
	 * @param menuIds      菜单id集合
	 * @param dataScopeIds 数据权限id集合
	 * @param apiScopeIds  接口权限id集合
	 * @return 是否成功
	 */
	boolean grant(@NotEmpty List<Long> roleIds, List<Long> menuIds, List<Long> dataScopeIds, List<Long> apiScopeIds);

	/**
	 * 新增或修改角色（带租户归属校验）
	 *
	 * @param role 角色实体
	 * @return 是否成功
	 */
	boolean submit(Role role);

	/**
	 * 删除角色（带租户归属校验）
	 *
	 * @param ids 角色主键集合
	 * @return 是否成功
	 */
	boolean remove(List<Long> ids);

	/**
	 * 获取角色ID
	 *
	 * @param tenantId  租户ID
	 * @param roleNames 角色名称集合
	 * @return 角色主键字符串
	 */
	String getRoleIds(String tenantId, String roleNames);

	/**
	 * 获取角色名
	 *
	 * @param roleIds 角色主键集合
	 * @return 角色名称集合
	 */
	List<String> getRoleNames(String roleIds);

}
