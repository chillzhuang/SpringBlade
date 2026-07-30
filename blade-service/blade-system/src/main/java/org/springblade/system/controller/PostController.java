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

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.swagger.annotation.ApiOrder;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.entity.Post;
import org.springblade.system.service.IPostService;
import org.springblade.system.vo.PostVO;
import org.springblade.system.wrapper.PostWrapper;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 岗位表 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/post")
@ApiOrder
@Tag(name = "岗位表", description = "岗位表接口")
public class PostController extends BladeController {

	private IPostService postService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@Operation(summary = "详情", description = "传入post")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<PostVO> detail(Post post) {
		Post detail = postService.getOne(Condition.getQueryWrapper(post));
		return R.data(PostWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 岗位表
	 */
	@GetMapping("/list")
	@Operation(summary = "分页", description = "传入post")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<IPage<PostVO>> list(Post post, Query query) {
		IPage<Post> pages = postService.page(Condition.getPage(query), Condition.getQueryWrapper(post));
		return R.data(PostWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 岗位表
	 */
	@GetMapping("/page")
	@Operation(summary = "分页", description = "传入post")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<IPage<PostVO>> page(PostVO post, Query query) {
		IPage<PostVO> pages = postService.selectPostPage(Condition.getPage(query), post);
		return R.data(pages);
	}

	/**
	 * 新增 岗位表
	 */
	@PostMapping("/save")
	@Operation(summary = "新增", description = "传入post")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R save(@Valid @RequestBody Post post) {
		return R.status(postService.submit(post));
	}

	/**
	 * 修改 岗位表
	 */
	@PostMapping("/update")
	@Operation(summary = "修改", description = "传入post")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R update(@Valid @RequestBody Post post) {
		return R.status(postService.submit(post));
	}

	/**
	 * 新增或修改 岗位表
	 */
	@PostMapping("/submit")
	@Operation(summary = "新增或修改", description = "传入post")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R submit(@Valid @RequestBody Post post) {
		return R.status(postService.submit(post));
	}


	/**
	 * 删除 岗位表
	 */
	@PostMapping("/remove")
	@Operation(summary = "逻辑删除", description = "传入ids")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(postService.remove(Func.toLongList(ids)));
	}

	/**
	 * 下拉数据源
	 */
	@GetMapping("/select")
	@Operation(summary = "下拉数据源", description = "传入post")
	public R<List<Post>> select(String tenantId) {
		return R.data(postService.selectByTenant(tenantId));
	}

}
