package org.springblade.test;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.util.encoders.Hex;
import org.springblade.core.tool.utils.SM2Util;
import org.springblade.core.tool.utils.StringPool;

/**
 * signKey生成器
 *
 * @author Chill
 */
public class Sm2KeyGenerator {

	public static void main(String[] args) {
		System.out.println("================ blade.auth 配置如下 =================");
		AsymmetricCipherKeyPair keyPair = SM2Util.generateKeyPair();
		String publicKey = SM2Util.getPublicKeyString(keyPair);
		String privateKey = SM2Util.getPrivateKeyString(keyPair);
		System.out.println("#blade配置 \n" +
			"blade:\n" +
			"  auth:\n" +
			"    public-key: " + publicKey + "\n" +
			"    private-key: " + privateKey);
		System.out.println("=======================================================");
		System.out.println(StringPool.EMPTY);
		System.out.println("============== saber website.js 配置如下 ===============");
		System.out.println("//saber配置\n" +
			"auth: {\n" +
			"  publicKey: '" + publicKey + "',\n" +
			"}");
		System.out.println("=======================================================");
		System.out.println(StringPool.EMPTY);
		System.out.println("============== 密码:[admin] 加密流程如下 ================");
		String password = "admin";
		byte[] encryptedData = SM2Util.encrypt(password, publicKey);
		String decryptedText = SM2Util.decrypt(encryptedData, privateKey);
		System.out.println("加密前: " + password);
		System.out.println("加密后: " + Hex.toHexString(encryptedData));
		System.out.println("解密后: " + decryptedText);
		System.out.println("请注意: 此密文为前端加密后调用token接口的密码参数");
		System.out.println("=======================================================");

	}

}
