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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tenant.TenantGuard;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.entity.Post;
import org.springblade.system.mapper.PostMapper;
import org.springblade.system.service.IPostService;
import org.springblade.system.vo.PostVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springblade.core.tenant.TenantGuard.EntityType.POST;

/**
 * 岗位表 服务实现类
 *
 * @author Chill
 */
@Service
public class PostServiceImpl extends BaseServiceImpl<PostMapper, Post> implements IPostService {

	@Override
	public IPage<PostVO> selectPostPage(IPage<PostVO> page, PostVO post) {
		return page.setRecords(baseMapper.selectPostPage(page, post));
	}

	@Override
	public String getPostIds(String tenantId, String postNames) {
		List<Post> postList = baseMapper.selectList(Wrappers.<Post>query().lambda().eq(Post::getTenantId, tenantId).in(Post::getPostName, Func.toStrList(postNames)));
		if (postList != null && !postList.isEmpty()) {
			return postList.stream().map(post -> Func.toStr(post.getId())).distinct().collect(Collectors.joining(","));
		}
		return null;
	}

	@Override
	public List<String> getPostNames(String postIds) {
		return baseMapper.getPostNames(Func.toLongArray(postIds));
	}

	@Override
	public boolean submit(Post post) {
		CacheUtil.clear(CacheUtil.SYS_CACHE);
		TenantGuard.bindTenant(this, post, POST);
		if (Func.isEmpty(post.getTenantId())) {
			throw new ServiceException("租户ID不能为空");
		}
		return saveOrUpdate(post);
	}

	@Override
	public boolean remove(List<Long> ids) {
		CacheUtil.clear(CacheUtil.SYS_CACHE);
		TenantGuard.verifyBatch(this, ids, POST);
		return deleteLogic(ids);
	}

	@Override
	public List<Post> selectByTenant(String tenantId) {
		String resolvedTenantId = SecureUtil.isAdministrator()
			? Func.toStr(tenantId, SecureUtil.getTenantId())
			: SecureUtil.getTenantId();
		return list(Wrappers.<Post>query().lambda().eq(Post::getTenantId, resolvedTenantId));
	}

}
