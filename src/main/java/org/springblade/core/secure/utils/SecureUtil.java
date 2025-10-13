/**
 * Copyright (c) 2018-2099, Chill Zhuang 庄骞 (bladejava@qq.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.core.secure.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springblade.core.launch.constant.TokenConstant;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.TokenInfo;
import org.springblade.core.secure.constant.SecureConstant;
import org.springblade.core.secure.exception.SecureException;
import org.springblade.core.secure.props.BladeTokenProperties;
import org.springblade.core.secure.provider.IClientDetails;
import org.springblade.core.secure.provider.IClientDetailsService;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.utils.*;

import javax.crypto.SecretKey;
import java.util.*;

/**
 * Secure工具类
 *
 * @author Chill
 */
public class SecureUtil {
	private static final String BLADE_USER_REQUEST_ATTR = "_BLADE_USER_REQUEST_ATTR_";

	private final static String HEADER = TokenConstant.HEADER;
	private final static String BEARER = TokenConstant.BEARER;
	private final static String CRYPTO = TokenConstant.CRYPTO;
	private final static String ACCOUNT = TokenConstant.ACCOUNT;
	private final static String USER_ID = TokenConstant.USER_ID;
	private final static String ROLE_ID = TokenConstant.ROLE_ID;
	private final static String DEPT_ID = TokenConstant.DEPT_ID;
	private final static String USER_NAME = TokenConstant.USER_NAME;
	private final static String ROLE_NAME = TokenConstant.ROLE_NAME;
	private final static String TENANT_ID = TokenConstant.TENANT_ID;
	private final static String CLIENT_ID = TokenConstant.CLIENT_ID;
	private final static Integer AUTH_LENGTH = TokenConstant.AUTH_LENGTH;
	private static IClientDetailsService CLIENT_DETAILS_SERVICE;
	private static BladeTokenProperties TOKEN_PROPERTIES;
	private static String BASE64_SECURITY;


	/**
	 * 获取客户端服务类
	 *
	 * @return clientDetailsService
	 */
	private static IClientDetailsService getClientDetailsService() {
		if (CLIENT_DETAILS_SERVICE == null) {
			CLIENT_DETAILS_SERVICE = SpringUtil.getBean(IClientDetailsService.class);
		}
		return CLIENT_DETAILS_SERVICE;
	}

	/**
	 * 获取配置类
	 *
	 * @return jwtProperties
	 */
	private static BladeTokenProperties getTokenProperties() {
		if (TOKEN_PROPERTIES == null) {
			TOKEN_PROPERTIES = SpringUtil.getBean(BladeTokenProperties.class);
		}
		return TOKEN_PROPERTIES;
	}

	/**
	 * 获取Token签名
	 *
	 * @return String
	 */
	private static String getBase64Security() {
		if (BASE64_SECURITY == null) {
			BASE64_SECURITY = Base64.getEncoder().encodeToString(getTokenProperties().getSignKey().getBytes(Charsets.UTF_8));
		}
		return BASE64_SECURITY;
	}

	/**
	 * 获取用户信息
	 *
	 * @return BladeUser
	 */
	public static BladeUser getUser() {
		HttpServletRequest request = WebUtil.getRequest();
		if (request == null) {
			return null;
		}
		// 优先从 request 中获取
		Object bladeUser = request.getAttribute(BLADE_USER_REQUEST_ATTR);
		if (bladeUser == null) {
			bladeUser = getUser(request);
			if (bladeUser != null) {
				// 设置到 request 中
				request.setAttribute(BLADE_USER_REQUEST_ATTR, bladeUser);
			}
		}
		return (BladeUser) bladeUser;
	}

	/**
	 * 获取用户信息
	 *
	 * @param auth auth
	 * @return BladeUser
	 */
	public static BladeUser getUser(String auth) {
		return getUser(getClaims(auth));
	}

	/**
	 * 获取用户信息
	 *
	 * @param request request
	 * @return BladeUser
	 */
	public static BladeUser getUser(HttpServletRequest request) {
		return getUser(getClaims(request));
	}

	/**
	 * 获取用户信息
	 *
	 * @param claims Claims
	 * @return BladeUser
	 */
	public static BladeUser getUser(Claims claims) {
		if (claims == null) {
			return null;
		}
		String clientId = Func.toStr(claims.get(SecureUtil.CLIENT_ID));
		Long userId = Func.toLong(claims.get(SecureUtil.USER_ID));
		String tenantId = Func.toStr(claims.get(SecureUtil.TENANT_ID));
		String roleId = Func.toStr(claims.get(SecureUtil.ROLE_ID));
		String deptId = Func.toStr(claims.get(SecureUtil.DEPT_ID));
		String account = Func.toStr(claims.get(SecureUtil.ACCOUNT));
		String roleName = Func.toStr(claims.get(SecureUtil.ROLE_NAME));
		String userName = Func.toStr(claims.get(SecureUtil.USER_NAME));
		BladeUser bladeUser = new BladeUser();
		bladeUser.setClientId(clientId);
		bladeUser.setUserId(userId);
		bladeUser.setTenantId(tenantId);
		bladeUser.setAccount(account);
		bladeUser.setRoleId(roleId);
		bladeUser.setDeptId(deptId);
		bladeUser.setRoleName(roleName);
		bladeUser.setUserName(userName);
		return bladeUser;
	}

	/**
	 * 是否为超管
	 *
	 * @return boolean
	 */
	public static boolean isAdministrator() {
		return StringUtil.containsAny(getUserRole(), RoleConstant.ADMIN);
	}

	/**
	 * 获取用户id
	 *
	 * @return userId
	 */
	public static Long getUserId() {
		BladeUser user = getUser();
		return (null == user) ? -1 : user.getUserId();
	}

	/**
	 * 获取用户id
	 *
	 * @param request request
	 * @return userId
	 */
	public static Long getUserId(HttpServletRequest request) {
		BladeUser user = getUser(request);
		return (null == user) ? -1 : user.getUserId();
	}

	/**
	 * 获取用户账号
	 *
	 * @return userAccount
	 */
	public static String getUserAccount() {
		BladeUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getAccount();
	}

	/**
	 * 获取用户账号
	 *
	 * @param request request
	 * @return userAccount
	 */
	public static String getUserAccount(HttpServletRequest request) {
		BladeUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getAccount();
	}

	/**
	 * 获取用户名
	 *
	 * @return userName
	 */
	public static String getUserName() {
		BladeUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getUserName();
	}

	/**
	 * 获取用户名
	 *
	 * @param request request
	 * @return userName
	 */
	public static String getUserName(HttpServletRequest request) {
		BladeUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getUserName();
	}

	/**
	 * 获取用户角色
	 *
	 * @return userName
	 */
	public static String getUserRole() {
		BladeUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getRoleName();
	}

	/**
	 * 获取用角色
	 *
	 * @param request request
	 * @return userName
	 */
	public static String getUserRole(HttpServletRequest request) {
		BladeUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getRoleName();
	}

	/**
	 * 获取租户ID
	 *
	 * @return tenantId
	 */
	public static String getTenantId() {
		BladeUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getTenantId();
	}

	/**
	 * 获取租户ID
	 *
	 * @param request request
	 * @return tenantId
	 */
	public static String getTenantId(HttpServletRequest request) {
		BladeUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getTenantId();
	}

	/**
	 * 获取客户端id
	 *
	 * @return tenantId
	 */
	public static String getClientId() {
		BladeUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getClientId();
	}

	/**
	 * 获取客户端id
	 *
	 * @param request request
	 * @return tenantId
	 */
	public static String getClientId(HttpServletRequest request) {
		BladeUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getClientId();
	}

	/**
	 * 获取Claims
	 *
	 * @param request request
	 * @return Claims
	 */
	public static Claims getClaims(HttpServletRequest request) {
		String auth = request.getHeader(SecureUtil.HEADER);
		if (StringUtil.isBlank(auth)) {
			auth = request.getParameter(SecureUtil.HEADER);
		}
		return getClaims(auth);
	}

	/**
	 * 获取Claims
	 *
	 * @param auth auth
	 * @return Claims
	 */
	public static Claims getClaims(String auth) {
		return SecureUtil.parseJWT(getToken(auth));
	}

	/**
	 * 获取请求传递的token串
	 *
	 * @param auth token
	 * @return String
	 */
	public static String getToken(String auth) {
		if (isBearer(auth)) {
			return auth.substring(AUTH_LENGTH);
		}
		if (isCrypto(auth)) {
			return AesUtil.decryptFormBase64ToString(auth.substring(AUTH_LENGTH), getTokenProperties().getAesKey());
		}
		return null;
	}

	/**
	 * 判断token类型为bearer
	 *
	 * @param auth token
	 * @return String
	 */
	public static Boolean isBearer(String auth) {
		if ((auth != null) && (auth.length() > AUTH_LENGTH)) {
			String headStr = auth.substring(0, 6).toLowerCase();
			return headStr.compareTo(BEARER) == 0;
		}
		return false;
	}

	/**
	 * 判断token类型为crypto
	 *
	 * @param auth token
	 * @return String
	 */
	public static Boolean isCrypto(String auth) {
		if ((auth != null) && (auth.length() > AUTH_LENGTH)) {
			String headStr = auth.substring(0, 6).toLowerCase();
			return headStr.compareTo(CRYPTO) == 0;
		}
		return false;
	}

	/**
	 * 获取请求头
	 *
	 * @return header
	 */
	public static String getHeader() {
		return getHeader(Objects.requireNonNull(WebUtil.getRequest()));
	}

	/**
	 * 获取请求头
	 *
	 * @param request request
	 * @return header
	 */
	public static String getHeader(HttpServletRequest request) {
		return request.getHeader(HEADER);
	}

	/**
	 * 解析jsonWebToken
	 *
	 * @param jsonWebToken jsonWebToken
	 * @return Claims
	 */
	public static Claims parseJWT(String jsonWebToken) {
		try {
			return Jwts.parser()
				.verifyWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(getBase64Security()))).build()
				.parseSignedClaims(jsonWebToken)
				.getPayload();
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 创建令牌
	 *
	 * @param user      user
	 * @param audience  audience
	 * @param issuer    issuer
	 * @param tokenType tokenType
	 * @return jwt
	 */
	public static TokenInfo createJWT(Map<String, String> user, String audience, String issuer, String tokenType) {

		String[] tokens = extractAndDecodeHeader();
		assert tokens.length == 2;
		String clientId = tokens[0];
		String clientSecret = tokens[1];

		// 获取客户端信息
		IClientDetails clientDetails = clientDetails(clientId);

		// 校验客户端信息
		if (!validateClient(clientDetails, clientId, clientSecret)) {
			throw new SecureException("客户端认证失败!");
		}

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// 生成签名密钥
		SecretKey signingKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(getBase64Security()));

		// 添加构成JWT的类
		JwtBuilder builder = Jwts.builder().header().add("typ", "JWT")
			.and().issuer(issuer).audience().add(audience)
			.and().signWith(signingKey);

		//设置JWT参数
		user.forEach(builder::claim);

		//设置应用id
		builder.claim(CLIENT_ID, clientId);

		//添加Token过期时间
		long expireMillis;
		if (tokenType.equals(TokenConstant.ACCESS_TOKEN)) {
			expireMillis = clientDetails.getAccessTokenValidity() * 1000;
		} else if (tokenType.equals(TokenConstant.REFRESH_TOKEN)) {
			expireMillis = clientDetails.getRefreshTokenValidity() * 1000;
		} else {
			expireMillis = getExpire();
		}
		long expMillis = nowMillis + expireMillis;
		Date exp = new Date(expMillis);
		builder.expiration(exp).notBefore(now);

		// 组装Token信息
		TokenInfo tokenInfo = new TokenInfo();
		tokenInfo.setToken(builder.compact());
		tokenInfo.setExpire((int) (expireMillis / 1000));

		return tokenInfo;
	}

	/**
	 * 获取过期时间(次日凌晨3点)
	 *
	 * @return expire
	 */
	public static long getExpire() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.HOUR_OF_DAY, 3);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis() - System.currentTimeMillis();
	}

	/**
	 * 客户端信息解码
	 */
	@SneakyThrows
	public static String[] extractAndDecodeHeader() {
		// 获取请求头客户端信息
		String header = Objects.requireNonNull(WebUtil.getRequest()).getHeader(SecureConstant.BASIC_HEADER_KEY);
		header = Func.toStr(header).replace(SecureConstant.BASIC_HEADER_PREFIX_EXT, SecureConstant.BASIC_HEADER_PREFIX);
		if (!header.startsWith(SecureConstant.BASIC_HEADER_PREFIX)) {
			throw new SecureException("No client information in request header");
		}
		byte[] base64Token = header.substring(6).getBytes(Charsets.UTF_8_NAME);

		byte[] decoded;
		try {
			decoded = Base64.getDecoder().decode(base64Token);
		} catch (IllegalArgumentException var7) {
			throw new RuntimeException("Failed to decode basic authentication token");
		}

		String token = new String(decoded, Charsets.UTF_8_NAME);
		int index = token.indexOf(StringPool.COLON);
		if (index == -1) {
			throw new RuntimeException("Invalid basic authentication token");
		} else {
			return new String[]{token.substring(0, index), token.substring(index + 1)};
		}
	}

	/**
	 * 获取请求头中的客户端id
	 */
	public static String getClientIdFromHeader() {
		String[] tokens = extractAndDecodeHeader();
		assert tokens.length == 2;
		return tokens[0];
	}

	/**
	 * 获取客户端信息
	 *
	 * @param clientId 客户端id
	 * @return clientDetails
	 */
	private static IClientDetails clientDetails(String clientId) {
		return getClientDetailsService().loadClientByClientId(clientId);
	}

	/**
	 * 校验Client
	 *
	 * @param clientId     客户端id
	 * @param clientSecret 客户端密钥
	 * @return boolean
	 */
	private static boolean validateClient(IClientDetails clientDetails, String clientId, String clientSecret) {
		if (clientDetails != null) {
			return StringUtil.equals(clientId, clientDetails.getClientId()) && StringUtil.equals(clientSecret, clientDetails.getClientSecret());
		}
		return false;
	}

}
