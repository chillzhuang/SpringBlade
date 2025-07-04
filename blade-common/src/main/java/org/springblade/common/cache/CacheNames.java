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
package org.springblade.common.cache;

/**
 * 缓存名
 *
 * @author Chill
 */
public interface CacheNames {

	String NOTICE_ONE = "notice:one";

	String DICT_VALUE = "dict:value";
	String DICT_LIST = "dict:list";

	/**
	 * 验证码key
	 */
	String CAPTCHA_KEY = "blade:auth::blade:captcha:";

	/**
	 * 登录失败key
	 */
	String USER_FAIL_KEY = "blade:user::blade:fail:";

	/**
	 * IP锁定key
	 */
	String IP_FAIL_KEY = "blade:ip::blade:fail:";

	/**
	 * 返回租户格式的key
	 *
	 * @param tenantId      租户编号
	 * @param cacheKey      缓存key
	 * @param cacheKeyValue 缓存key值
	 * @return tenantKey
	 */
	static String tenantKey(String tenantId, String cacheKey, String cacheKeyValue) {
		return tenantId.concat(":").concat(cacheKey).concat(cacheKeyValue);
	}

}
