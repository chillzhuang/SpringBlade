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
package org.springblade.system.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.core.mp.support.Query;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.entity.UserInfo;
import org.springblade.system.user.entity.UserOauth;
import org.springblade.system.user.vo.UserVO;
import org.springblade.system.excel.UserExcel;

import java.util.List;
import java.util.Map;

/**
 * 服务类
 *
 * @author Chill
 */
public interface IUserService extends BaseService<User> {

	/**
	 * 新增或修改用户（带租户归属校验）
	 *
	 * @param user 用户实体
	 * @return 是否成功
	 */
	boolean submit(User user);

	/**
	 * 修改用户（带租户归属校验）
	 *
	 * @param user 用户实体
	 * @return 是否成功
	 */
	boolean update(User user);

	/**
	 * 删除用户（带租户归属校验）
	 *
	 * @param userIds 用户主键集合
	 * @return 是否成功
	 */
	boolean remove(List<Long> userIds);

	/**
	 * 解锁用户登录失败计数（带租户归属校验）
	 *
	 * @param userIds 用户主键集合
	 * @return 是否成功
	 */
	boolean unlock(List<Long> userIds);

	/**
	 * 修改用户基本信息
	 *
	 * @param user 用户实体
	 * @return 是否成功
	 */
	boolean updateUserInfo(User user);

	/**
	 * 自定义分页
	 *
	 * @param page 分页参数
	 * @param user 用户查询条件
	 * @return 用户分页数据
	 */
	IPage<User> selectUserPage(IPage<User> page, User user);

	/**
	 * 分页列表（含超管判定 + 当前租户隔离）
	 * <p>
	 * 仅超级管理员可指定任意 tenantId 查询；其他用户传入的 tenantId 一律被忽略，
	 * 强制使用当前会话租户。
	 *
	 * @param user  查询条件 Map
	 * @param query 分页参数
	 * @return 分页结果 VO，非超管时仅命中当前会话租户
	 */
	IPage<UserVO> selectPage(Map<String, Object> user, Query query);

	/**
	 * 用户信息
	 *
	 * @param userId 用户主键
	 * @return 用户信息
	 */
	UserInfo userInfo(Long userId);

	/**
	 * 用户信息
	 *
	 * @param tenantId 租户编号
	 * @param account  账号
	 * @param password 密码
	 * @return 用户信息
	 */
	UserInfo userInfo(String tenantId, String account, String password);

	/**
	 * 用户信息
	 *
	 * @param userOauth 第三方授权信息
	 * @return 用户信息
	 */
	UserInfo userInfo(UserOauth userOauth);

	/**
	 * 给用户设置角色
	 *
	 * @param userIds 用户主键集合
	 * @param roleIds 角色主键集合
	 * @return 是否成功
	 */
	boolean grant(String userIds, String roleIds);

	/**
	 * 初始化密码
	 *
	 * @param userIds 用户主键集合
	 * @return 是否成功
	 */
	boolean resetPassword(String userIds);

	/**
	 * 修改密码
	 *
	 * @param userId       用户主键
	 * @param oldPassword  原密码
	 * @param newPassword  新密码
	 * @param newPassword1 确认密码
	 * @return 是否成功
	 */
	boolean updatePassword(Long userId, String oldPassword, String newPassword, String newPassword1);

	/**
	 * 获取角色名
	 *
	 * @param roleIds 角色主键集合
	 * @return 角色名称集合
	 */
	List<String> getRoleName(String roleIds);

	/**
	 * 获取部门名
	 *
	 * @param deptIds 部门主键集合
	 * @return 部门名称集合
	 */
	List<String> getDeptName(String deptIds);

	/**
	 * 导入用户数据
	 *
	 * @param data 用户导入数据集合
	 */
	void importUser(List<UserExcel> data);

	/**
	 * 获取导出用户数据
	 *
	 * @param queryWrapper 查询条件构造器
	 * @return 用户导出数据集合
	 */
	List<UserExcel> exportUser(Wrapper<User> queryWrapper);

	/**
	 * 获取导出用户数据（含超管判定 + 当前租户隔离）
	 * <p>
	 * 仅超级管理员可导出全量数据；其他用户被强制限定在自身租户范围内。
	 * 自动叠加未删除条件。
	 *
	 * @param user 查询条件 Map
	 * @return 导出数据，非超管时仅命中当前会话租户
	 */
	List<UserExcel> exportUser(Map<String, Object> user);

	/**
	 * 注册用户
	 *
	 * @param user    用户实体
	 * @param oauthId 第三方授权主键
	 * @return 是否成功
	 */
	boolean registerGuest(User user, Long oauthId);
}
