package org.springblade.seata.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.seata.order.entity.Order;

/**
 * IOrderService
 *
 * @author Chill
 */
public interface IOrderService extends IService<Order> {

	/**
	 * 创建订单
	 *
	 * @param userId        用户id
	 * @param commodityCode 商品代码
	 * @param count         数量
	 * @return boolean
	 */
	boolean createOrder(String userId, String commodityCode, Integer count);

}
