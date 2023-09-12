package org.springblade.test;

import org.springblade.core.tool.utils.AesUtil;

/**
 * aesKey生成器
 *
 * @author Chill
 */
public class AesKeyGenerator {

	public static void main(String[] args) {
		System.out.println("=======================================================");
		for (int i = 0; i < 10; i++) {
			String aesKey = AesUtil.genAesKey();
			System.out.println("SpringBlade AesKey：[" + aesKey + "] ");
		}
		System.out.println("=======================================================");
		System.out.println("====== blade.token.aes-key 的值从中挑选一个便可 =========");
		System.out.println("=======================================================");
	}

}
