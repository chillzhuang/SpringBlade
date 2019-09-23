package org.springblade.seata.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * StorageClient
 *
 * @author Chill
 */
@FeignClient(name = "blade-seata-storage", fallback = StorageClientFallback.class)
public interface IStorageClient {

	/**
	 * 减库存
	 *
	 * @param commodityCode 商品代码
	 * @param count         数量
	 * @return boolean
	 */
	@GetMapping("/deduct")
	int deduct(@RequestParam("commodityCode") String commodityCode, @RequestParam("count") Integer count);

}
