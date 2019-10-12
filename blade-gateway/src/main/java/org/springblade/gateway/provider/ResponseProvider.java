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
package org.springblade.gateway.provider;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求响应返回
 *
 * @author Chill
 */
public class ResponseProvider {

	/**
	 * 成功
	 *
	 * @param message 信息
	 * @return
	 */
	public static Map<String, Object> success(String message) {
		return response(200, message);
	}

	/**
	 * 失败
	 *
	 * @param message 信息
	 * @return
	 */
	public static Map<String, Object> fail(String message) {
		return response(400, message);
	}

	/**
	 * 未授权
	 *
	 * @param message 信息
	 * @return
	 */
	public static Map<String, Object> unAuth(String message) {
		return response(401, message);
	}

	/**
	 * 服务器异常
	 *
	 * @param message 信息
	 * @return
	 */
	public static Map<String, Object> error(String message) {
		return response(500, message);
	}

	/**
	 * 构建返回的JSON数据格式
	 *
	 * @param status  状态码
	 * @param message 信息
	 * @return
	 */
	public static Map<String, Object> response(int status, String message) {
		Map<String, Object> map = new HashMap<>(16);
		map.put("code", status);
		map.put("message", message);
		map.put("data", null);
		return map;
	}

}
