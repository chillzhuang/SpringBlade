package org.springblade.common.constant;

/**
 * 通用常量
 *
 * @author Chill
 */
public interface CommonConstant {

	/**
	 * consul dev 地址
	 */
	String CONSUL_DEV_HOST = "http://localhost";

	/**
	 * consul prod 地址
	 */
	String CONSUL_PROD_HOST = "http://192.168.186.129";

	/**
	 * consul端口
	 */
	String CONSUL_PORT = "8500";

	/**
	 * 顶级父节点id
	 */
	Integer TOP_PARENT_ID = 0;

	/**
	 * 顶级父节点名称
	 */
	String TOP_PARENT_NAME = "顶级";


	/**
	 * 默认密码
	 */
	String DEFAULT_PASSWORD = "123456";
}
