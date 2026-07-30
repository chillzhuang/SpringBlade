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
package org.springblade.gateway.provider;

import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.LinkedHashSet;

/**
 * RequestProvider
 *
 * @author Chill
 */
public class RequestProvider {

	/**
	 * 获取原始请求路径
	 * <p>
	 * 取裁剪服务名前缀之前的路径，避免路径判定依赖裁剪结果。返回未解码的 rawPath 且不含查询串，
	 * 解码与归一化交由判定方按自身语义处理，此处不做加工。
	 *
	 * @param exchange 当前请求上下文
	 * @return 原始请求路径（未解码，不含查询串）
	 */
	public static String getOriginalRequestPath(ServerWebExchange exchange) {
		ServerHttpRequest request = exchange.getRequest();
		LinkedHashSet<URI> uris = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
		URI requestUri = uris.stream().findFirst().orElse(request.getURI());
		return requestUri.getRawPath();
	}

}
