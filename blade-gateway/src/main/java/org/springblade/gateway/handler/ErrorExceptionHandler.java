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
package org.springblade.gateway.handler;

import org.springblade.gateway.provider.ResponseProvider;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

/**
 * 异常处理
 *
 * @author Chill
 */
public class ErrorExceptionHandler extends DefaultErrorWebExceptionHandler {

	public ErrorExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
								 ErrorProperties errorProperties, ApplicationContext applicationContext) {
		super(errorAttributes, resourceProperties, errorProperties, applicationContext);
	}

	/**
	 * 获取异常属性
	 */
	@Override
	protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
		int code = 500;
		Throwable error = super.getError(request);
		if (error instanceof NotFoundException) {
			code = 404;
		}
		if (error instanceof ResponseStatusException) {
			code = ((ResponseStatusException) error).getStatus().value();
		}
		return ResponseProvider.response(code, this.buildMessage(request, error));
	}

	/**
	 * 指定响应处理方法为JSON处理的方法
	 *
	 * @param errorAttributes
	 */
	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
	}

	/**
	 * 根据code获取对应的HttpStatus
	 *
	 * @param errorAttributes
	 */
	@Override
	protected HttpStatus getHttpStatus(Map<String, Object> errorAttributes) {
		int statusCode = (int) errorAttributes.get("code");
		return HttpStatus.valueOf(statusCode);
	}

	/**
	 * 构建异常信息
	 *
	 * @param request
	 * @param ex
	 * @return
	 */
	private String buildMessage(ServerRequest request, Throwable ex) {
		StringBuilder message = new StringBuilder("Failed to handle request [");
		message.append(request.methodName());
		message.append(" ");
		message.append(request.uri());
		message.append("]");
		if (ex != null) {
			message.append(": ");
			message.append(ex.getMessage());
		}
		return message.toString();
	}

}
