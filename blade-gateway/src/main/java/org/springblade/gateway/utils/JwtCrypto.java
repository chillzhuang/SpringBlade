/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.gateway.utils;

import lombok.SneakyThrows;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import reactor.util.annotation.Nullable;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

/**
 * JwtCrypto
 *
 * @author Chill
 */
public class JwtCrypto {

	public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
	public static final String BLADE_CRYPTO_AES_KEY = "blade.token.aes-key";


	/**
	 * Base64加密
	 *
	 * @param content    文本内容
	 * @param aesTextKey 文本密钥
	 * @return {String}
	 */
	public static String encryptToString(String content, String aesTextKey) {
		return Base64.getEncoder().encodeToString(encrypt(content, aesTextKey));
	}

	/**
	 * Base64加密
	 *
	 * @param content    内容
	 * @param aesTextKey 文本密钥
	 * @return {String}
	 */
	public static String encryptToString(byte[] content, String aesTextKey) {
		return Base64.getEncoder().encodeToString(encrypt(content, aesTextKey));
	}

	/**
	 * 加密
	 *
	 * @param content    文本内容
	 * @param aesTextKey 文本密钥
	 * @return byte[]
	 */
	public static byte[] encrypt(String content, String aesTextKey) {
		return encrypt(content.getBytes(DEFAULT_CHARSET), aesTextKey);
	}

	/**
	 * 加密
	 *
	 * @param content    文本内容
	 * @param charset    编码
	 * @param aesTextKey 文本密钥
	 * @return byte[]
	 */
	public static byte[] encrypt(String content, Charset charset, String aesTextKey) {
		return encrypt(content.getBytes(charset), aesTextKey);
	}

	/**
	 * 加密
	 *
	 * @param content    内容
	 * @param aesTextKey 文本密钥
	 * @return byte[]
	 */
	public static byte[] encrypt(byte[] content, String aesTextKey) {
		return encrypt(content, Objects.requireNonNull(aesTextKey).getBytes(DEFAULT_CHARSET));
	}

	/**
	 * Base64解密
	 *
	 * @param content    文本内容
	 * @param aesTextKey 文本密钥
	 * @return {String}
	 */
	@Nullable
	public static String decryptToString(@Nullable String content, @Nullable String aesTextKey) {
		if (!StringUtils.hasText(content) || !StringUtils.hasText(aesTextKey)) {
			return null;
		}
		byte[] hexBytes = decrypt(Base64.getDecoder().decode(content.getBytes(DEFAULT_CHARSET)), aesTextKey);
		return new String(hexBytes, DEFAULT_CHARSET);
	}


	/**
	 * 解密
	 *
	 * @param content    内容
	 * @param aesTextKey 文本密钥
	 * @return byte[]
	 */
	public static byte[] decrypt(byte[] content, String aesTextKey) {
		return decrypt(content, Objects.requireNonNull(aesTextKey).getBytes(DEFAULT_CHARSET));
	}


	/**
	 * 解密
	 *
	 * @param content 内容
	 * @param aesKey  密钥
	 * @return byte[]
	 */
	public static byte[] encrypt(byte[] content, byte[] aesKey) {
		return aes(Pkcs7Encoder.encode(content), aesKey, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 加密
	 *
	 * @param encrypted 内容
	 * @param aesKey    密钥
	 * @return byte[]
	 */
	public static byte[] decrypt(byte[] encrypted, byte[] aesKey) {
		return Pkcs7Encoder.decode(aes(encrypted, aesKey, Cipher.DECRYPT_MODE));
	}

	/**
	 * ase加密
	 *
	 * @param encrypted 内容
	 * @param aesKey    密钥
	 * @param mode      模式
	 * @return byte[]
	 */
	@SneakyThrows
	private static byte[] aes(byte[] encrypted, byte[] aesKey, int mode) {
		Assert.isTrue(aesKey.length == 32, "IllegalAesKey, aesKey's length must be 32");
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
		IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
		cipher.init(mode, keySpec, iv);
		return cipher.doFinal(encrypted);
	}

	/**
	 * 提供基于PKCS7算法的加解密接口.
	 */
	private static class Pkcs7Encoder {
		private static final int BLOCK_SIZE = 32;

		private static byte[] encode(byte[] src) {
			int count = src.length;
			// 计算需要填充的位数
			int amountToPad = BLOCK_SIZE - (count % BLOCK_SIZE);
			// 获得补位所用的字符
			byte pad = (byte) (amountToPad & 0xFF);
			byte[] pads = new byte[amountToPad];
			for (int index = 0; index < amountToPad; index++) {
				pads[index] = pad;
			}
			int length = count + amountToPad;
			byte[] dest = new byte[length];
			System.arraycopy(src, 0, dest, 0, count);
			System.arraycopy(pads, 0, dest, count, amountToPad);
			return dest;
		}

		private static byte[] decode(byte[] decrypted) {
			int pad = decrypted[decrypted.length - 1];
			if (pad < 1 || pad > BLOCK_SIZE) {
				pad = 0;
			}
			if (pad > 0) {
				return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
			}
			return decrypted;
		}
	}
}
