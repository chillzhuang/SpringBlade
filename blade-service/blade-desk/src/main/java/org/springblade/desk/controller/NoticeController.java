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
package org.springblade.desk.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springblade.common.cache.CacheNames;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.desk.entity.Notice;
import org.springblade.desk.service.INoticeService;
import org.springblade.desk.vo.NoticeVO;
import org.springblade.desk.wrapper.NoticeWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 控制器
 *
 * @author Chill
 * @since 2018-09-29
 */
@RestController
@RequestMapping("notice")
@AllArgsConstructor
@Tag(name = "用户博客", description = "博客接口")
public class NoticeController extends BladeController implements CacheNames {

	private INoticeService noticeService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入notice")
	public R<NoticeVO> detail(Notice notice) {
		Notice detail = noticeService.getOne(Condition.getQueryWrapper(notice));
		return R.data(NoticeWrapper.build().entityVO(detail));
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "category", description = "公告类型", in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
		@Parameter(name = "title", description = "公告标题", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入notice")
	public R<IPage<NoticeVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> notice, Query query) {
		IPage<Notice> pages = noticeService.page(Condition.getPage(query), Condition.getQueryWrapper(notice, Notice.class));
		return R.data(NoticeWrapper.build().pageVO(pages));
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "新增", description = "传入notice")
	public R save(@RequestBody Notice notice) {
		return R.status(noticeService.save(notice));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "修改", description = "传入notice")
	public R update(@RequestBody Notice notice) {
		return R.status(noticeService.updateById(notice));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "新增或修改", description = "传入notice")
	public R submit(@RequestBody Notice notice) {
		return R.status(noticeService.saveOrUpdate(notice));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "逻辑删除", description = "传入notice")
	public R remove(@Parameter(description = "主键集合") @RequestParam String ids) {
		boolean temp = noticeService.deleteLogic(Func.toLongList(ids));
		return R.status(temp);
	}

}
