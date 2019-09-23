package org.springblade.seata.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Order
 *
 * @author Chill
 */
@Data
@Accessors(chain = true)
@TableName("tb_order")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	private String userId;
	private String commodityCode;
	private Integer count;
	private BigDecimal money;

}
