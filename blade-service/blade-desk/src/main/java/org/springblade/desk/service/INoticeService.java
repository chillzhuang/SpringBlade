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
package org.springblade.desk.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.desk.entity.Notice;

/**
 * 服务类
 *
 * @author Chill
 * @since 2018-09-29
 */
public interface INoticeService extends BaseService<Notice> {

	/**
	 * 自定义分页
	 * @param page
	 * @param notice
	 * @return
	 */
	IPage<Notice> selectNoticePage(IPage<Notice> page, Notice notice);

}
