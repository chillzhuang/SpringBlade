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
package org.springblade.demo.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.modules.system.service.IDictService;
import org.springblade.demo.entity.Blog;
import org.springblade.demo.vo.BlogVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author BladeX
 * @since 2019-02-13
 */
@AllArgsConstructor
public class BlogWrapper extends BaseEntityWrapper<Blog, BlogVO>  {

	private IDictService dictService;

	@Override
	public BlogVO entityVO(Blog blog) {
		BlogVO blogVO = BeanUtil.copy(blog, BlogVO.class);

		/*R<String> dict = dictService.getValue("blog" , blogVO.getCategory());
		if (dict.isSuccess()) {
			String categoryName = dict.getData();
			blogVO.setCategoryName(categoryName);
		}*/

		return blogVO;
	}

}
