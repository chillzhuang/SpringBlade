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
package org.springblade.system.wrapper;

import org.springblade.common.constant.CommonConstant;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.node.INode;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.system.entity.Dict;
import org.springblade.system.service.IDictService;
import org.springblade.system.vo.DictVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class DictWrapper extends BaseEntityWrapper<Dict, DictVO> {

	private static IDictService dictService;

	static {
		dictService = SpringUtil.getBean(IDictService.class);
	}

	public static DictWrapper build() {
		return new DictWrapper();
	}

	@Override
	public DictVO entityVO(Dict dict) {
		DictVO dictVO = BeanUtil.copy(dict, DictVO.class);
		if (Func.equals(dict.getParentId(), CommonConstant.TOP_PARENT_ID)) {
			dictVO.setParentName(CommonConstant.TOP_PARENT_NAME);
		} else {
			Dict parent = dictService.getById(dict.getParentId());
			dictVO.setParentName(parent.getDictValue());
		}
		return dictVO;
	}

	public List<INode> listNodeVO(List<Dict> list) {
		List<INode> collect = list.stream().map(dict -> BeanUtil.copy(dict, DictVO.class)).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
