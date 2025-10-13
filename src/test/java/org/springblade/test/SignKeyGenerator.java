package org.springblade.test;

import org.springblade.core.tool.utils.AesUtil;
import org.springblade.core.tool.utils.RandomType;
import org.springblade.core.tool.utils.StringUtil;

/**
 * signKey生成器
 *
 * @author Chill
 */
public class SignKeyGenerator {

	public static void main(String[] args) {
		System.out.println("=========== blade.token.sign-key 配置如下 ==============");
		System.out.println("#blade配置\n" +
			"blade:\n" +
			"  token:\n" +
			"    sign-key: " + StringUtil.random(32, RandomType.ALL) +"\n" +
			"    aes-key: " + AesUtil.genAesKey() );
		System.out.println("=======================================================");
	}

}
