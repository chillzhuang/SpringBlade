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
package org.springblade.core.secure;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * AuthInfo
 *
 * @author Chill
 */
@Data
@Schema(description = "认证信息")
public class AuthInfo {
	@Schema(description = "令牌")
	private String accessToken;
	@Schema(description = "令牌类型")
	private String tokenType;
	@Schema(description = "刷新令牌")
	private String refreshToken;
	@Schema(description = "用户ID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;
	@Schema(description = "租户ID")
	private String tenantId;
	@Schema(description = "第三方系统ID")
	private String oauthId;
	@Schema(description = "头像")
	private String avatar = "https://bladex.cn/images/logo.png";
	@Schema(description = "角色名")
	private String authority;
	@Schema(description = "用户名")
	private String userName;
	@Schema(description = "账号名")
	private String account;
	@Schema(description = "过期时间")
	private long expiresIn;
	@Schema(description = "许可证")
	private String license = "powered by blade";
}
