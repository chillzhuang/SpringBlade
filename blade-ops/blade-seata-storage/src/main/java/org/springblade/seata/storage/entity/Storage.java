package org.springblade.seata.storage.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * storage
 *
 * @author Chill
 */
@Data
@TableName("tb_storage")
public class Storage implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String commodityCode;
	private Long count;

}
