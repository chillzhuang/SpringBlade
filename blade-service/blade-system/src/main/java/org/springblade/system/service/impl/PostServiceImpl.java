/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
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
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.entity.Post;
import org.springblade.system.mapper.PostMapper;
import org.springblade.system.service.IPostService;
import org.springblade.system.vo.PostVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
		if (postList != null && postList.size() > 0) {
			return postList.stream().map(post -> Func.toStr(post.getId())).distinct().collect(Collectors.joining(","));
		}
		return null;
	}

	@Override
	public List<String> getPostNames(String postIds) {
		return baseMapper.getPostNames(Func.toLongArray(postIds));
	}

}
