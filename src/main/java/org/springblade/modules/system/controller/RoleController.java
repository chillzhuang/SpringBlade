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
package org.springblade.modules.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.entity.Role;
import org.springblade.modules.system.service.IRoleService;
import org.springblade.modules.system.vo.GrantVO;
import org.springblade.modules.system.vo.RoleVO;
import org.springblade.modules.system.wrapper.RoleWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 控制器
 *
 * @author Chill
 */
@Hidden
@RestController
@AllArgsConstructor
@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/role")
@Tag(name = "角色", description = "角色")
public class RoleController extends BladeController {

	private IRoleService roleService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入role")
	public R<RoleVO> detail(Role role) {
		Role detail = roleService.getOne(Condition.getQueryWrapper(role));
		return R.data(RoleWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "roleName", description = "参数名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "roleAlias", description = "角色别名", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 2)
	@Operation(summary = "列表", description = "传入role")
	public R<List<RoleVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> role) {
		return R.data(roleService.selectList(role));
	}

	/**
	 * 获取角色树形结构
	 * <p>
	 * 仅超级管理员可指定任意 tenantId 查询；其他用户强制使用自身 tenantId，
	 * 防止通过 tenantId 参数越权读取其他租户的角色树。
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "树形结构", description = "树形结构")
	public R<List<RoleVO>> tree(String tenantId) {
		return R.data(roleService.tree(tenantId));
	}

	/**
	 * 获取指定角色树形结构
	 * <p>
	 * 仅超级管理员可通过任意 roleId 跳转到对应租户的角色树（用于跨租户管理）；
	 * 其他用户忽略 roleId 参数，强制返回自身租户的角色树，
	 * 防止通过 roleId 越权读取其他租户的角色结构。
	 */
	@GetMapping("/tree-by-id")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "树形结构", description = "树形结构")
	public R<List<RoleVO>> treeById(Long roleId) {
		return R.data(roleService.treeById(roleId));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "新增或修改", description = "传入role")
	public R submit(@Valid @RequestBody Role role) {
		return R.status(roleService.submit(role));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(roleService.remove(Func.toLongList(ids)));
	}

	/**
	 * 设置角色权限
	 */
	@PostMapping("/grant")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "权限设置", description = "传入roleId集合以及menuId集合")
	public R grant(@RequestBody GrantVO grantVO) {
		boolean temp = roleService.grant(grantVO.getRoleIds(), grantVO.getMenuIds(), grantVO.getDataScopeIds(), grantVO.getApiScopeIds());
		return R.status(temp);
	}

}
