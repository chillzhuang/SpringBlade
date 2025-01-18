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
package org.springblade.core.log.wrapper;

import org.springblade.core.log.model.LogError;
import org.springblade.core.log.pojo.LogErrorVO;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.sensitive.SensitiveUtil;
import org.springblade.core.tool.sensitive.SensitiveWord;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.StringUtil;

import java.util.Objects;

/**
 * Log包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class LogErrorWrapper extends BaseEntityWrapper<LogError, LogErrorVO> {

	public static LogErrorWrapper build() {
		return new LogErrorWrapper();
	}

	@Override
	public LogErrorVO entityVO(LogError logError) {
		return Objects.requireNonNull(BeanUtil.copyProperties(logError, LogErrorVO.class));
	}

	public LogError entity(LogError logError) {
		String params = logError.getParams();
		String stackTrace = logError.getStackTrace();
		String message = logError.getMessage();
		if (StringUtil.isNotBlank(params)) {
			logError.setParams(SensitiveUtil.processWithWords(params, SensitiveWord.SECURE.getWords()));
		}
		if (StringUtil.isNotBlank(stackTrace)) {
			logError.setStackTrace(SensitiveUtil.processWithWords(stackTrace, SensitiveWord.SECURE.getWords()));
		}
		if (StringUtil.isNotBlank(message)) {
			logError.setMessage(SensitiveUtil.processWithWords(message, SensitiveWord.SECURE.getWords()));
		}
		return logError;
	}
}
