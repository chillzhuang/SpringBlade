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
package org.springblade.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.system.dto.MenuDTO;
import org.springblade.system.entity.Menu;
import org.springblade.system.vo.MenuVO;

import java.util.List;
import java.util.Map;

/**
 * Mapper 接口
 *
 * @author Chill
 */
public interface MenuMapper extends BaseMapper<Menu> {

	/**
	 * 自定义分页
	 *
	 * @param page  分页参数
	 * @param menu  菜单查询条件
	 * @return 菜单分页数据
	 */
	List<MenuVO> selectMenuPage(IPage page, MenuVO menu);

	/**
	 * 懒加载菜单列表
	 *
	 * @param parentId 父级菜单ID
	 * @param param    查询参数
	 * @return 菜单列表
	 */
	List<MenuVO> lazyMenuList(Long parentId, Map<String, Object> param);

	/**
	 * 树形结构
	 *
	 * @return 菜单树形结构
	 */
	List<MenuVO> tree();

	/**
	 * 授权树形结构
	 *
	 * @return 菜单授权树形结构
	 */
	List<MenuVO> grantTree();

	/**
	 * 授权树形结构
	 *
	 * @param roleId 角色ID集合
	 * @return 菜单授权树形结构
	 */
	List<MenuVO> grantTreeByRole(List<Long> roleId);

	/**
	 * 数据权限授权树形结构
	 *
	 * @return 数据权限授权树形结构
	 */
	List<MenuVO> grantDataScopeTree();

	/**
	 * 数据权限授权树形结构
	 *
	 * @param roleId 角色ID集合
	 * @return 数据权限授权树形结构
	 */
	List<MenuVO> grantDataScopeTreeByRole(List<Long> roleId);

	/**
	 * 接口权限授权树形结构
	 *
	 * @return 接口权限授权树形结构
	 */
	List<MenuVO> grantApiScopeTree();

	/**
	 * 接口权限授权树形结构
	 *
	 * @param roleId 角色ID集合
	 * @return 接口权限授权树形结构
	 */
	List<MenuVO> grantApiScopeTreeByRole(List<Long> roleId);

	/**
	 * 顶部菜单树形结构
	 *
	 * @return 顶部菜单树形结构
	 */
	List<MenuVO> grantTopTree();

	/**
	 * 顶部菜单树形结构
	 *
	 * @param roleId 角色ID集合
	 * @return 顶部菜单树形结构
	 */
	List<MenuVO> grantTopTreeByRole(List<Long> roleId);

	/**
	 * 所有菜单
	 *
	 * @return 全部菜单集合
	 */
	List<Menu> allMenu();

	/**
	 * 权限配置菜单
	 *
	 * @param roleId 角色ID集合
	 * @return 角色配置的菜单集合
	 */
	List<Menu> roleMenu(List<Long> roleId);

	/**
	 * 菜单树形结构
	 *
	 * @param roleId 角色ID集合
	 * @return 角色路由菜单集合
	 */
	List<Menu> routes(List<Long> roleId);

	/**
	 * 按钮树形结构
	 *
	 * @param roleId 角色ID集合
	 * @return 角色按钮菜单集合
	 */
	List<Menu> buttons(List<Long> roleId);

	/**
	 * 获取配置的角色权限
	 *
	 * @param roleIds 角色ID集合
	 * @return 角色权限路由集合
	 */
	List<MenuDTO> authRoutes(List<Long> roleIds);

	/**
	 * 根据角色ID获取角色菜单
	 *
	 * @param roleId 角色ID集合
	 * @return 角色菜单集合
	 */
	List<Menu> roleMenuByRoleId(List<Long> roleId);

	/**
	 * 根据顶部菜单ID获取菜单
	 *
	 * @param topMenuId 顶部菜单ID
	 * @return 顶部菜单关联的菜单集合
	 */
	List<Menu> roleMenuByTopMenuId(Long topMenuId);
}
