-- ----------------------------
-- 角色权限表新增权限类型字段
-- ----------------------------
ALTER TABLE `blade_role_scope`
    ADD COLUMN `scope_category` int NULL COMMENT '权限类型(1:数据权限、2:接口权限)' AFTER `id`,
    MODIFY COLUMN `scope_id` bigint NULL DEFAULT NULL COMMENT '权限id' AFTER `id`;

-- ----------------------------
-- 角色权限表设置历史数据权限类型为1
-- ----------------------------
UPDATE `blade_role_scope` SET `scope_category` = 1 WHERE `scope_category` IS NULL;

-- ----------------------------
-- 创建接口权限表
-- ----------------------------
CREATE TABLE `blade_scope_api`  (
    `id` bigint NOT NULL COMMENT '主键',
    `menu_id` bigint NULL DEFAULT NULL COMMENT '菜单主键',
    `resource_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源编号',
    `scope_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口权限名',
    `scope_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口权限地址',
    `scope_type` int NULL DEFAULT NULL COMMENT '接口权限类型',
    `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口权限备注',
    `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
    `create_dept` bigint NULL DEFAULT NULL COMMENT '创建部门',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
    `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
    `status` int NULL DEFAULT 1 COMMENT '状态',
    `is_deleted` int NULL DEFAULT 0 COMMENT '是否已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '接口权限表';

-- ----------------------------
-- 字典表新增接口权限类型数据
-- ----------------------------
INSERT INTO `blade_dict` (`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1123598814738675237, 0, 'api_scope_type', '-1', '接口权限', 10, NULL, 0);
INSERT INTO `blade_dict` (`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1123598814738675238, 1123598814738675237, 'api_scope_type', '1', '系统接口', 1, NULL,  0);
INSERT INTO `blade_dict` (`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1123598814738675239, 1123598814738675237, 'api_scope_type', '2', '业务接口', 2, NULL,  0);

-- ----------------------------
-- 菜单表新增接口权限菜单
-- ----------------------------
INSERT INTO `blade_menu` (`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1123598815738675311, 1123598815738675307, 'api_scope', '接口权限', 'menu', '/authority/apiscope', 'iconfont iconicon_send', 3, 1, 0, 1, NULL, 0);
INSERT INTO `blade_menu` (`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1123598815738675312, 1123598815738675311, 'api_scope_setting', '权限配置', 'setting', NULL, 'setting', 1, 2, 2, 1, NULL, 0);
INSERT INTO `blade_role_menu` (`id`, `menu_id`, `role_id`) VALUES (2006703481530257413, 1123598815738675311, 1123598816738675201);
INSERT INTO `blade_role_menu` (`id`, `menu_id`, `role_id`) VALUES (2006703481530257414, 1123598815738675312, 1123598816738675201);
