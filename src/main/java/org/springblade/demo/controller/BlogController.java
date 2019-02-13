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
package org.springblade.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.modules.system.service.IDictService;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.demo.entity.Blog;
import org.springblade.demo.vo.BlogVO;
import org.springblade.demo.wrapper.BlogWrapper;
import org.springblade.demo.service.IBlogService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 *  控制器
 *
 * @author BladeX
 * @since 2019-02-13
 */
@RestController
@AllArgsConstructor
@RequestMapping("/blog")
@Api(value = "", tags = "接口")
public class BlogController extends BladeController {

	private IBlogService blogService;

	private IDictService dictService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入blog", position = 1)
	public R<BlogVO> detail(Blog blog) {
		Blog detail = blogService.getOne(Condition.getQueryWrapper(blog));
		BlogWrapper blogWrapper = new BlogWrapper(dictService);
		return R.data(blogWrapper.entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入blog", position = 2)
	public R<IPage<BlogVO>> list(Blog blog, Query query) {
		IPage<Blog> pages = blogService.page(Condition.getPage(query), Condition.getQueryWrapper(blog));
		BlogWrapper blogWrapper = new BlogWrapper(dictService);
		return R.data(blogWrapper.pageVO(pages));
	}

	/**
	* 自定义分页 
	*/
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入blog", position = 3)
	public R<IPage<BlogVO>> page(BlogVO blog, Query query) {
		IPage<BlogVO> pages = blogService.selectBlogPage(Condition.getPage(query), blog);
		return R.data(pages);
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入blog", position = 4)
	public R save(@Valid @RequestBody Blog blog) {
		return R.status(blogService.save(blog));
	}

	/**
	* 修改 
	*/
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入blog", position = 5)
	public R update(@Valid @RequestBody Blog blog) {
		return R.status(blogService.updateById(blog));
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入blog", position = 6)
	public R submit(@Valid @RequestBody Blog blog) {
		return R.status(blogService.saveOrUpdate(blog));
	}

	
	/**
	* 删除 
	*/
	@PostMapping("/remove")
	@ApiOperation(value = "物理删除", notes = "传入ids", position = 7)
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(blogService.removeByIds(Func.toIntList(ids)));
	}

	
}
