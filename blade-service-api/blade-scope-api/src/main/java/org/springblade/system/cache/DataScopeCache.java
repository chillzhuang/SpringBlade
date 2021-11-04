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
package org.springblade.system.cache;

import org.springblade.core.datascope.model.DataScopeModel;
import org.springblade.core.tool.utils.*;
import org.springblade.system.feign.IDataScopeClient;

import java.util.List;

import static org.springblade.core.tool.utils.CacheUtil.SYS_CACHE;


/**
 * 数据权限缓存
 *
 * @author Chill
 */
public class DataScopeCache {

	private static final String SCOPE_CACHE_CODE = "dataScope:code:";
	private static final String SCOPE_CACHE_CLASS = "dataScope:class:";
	private static final String DEPT_CACHE_ANCESTORS = "dept:ancestors:";

	private static IDataScopeClient dataScopeClient;

	private static IDataScopeClient getDataScopeClient() {
		if (dataScopeClient == null) {
			dataScopeClient = SpringUtil.getBean(IDataScopeClient.class);
		}
		return dataScopeClient;
	}

	/**
	 * 获取数据权限
	 *
	 * @param mapperId 数据权限mapperId
	 * @param roleId   用户角色集合
	 * @return DataScopeModel
	 */
	public static DataScopeModel getDataScopeByMapper(String mapperId, String roleId) {
		DataScopeModel dataScope = CacheUtil.get(SYS_CACHE, SCOPE_CACHE_CLASS, mapperId + StringPool.COLON + roleId, DataScopeModel.class);
		if (dataScope == null || !dataScope.getSearched()) {
			dataScope = getDataScopeClient().getDataScopeByMapper(mapperId, roleId);
			CacheUtil.put(SYS_CACHE, SCOPE_CACHE_CLASS, mapperId + StringPool.COLON + roleId, dataScope);
		}
		return StringUtil.isNotBlank(dataScope.getResourceCode()) ? dataScope : null;
	}

	/**
	 * 获取数据权限
	 *
	 * @param code 数据权限资源编号
	 * @return DataScopeModel
	 */
	public static DataScopeModel getDataScopeByCode(String code) {
		DataScopeModel dataScope = CacheUtil.get(SYS_CACHE, SCOPE_CACHE_CODE, code, DataScopeModel.class);
		if (dataScope == null || !dataScope.getSearched()) {
			dataScope = getDataScopeClient().getDataScopeByCode(code);
			CacheUtil.put(SYS_CACHE, SCOPE_CACHE_CODE, code, dataScope);
		}
		return StringUtil.isNotBlank(dataScope.getResourceCode()) ? dataScope : null;
	}

	/**
	 * 获取部门子级
	 *
	 * @param deptId 部门id
	 * @return deptIds
	 */
	public static List<Long> getDeptAncestors(Long deptId) {
		List ancestors = CacheUtil.get(SYS_CACHE, DEPT_CACHE_ANCESTORS, deptId, List.class);
		if (CollectionUtil.isEmpty(ancestors)) {
			ancestors = getDataScopeClient().getDeptAncestors(deptId);
			CacheUtil.put(SYS_CACHE, DEPT_CACHE_ANCESTORS, deptId, ancestors);
		}
		return ancestors;
	}
}
