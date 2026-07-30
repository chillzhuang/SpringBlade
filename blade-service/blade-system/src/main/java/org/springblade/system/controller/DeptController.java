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
package org.springblade.system.controller;

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
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.swagger.annotation.ApiOrder;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.entity.Dept;
import org.springblade.system.service.IDeptService;
import org.springblade.system.vo.DeptVO;
import org.springblade.system.wrapper.DeptWrapper;
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
@RequestMapping("/dept")
@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
@ApiOrder
@Tag(name = "部门", description = "部门")
public class DeptController extends BladeController {

	private IDeptService deptService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@Operation(summary = "详情", description = "传入dept")
	public R<DeptVO> detail(Dept dept) {
		Dept detail = deptService.getOne(Condition.getQueryWrapper(dept));
		return R.data(DeptWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "deptName", description = "部门名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "fullName", description = "部门全称", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@Operation(summary = "列表", description = "传入dept")
	public R<List<DeptVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> dept) {
		return R.data(deptService.selectList(dept));
	}

	/**
	 * 获取部门树形结构
	 */
	@GetMapping("/tree")
	@Operation(summary = "树形结构", description = "树形结构")
	public R<List<DeptVO>> tree(String tenantId) {
		return R.data(deptService.tree(tenantId));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@Operation(summary = "新增或修改", description = "传入dept")
	public R submit(@Valid @RequestBody Dept dept) {
		return R.status(deptService.submit(dept));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@Operation(summary = "删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deptService.remove(Func.toLongList(ids)));
	}


}
