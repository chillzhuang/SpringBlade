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
package org.springblade.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.core.mp.support.Query;
import org.springblade.system.entity.Tenant;

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
	 * @param page   分页参数
	 * @param tenant 租户查询条件
	 * @return 租户分页数据
	 */
	IPage<Tenant> selectTenantPage(IPage<Tenant> page, Tenant tenant);

	/**
	 * 根据租户编号获取实体
	 *
	 * @param tenantId 租户编号
	 * @return 租户实体
	 */
	Tenant getByTenantId(String tenantId);

	/**
	 * 新增
	 *
	 * @param tenant 租户实体
	 * @return 是否成功
	 */
	boolean saveTenant(Tenant tenant);

	/**
	 * 删除租户并清理缓存
	 *
	 * @param ids 主键集合
	 * @return 是否成功
	 */
	boolean removeTenant(List<Long> ids);

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
