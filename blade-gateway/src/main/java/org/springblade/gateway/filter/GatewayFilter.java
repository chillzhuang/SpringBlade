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
package org.springblade.gateway.filter;

import lombok.RequiredArgsConstructor;
import org.springblade.gateway.props.RequestProperties;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * 全局拦截器
 *
 * @author Chill
 */
@RequiredArgsConstructor
public class GatewayFilter implements WebFilter, Ordered {

	/**
	 * 请求配置
	 */
	private final RequestProperties requestProperties;
	/**
	 * 路径匹配
	 */
	private final AntPathMatcher antPathMatcher = new AntPathMatcher();

	/**
	 * 默认拦截地址
	 */
	private final List<String> defaultBlockUrl = List.of("/**/actuator/**", "/health/**");
	/**
	 * 默认白名单
	 */
	private final List<String> defaultWhiteList = List.of("127.0.0.1", "172.30.*.*", "192.168.*.*", "10.*.*.*", "0:0:0:0:0:0:0:1");
	/**
	 * 默认提示信息
	 */
	private final static String DEFAULT_MESSAGE = "当前请求被拒绝，请联系管理员！";

	/**
	 * 这里为支持的请求头，如果有自定义的header字段请自己添加
	 */
	private static final String ALLOWED_HEADERS = "X-Requested-With, Tenant-Id, Blade-Auth, Content-Type, Authorization, credential, X-XSRF-TOKEN, token, username, client, knfie4j-gateway-request, knife4j-gateway-code, request-origion";
	private static final String ALLOWED_METHODS = "GET,POST,PUT,DELETE,OPTIONS,HEAD";
	private static final String ALLOWED_ORIGIN = "*";
	private static final String ALLOWED_EXPOSE = "*";
	private static final String MAX_AGE = "18000L";


	@NonNull
	@Override
	public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		// 处理跨域请求
		if (CorsUtils.isCorsRequest(request)) {
			ServerHttpResponse response = exchange.getResponse();
			HttpHeaders headers = response.getHeaders();
			headers.add("Access-Control-Allow-Headers", ALLOWED_HEADERS);
			headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
			headers.add("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
			headers.add("Access-Control-Expose-Headers", ALLOWED_EXPOSE);
			headers.add("Access-Control-Max-Age", MAX_AGE);
			headers.add("Access-Control-Allow-Credentials", "true");
			if (request.getMethod() == HttpMethod.OPTIONS) {
				response.setStatusCode(HttpStatus.OK);
				return Mono.empty();
			}
		}
		// 处理黑白名单与拦截请求
		if (requestProperties.getEnabled()) {
			String path = request.getPath().value();
			String ip = Objects.requireNonNull(request.getRemoteAddress()).getHostString();
			if (isRequestBlock(path, ip)) {
				throw new RuntimeException(DEFAULT_MESSAGE);
			}
		}
		return chain.filter(exchange);
	}


	/**
	 * 是否白名单
	 *
	 * @param ip ip地址
	 * @return boolean
	 */
	private boolean isWhiteList(String ip) {
		List<String> whiteList = requestProperties.getWhiteList();
		String[] defaultWhiteIps = defaultWhiteList.toArray(new String[0]);
		String[] whiteIps = whiteList.toArray(new String[0]);
		return PatternMatchUtils.simpleMatch(defaultWhiteIps, ip) || PatternMatchUtils.simpleMatch(whiteIps, ip);
	}

	/**
	 * 是否黑名单
	 *
	 * @param ip ip地址
	 * @return boolean
	 */
	private boolean isBlackList(String ip) {
		List<String> blackList = requestProperties.getBlackList();
		String[] blackIps = blackList.toArray(new String[0]);
		return PatternMatchUtils.simpleMatch(blackIps, ip);
	}

	/**
	 * 是否禁用请求访问
	 *
	 * @param path 请求路径
	 * @return boolean
	 */
	private boolean isRequestBlock(String path) {
		List<String> blockUrl = requestProperties.getBlockUrl();
		return defaultBlockUrl.stream().anyMatch(pattern -> antPathMatcher.match(pattern, path)) ||
			blockUrl.stream().anyMatch(pattern -> antPathMatcher.match(pattern, path));
	}

	/**
	 * 是否拦截请求
	 *
	 * @param path 请求路径
	 * @param ip   ip地址
	 * @return boolean
	 */
	private boolean isRequestBlock(String path, String ip) {
		return (isRequestBlock(path) && !isWhiteList(ip)) || isBlackList(ip);
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}
}
