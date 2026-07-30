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
import com.baomidou.mybatisplus.spring.service.IService;
import org.springblade.system.entity.Dict;
import org.springblade.system.vo.DictVO;

import java.util.List;

/**
 * 服务类
 *
 * @author Chill
 */
public interface IDictService extends IService<Dict> {

	/**
	 * 自定义分页
	 *
	 * @param page 分页参数
	 * @param dict 字典查询条件
	 * @return 字典分页数据
	 */
	IPage<DictVO> selectDictPage(IPage<DictVO> page, DictVO dict);

	/**
	 * 树形结构
	 *
	 * @return 字典树
	 */
	List<DictVO> tree();

	/**
	 * 获取字典表对应中文
	 *
	 * @param code    字典编号
	 * @param dictKey 字典序号
	 * @return 字典值对应的中文
	 */
	String getValue(String code, Integer dictKey);

	/**
	 * 获取字典表
	 *
	 * @param code 字典编号
	 * @return 字典集合
	 */
	List<Dict> getList(String code);

	/**
	 * 新增或修改
	 * @param dict 字典实体
	 * @return 是否成功
	 */
	boolean submit(Dict dict);

	/**
	 * 删除字典并清理缓存
	 *
	 * @param ids 主键集合
	 * @return 是否成功
	 */
	boolean removeDict(List<Long> ids);

}
