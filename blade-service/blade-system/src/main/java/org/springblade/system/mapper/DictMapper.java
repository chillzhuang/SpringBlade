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
package org.springblade.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.system.entity.Dict;
import org.springblade.system.vo.DictVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author Chill
 */
public interface DictMapper extends BaseMapper<Dict> {

	/**
	 * 自定义分页
	 *
	 * @param page  分页参数
	 * @param dict  字典查询条件
	 * @return 字典分页数据
	 */
	List<DictVO> selectDictPage(IPage page, DictVO dict);

	/**
	 * 获取字典表对应中文
	 *
	 * @param code    字典编号
	 * @param dictKey 字典序号
	 * @return 字典对应的中文值
	 */
	String getValue(String code, Integer dictKey);

	/**
	 * 获取字典表
	 *
	 * @param code 字典编号
	 * @return 字典数据集合
	 */
	List<Dict> getList(String code);

	/**
	 * 获取树形节点
	 *
	 * @return 字典树形结构
	 */
	List<DictVO> tree();

}
