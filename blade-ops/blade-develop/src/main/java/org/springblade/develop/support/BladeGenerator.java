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
package org.springblade.develop.support;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.PostgreSqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 代码生成器配置类
 *
 * @author Chill
 */
@Data
@Slf4j
public class BladeGenerator {
	/**
	 * 代码所在服务名
	 */
	private String serviceName = "blade-service";
	/**
	 * 代码生成的包名
	 */
	private String packageName = "org.springblade.test";
	/**
	 * 代码后端生成的地址
	 */
	private String packageDir;
	/**
	 * 代码前端生成的地址
	 */
	private String packageWebDir;
	/**
	 * 需要去掉的表前缀
	 */
	private String[] tablePrefix = {"blade_"};
	/**
	 * 需要生成的表名(两者只能取其一)
	 */
	private String[] includeTables = {"blade_test"};
	/**
	 * 需要排除的表名(两者只能取其一)
	 */
	private String[] excludeTables = {};
	/**
	 * 是否包含基础业务字段
	 */
	private Boolean hasSuperEntity = Boolean.FALSE;
	/**
	 * 基础业务字段
	 */
	private String[] superEntityColumns = {"id", "create_time", "create_user", "update_time", "update_user", "status", "is_deleted"};
	/**
	 * 是否启用swagger
	 */
	private Boolean isSwagger2 = Boolean.TRUE;

	public void run() {
		Properties props = getProperties();
		AutoGenerator mpg = new AutoGenerator();
		GlobalConfig gc = new GlobalConfig();
		String outputDir = getOutputDir();
		String author = props.getProperty("author");
		gc.setOutputDir(outputDir);
		gc.setAuthor(author);
		gc.setFileOverride(true);
		gc.setOpen(false);
		gc.setActiveRecord(false);
		gc.setEnableCache(false);
		gc.setBaseResultMap(true);
		gc.setBaseColumnList(true);
		gc.setMapperName("%sMapper");
		gc.setXmlName("%sMapper");
		gc.setServiceName("I%sService");
		gc.setServiceImplName("%sServiceImpl");
		gc.setControllerName("%sController");
		gc.setSwagger2(isSwagger2);
		mpg.setGlobalConfig(gc);
		DataSourceConfig dsc = new DataSourceConfig();
		String driverName = props.getProperty("spring.datasource.driver-class-name");
		if (StringUtil.containsAny(driverName, DbType.MYSQL.getDb())) {
			dsc.setDbType(DbType.MYSQL);
			dsc.setTypeConvert(new MySqlTypeConvert());
		} else {
			dsc.setDbType(DbType.POSTGRE_SQL);
			dsc.setTypeConvert(new PostgreSqlTypeConvert());
		}
		dsc.setUrl(props.getProperty("spring.datasource.url"));
		dsc.setDriverName(driverName);
		dsc.setUsername(props.getProperty("spring.datasource.username"));
		dsc.setPassword(props.getProperty("spring.datasource.password"));
		mpg.setDataSource(dsc);
		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		// strategy.setCapitalMode(true);// 全局大写命名
		// strategy.setDbColumnUnderline(true);//全局下划线命名
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
		strategy.setTablePrefix(tablePrefix);
		if (includeTables.length > 0) {
			strategy.setInclude(includeTables);
		}
		if (excludeTables.length > 0) {
			strategy.setExclude(excludeTables);
		}
		if (hasSuperEntity) {
			strategy.setSuperEntityClass("org.springblade.core.mp.base.BaseEntity");
			strategy.setSuperEntityColumns(superEntityColumns);
			strategy.setSuperServiceClass("org.springblade.core.mp.base.BaseService");
			strategy.setSuperServiceImplClass("org.springblade.core.mp.base.BaseServiceImpl");
		} else {
			strategy.setSuperServiceClass("com.baomidou.mybatisplus.extension.service.IService");
			strategy.setSuperServiceImplClass("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");
		}
		// 自定义 controller 父类
		strategy.setSuperControllerClass("org.springblade.core.boot.ctrl.BladeController");
		strategy.setEntityBuilderModel(false);
		strategy.setEntityLombokModel(true);
		strategy.setControllerMappingHyphenStyle(true);
		mpg.setStrategy(strategy);
		// 包配置
		PackageConfig pc = new PackageConfig();
		// 控制台扫描
		pc.setModuleName(null);
		pc.setParent(packageName);
		pc.setController("controller");
		pc.setEntity("entity");
		pc.setXml("mapper");
		mpg.setPackageInfo(pc);
		mpg.setCfg(getInjectionConfig());
		mpg.execute();
	}

	private InjectionConfig getInjectionConfig() {
		String servicePackage = serviceName.split("-").length > 1 ? serviceName.split("-")[1] : serviceName;
		// 自定义配置
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				Map<String, Object> map = new HashMap<>(16);
				map.put("serviceName", serviceName);
				map.put("servicePackage", servicePackage);
				this.setMap(map);
			}
		};
		List<FileOutConfig> focList = new ArrayList<>();
		focList.add(new FileOutConfig("/templates/entityVO.java.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				return getOutputDir() + "/" + packageName.replace(".", "/") + "/" + "vo" + "/" + tableInfo.getEntityName() + "VO" + StringPool.DOT_JAVA;
			}
		});
		focList.add(new FileOutConfig("/templates/entityDTO.java.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				return getOutputDir() + "/" + packageName.replace(".", "/") + "/" + "dto" + "/" + tableInfo.getEntityName() + "DTO" + StringPool.DOT_JAVA;
			}
		});
		focList.add(new FileOutConfig("/templates/wrapper.java.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				return getOutputDir() + "/" + packageName.replace(".", "/") + "/" + "wrapper" + "/" + tableInfo.getEntityName() + "Wrapper" + StringPool.DOT_JAVA;
			}
		});
		if (Func.isNotBlank(packageWebDir)) {
			focList.add(new FileOutConfig("/templates/sword/action.js.vm") {
				@Override
				public String outputFile(TableInfo tableInfo) {
					return getOutputWebDir() + "/actions" + "/" + tableInfo.getEntityName().toLowerCase() + ".js";
				}
			});
			focList.add(new FileOutConfig("/templates/sword/model.js.vm") {
				@Override
				public String outputFile(TableInfo tableInfo) {
					return getOutputWebDir() + "/models" + "/" + tableInfo.getEntityName().toLowerCase() + ".js";
				}
			});
			focList.add(new FileOutConfig("/templates/sword/service.js.vm") {
				@Override
				public String outputFile(TableInfo tableInfo) {
					return getOutputWebDir() + "/services" + "/" + tableInfo.getEntityName().toLowerCase() + ".js";
				}
			});
			focList.add(new FileOutConfig("/templates/sword/list.js.vm") {
				@Override
				public String outputFile(TableInfo tableInfo) {
					return getOutputWebDir() + "/pages" + "/" + StringUtil.upperFirst(servicePackage) + "/" + tableInfo.getEntityName() + "/" + tableInfo.getEntityName() + ".js";
				}
			});
			focList.add(new FileOutConfig("/templates/sword/add.js.vm") {
				@Override
				public String outputFile(TableInfo tableInfo) {
					return getOutputWebDir() + "/pages" + "/" + StringUtil.upperFirst(servicePackage) + "/" + tableInfo.getEntityName() + "/" + tableInfo.getEntityName() + "Add.js";
				}
			});
			focList.add(new FileOutConfig("/templates/sword/edit.js.vm") {
				@Override
				public String outputFile(TableInfo tableInfo) {
					return getOutputWebDir() + "/pages" + "/" + StringUtil.upperFirst(servicePackage) + "/" + tableInfo.getEntityName() + "/" + tableInfo.getEntityName() + "Edit.js";
				}
			});
			focList.add(new FileOutConfig("/templates/sword/view.js.vm") {
				@Override
				public String outputFile(TableInfo tableInfo) {
					return getOutputWebDir() + "/pages" + "/" + StringUtil.upperFirst(servicePackage) + "/" + tableInfo.getEntityName() + "/" + tableInfo.getEntityName() + "View.js";
				}
			});
		}
		cfg.setFileOutConfigList(focList);
		return cfg;
	}

	/**
	 * 获取配置文件
	 *
	 * @return 配置Props
	 */
	private Properties getProperties() {
		// 读取配置文件
		Resource resource = new ClassPathResource("/templates/props/generator.properties");
		Properties props = new Properties();
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}

	/**
	 * 生成到项目中
	 *
	 * @return outputDir
	 */
	public String getOutputDir() {
		return Func.isBlank(packageDir) ? System.getProperty("user.dir") : packageDir + "/src/main/java";
	}

	/**
	 * 生成到Web项目中
	 *
	 * @return outputDir
	 */
	public String getOutputWebDir() {
		return Func.isBlank(packageWebDir) ? System.getProperty("user.dir") : packageWebDir + "/src";
	}

	/**
	 * 页面生成的文件名
	 */
	private String getGeneratorViewPath(String viewOutputDir, TableInfo tableInfo, String suffixPath) {
		String name = StringUtils.firstToLowerCase(tableInfo.getEntityName());
		String path = viewOutputDir + "/" + name + "/" + name + suffixPath;
		File viewDir = new File(path).getParentFile();
		if (!viewDir.exists()) {
			viewDir.mkdirs();
		}
		return path;
	}

}
