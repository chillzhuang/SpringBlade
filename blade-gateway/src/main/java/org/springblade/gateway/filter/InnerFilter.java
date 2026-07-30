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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springblade.gateway.provider.RequestProvider;
import org.springblade.gateway.provider.ResponseProvider;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 内部接口隔离，拒绝外部对 Feign 内部接口的访问。
 * <p>
 * Feign 接口统一以 {@code /feign/client} 为前缀，仅供服务间调用。服务间调用经注册中心直连、不经过网关，
 * 故抵达网关且命中该前缀的请求必来自外部，一律拒绝，与是否持有合法令牌无关。
 * <p>
 * 前缀由连续两段构成，判定依赖段间的相邻关系，故归一化须与容器映射前的处理及其顺序保持等价，
 * 否则相邻关系会被路径变形撑开而失配。归一化按容器顺序进行：切分 → 剥离矩阵参数 → 解码 → 再次切分 → 消解相对段。
 *
 * @author Chill
 */
@Slf4j
@Component
@AllArgsConstructor
public class InnerFilter implements GlobalFilter, Ordered {

	private static final String MSG_INNER_FORBIDDEN = "禁止访问内部接口";

	/**
	 * Feign 内部接口保留段序列，业务接口不得占用
	 */
	private static final String[] FEIGN_SEGMENTS = {"feign", "client"};

	/**
	 * 路径分隔符，反斜杠一并纳入以对齐容器归一化
	 */
	private static final String PATH_SEPARATORS = "/\\";

	/**
	 * 段内矩阵参数分隔符
	 */
	private static final char MATRIX_SEPARATOR = ';';

	/**
	 * 相对路径段
	 */
	private static final String CURRENT_SEGMENT = ".";

	private static final String PARENT_SEGMENT = "..";

	private final ObjectMapper objectMapper;

	@NonNull
	@Override
	public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull GatewayFilterChain chain) {
		// 取裁剪服务名前缀之前的原始路径，避免依赖裁剪结果
		String originalPath = RequestProvider.getOriginalRequestPath(exchange);
		if (isInnerRequest(originalPath)) {
			return forbid(exchange.getResponse());
		}
		return chain.filter(exchange);
	}

	/**
	 * 判断是否为内部接口请求
	 *
	 * @param rawPath 原始请求路径（未解码，不含查询串）
	 * @return 是否为内部接口
	 */
	private boolean isInnerRequest(String rawPath) {
		if (!StringUtils.hasText(rawPath)) {
			return false;
		}
		List<String> segments;
		try {
			segments = normalize(rawPath);
		} catch (IllegalArgumentException exception) {
			// 编码非法则无法推断容器的解码结果，从严拒绝
			log.warn("非法编码路径已拒绝");
			return true;
		}
		return containsFeignSegments(segments);
	}

	/**
	 * 归一化为路径段列表，处理顺序与容器映射前保持一致
	 *
	 * @param rawPath 原始请求路径
	 * @return 归一化后的路径段列表
	 */
	private List<String> normalize(String rawPath) {
		Deque<String> segments = new ArrayDeque<>();
		for (String token : StringUtils.tokenizeToStringArray(rawPath, PATH_SEPARATORS)) {
			// 按原始形态剥离矩阵参数后再解码，顺序与容器一致
			int matrixIndex = token.indexOf(MATRIX_SEPARATOR);
			String decoded = UriUtils.decode(matrixIndex < 0 ? token : token.substring(0, matrixIndex), StandardCharsets.UTF_8);
			// 解码可能引入分隔符，需再次切分
			for (String segment : StringUtils.tokenizeToStringArray(decoded, PATH_SEPARATORS)) {
				if (CURRENT_SEGMENT.equals(segment)) {
					continue;
				}
				if (PARENT_SEGMENT.equals(segment)) {
					segments.pollLast();
					continue;
				}
				segments.addLast(segment);
			}
		}
		return new ArrayList<>(segments);
	}

	/**
	 * 判断路径段列表中是否存在连续的 Feign 保留段
	 *
	 * @param segments 归一化后的路径段列表
	 * @return 是否存在保留段
	 */
	private boolean containsFeignSegments(List<String> segments) {
		for (int index = 0; index + FEIGN_SEGMENTS.length <= segments.size(); index++) {
			if (matchesAt(segments, index)) {
				return true;
			}
		}
		return false;
	}

	private boolean matchesAt(List<String> segments, int index) {
		for (int offset = 0; offset < FEIGN_SEGMENTS.length; offset++) {
			if (!FEIGN_SEGMENTS[offset].equalsIgnoreCase(segments.get(index + offset))) {
				return false;
			}
		}
		return true;
	}

	private Mono<Void> forbid(ServerHttpResponse resp) {
		resp.setStatusCode(HttpStatus.FORBIDDEN);
		resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
		String result = "";
		try {
			result = objectMapper.writeValueAsString(ResponseProvider.response(HttpStatus.FORBIDDEN.value(), MSG_INNER_FORBIDDEN));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
		}
		DataBuffer buffer = resp.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
		return resp.writeWith(Flux.just(buffer));
	}

	@Override
	public int getOrder() {
		// 晚于 RequestFilter(-1000) 以取到原始路径，早于 AuthFilter(-100) 以免对被拒路径做无谓鉴权
		return -150;
	}

}
