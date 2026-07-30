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
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.support.Kv;
import org.springblade.system.entity.Menu;
import org.springblade.system.vo.MenuVO;

import java.util.List;
import java.util.Map;

/**
 * 服务类
 *
 * @author Chill
 */
public interface IMenuService extends IService<Menu> {

	/**
	 * 自定义分页
	 *
	 * @param page 分页参数
	 * @param menu 菜单查询条件
	 * @return 菜单分页数据
	 */
	IPage<MenuVO> selectMenuPage(IPage<MenuVO> page, MenuVO menu);

	/**
	 * 懒加载菜单列表
	 *
	 * @param parentId 父级菜单主键
	 * @param param    查询参数
	 * @return 菜单列表
	 */
	List<MenuVO> lazyMenuList(Long parentId, Map<String, Object> param);

	/**
	 * 菜单树形结构
	 *
	 * @param roleId    角色id集合
	 * @param topMenuId 顶部菜单id
	 * @return 路由菜单树
	 */
	List<MenuVO> routes(String roleId, Long topMenuId);

	/**
	 * 按钮树形结构
	 *
	 * @param roleId 角色id集合
	 * @return 按钮树
	 */
	List<MenuVO> buttons(String roleId);

	/**
	 * 树形结构
	 *
	 * @return 菜单树
	 */
	List<MenuVO> tree();

	/**
	 * 授权树形结构
	 *
	 * @param user 当前用户
	 * @return 菜单授权树
	 */
	List<MenuVO> grantTree(BladeUser user);

	/**
	 * 数据权限授权树形结构
	 *
	 * @param user 当前用户
	 * @return 数据权限授权树
	 */
	List<MenuVO> grantDataScopeTree(BladeUser user);

	/**
	 * 接口权限授权树形结构
	 *
	 * @param user 当前用户
	 * @return 接口权限授权树
	 */
	List<MenuVO> grantApiScopeTree(BladeUser user);

	/**
	 * 默认选中节点
	 *
	 * @param roleIds 角色id集合
	 * @return 选中节点集合
	 */
	List<String> roleTreeKeys(String roleIds);

	/**
	 * 默认选中节点
	 *
	 * @param roleIds 角色id集合
	 * @return 选中节点集合
	 */
	List<String> dataScopeTreeKeys(String roleIds);

	/**
	 * 接口权限默认选中节点
	 *
	 * @param roleIds 角色id集合
	 * @return 选中节点集合
	 */
	List<String> apiScopeTreeKeys(String roleIds);

	/**
	 * 获取配置的角色权限
	 *
	 * @param user 当前用户
	 * @return 角色权限集合
	 */
	List<Kv> authRoutes(BladeUser user);

	/**
	 * 顶部菜单授权树形结构
	 *
	 * @param user 当前用户
	 * @return 顶部菜单授权树
	 */
	List<MenuVO> grantTopTree(BladeUser user);

	/**
	 * 顶部菜单默认选中节点
	 *
	 * @param topMenuIds 顶部菜单id集合
	 * @return 选中节点集合
	 */
	List<String> topTreeKeys(String topMenuIds);

}
