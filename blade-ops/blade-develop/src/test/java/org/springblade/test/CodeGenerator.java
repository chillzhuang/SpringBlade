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
package org.springblade.test;


import org.springblade.develop.support.BladeCodeGenerator;

/**
 * 代码生成器
 *
 * @author Chill
 */
public class CodeGenerator {

	/**
	 * 代码生成的模块名
	 */
	public static String CODE_NAME = "租户管理";
	/**
	 * 代码所在服务名
	 */
	public static String SERVICE_NAME = "blade-system";
	/**
	 * 代码生成的包名
	 */
	public static String PACKAGE_NAME = "org.springblade.system";
	/**
	 * 前端代码生成所属系统
	 */
	public static String SYSTEM_NAME = "sword";
	/**
	 * 前端代码生成地址
	 */
	public static String PACKAGE_WEB_DIR = "/Users/chill/Workspaces/product/Sword";
	/**
	 * 需要去掉的表前缀
	 */
	public static String[] TABLE_PREFIX = {"blade_"};
	/**
	 * 需要生成的表名(两者只能取其一)
	 */
	public static String[] INCLUDE_TABLES = {"blade_tenant"};
	/**
	 * 需要排除的表名(两者只能取其一)
	 */
	public static String[] EXCLUDE_TABLES = {};
	/**
	 * 是否包含基础业务字段
	 */
	public static Boolean HAS_SUPER_ENTITY = Boolean.TRUE;
	/**
	 * 基础业务字段
	 */
	public static String[] SUPER_ENTITY_COLUMNS = {"create_time", "create_user", "update_time", "update_user", "status", "is_deleted"};


	/**
	 * RUN THIS
	 */
	public static void main(String[] args) {
		BladeCodeGenerator generator = new BladeCodeGenerator();
		generator.setCodeName(CODE_NAME);
		generator.setServiceName(SERVICE_NAME);
		generator.setSystemName(SYSTEM_NAME);
		generator.setPackageName(PACKAGE_NAME);
		generator.setPackageWebDir(PACKAGE_WEB_DIR);
		generator.setTablePrefix(TABLE_PREFIX);
		generator.setIncludeTables(INCLUDE_TABLES);
		generator.setExcludeTables(EXCLUDE_TABLES);
		generator.setHasSuperEntity(HAS_SUPER_ENTITY);
		generator.setSuperEntityColumns(SUPER_ENTITY_COLUMNS);
		generator.run();
	}

}
