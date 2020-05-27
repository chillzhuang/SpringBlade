/*
 Navicat Premium Data Transfer

 Source Server         : mysql_localhost
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : blade

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 22/04/2020 16:42:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blade_client
-- ----------------------------
DROP TABLE IF EXISTS `blade_client`;
CREATE TABLE `blade_client`  (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `client_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端id',
  `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端密钥',
  `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源集合',
  `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '授权范围',
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '授权类型',
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回调地址',
  `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限',
  `access_token_validity` int(11) NOT NULL COMMENT '令牌过期秒数',
  `refresh_token_validity` int(11) NOT NULL COMMENT '刷新令牌过期秒数',
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附加说明',
  `autoapprove` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自动授权',
  `create_user` bigint(64) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NOT NULL COMMENT '状态',
  `is_deleted` int(2) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户端表';

-- ----------------------------
-- Records of blade_client
-- ----------------------------
BEGIN;
INSERT INTO `blade_client` VALUES (1123598811738675201, 'sword', 'sword_secret', NULL, 'all', 'refresh_token,password,authorization_code', 'http://localhost:8888', NULL, 3600, 604800, NULL, NULL, 1, '2019-03-24 10:40:55', 1, '2019-03-24 10:40:59', 1, 0), (1123598811738675202, 'saber', 'saber_secret', NULL, 'all', 'refresh_token,password,authorization_code', 'http://localhost:8080', NULL, 3600, 604800, NULL, NULL, 1, '2019-03-24 10:42:29', 1, '2019-03-24 10:42:32', 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for blade_code
-- ----------------------------
DROP TABLE IF EXISTS `blade_code`;
CREATE TABLE `blade_code`  (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `datasource_id` bigint(64) NULL DEFAULT NULL COMMENT '数据源主键',
  `service_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务名称',
  `code_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块名称',
  `table_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表名',
  `table_prefix` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表前缀',
  `pk_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主键名',
  `package_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '后端包名',
  `base_mode` int(2) NULL DEFAULT NULL COMMENT '基础业务模式',
  `wrap_mode` int(2) NULL DEFAULT NULL COMMENT '包装器模式',
  `api_path` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '后端路径',
  `web_path` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端路径',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成表';

-- ----------------------------
-- Records of blade_code
-- ----------------------------
BEGIN;
INSERT INTO `blade_code` VALUES (1123598812738675201, 1123598812738675201, 'blade-demo', '通知公告', 'blade_notice', 'blade_', 'id', 'org.springblade.desktop', 1, 1, 'D:\\Develop\\WorkSpace\\Git\\SpringBlade\\blade-ops\\blade-develop', 'D:\\Develop\\WorkSpace\\Git\\Sword', 0);
COMMIT;

-- ----------------------------
-- Table structure for blade_datasource
-- ----------------------------
DROP TABLE IF EXISTS `blade_datasource`;
CREATE TABLE `blade_datasource`  (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `driver_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '驱动类',
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '连接地址',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` bigint(64) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态',
  `is_deleted` int(2) NULL DEFAULT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据源配置表';

-- ----------------------------
-- Records of blade_datasource
-- ----------------------------
BEGIN;
INSERT INTO `blade_datasource` VALUES (1123598812738675201, 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://localhost:3306/blade?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true', 'root', 'root', 'mysql', 1, '2019-08-14 11:43:06', 1, '2019-08-14 11:43:06', 1, 0), (1123598812738675202, 'postgresql', 'org.postgresql.Driver', 'jdbc:postgresql://127.0.0.1:5432/blade', 'postgres', '123456', 'postgresql', 1, '2019-08-14 11:43:41', 1, '2019-08-14 11:43:41', 1, 0), (1123598812738675203, 'oracle', 'oracle.jdbc.OracleDriver', 'jdbc:oracle:thin:@127.0.0.1:49161:orcl', 'BLADE', 'blade', 'oracle', 1, '2019-08-14 11:44:03', 1, '2019-08-14 11:44:03', 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for blade_dept
-- ----------------------------
DROP TABLE IF EXISTS `blade_dept`;
CREATE TABLE `blade_dept`  (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',
  `parent_id` bigint(64) NULL DEFAULT 0 COMMENT '父主键',
  `dept_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门名',
  `full_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门全称',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表';

-- ----------------------------
-- Records of blade_dept
-- ----------------------------
BEGIN;
INSERT INTO `blade_dept` VALUES (1123598813738675201, '000000', 0, '刀锋科技', '江苏刀锋科技有限公司', 1, NULL, 0), (1123598813738675202, '000000', 1123598813738675201, '常州刀锋', '常州刀锋科技有限公司', 1, NULL, 0), (1123598813738675203, '000000', 1123598813738675201, '苏州刀锋', '苏州刀锋科技有限公司', 1, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for blade_dict
-- ----------------------------
DROP TABLE IF EXISTS `blade_dict`;
CREATE TABLE `blade_dict`  (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `parent_id` bigint(64) NULL DEFAULT 0 COMMENT '父主键',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典码',
  `dict_key` int(2) NULL DEFAULT NULL COMMENT '字典值',
  `dict_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典名称',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典备注',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典表';

-- ----------------------------
-- Records of blade_dict
-- ----------------------------
BEGIN;
INSERT INTO `blade_dict` VALUES (1123598814738675201, 0, 'sex', -1, '性别', 1, NULL, 0), (1123598814738675202, 1123598814738675201, 'sex', 1, '男', 1, NULL, 0), (1123598814738675203, 1123598814738675201, 'sex', 2, '女', 2, NULL, 0), (1123598814738675204, 0, 'notice', -1, '通知类型', 2, NULL, 0), (1123598814738675205, 1123598814738675204, 'notice', 1, '发布通知', 1, NULL, 0), (1123598814738675206, 1123598814738675204, 'notice', 2, '批转通知', 2, NULL, 0), (1123598814738675207, 1123598814738675204, 'notice', 3, '转发通知', 3, NULL, 0), (1123598814738675208, 1123598814738675204, 'notice', 4, '指示通知', 4, NULL, 0), (1123598814738675209, 1123598814738675204, 'notice', 5, '任免通知', 5, NULL, 0), (1123598814738675210, 1123598814738675204, 'notice', 6, '事务通知', 6, NULL, 0), (1123598814738675211, 0, 'menu_category', -1, '菜单类型', 3, NULL, 0), (1123598814738675212, 1123598814738675211, 'menu_category', 1, '菜单', 1, NULL, 0), (1123598814738675213, 1123598814738675211, 'menu_category', 2, '按钮', 2, NULL, 0), (1123598814738675214, 0, 'button_func', -1, '按钮功能', 4, NULL, 0), (1123598814738675215, 1123598814738675214, 'button_func', 1, '工具栏', 1, NULL, 0), (1123598814738675216, 1123598814738675214, 'button_func', 2, '操作栏', 2, NULL, 0), (1123598814738675217, 1123598814738675214, 'button_func', 3, '工具操作栏', 3, NULL, 0), (1123598814738675218, 0, 'yes_no', -1, '是否', 5, NULL, 0), (1123598814738675219, 1123598814738675218, 'yes_no', 1, '否', 1, NULL, 0), (1123598814738675220, 1123598814738675218, 'yes_no', 2, '是', 2, NULL, 0), (1123598814738777220, 0, 'post_category', -1, '岗位类型', 12, NULL, 0), (1123598814738777221, 1123598814738777220, 'post_category', 1, '高层', 1, NULL, 0), (1123598814738777222, 1123598814738777220, 'post_category', 2, '中层', 2, NULL, 0), (1123598814738777223, 1123598814738777220, 'post_category', 3, '基层', 3, NULL, 0), (1123598814738777224, 1123598814738777220, 'post_category', 4, '其他', 4, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for blade_log_api
-- ----------------------------
DROP TABLE IF EXISTS `blade_log_api`;
CREATE TABLE `blade_log_api`  (
  `id` bigint(64) NOT NULL COMMENT '编号',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',
  `service_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务ID',
  `server_host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器名',
  `server_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器IP地址',
  `env` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器环境',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '日志类型',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '日志标题',
  `method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作方式',
  `request_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求URI',
  `user_agent` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户代理',
  `remote_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作IP地址',
  `method_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法类',
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法名',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '操作提交的数据',
  `time` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '接口日志表';

-- ----------------------------
-- Table structure for blade_log_error
-- ----------------------------
DROP TABLE IF EXISTS `blade_log_error`;
CREATE TABLE `blade_log_error`  (
  `id` bigint(64) NOT NULL COMMENT '编号',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',
  `service_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务ID',
  `server_host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器名',
  `server_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器IP地址',
  `env` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统环境',
  `method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作方式',
  `request_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求URI',
  `user_agent` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户代理',
  `stack_trace` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '堆栈',
  `exception_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '异常名',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '异常信息',
  `line_number` int(11) NULL DEFAULT NULL COMMENT '错误行数',
  `remote_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作IP地址',
  `method_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法类',
  `file_name` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法名',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '操作提交的数据',
  `time` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '错误日志表';

-- ----------------------------
-- Table structure for blade_log_usual
-- ----------------------------
DROP TABLE IF EXISTS `blade_log_usual`;
CREATE TABLE `blade_log_usual`  (
  `id` bigint(64) NOT NULL COMMENT '编号',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',
  `service_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务ID',
  `server_host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器名',
  `server_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器IP地址',
  `env` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统环境',
  `log_level` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志级别',
  `log_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志业务id',
  `log_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '日志数据',
  `method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作方式',
  `request_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求URI',
  `remote_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作IP地址',
  `method_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法类',
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法名',
  `user_agent` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户代理',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '操作提交的数据',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '执行时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通用日志表';

-- ----------------------------
-- Table structure for blade_menu
-- ----------------------------
DROP TABLE IF EXISTS `blade_menu`;
CREATE TABLE `blade_menu`  (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `parent_id` bigint(64) NULL DEFAULT 0 COMMENT '父级菜单',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `alias` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单别名',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求地址',
  `source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单资源',
  `sort` int(2) NULL DEFAULT NULL COMMENT '排序',
  `category` int(2) NULL DEFAULT NULL COMMENT '菜单类型',
  `action` int(2) NULL DEFAULT 0 COMMENT '操作按钮类型',
  `is_open` int(2) NULL DEFAULT 1 COMMENT '是否打开新页面',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表';

-- ----------------------------
-- Records of blade_menu
-- ----------------------------
BEGIN;
INSERT INTO `blade_menu` VALUES (1123598815738675201, 0, 'desk', '工作台', 'menu', '/desk', 'iconfont iconicon_airplay', 1, 1, 0, 1, NULL, 0), (1123598815738675202, 1123598815738675201, 'notice', '通知公告', 'menu', '/desk/notice', 'iconfont iconicon_sms', 1, 1, 0, 1, NULL, 0), (1123598815738675203, 0, 'system', '系统管理', 'menu', '/system', 'iconfont iconicon_setting', 2, 1, 0, 1, NULL, 0), (1123598815738675204, 1123598815738675203, 'user', '用户管理', 'menu', '/system/user', 'iconfont iconicon_principal', 1, 1, 0, 1, NULL, 0), (1123598815738675205, 1123598815738675203, 'dept', '部门管理', 'menu', '/system/dept', 'iconfont iconicon_group', 2, 1, 0, 1, NULL, 0), (1123598815738675206, 1123598815738675203, 'dict', '字典管理', 'menu', '/system/dict', 'iconfont iconicon_addresslist', 3, 1, 0, 1, NULL, 0), (1123598815738675207, 1123598815738675203, 'menu', '菜单管理', 'menu', '/system/menu', 'iconfont iconicon_subordinate', 4, 1, 0, 1, NULL, 0), (1123598815738675208, 1123598815738675203, 'role', '角色管理', 'menu', '/system/role', 'iconfont iconicon_boss', 5, 1, 0, 1, NULL, 0), (1123598815738675209, 1123598815738675203, 'param', '参数管理', 'menu', '/system/param', 'iconfont iconicon_community_line', 6, 1, 0, 1, NULL, 0), (1123598815738675210, 0, 'monitor', '系统监控', 'menu', '/monitor', 'iconfont icon-yanzhengma', 3, 1, 0, 1, NULL, 0), (1123598815738675211, 1123598815738675210, 'doc', '接口文档', 'menu', 'http://localhost/doc.html', 'iconfont iconicon_study', 1, 1, 0, 2, NULL, 0), (1123598815738675212, 1123598815738675210, 'admin', '服务治理', 'menu', 'http://localhost:7002', 'iconfont icon-canshu', 2, 1, 0, 2, NULL, 0), (1123598815738675213, 1123598815738675210, 'log', '日志管理', 'menu', '/monitor/log', 'iconfont iconicon_doc', 3, 1, 0, 1, NULL, 0), (1123598815738675214, 1123598815738675213, 'log_usual', '通用日志', 'menu', '/monitor/log/usual', NULL, 1, 1, 0, 1, NULL, 0), (1123598815738675215, 1123598815738675213, 'log_api', '接口日志', 'menu', '/monitor/log/api', NULL, 2, 1, 0, 1, NULL, 0), (1123598815738675216, 1123598815738675213, 'log_error', '错误日志', 'menu', '/monitor/log/error', NULL, 3, 1, 0, 1, NULL, 0), (1123598815738675217, 0, 'tool', '研发工具', 'menu', '/tool', 'iconfont icon-wxbgongju', 4, 1, 0, 1, NULL, 0), (1123598815738675218, 1123598815738675217, 'code', '代码生成', 'menu', '/tool/code', 'iconfont iconicon_savememo', 1, 1, 0, 1, NULL, 0), (1123598815738675219, 1123598815738675202, 'notice_add', '新增', 'add', '/desk/notice/add', 'plus', 1, 2, 1, 1, NULL, 0), (1123598815738675220, 1123598815738675202, 'notice_edit', '修改', 'edit', '/desk/notice/edit', 'form', 2, 2, 2, 1, NULL, 0), (1123598815738675221, 1123598815738675202, 'notice_delete', '删除', 'delete', '/api/blade-desk/notice/remove', 'delete', 3, 2, 3, 1, NULL, 0), (1123598815738675222, 1123598815738675202, 'notice_view', '查看', 'view', '/desk/notice/view', 'file-text', 4, 2, 2, 1, NULL, 0), (1123598815738675223, 1123598815738675204, 'user_add', '新增', 'add', '/system/user/add', 'plus', 1, 2, 1, 1, NULL, 0), (1123598815738675224, 1123598815738675204, 'user_edit', '修改', 'edit', '/system/user/edit', 'form', 2, 2, 2, 1, NULL, 0), (1123598815738675225, 1123598815738675204, 'user_delete', '删除', 'delete', '/api/blade-user/remove', 'delete', 3, 2, 3, 1, NULL, 0), (1123598815738675226, 1123598815738675204, 'user_role', '角色配置', 'role', NULL, 'user-add', 4, 2, 1, 1, NULL, 0), (1123598815738675227, 1123598815738675204, 'user_reset', '密码重置', 'reset-password', '/api/blade-user/reset-password', 'retweet', 5, 2, 1, 1, NULL, 0), (1123598815738675228, 1123598815738675204, 'user_view', '查看', 'view', '/system/user/view', 'file-text', 6, 2, 2, 1, NULL, 0), (1123598815738675229, 1123598815738675205, 'dept_add', '新增', 'add', '/system/dept/add', 'plus', 1, 2, 1, 1, NULL, 0), (1123598815738675230, 1123598815738675205, 'dept_edit', '修改', 'edit', '/system/dept/edit', 'form', 2, 2, 2, 1, NULL, 0), (1123598815738675231, 1123598815738675205, 'dept_delete', '删除', 'delete', '/api/blade-system/dept/remove', 'delete', 3, 2, 3, 1, NULL, 0), (1123598815738675232, 1123598815738675205, 'dept_view', '查看', 'view', '/system/dept/view', 'file-text', 4, 2, 2, 1, NULL, 0), (1123598815738675233, 1123598815738675206, 'dict_add', '新增', 'add', '/system/dict/add', 'plus', 1, 2, 1, 1, NULL, 0), (1123598815738675234, 1123598815738675206, 'dict_edit', '修改', 'edit', '/system/dict/edit', 'form', 2, 2, 2, 1, NULL, 0), (1123598815738675235, 1123598815738675206, 'dict_delete', '删除', 'delete', '/api/blade-system/dict/remove', 'delete', 3, 2, 3, 1, NULL, 0), (1123598815738675236, 1123598815738675206, 'dict_view', '查看', 'view', '/system/dict/view', 'file-text', 4, 2, 2, 1, NULL, 0), (1123598815738675237, 1123598815738675207, 'menu_add', '新增', 'add', '/system/menu/add', 'plus', 1, 2, 1, 1, NULL, 0), (1123598815738675238, 1123598815738675207, 'menu_edit', '修改', 'edit', '/system/menu/edit', 'form', 2, 2, 2, 1, NULL, 0), (1123598815738675239, 1123598815738675207, 'menu_delete', '删除', 'delete', '/api/blade-system/menu/remove', 'delete', 3, 2, 3, 1, NULL, 0), (1123598815738675240, 1123598815738675207, 'menu_view', '查看', 'view', '/system/menu/view', 'file-text', 4, 2, 2, 1, NULL, 0), (1123598815738675241, 1123598815738675208, 'role_add', '新增', 'add', '/system/role/add', 'plus', 1, 2, 1, 1, NULL, 0), (1123598815738675242, 1123598815738675208, 'role_edit', '修改', 'edit', '/system/role/edit', 'form', 2, 2, 2, 1, NULL, 0), (1123598815738675243, 1123598815738675208, 'role_delete', '删除', 'delete', '/api/blade-system/role/remove', 'delete', 3, 2, 3, 1, NULL, 0), (1123598815738675244, 1123598815738675208, 'role_view', '查看', 'view', '/system/role/view', 'file-text', 4, 2, 2, 1, NULL, 0), (1123598815738675245, 1123598815738675209, 'param_add', '新增', 'add', '/system/param/add', 'plus', 1, 2, 1, 1, NULL, 0), (1123598815738675246, 1123598815738675209, 'param_edit', '修改', 'edit', '/system/param/edit', 'form', 2, 2, 2, 1, NULL, 0), (1123598815738675247, 1123598815738675209, 'param_delete', '删除', 'delete', '/api/blade-system/param/remove', 'delete', 3, 2, 3, 1, NULL, 0), (1123598815738675248, 1123598815738675209, 'param_view', '查看', 'view', '/system/param/view', 'file-text', 4, 2, 2, 1, NULL, 0), (1123598815738675249, 1123598815738675214, 'log_usual_view', '查看', 'view', '/monitor/log/usual/view', 'file-text', 4, 2, 2, 1, NULL, 0), (1123598815738675250, 1123598815738675215, 'log_api_view', '查看', 'view', '/monitor/log/api/view', 'file-text', 4, 2, 2, 1, NULL, 0), (1123598815738675251, 1123598815738675216, 'log_error_view', '查看', 'view', '/monitor/log/error/view', 'file-text', 4, 2, 2, 1, NULL, 0), (1123598815738675252, 1123598815738675218, 'code_add', '新增', 'add', '/tool/code/add', 'plus', 1, 2, 1, 1, NULL, 0), (1123598815738675253, 1123598815738675218, 'code_edit', '修改', 'edit', '/tool/code/edit', 'form', 2, 2, 2, 1, NULL, 0), (1123598815738675254, 1123598815738675218, 'code_delete', '删除', 'delete', '/api/blade-system/code/remove', 'delete', 3, 2, 3, 1, NULL, 0), (1123598815738675255, 1123598815738675218, 'code_view', '查看', 'view', '/tool/code/view', 'file-text', 4, 2, 2, 1, NULL, 0), (1123598815738675256, 1123598815738675203, 'tenant', '租户管理', 'menu', '/system/tenant', 'iconfont icon-quanxian', 7, 1, 0, 1, NULL, 0), (1123598815738675257, 1123598815738675256, 'tenant_add', '新增', 'add', '/system/tenant/add', 'plus', 1, 2, 1, 1, NULL, 0), (1123598815738675258, 1123598815738675256, 'tenant_edit', '修改', 'edit', '/system/tenant/edit', 'form', 2, 2, 2, 1, NULL, 0), (1123598815738675259, 1123598815738675256, 'tenant_delete', '删除', 'delete', '/api/blade-system/tenant/remove', 'delete', 3, 2, 3, 1, NULL, 0), (1123598815738675260, 1123598815738675256, 'tenant_view', '查看', 'view', '/system/tenant/view', 'file-text', 4, 2, 2, 1, NULL, 0), (1123598815738675261, 1123598815738675203, 'client', '应用管理', 'menu', '/system/client', 'iconfont iconicon_mobilephone', 8, 1, 0, 1, NULL, 0), (1123598815738675262, 1123598815738675261, 'client_add', '新增', 'add', '/system/client/add', 'plus', 1, 2, 1, 1, NULL, 0), (1123598815738675263, 1123598815738675261, 'client_edit', '修改', 'edit', '/system/client/edit', 'form', 2, 2, 2, 2, NULL, 0), (1123598815738675264, 1123598815738675261, 'client_delete', '删除', 'delete', '/api/blade-system/client/remove', 'delete', 3, 2, 3, 3, NULL, 0), (1123598815738675265, 1123598815738675261, 'client_view', '查看', 'view', '/system/client/view', 'file-text', 4, 2, 2, 2, NULL, 0), (1123598815738675266, 1123598815738675217, 'datasource', '数据源管理', 'menu', '/tool/datasource', 'iconfont icon-caidanguanli', 2, 1, 0, 1, NULL, 0), (1123598815738675267, 1123598815738675266, 'datasource_add', '新增', 'add', '/tool/datasource/add', 'plus', 1, 2, 1, 1, NULL, 0), (1123598815738675268, 1123598815738675266, 'datasource_edit', '修改', 'edit', '/tool/datasource/edit', 'form', 2, 2, 2, 2, NULL, 0), (1123598815738675269, 1123598815738675266, 'datasource_delete', '删除', 'delete', '/api/blade-develop/datasource/remove', 'delete', 3, 2, 3, 3, NULL, 0), (1123598815738675270, 1123598815738675266, 'datasource_view', '查看', 'view', '/tool/datasource/view', 'file-text', 4, 2, 2, 2, NULL, 0), (1164733389668962251, 1123598815738675203, 'post', '岗位管理', 'menu', '/system/post', 'iconfont iconicon_message', 2, 1, 0, 1, NULL, 0), (1164733389668962252, 1164733389668962251, 'post_add', '新增', 'add', '/system/post/add', 'plus', 1, 2, 1, 1, NULL, 0), (1164733389668962253, 1164733389668962251, 'post_edit', '修改', 'edit', '/system/post/edit', 'form', 2, 2, 2, 1, NULL, 0), (1164733389668962254, 1164733389668962251, 'post_delete', '删除', 'delete', '/api/blade-system/post/remove', 'delete', 3, 2, 3, 1, NULL, 0), (1164733389668962255, 1164733389668962251, 'post_view', '查看', 'view', '/system/post/view', 'file-text', 4, 2, 2, 1, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for blade_notice
-- ----------------------------
DROP TABLE IF EXISTS `blade_notice`;
CREATE TABLE `blade_notice`  (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `category` int(11) NULL DEFAULT NULL COMMENT '类型',
  `release_time` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
  `create_user` bigint(64) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态',
  `is_deleted` int(2) NULL DEFAULT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通知公告表';

-- ----------------------------
-- Records of blade_notice
-- ----------------------------
BEGIN;
INSERT INTO `blade_notice` VALUES (1123598818738675223, '000000', '测试公告', 3, '2018-12-31 20:03:31', '222', 1123598821738675201, '2018-12-05 20:03:31', 1123598821738675201, '2018-12-28 11:10:51', 1, 0), (1123598818738675224, '000000', '测试公告2', 1, '2018-12-05 20:03:31', '333', 1123598821738675201, '2018-12-28 10:32:26', 1123598821738675201, '2018-12-28 11:10:34', 1, 0), (1123598818738675225, '000000', '测试公告3', 6, '2018-12-29 00:00:00', '11111', 1123598821738675201, '2018-12-28 11:03:44', 1123598821738675201, '2018-12-28 11:10:28', 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for blade_param
-- ----------------------------
DROP TABLE IF EXISTS `blade_param`;
CREATE TABLE `blade_param`  (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `param_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数名',
  `param_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数键',
  `param_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数值',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` bigint(64) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '参数表';

-- ----------------------------
-- Records of blade_param
-- ----------------------------
BEGIN;
INSERT INTO `blade_param` VALUES (1123598819738675201, '是否开启注册功能', 'account.registerUser', 'true', '开启注册', 1123598821738675201, '2018-12-28 12:19:01', 1123598821738675201, '2018-12-28 12:19:01', 1, 0), (1123598819738675202, '账号初始密码', 'account.initPassword', '123456', '初始密码', 1123598821738675201, '2018-12-28 12:19:01', 1123598821738675201, '2018-12-28 12:19:01', 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for blade_post
-- ----------------------------
DROP TABLE IF EXISTS `blade_post`;
CREATE TABLE `blade_post`  (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',
  `category` int(11) NULL DEFAULT NULL COMMENT '岗位类型',
  `post_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '岗位编号',
  `post_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '岗位名称',
  `sort` int(2) NULL DEFAULT NULL COMMENT '岗位排序',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '岗位描述',
  `create_user` bigint(64) NULL DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint(64) NULL DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态',
  `is_deleted` int(2) NULL DEFAULT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位表';

-- ----------------------------
-- Records of blade_post
-- ----------------------------
BEGIN;
INSERT INTO `blade_post` VALUES (1123598817738675201, '000000', 1, 'ceo', '首席执行官', 1, '总经理', 1123598821738675201, 1123598813738675201, '2020-04-01 00:00:00', 1123598821738675201, '2020-04-01 00:00:00', 1, 0), (1123598817738675202, '000000', 1, 'coo', '首席运营官', 2, '常务总经理', 1123598821738675201, 1123598813738675201, '2020-04-01 00:00:00', 1123598821738675201, '2020-04-01 00:00:00', 1, 0), (1123598817738675203, '000000', 1, 'cfo', '首席财务官', 3, '财务总经理', 1123598821738675201, 1123598813738675201, '2020-04-01 00:00:00', 1123598821738675201, '2020-04-01 00:00:00', 1, 0), (1123598817738675204, '000000', 1, 'cto', '首席技术官', 4, '技术总监', 1123598821738675201, 1123598813738675201, '2020-04-01 00:00:00', 1123598821738675201, '2020-04-01 00:00:00', 1, 0), (1123598817738675205, '000000', 1, 'cio', '首席信息官', 5, '信息总监', 1123598821738675201, 1123598813738675201, '2020-04-01 00:00:00', 1123598821738675201, '2020-04-01 00:00:00', 1, 0), (1123598817738675206, '000000', 2, 'pm', '技术经理', 6, '研发和产品是永远的朋友', 1123598821738675201, 1123598813738675201, '2020-04-01 00:00:00', 1123598821738675201, '2020-04-01 00:00:00', 1, 0), (1123598817738675207, '000000', 2, 'hrm', '人力经理', 7, '人力资源部门工作管理者', 1123598821738675201, 1123598813738675201, '2020-04-01 00:00:00', 1123598821738675201, '2020-04-01 00:00:00', 1, 0), (1123598817738675208, '000000', 3, 'staff', '普通员工', 8, '普通员工', 1123598821738675201, 1123598813738675201, '2020-04-01 00:00:00', 1123598821738675201, '2020-04-01 00:00:00', 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for blade_role
-- ----------------------------
DROP TABLE IF EXISTS `blade_role`;
CREATE TABLE `blade_role`  (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',
  `parent_id` bigint(64) NULL DEFAULT 0 COMMENT '父主键',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `role_alias` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色别名',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表';

-- ----------------------------
-- Records of blade_role
-- ----------------------------
BEGIN;
INSERT INTO `blade_role` VALUES (1123598816738675201, '000000', 0, '超级管理员', 1, 'administrator', 0), (1123598816738675202, '000000', 0, '用户', 2, 'user', 0);
COMMIT;

-- ----------------------------
-- Table structure for blade_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `blade_role_menu`;
CREATE TABLE `blade_role_menu`  (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `menu_id` bigint(64) NULL DEFAULT NULL COMMENT '菜单id',
  `role_id` bigint(64) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单表';

-- ----------------------------
-- Records of blade_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `blade_role_menu` VALUES (1123598817738675201, 1123598815738675201, 1123598816738675201), (1123598817738675202, 1123598815738675202, 1123598816738675201), (1123598817738675203, 1123598815738675203, 1123598816738675201), (1123598817738675204, 1123598815738675204, 1123598816738675201), (1123598817738675205, 1123598815738675205, 1123598816738675201), (1123598817738675206, 1123598815738675206, 1123598816738675201), (1123598817738675207, 1123598815738675207, 1123598816738675201), (1123598817738675208, 1123598815738675208, 1123598816738675201), (1123598817738675209, 1123598815738675209, 1123598816738675201), (1123598817738675210, 1123598815738675210, 1123598816738675201), (1123598817738675211, 1123598815738675211, 1123598816738675201), (1123598817738675212, 1123598815738675212, 1123598816738675201), (1123598817738675213, 1123598815738675213, 1123598816738675201), (1123598817738675214, 1123598815738675214, 1123598816738675201), (1123598817738675215, 1123598815738675215, 1123598816738675201), (1123598817738675216, 1123598815738675216, 1123598816738675201), (1123598817738675217, 1123598815738675217, 1123598816738675201), (1123598817738675218, 1123598815738675218, 1123598816738675201), (1123598817738675219, 1123598815738675219, 1123598816738675201), (1123598817738675220, 1123598815738675220, 1123598816738675201), (1123598817738675221, 1123598815738675221, 1123598816738675201), (1123598817738675222, 1123598815738675222, 1123598816738675201), (1123598817738675223, 1123598815738675223, 1123598816738675201), (1123598817738675224, 1123598815738675224, 1123598816738675201), (1123598817738675225, 1123598815738675225, 1123598816738675201), (1123598817738675226, 1123598815738675226, 1123598816738675201), (1123598817738675227, 1123598815738675227, 1123598816738675201), (1123598817738675228, 1123598815738675228, 1123598816738675201), (1123598817738675229, 1123598815738675229, 1123598816738675201), (1123598817738675230, 1123598815738675230, 1123598816738675201), (1123598817738675231, 1123598815738675231, 1123598816738675201), (1123598817738675232, 1123598815738675232, 1123598816738675201), (1123598817738675233, 1123598815738675233, 1123598816738675201), (1123598817738675234, 1123598815738675234, 1123598816738675201), (1123598817738675235, 1123598815738675235, 1123598816738675201), (1123598817738675236, 1123598815738675236, 1123598816738675201), (1123598817738675237, 1123598815738675237, 1123598816738675201), (1123598817738675238, 1123598815738675238, 1123598816738675201), (1123598817738675239, 1123598815738675239, 1123598816738675201), (1123598817738675240, 1123598815738675240, 1123598816738675201), (1123598817738675241, 1123598815738675241, 1123598816738675201), (1123598817738675242, 1123598815738675242, 1123598816738675201), (1123598817738675243, 1123598815738675243, 1123598816738675201), (1123598817738675244, 1123598815738675244, 1123598816738675201), (1123598817738675245, 1123598815738675245, 1123598816738675201), (1123598817738675246, 1123598815738675246, 1123598816738675201), (1123598817738675247, 1123598815738675247, 1123598816738675201), (1123598817738675248, 1123598815738675248, 1123598816738675201), (1123598817738675249, 1123598815738675249, 1123598816738675201), (1123598817738675250, 1123598815738675250, 1123598816738675201), (1123598817738675251, 1123598815738675251, 1123598816738675201), (1123598817738675252, 1123598815738675252, 1123598816738675201), (1123598817738675253, 1123598815738675253, 1123598816738675201), (1123598817738675254, 1123598815738675254, 1123598816738675201), (1123598817738675255, 1123598815738675255, 1123598816738675201), (1123598817738675256, 1123598815738675256, 1123598816738675201), (1123598817738675257, 1123598815738675257, 1123598816738675201), (1123598817738675258, 1123598815738675258, 1123598816738675201), (1123598817738675259, 1123598815738675259, 1123598816738675201), (1123598817738675260, 1123598815738675260, 1123598816738675201), (1123598817738675261, 1123598815738675261, 1123598816738675201), (1123598817738675262, 1123598815738675262, 1123598816738675201), (1123598817738675263, 1123598815738675263, 1123598816738675201), (1123598817738675264, 1123598815738675264, 1123598816738675201), (1123598817738675265, 1123598815738675265, 1123598816738675201), (1123598817738675266, 1123598815738675266, 1123598816738675201), (1123598817738675267, 1123598815738675267, 1123598816738675201), (1123598817738675268, 1123598815738675268, 1123598816738675201), (1123598817738675269, 1123598815738675269, 1123598816738675201), (1123598817738675270, 1123598815738675270, 1123598816738675201), (1161272893875225001, 1164733389668962251, 1123598816738675201), (1161272893875225002, 1164733389668962252, 1123598816738675201), (1161272893875225003, 1164733389668962253, 1123598816738675201), (1161272893875225004, 1164733389668962254, 1123598816738675201), (1161272893875225005, 1164733389668962255, 1123598816738675201), (1161272893875225006, 1164733389668962256, 1123598816738675201);
COMMIT;

-- ----------------------------
-- Table structure for blade_tenant
-- ----------------------------
DROP TABLE IF EXISTS `blade_tenant`;
CREATE TABLE `blade_tenant`  (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户ID',
  `tenant_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户名称',
  `linkman` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `contact_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系地址',
  `create_user` bigint(64) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '租户表';

-- ----------------------------
-- Records of blade_tenant
-- ----------------------------
BEGIN;
INSERT INTO `blade_tenant` VALUES (1123598820738675201, '000000', '管理组', 'admin', '666666', '管理组', 1123598821738675201, '2019-01-01 00:00:39', 1123598821738675201, '2019-01-01 00:00:39', 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for blade_user
-- ----------------------------
DROP TABLE IF EXISTS `blade_user`;
CREATE TABLE `blade_user`  (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',
  `code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户编号',
  `account` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真名',
  `avatar` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `email` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机',
  `birthday` datetime(0) NULL DEFAULT NULL COMMENT '生日',
  `sex` smallint(6) NULL DEFAULT NULL COMMENT '性别',
  `role_id` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色id',
  `dept_id` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门id',
  `post_id` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '岗位id',
  `create_user` bigint(64) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表';

-- ----------------------------
-- Records of blade_user
-- ----------------------------
BEGIN;
INSERT INTO `blade_user` VALUES (1123598821738675201, '000000', NULL, 'admin', '90b9aa7e25f80cf4f64e990b78a9fc5ebd6cecad', '管理员', '管理员', '', 'admin@bladex.vip', '22233322', '2018-08-08 00:00:00', 1, '1123598816738675201', '1123598813738675201', '1123598817738675201', 1123598821738675201, '2018-08-08 00:00:00', 1123598821738675201, '2018-08-08 00:00:00', 1, 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
