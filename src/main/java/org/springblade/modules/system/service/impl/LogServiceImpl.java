package org.springblade.modules.system.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.core.log.model.LogApi;
import org.springblade.core.log.model.LogError;
import org.springblade.core.log.model.LogUsual;
import org.springblade.modules.system.service.ILogApiService;
import org.springblade.modules.system.service.ILogErrorService;
import org.springblade.modules.system.service.ILogService;
import org.springblade.modules.system.service.ILogUsualService;
import org.springframework.stereotype.Service;

/**
 * Created by Blade.
 *
 * @author zhuangqian
 */
@Service
@AllArgsConstructor
public class LogServiceImpl implements ILogService {

	ILogUsualService usualService;
	ILogApiService apiService;
	ILogErrorService errorService;

	@Override
	public Boolean saveUsualLog(LogUsual log) {
		return usualService.save(log);
	}

	@Override
	public Boolean saveApiLog(LogApi log) {
		return apiService.save(log);
	}

	@Override
	public Boolean saveErrorLog(LogError log) {
		return errorService.save(log);
	}

}
