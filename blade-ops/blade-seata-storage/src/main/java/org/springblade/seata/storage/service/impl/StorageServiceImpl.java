package org.springblade.seata.storage.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.seata.storage.entity.Storage;
import org.springblade.seata.storage.mapper.StorageMapper;
import org.springblade.seata.storage.service.IStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * StorageServiceImpl
 *
 * @author Chill
 */
@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements IStorageService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deduct(String commodityCode, int count) {
		Storage storage = baseMapper.selectOne(Wrappers.<Storage>query().lambda().eq(Storage::getCommodityCode, commodityCode));
		if (storage.getCount() < count) {
			throw new RuntimeException("超过库存数，扣除失败！");
		}
		storage.setCount(storage.getCount() - count);
		return baseMapper.updateById(storage);
	}

}
