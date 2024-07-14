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
package org.springblade.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.entity.Menu;
import org.springblade.system.service.IMenuService;
import org.springblade.system.vo.CheckedTreeVO;
import org.springblade.system.vo.GrantTreeVO;
import org.springblade.system.vo.MenuVO;
import org.springblade.system.wrapper.MenuWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/menu")
@Tag(name = "菜单", description = "菜单")
public class MenuController extends BladeController {

	private IMenuService menuService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入menu")
	public R<MenuVO> detail(Menu menu) {
		Menu detail = menuService.getOne(Condition.getQueryWrapper(menu));
		return R.data(MenuWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "code", description = "菜单编号", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "name", description = "菜单名称", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	@ApiOperationSupport(order = 2)
	@Operation(summary = "列表", description = "传入menu")
	public R<List<MenuVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> menu) {
		List<Menu> list = menuService.list(Condition.getQueryWrapper(menu, Menu.class).lambda().orderByAsc(Menu::getSort));
		return R.data(MenuWrapper.build().listNodeVO(list));
	}

	/**
	 * 菜单列表
	 */
	@GetMapping("/menu-list")
	@Parameters({
		@Parameter(name = "code", description = "菜单编号", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "name", description = "菜单名称", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	@ApiOperationSupport(order = 3)
	@Operation(summary = "菜单列表", description = "传入menu")
	public R<List<MenuVO>> menuList(@Parameter(hidden = true) @RequestParam Map<String, Object> menu) {
		List<Menu> list = menuService.list(Condition.getQueryWrapper(menu, Menu.class).lambda().eq(Menu::getCategory, 1).orderByAsc(Menu::getSort));
		return R.data(MenuWrapper.build().listNodeVO(list));
	}

	/**
	 * 懒加载菜单列表
	 */
	@GetMapping("/lazy-menu-list")
	@Parameters({
		@Parameter(name = "code", description = "菜单编号", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "name", description = "菜单名称", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	@ApiOperationSupport(order = 4)
	@Operation(summary = "懒加载菜单列表", description = "传入menu")
	public R<List<MenuVO>> lazyMenuList(Long parentId, @Parameter(hidden = true) @RequestParam Map<String, Object> menu) {
		List<MenuVO> list = menuService.lazyMenuList(parentId, menu);
		return R.data(MenuWrapper.build().listNodeLazyVO(list));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	@ApiOperationSupport(order = 5)
	@Operation(summary = "新增或修改", description = "传入menu")
	public R submit(@Valid @RequestBody Menu menu) {
		return R.status(menuService.saveOrUpdate(menu));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	@ApiOperationSupport(order = 6)
	@Operation(summary = "删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(menuService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 前端菜单数据
	 */
	@GetMapping("/routes")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "前端菜单数据", description = "前端菜单数据")
	public R<List<MenuVO>> routes(BladeUser user) {
		List<MenuVO> list = menuService.routes((user == null || user.getUserId() == 0L) ? null : user.getRoleId());
		return R.data(list);
	}

	/**
	 * 前端按钮数据
	 */
	@GetMapping("/buttons")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "前端按钮数据", description = "前端按钮数据")
	public R<List<MenuVO>> buttons(BladeUser user) {
		List<MenuVO> list = menuService.buttons(user.getRoleId());
		return R.data(list);
	}

	/**
	 * 获取菜单树形结构
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "树形结构", description = "树形结构")
	public R<List<MenuVO>> tree() {
		List<MenuVO> tree = menuService.tree();
		return R.data(tree);
	}

	/**
	 * 获取权限分配树形结构
	 */
	@GetMapping("/grant-tree")
	@ApiOperationSupport(order = 10)
	@Operation(summary = "权限分配树形结构", description = "权限分配树形结构")
	public R<GrantTreeVO> grantTree(BladeUser user) {
		GrantTreeVO vo = new GrantTreeVO();
		vo.setMenu(menuService.grantTree(user));
		vo.setDataScope(menuService.grantDataScopeTree(user));
		return R.data(vo);
	}

	/**
	 * 获取权限分配树形结构
	 */
	@GetMapping("/role-tree-keys")
	@ApiOperationSupport(order = 11)
	@Operation(summary = "角色所分配的树", description = "角色所分配的树")
	public R<CheckedTreeVO> roleTreeKeys(String roleIds) {
		CheckedTreeVO vo = new CheckedTreeVO();
		vo.setMenu(menuService.roleTreeKeys(roleIds));
		vo.setDataScope(menuService.dataScopeTreeKeys(roleIds));
		return R.data(vo);
	}

	/**
	 * 获取配置的角色权限
	 */
	@GetMapping("auth-routes")
	@ApiOperationSupport(order = 12)
	@Operation(summary = "菜单的角色权限")
	public R<List<Kv>> authRoutes(BladeUser user) {
		if (Func.isEmpty(user) || user.getUserId() == 0L) {
			return null;
		}
		return R.data(menuService.authRoutes(user));
	}

}
