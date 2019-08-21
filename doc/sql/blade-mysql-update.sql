-- ----------------------------
-- 创建数据源配置表
-- ----------------------------
CREATE TABLE `blade_datasource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) NULL COMMENT '名称',
  `driver_class` varchar(100) NULL COMMENT '驱动类',
  `url` varchar(500) NULL COMMENT '连接地址',
  `username` varchar(50) NULL COMMENT '用户名',
  `password` varchar(50) NULL COMMENT '密码',
  `remark` varchar(255) NULL COMMENT '备注',
  `create_user` bigint(64) NULL DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint(64) NULL DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态',
  `is_deleted` int(2) NULL DEFAULT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) COMMENT = '数据源配置表' ;

-- ----------------------------
-- 插入数据源默认数据
-- ----------------------------
INSERT INTO `blade_datasource`(`id`, `name`, `driver_class`, `url`, `username`, `password`, `remark`, `create_user`, `create_dept`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (1, 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://localhost:3306/blade?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true', 'root', 'root', 'mysql', 1, 1, '2019-08-14 11:43:06', 1, '2019-08-14 11:43:06', 1, 0);
INSERT INTO `blade_datasource`(`id`, `name`, `driver_class`, `url`, `username`, `password`, `remark`, `create_user`, `create_dept`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (2, 'postgresql', 'org.postgresql.Driver', 'jdbc:postgresql://127.0.0.1:5432/blade', 'postgres', '123456', 'postgresql', 1, 1, '2019-08-14 11:43:41', 1, '2019-08-14 11:43:41', 1, 0);
INSERT INTO `blade_datasource`(`id`, `name`, `driver_class`, `url`, `username`, `password`, `remark`, `create_user`, `create_dept`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3, 'oracle', 'oracle.jdbc.OracleDriver', 'jdbc:oracle:thin:@127.0.0.1:49161:orcl', 'BLADE', 'blade', 'oracle', 1, 1, '2019-08-14 11:44:03', 1, '2019-08-14 11:44:03', 1, 0);

-- ----------------------------
-- 插入数据源菜单数据
-- ----------------------------
INSERT INTO `blade_menu`(`parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES (17, 'datasource', '数据源管理', 'menu', '/tool/datasource', 'iconfont icon-caidanguanli', 2, 1, 0, 1, NULL, 0);
set @parentid = (SELECT LAST_INSERT_ID());
INSERT INTO `blade_menu`(`parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES (@parentid, 'datasource_add', '新增', 'add', '/tool/datasource/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `blade_menu`(`parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES (@parentid, 'datasource_edit', '修改', 'edit', '/tool/datasource/edit', 'form', 2, 2, 2, 2, NULL, 0);
INSERT INTO `blade_menu`(`parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES (@parentid, 'datasource_delete', '删除', 'delete', '/api/blade-develop/datasource/remove', 'delete', 3, 2, 3, 3, NULL, 0);
INSERT INTO `blade_menu`(`parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES (@parentid, 'datasource_view', '查看', 'view', '/tool/datasource/view', 'file-text', 4, 2, 2, 2, NULL, 0);

-- ----------------------------
-- 代码生成表增加数据源字段
-- ----------------------------
ALTER TABLE `blade_code`
    ADD COLUMN `datasource_id` bigint(64) NULL COMMENT '数据源主键' AFTER `id`,
    ADD COLUMN `base_mode` int(2) NULL COMMENT '基础业务模式' AFTER `package_name`,
    ADD COLUMN `wrap_mode` int(2) NULL COMMENT '包装器模式' AFTER `base_mode`;


-- ----------------------------
-- 代码生成记录增加数据源字段
-- ----------------------------
UPDATE `blade_code` SET `datasource_id` = 1, `base_mode` = 1, `wrap_mode` = 1 WHERE `id` = 1;
