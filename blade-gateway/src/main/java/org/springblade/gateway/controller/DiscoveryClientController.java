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
package org.springblade.gateway.controller;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.health.model.Check;
import com.ecwid.consul.v1.health.model.HealthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务发现控制器
 *
 * @author Chill
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/discovery")
public class DiscoveryClientController {

	private final DiscoveryClient discoveryClient;

	private final ConsulClient consulClient;

	/**
	 * 获取服务实例
	 */
	@GetMapping("/instances")
	public Map<String, List<ServiceInstance>> instances() {
		Map<String, List<ServiceInstance>> instances = new HashMap<>(16);
		List<String> services = discoveryClient.getServices();
		services.forEach(s -> {
			List<ServiceInstance> list = discoveryClient.getInstances(s);
			instances.put(s, list);
		});
		return instances;
	}

	/**
	 * 删除指定无效服务
	 *
	 * @param serviceName 服务名
	 * @return
	 */
	@RequestMapping("/deRegister/{serviceName}")
	public String deRegister(@PathVariable String serviceName) {
		serviceDeregister(serviceName);
		return "服务删除成功";
	}

	/**
	 * 删除所有无效服务
	 *
	 * @return
	 */
	@RequestMapping("/deRegisterAll")
	public String deRegisterAll() {
		consulClient.getAgentChecks().getValue().forEach((k, v) -> serviceDeregister(v.getServiceName()));
		return "服务删除成功";
	}

	/**
	 * 根据serviceName删除无效服务
	 *
	 * @param serviceName
	 */
	public void serviceDeregister(String serviceName) {
		List<HealthService> response = consulClient.getHealthServices(serviceName, false, null).getValue();
		for (HealthService service : response) {
			// 创建一个用来剔除无效实例的ConsulClient，连接到无效实例注册的agent
			ConsulClient clearClient = new ConsulClient(service.getNode().getAddress());
			service.getChecks().forEach(check -> {
				if (check.getStatus() != Check.CheckStatus.PASSING) {
					log.info("deregister : {}", check.getServiceId());
					clearClient.agentServiceDeregister(check.getServiceId());
				}
			});
		}
	}

}
