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
package org.springblade.core.log.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springblade.core.log.model.LogApi;
import org.springblade.core.log.pojo.LogApiVO;
import org.springblade.core.log.service.ILogApiService;
import org.springblade.core.log.wrapper.LogApiWrapper;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.utils.StringPool;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 控制器
 *
 * @author Chill
 * @since 2018-09-26
 */
@Hidden
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class LogApiController {

	private ILogApiService logService;

	/**
	 * 查询单条
	 */
	@GetMapping("/detail")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<LogApi> detail(LogApi log) {
		LogApi logApi = logService.getOne(Condition.getQueryWrapper(log));
		return R.data(LogApiWrapper.build().entity(logApi));
	}

	/**
	 * 查询多条(分页)
	 */
	@GetMapping("/list")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<IPage<LogApiVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> log, Query query) {
		query.setAscs("create_time");
		query.setDescs(StringPool.EMPTY);
		IPage<LogApi> pages = logService.page(Condition.getPage(query), Condition.getQueryWrapper(log, LogApi.class));
		return R.data(LogApiWrapper.build().pageVO(pages));
	}

}
