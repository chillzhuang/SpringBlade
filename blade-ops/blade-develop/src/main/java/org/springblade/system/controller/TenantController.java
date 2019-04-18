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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.system.feign.IDictClient;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.system.entity.Tenant;
import org.springblade.system.vo.TenantVO;
import org.springblade.system.wrapper.TenantWrapper;
import org.springblade.system.service.ITenantService;
import org.springblade.core.boot.ctrl.BladeController;
import java.util.List;

/**
 *  控制器
 *
 * @author Blade
 * @since 2019-04-17
 */
@RestController
@AllArgsConstructor
@RequestMapping("/tenant")
@Api(value = "", tags = "接口")
public class TenantController extends BladeController {

	private ITenantService tenantService;

	private IDictClient dictClient;

	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入tenant", position = 1)
	public R<TenantVO> detail(Tenant tenant) {
		Tenant detail = tenantService.getOne(Condition.getQueryWrapper(tenant));
		TenantWrapper tenantWrapper = new TenantWrapper(dictClient);
		return R.data(tenantWrapper.entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入tenant", position = 2)
	public R<IPage<TenantVO>> list(Tenant tenant, Query query) {
		IPage<Tenant> pages = tenantService.page(Condition.getPage(query), Condition.getQueryWrapper(tenant));
		TenantWrapper tenantWrapper = new TenantWrapper(dictClient);
		return R.data(tenantWrapper.pageVO(pages));
	}

	/**
	* 自定义分页 
	*/
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入tenant", position = 3)
	public R<IPage<TenantVO>> page(TenantVO tenant, Query query) {
		IPage<TenantVO> pages = tenantService.selectTenantPage(Condition.getPage(query), tenant);
		return R.data(pages);
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入tenant", position = 4)
	public R save(@Valid @RequestBody Tenant tenant) {
		return R.status(tenantService.save(tenant));
	}

	/**
	* 修改 
	*/
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入tenant", position = 5)
	public R update(@Valid @RequestBody Tenant tenant) {
		return R.status(tenantService.updateById(tenant));
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入tenant", position = 6)
	public R submit(@Valid @RequestBody Tenant tenant) {
		return R.status(tenantService.saveOrUpdate(tenant));
	}

	
	/**
	* 删除 
	*/
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids", position = 7)
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(tenantService.deleteLogic(Func.toIntList(ids)));
	}

	
}
