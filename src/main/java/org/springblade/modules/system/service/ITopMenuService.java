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
package org.springblade.modules.system.service;

import jakarta.validation.constraints.NotEmpty;
import org.springblade.core.mp.base.BaseService;
import org.springblade.modules.system.entity.TopMenu;

import java.util.List;

/**
 * 顶部菜单表 服务类
 *
 * @author BladeX
 */
public interface ITopMenuService extends BaseService<TopMenu> {

	/**
	 * 顶部菜单配置
	 *
	 * @param topMenuIds 顶部菜单id集合
	 * @param menuIds    菜单id集合
	 * @return 是否成功
	 */
	boolean grant(@NotEmpty List<Long> topMenuIds, @NotEmpty List<Long> menuIds);

}
