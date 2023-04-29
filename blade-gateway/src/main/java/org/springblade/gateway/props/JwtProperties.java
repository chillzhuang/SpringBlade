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
package org.springblade.gateway.props;

import io.jsonwebtoken.JwtException;
import lombok.Data;
import org.springblade.core.launch.constant.TokenConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWT配置
 *
 * @author Chill
 */
@Data
@ConfigurationProperties("blade.token")
public class JwtProperties {

	/**
	 * token是否有状态
	 */
	private Boolean state = Boolean.FALSE;

	/**
	 * 是否只可同时在线一人
	 */
	private Boolean single = Boolean.FALSE;

	/**
	 * token签名
	 */
	private String signKey = "";

	/**
	 * 获取签名规则
	 */
	public String getSignKey() {
		if (this.signKey.length() < TokenConstant.SIGN_KEY_LENGTH) {
			throw new JwtException("请配置 blade.token.sign-key 的值, 长度32位以上");
		}
		return this.signKey;
	}

}
