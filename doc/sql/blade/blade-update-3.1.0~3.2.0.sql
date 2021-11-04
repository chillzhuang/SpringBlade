
-- ----------------------------
-- 创建数据权限表
-- ----------------------------
CREATE TABLE `blade_scope_data`  (
     `id` bigint(20) NOT NULL COMMENT '主键',
     `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单主键',
     `resource_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源编号',
     `scope_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据权限名称',
     `scope_field` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据权限字段',
     `scope_class` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据权限类名',
     `scope_column` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据权限字段',
     `scope_type` int(2) NULL DEFAULT NULL COMMENT '数据权限类型',
     `scope_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据权限值域',
     `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据权限备注',
     `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
     `create_dept` bigint(20) NULL DEFAULT NULL COMMENT '创建部门',
     `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
     `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
     `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
     `status` int(2) NULL DEFAULT NULL COMMENT '状态',
     `is_deleted` int(2) NULL DEFAULT NULL COMMENT '是否已删除',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据权限表';


-- ----------------------------
-- 创建数据权限角色表
-- ----------------------------
DROP TABLE IF EXISTS `blade_role_scope`;
CREATE TABLE `blade_role_scope`  (
     `id` bigint(20) NOT NULL COMMENT '主键',
     `scope_id` bigint(20) NULL DEFAULT NULL COMMENT '数据权限id',
     `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- 部门表增加字段
-- ----------------------------
ALTER TABLE `blade_dept`
    ADD COLUMN `ancestors` varchar(2000) NULL COMMENT '祖级列表' AFTER `parent_id`;

UPDATE `blade_dept` SET `tenant_id` = '000000', `parent_id` = 0, `ancestors` = '0',  `dept_name` = '刀锋科技', `full_name` = '江苏刀锋科技有限公司', `sort` = 1, `remark` = NULL, `is_deleted` = 0 WHERE `id` = 1123598813738675201;
UPDATE `blade_dept` SET `tenant_id` = '000000', `parent_id` = 1123598813738675201, `ancestors` = '0,1123598813738675201',  `dept_name` = '常州刀锋', `full_name` = '常州刀锋科技有限公司', `sort` = 1, `remark` = NULL, `is_deleted` = 0 WHERE `id` = 1123598813738675202;
UPDATE `blade_dept` SET `tenant_id` = '000000', `parent_id` = 1123598813738675201, `ancestors` = '0,1123598813738675201',  `dept_name` = '苏州刀锋', `full_name` = '苏州刀锋科技有限公司', `sort` = 1, `remark` = NULL, `is_deleted` = 0 WHERE `id` = 1123598813738675203;

-- ----------------------------
-- 增加字典项
-- ----------------------------
INSERT INTO `blade_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1123598814738675231, 0, 'data_scope_type', -1, '数据权限', 8, NULL, 0);
INSERT INTO `blade_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1123598814738675232, 1123598814738675231, 'data_scope_type', 1, '全部可见', 1, NULL, 0);
INSERT INTO `blade_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1123598814738675233, 1123598814738675231, 'data_scope_type', 2, '本人可见', 2, NULL, 0);
INSERT INTO `blade_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1123598814738675234, 1123598814738675231, 'data_scope_type', 3, '所在机构可见', 3, NULL, 0);
INSERT INTO `blade_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1123598814738675235, 1123598814738675231, 'data_scope_type', 4, '所在机构及子级可见', 4, NULL, 0);
INSERT INTO `blade_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1123598814738675236, 1123598814738675231, 'data_scope_type', 5, '自定义', 5, NULL, 0);


-- ----------------------------
-- 增加权限管理模块
-- ----------------------------
INSERT INTO `blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1123598815738675307, 0, 'authority', '权限管理', 'menu', '/authority', 'iconfont icon-bofangqi-suoping', 98, 1, 0, 1, '', 0);

-- ----------------------------
-- 角色管理迁移至权限管理
-- ----------------------------
DELETE  FROM `blade_menu` WHERE ID = 1123598815738675208;
INSERT INTO `blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1123598815738675308, 1123598815738675307, 'role', '角色管理', 'menu', '/authority/role', 'iconfont iconicon_boss', 1, 1, 0, 1, NULL, 0);
UPDATE `blade_menu` SET `parent_id` = 1123598815738675308, `code` = 'role_add', `name` = '新增', `alias` = 'add', `path` = '/authority/role/add', `source` = 'plus', `sort` = 1, `category` = 2, `action` = 1, `is_open` = 1, `remark` = NULL, `is_deleted` = 0 WHERE `id` = 1123598815738675241;
UPDATE `blade_menu` SET `parent_id` = 1123598815738675308, `code` = 'role_edit', `name` = '修改', `alias` = 'edit', `path` = '/authority/role/edit', `source` = 'form', `sort` = 2, `category` = 2, `action` = 2, `is_open` = 1, `remark` = NULL, `is_deleted` = 0 WHERE `id` = 1123598815738675242;
UPDATE `blade_menu` SET `parent_id` = 1123598815738675308, `code` = 'role_delete', `name` = '删除', `alias` = 'delete', `path` = '/api/blade-system/role/remove', `source` = 'delete', `sort` = 3, `category` = 2, `action` = 3, `is_open` = 1, `remark` = NULL, `is_deleted` = 0 WHERE `id` = 1123598815738675243;
UPDATE `blade_menu` SET `parent_id` = 1123598815738675308, `code` = 'role_view', `name` = '查看', `alias` = 'view', `path` = '/authority/role/view', `source` = 'file-text', `sort` = 4, `category` = 2, `action` = 2, `is_open` = 1, `remark` = NULL, `is_deleted` = 0 WHERE `id` = 1123598815738675244;


-- ----------------------------
-- 增加数据权限及接口权限独立菜单
-- ----------------------------
INSERT INTO `blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1123598815738675309, 1123598815738675307, 'data_scope', '数据权限', 'menu', '/authority/datascope', 'iconfont icon-shujuzhanshi2', 2, 1, 0, 1, '', 0);
INSERT INTO `blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1123598815738675310, 1123598815738675309, 'data_scope_setting', '权限配置', 'setting', NULL, 'setting', 1, 2, 2, 1, NULL, 0);


-- ----------------------------
-- 增加数据权限及菜单权限
-- ----------------------------
INSERT INTO `blade_role_menu`(`id`, `menu_id`, `role_id`) VALUES (1455363615489028098, 1123598815738675307, 1123598816738675201);
INSERT INTO `blade_role_menu`(`id`, `menu_id`, `role_id`) VALUES (1455363615505805313, 1123598815738675309, 1123598816738675201);
INSERT INTO `blade_role_menu`(`id`, `menu_id`, `role_id`) VALUES (1455363615518388225, 1123598815738675310, 1123598816738675201);


-- ----------------------------
-- 增加create_dept字段并赋默认值
-- ----------------------------
ALTER TABLE `blade_client`
    ADD COLUMN `create_dept` bigint(20) NULL COMMENT '创建部门' AFTER `create_user`;
ALTER TABLE `blade_notice`
    ADD COLUMN `create_dept` bigint(20) NULL COMMENT '创建部门' AFTER `create_user`;
ALTER TABLE `blade_param`
    ADD COLUMN `create_dept` bigint(20) NULL COMMENT '创建部门' AFTER `create_user`;
ALTER TABLE `blade_tenant`
    ADD COLUMN `create_dept` bigint(20) NULL COMMENT '创建部门' AFTER `create_user`;
ALTER TABLE `blade_user`
    ADD COLUMN `create_dept` bigint(20) NULL COMMENT '创建部门' AFTER `create_user`;

UPDATE `blade_client` SET create_dept = 1123598813738675201 WHERE create_dept IS NULL;
UPDATE `blade_notice` SET create_dept = 1123598813738675201 WHERE create_dept IS NULL;
UPDATE `blade_param` SET create_dept = 1123598813738675201 WHERE create_dept IS NULL;
UPDATE `blade_tenant` SET create_dept = 1123598813738675201 WHERE create_dept IS NULL;
UPDATE `blade_user` SET create_dept = 1123598813738675201 WHERE create_dept IS NULL;





