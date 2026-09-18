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
package org.springblade.gateway.utils;

import org.springblade.core.launch.utils.INetUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import java.net.InetSocketAddress;

/**
 * 网关请求工具
 *
 * @author Chill
 */
public class WebUtil {

	/**
	 * 获取客户端ip
	 * <p>
	 * 对端为公网地址时取对端本身；经反向代理进入时沿代理链识别真实客户端，
	 * 规则见 {@link INetUtil#resolveClientIp}。可信前提是网关端口只从内网或公网入口可达。
	 *
	 * @param request 请求
	 * @return 客户端地址，无连接对端时为 null
	 */
	public static String getIP(ServerHttpRequest request) {
		InetSocketAddress remoteAddress = request.getRemoteAddress();
		String peer = remoteAddress == null ? null : remoteAddress.getHostString();
		HttpHeaders headers = request.getHeaders();
		return INetUtil.resolveClientIp(peer, headers.getOrEmpty(INetUtil.X_REAL_IP), headers.getOrEmpty(INetUtil.X_FORWARDED_FOR));
	}

	/**
	 * 标定客户端ip
	 * <p>
	 * 网关是唯一的信任边界，把解析结果覆盖写入 X-Real-IP 供下游服务采信，客户端自带的值一律作废；
	 * 无法确定客户端地址时剥离该头而不是放行原值。请求对象不可变，返回携带新请求的上下文。
	 *
	 * @param exchange 当前请求上下文
	 * @param clientIp 客户端地址
	 * @return 标定后的请求上下文
	 */
	public static ServerWebExchange setIP(ServerWebExchange exchange, String clientIp) {
		ServerHttpRequest request = exchange.getRequest().mutate()
			.headers(headers -> {
				if (clientIp == null) {
					headers.remove(INetUtil.X_REAL_IP);
				} else {
					headers.set(INetUtil.X_REAL_IP, clientIp);
				}
			})
			.build();
		return exchange.mutate().request(request).build();
	}

}
