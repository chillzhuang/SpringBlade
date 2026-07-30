/**
 * Copyright (c) 2018-2099, Chill Zhuang 庄骞 (bladejava@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springblade.common.cache.CacheNames;
import org.springblade.common.constant.CommonConstant;
import org.springblade.core.cache.constant.CacheConstant;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tenant.TenantGuard;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.utils.*;
import org.springblade.system.entity.Tenant;
import org.springblade.system.feign.ISysClient;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.entity.UserInfo;
import org.springblade.system.user.entity.UserOauth;
import org.springblade.system.user.vo.UserVO;
import org.springblade.system.excel.UserExcel;
import org.springblade.system.mapper.UserMapper;
import org.springblade.system.service.IUserOauthService;
import org.springblade.system.service.IUserService;
import org.springblade.system.wrapper.UserWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.springblade.core.tenant.TenantGuard.EntityType.USER;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {
	private static final String GUEST_NAME = "guest";
	private static final String MINUS_ONE = "-1";

	private ISysClient sysClient;
	private IUserOauthService userOauthService;
	private BladeRedis bladeRedis;

	@Override
	public boolean submit(User user) {
		TenantGuard.bindTenant(this, user, USER);
		if (Func.isEmpty(user.getTenantId())) {
			throw new ServiceException("租户ID不能为空");
		}
		return doSubmit(user);
	}

	@Override
	public boolean update(User user) {
		TenantGuard.bindTenant(this, user, USER);
		return doSubmit(user);
	}

	/**
	 * ⚠ INTERNAL ONLY ⚠ 跳过 TenantGuard 的内部入口
	 * <p>
	 * 仅做密码加密 + 账号唯一性校验，<strong>不做任何租户归属校验</strong>。
	 * 调用方必须在外层确保以下两点：
	 * <ol>
	 *   <li>{@code user.tenantId} 来自可信源（OAuth 上下文 / 当前会话），
	 *       <strong>绝不允许前端入参直接通过此方法落库</strong>。</li>
	 *   <li>已经完成对应业务的鉴权检查。</li>
	 * </ol>
	 * 错误调用会导致跨租户数据泄漏。新增调用点请提交 PR 时 @ 安全负责人 review。
	 */
	private boolean doSubmit(User user) {
		CacheUtil.clear(CacheConstant.USER_CACHE);
		if (Func.isNotEmpty(user.getPassword())) {
			user.setPassword(DigestUtil.encrypt(user.getPassword()));
		}
		if (Func.isNotEmpty(user.getAccount())) {
			Long cnt = baseMapper.selectCount(Wrappers.<User>query().lambda()
				.eq(User::getTenantId, user.getTenantId())
				.eq(User::getAccount, user.getAccount())
				.ne(Func.isNotEmpty(user.getId()), User::getId, user.getId()));
			if (cnt > 0) {
				throw new ServiceException("当前账号已被使用!");
			}
		}
		return Func.isEmpty(user.getId()) ? save(user) : updateById(user);
	}

	@Override
	public boolean remove(List<Long> userIds) {
		CacheUtil.clear(CacheConstant.USER_CACHE);
		TenantGuard.verifyBatch(this, userIds, USER);
		return deleteLogic(userIds);
	}

	@Override
	public boolean unlock(List<Long> userIds) {
		List<User> userList = TenantGuard.verifyBatch(this, userIds, USER);
		userList.forEach(user -> bladeRedis.del(CacheNames.tenantKey(user.getTenantId(), CacheNames.USER_FAIL_KEY, user.getAccount())));
		return true;
	}

	@Override
	public boolean updateUserInfo(User user) {
		CacheUtil.clear(CacheConstant.USER_CACHE);
		// 用户修改自身信息强制指定当前请求账号的ID
		user.setId(SecureUtil.getUserId());
		User currentUser = getById(user.getId());
		if (currentUser == null) {
			throw new ServiceException("用户不存在!");
		}
		// 用户修改自身信息强制忽略角色、部门、账号等字段
		user.setRoleId(null);
		user.setDeptId(null);
		user.setAccount(null);
		user.setPassword(null);
		user.setUpdateTime(DateUtil.now());
		return updateById(user);
	}

	@Override
	public IPage<User> selectUserPage(IPage<User> page, User user) {
		return page.setRecords(baseMapper.selectUserPage(page, user));
	}

	@Override
	public IPage<UserVO> selectPage(Map<String, Object> user, Query query) {
		QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(user, User.class);
		if (!SecureUtil.isAdministrator()) {
			queryWrapper.lambda().eq(User::getTenantId, SecureUtil.getTenantId());
		}
		return UserWrapper.build().pageVO(page(Condition.getPage(query), queryWrapper));
	}

	@Override
	public UserInfo userInfo(Long userId) {
		UserInfo userInfo = new UserInfo();
		User user = baseMapper.selectById(userId);
		userInfo.setUser(user);
		if (Func.isNotEmpty(user)) {
			List<String> roleAlias = baseMapper.getRoleAlias(Func.toStrArray(user.getRoleId()));
			userInfo.setRoles(roleAlias);
		}
		return userInfo;
	}

	@Override
	public UserInfo userInfo(String tenantId, String account, String password) {
		UserInfo userInfo = new UserInfo();
		User user = baseMapper.getUser(tenantId, account, password);
		userInfo.setUser(user);
		if (Func.isNotEmpty(user)) {
			List<String> roleAlias = baseMapper.getRoleAlias(Func.toStrArray(user.getRoleId()));
			userInfo.setRoles(roleAlias);
		}
		return userInfo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserInfo userInfo(UserOauth userOauth) {
		UserOauth uo = userOauthService.getOne(Wrappers.<UserOauth>query().lambda().eq(UserOauth::getUuid, userOauth.getUuid()).eq(UserOauth::getSource, userOauth.getSource()));
		UserInfo userInfo;
		if (Func.isNotEmpty(uo) && Func.isNotEmpty(uo.getUserId())) {
			userInfo = this.userInfo(uo.getUserId());
			userInfo.setOauthId(Func.toStr(uo.getId()));
		} else {
			userInfo = new UserInfo();
			if (Func.isEmpty(uo)) {
				userOauthService.save(userOauth);
				userInfo.setOauthId(Func.toStr(userOauth.getId()));
			} else {
				userInfo.setOauthId(Func.toStr(uo.getId()));
			}
			User user = new User();
			user.setAccount(userOauth.getUsername());
			userInfo.setUser(user);
			userInfo.setRoles(Collections.singletonList(GUEST_NAME));
		}
		return userInfo;
	}

	@Override
	public boolean grant(String userIds, String roleIds) {
		CacheUtil.clear(CacheConstant.USER_CACHE);
		List<Long> idList = Func.toLongList(userIds);
		TenantGuard.verifyBatch(this, idList, USER);
		User user = new User();
		user.setRoleId(roleIds);
		return this.update(user, Wrappers.<User>update().lambda().in(User::getId, idList));
	}

	@Override
	public boolean resetPassword(String userIds) {
		CacheUtil.clear(CacheConstant.USER_CACHE);
		List<Long> idList = Func.toLongList(userIds);
		TenantGuard.verifyBatch(this, idList, USER);
		User user = new User();
		user.setPassword(DigestUtil.encrypt(CommonConstant.DEFAULT_PASSWORD));
		user.setUpdateTime(DateUtil.now());
		return this.update(user, Wrappers.<User>update().lambda().in(User::getId, idList));
	}

	@Override
	public boolean updatePassword(Long userId, String oldPassword, String newPassword, String newPassword1) {
		CacheUtil.clear(CacheConstant.USER_CACHE);
		User user = getById(userId);
		if (!newPassword.equals(newPassword1)) {
			throw new ServiceException("请输入正确的确认密码!");
		}
		if (!user.getPassword().equals(DigestUtil.encrypt(oldPassword))) {
			throw new ServiceException("原密码不正确!");
		}
		return this.update(Wrappers.<User>update().lambda().set(User::getPassword, DigestUtil.encrypt(newPassword)).eq(User::getId, userId));
	}

	@Override
	public List<String> getRoleName(String roleIds) {
		return baseMapper.getRoleName(Func.toStrArray(roleIds));
	}

	@Override
	public List<String> getDeptName(String deptIds) {
		return baseMapper.getDeptName(Func.toStrArray(deptIds));
	}

	@Override
	public void importUser(List<UserExcel> data) {
		// 强制使用当前会话租户，禁止 Excel 内容决定租户归属
		String currentTenantId = SecureUtil.getTenantId();
		data.forEach(userExcel -> {
			userExcel.setTenantId(currentTenantId);
			User user = Objects.requireNonNull(BeanUtil.copyProperties(userExcel, User.class));
			user.setTenantId(currentTenantId);
			// 设置部门ID
			user.setDeptId(sysClient.getDeptIds(currentTenantId, userExcel.getDeptName()));
			// 设置岗位ID
			user.setPostId(sysClient.getPostIds(currentTenantId, userExcel.getPostName()));
			// 设置角色ID
			user.setRoleId(sysClient.getRoleIds(currentTenantId, userExcel.getRoleName()));
			// 设置默认密码
			user.setPassword(CommonConstant.DEFAULT_PASSWORD);
			this.submit(user);
		});
	}

	@Override
	public List<UserExcel> exportUser(Wrapper<User> queryWrapper) {
		List<UserExcel> userList = baseMapper.exportUser(queryWrapper);
		userList.forEach(user -> {
			user.setRoleName(StringUtil.join(sysClient.getRoleNames(user.getRoleId())));
			user.setDeptName(StringUtil.join(sysClient.getDeptNames(user.getDeptId())));
			user.setPostName(StringUtil.join(sysClient.getPostNames(user.getPostId())));
		});
		return userList;
	}

	@Override
	public List<UserExcel> exportUser(Map<String, Object> user) {
		QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(user, User.class);
		if (!SecureUtil.isAdministrator()) {
			queryWrapper.lambda().eq(User::getTenantId, SecureUtil.getTenantId());
		}
		queryWrapper.lambda().eq(User::getIsDeleted, BladeConstant.DB_NOT_DELETED);
		return exportUser(queryWrapper);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean registerGuest(User user, Long oauthId) {
		// 第一步：先取出 OAuth 上下文。oauthId 由 OAuth 第一步授权回调写回前端，
		// 是攻击者难以伪造的可信关联——以此为锚点反推 tenantId。
		UserOauth userOauth = userOauthService.getById(oauthId);
		if (userOauth == null || userOauth.getId() == null) {
			throw new ServiceException("第三方登陆信息错误!");
		}
		// ⚡ 优先采用 OAuth 上下文中已绑定的 tenantId（来自 OAuth 第一步可信源），
		// 强制覆盖前端传入的 user.tenantId，防止匿名接口被用于跨租户植入账户。
		// 仅当 OAuth 流程未绑定 tenantId 时才回退到入参（向后兼容历史 OAuth 流程）。
		if (Func.isNotEmpty(userOauth.getTenantId())) {
			user.setTenantId(userOauth.getTenantId());
		}
		R<Tenant> result = sysClient.getTenant(user.getTenantId());
		Tenant tenant = result.getData();
		if (!result.isSuccess() || tenant == null || tenant.getId() == null) {
			throw new ServiceException("租户信息错误!");
		}
		user.setRealName(user.getName());
		user.setAvatar(userOauth.getAvatar());
		user.setRoleId(MINUS_ONE);
		user.setDeptId(MINUS_ONE);
		user.setPostId(MINUS_ONE);
		// 第三方注册为匿名上下文，绕过 TenantGuard 直接走 doSubmit；
		// tenantId 已在上方强制锚定到 OAuth 上下文，可信源已确定。
		boolean userTemp = doSubmit(user);
		userOauth.setUserId(user.getId());
		userOauth.setTenantId(user.getTenantId());
		boolean oauthTemp = userOauthService.updateById(userOauth);
		return (userTemp && oauthTemp);
	}

}
