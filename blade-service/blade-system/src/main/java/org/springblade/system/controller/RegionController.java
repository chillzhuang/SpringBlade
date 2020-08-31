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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.node.INode;
import org.springblade.system.entity.Region;
import org.springblade.system.service.IRegionService;
import org.springblade.system.vo.RegionVO;
import org.springblade.system.wrapper.RegionWrapper;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 行政区划表 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/region")
@Api(value = "行政区划表", tags = "行政区划表接口")
public class RegionController extends BladeController {

	private final IRegionService regionService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入region")
	public R<RegionVO> detail(Region region) {
		Region detail = regionService.getOne(Condition.getQueryWrapper(region));
		return R.data(RegionWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 行政区划表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入region")
	public R<IPage<Region>> list(Region region, Query query) {
		IPage<Region> pages = regionService.page(Condition.getPage(query), Condition.getQueryWrapper(region));
		return R.data(pages);
	}

	/**
	 * 懒加载列表
	 */
	@GetMapping("/lazy-list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "区划编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "name", value = "区划名称", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "懒加载列表", notes = "传入menu")
	public R<List<INode>> lazyList(String parentCode, @ApiIgnore @RequestParam Map<String, Object> menu) {
		List<INode> list = regionService.lazyList(parentCode, menu);
		return R.data(RegionWrapper.build().listNodeLazyVO(list));
	}

	/**
	 * 懒加载列表
	 */
	@GetMapping("/lazy-tree")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "区划编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "name", value = "区划名称", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "懒加载列表", notes = "传入menu")
	public R<List<INode>> lazyTree(String parentCode, @ApiIgnore @RequestParam Map<String, Object> menu) {
		List<INode> list = regionService.lazyTree(parentCode, menu);
		return R.data(RegionWrapper.build().listNodeLazyVO(list));
	}

	/**
	 * 新增 行政区划表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "新增", notes = "传入region")
	public R save(@Valid @RequestBody Region region) {
		return R.status(regionService.save(region));
	}

	/**
	 * 修改 行政区划表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "修改", notes = "传入region")
	public R update(@Valid @RequestBody Region region) {
		return R.status(regionService.updateById(region));
	}

	/**
	 * 新增或修改 行政区划表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "新增或修改", notes = "传入region")
	public R submit(@Valid @RequestBody Region region) {
		return R.status(regionService.submit(region));
	}


	/**
	 * 删除 行政区划表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入主键")
	public R remove(@ApiParam(value = "主键", required = true) @RequestParam String id) {
		return R.status(regionService.removeRegion(id));
	}

	/**
	 * 行政区划下拉数据源
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "下拉数据源", notes = "传入tenant")
	public R<List<Region>> select(@RequestParam(required = false, defaultValue = "00") String code) {
		List<Region> list = regionService.list(Wrappers.<Region>query().lambda().eq(Region::getParentCode, code));
		return R.data(list);
	}


}
