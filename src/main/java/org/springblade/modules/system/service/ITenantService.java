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

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.core.mp.support.Query;
import org.springblade.modules.system.entity.Tenant;

import java.util.List;
import java.util.Map;

/**
 * 服务类
 *
 * @author Chill
 */
public interface ITenantService extends BaseService<Tenant> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param tenant
	 * @return
	 */
	IPage<Tenant> selectTenantPage(IPage<Tenant> page, Tenant tenant);

	/**
	 * 新增
	 *
	 * @param tenant
	 * @return
	 */
	boolean saveTenant(Tenant tenant);

	/**
	 * 详情查询（含超管判定 + 当前租户隔离）
	 *
	 * @param tenant 查询条件
	 * @return 单条记录，非超管时仅命中当前会话租户
	 */
	Tenant getDetail(Tenant tenant);

	/**
	 * 分页列表（含超管判定 + 当前租户隔离）
	 *
	 * @param tenant 查询条件 Map
	 * @param query  分页参数
	 * @return 分页结果，非超管时仅命中当前会话租户
	 */
	IPage<Tenant> selectPage(Map<String, Object> tenant, Query query);

	/**
	 * 下拉数据源（含超管判定 + 当前租户隔离）
	 *
	 * @param tenant 查询条件
	 * @return 列表，非超管时仅命中当前会话租户
	 */
	List<Tenant> selectList(Tenant tenant);

}
