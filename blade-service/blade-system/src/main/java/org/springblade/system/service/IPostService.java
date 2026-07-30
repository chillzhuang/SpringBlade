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

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.system.entity.Post;
import org.springblade.system.vo.PostVO;

import java.util.List;

/**
 * 岗位表 服务类
 *
 * @author Chill
 */
public interface IPostService extends BaseService<Post> {

	/**
	 * 自定义分页
	 *
	 * @param page 分页参数
	 * @param post 岗位查询条件
	 * @return 岗位分页数据
	 */
	IPage<PostVO> selectPostPage(IPage<PostVO> page, PostVO post);

	/**
	 * 获取岗位ID
	 *
	 * @param tenantId  租户ID
	 * @param postNames 岗位名称集合
	 * @return 岗位主键字符串
	 */
	String getPostIds(String tenantId, String postNames);

	/**
	 * 获取岗位名
	 *
	 * @param postIds 岗位主键集合
	 * @return 岗位名称集合
	 */
	List<String> getPostNames(String postIds);

	/**
	 * 新增或修改岗位（带租户归属校验）
	 *
	 * @param post 岗位实体
	 * @return 是否成功
	 */
	boolean submit(Post post);

	/**
	 * 删除岗位（带租户归属校验）
	 *
	 * @param ids 岗位主键集合
	 * @return 是否成功
	 */
	boolean remove(List<Long> ids);

	/**
	 * 下拉数据源（含超管判定 + 当前租户隔离）
	 * <p>
	 * 仅超级管理员可指定任意 tenantId 查询；其他用户传入的 tenantId 一律被忽略，
	 * 强制使用当前会话租户。
	 *
	 * @param tenantId 入参 tenantId（仅超管生效）
	 * @return 岗位列表
	 */
	List<Post> selectByTenant(String tenantId);

}
