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
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.system.entity.Menu;
import org.springblade.system.feign.IDictClient;
import org.springblade.system.service.IMenuService;
import org.springblade.system.vo.MenuVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class MenuWrapper extends BaseEntityWrapper<Menu, MenuVO> {

	private static IMenuService menuService;

	private static IDictClient dictClient;

	static {
		menuService = SpringUtil.getBean(IMenuService.class);
		dictClient = SpringUtil.getBean(IDictClient.class);
	}

	public static MenuWrapper build() {
		return new MenuWrapper();
	}

	@Override
	public MenuVO entityVO(Menu menu) {
		MenuVO menuVO = BeanUtil.copy(menu, MenuVO.class);
		if (Func.equals(menu.getParentId(), CommonConstant.TOP_PARENT_ID)) {
			menuVO.setParentName(CommonConstant.TOP_PARENT_NAME);
		} else {
			Menu parent = menuService.getById(menu.getParentId());
			menuVO.setParentName(parent.getName());
		}
		R<String> d1 = dictClient.getValue("menu_category", Func.toInt(menuVO.getCategory()));
		R<String> d2 = dictClient.getValue("button_func", Func.toInt(menuVO.getAction()));
		R<String> d3 = dictClient.getValue("yes_no", Func.toInt(menuVO.getIsOpen()));
		if (d1.isSuccess()) {
			menuVO.setCategoryName(d1.getData());
		}
		if (d2.isSuccess()) {
			menuVO.setActionName(d2.getData());
		}
		if (d3.isSuccess()) {
			menuVO.setIsOpenName(d3.getData());
		}
		return menuVO;
	}


	public List<MenuVO> listNodeVO(List<Menu> list) {
		List<MenuVO> collect = list.stream().map(menu -> BeanUtil.copy(menu, MenuVO.class)).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
