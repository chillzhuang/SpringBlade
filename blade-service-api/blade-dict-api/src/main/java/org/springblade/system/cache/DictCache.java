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
package org.springblade.system.cache;

import org.springblade.core.cache.constant.CacheConstant;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.system.entity.Dict;
import org.springblade.system.feign.IDictClient;

import java.util.List;

/**
 * 字典缓存工具类
 * <p>
 * 通过 {@link SpringUtil} 在运行时懒加载 Feign 客户端，供各服务复用字典数据缓存。
 *
 * @author Chill
 */
public class DictCache {

	private static final String DICT_VALUE = "dict:value:";
	private static final String DICT_LIST = "dict:list:";

	private static IDictClient dictClient;

	private static IDictClient getDictClient() {
		if (dictClient == null) {
			dictClient = SpringUtil.getBean(IDictClient.class);
		}
		return dictClient;
	}

	/**
	 * 获取字典值
	 *
	 * @param code    字典编号
	 * @param dictKey 字典键
	 * @return 字典值
	 */
	public static String getValue(String code, Integer dictKey) {
		return CacheUtil.get(CacheConstant.DICT_CACHE, DICT_VALUE + code + StringPool.COLON, String.valueOf(dictKey), () -> getDictClient().getValue(code, dictKey).getData());
	}

	/**
	 * 获取字典集合
	 *
	 * @param code 字典编号
	 * @return 字典集合
	 */
	public static List<Dict> getList(String code) {
		return CacheUtil.get(CacheConstant.DICT_CACHE, DICT_LIST, code, () -> getDictClient().getList(code).getData());
	}

}
