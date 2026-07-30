package org.springblade.seata.storage.controller;

import lombok.AllArgsConstructor;
import org.springblade.seata.storage.service.IStorageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * StorageController
 *
 * @author Chill
 */
@RestController
@RequestMapping("/feign/client/storage")
@AllArgsConstructor
public class StorageController {

	private IStorageService storageService;

	/**
	 * 减库存
	 */
	@RequestMapping(path = "/deduct")
	public int deduct(String commodityCode, Integer count) {
		return storageService.deduct(commodityCode, count);
	}

}
