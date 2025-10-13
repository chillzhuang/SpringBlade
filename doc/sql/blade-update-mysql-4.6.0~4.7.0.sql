-- ----------------------------
-- Table structure for blade_top_menu
-- ----------------------------
CREATE TABLE `blade_top_menu`  (
  `id` bigint NOT NULL COMMENT '主键',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '顶部菜单编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '顶部菜单名',
  `source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '顶部菜单资源',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '顶部菜单路由',
  `sort` int NULL DEFAULT NULL COMMENT '顶部菜单排序',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint NULL DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `status` int NULL DEFAULT 1 COMMENT '状态',
  `is_deleted` int NULL DEFAULT 0 COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '顶部菜单表';

-- ----------------------------
-- Table structure for blade_top_menu_setting
-- ----------------------------
CREATE TABLE `blade_top_menu_setting`  (
  `id` bigint NOT NULL COMMENT '主键',
  `top_menu_id` bigint NULL DEFAULT NULL COMMENT '顶部菜单主键',
  `menu_id` bigint NULL DEFAULT NULL COMMENT '菜单主键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '顶部菜单配置表';

-- ----------------------------
-- 菜单数据新增
-- ----------------------------
INSERT INTO `blade_menu` (`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1123598815738675208, 1123598815738675203, 'topmenu', '顶部菜单', 'menu', '/system/topmenu', 'iconfont icon-canshu', 7, 1, 0, 1, NULL, 0);
INSERT INTO `blade_menu` (`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1123598815738675313, 1123598815738675208, 'topmenu_add', '新增', 'add', '/system/topmenu/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `blade_menu` (`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1123598815738675314, 1123598815738675208, 'topmenu_edit', '修改', 'edit', '/system/topmenu/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `blade_menu` (`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1123598815738675315, 1123598815738675208, 'topmenu_delete', '删除', 'delete', '/api/blade-system/topmenu/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `blade_menu` (`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1123598815738675316, 1123598815738675208, 'topmenu_view', '查看', 'view', '/system/topmenu/view', 'file-text', 4, 2, 2, 1, NULL, 0);
INSERT INTO `blade_menu` (`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`) VALUES (1123598815738675317, 1123598815738675208, 'topmenu_setting', '菜单配置', 'setting', NULL, 'setting', 5, 2, 1, 1, NULL, 0);

INSERT `blade_role_menu` (`id`, `menu_id`, `role_id`) VALUES (1977345456189423620, 1123598815738675208, 1123598816738675201);
INSERT `blade_role_menu` (`id`, `menu_id`, `role_id`) VALUES (1977345456189423621, 1123598815738675313, 1123598816738675201);
INSERT `blade_role_menu` (`id`, `menu_id`, `role_id`) VALUES (1977345456189423622, 1123598815738675314, 1123598816738675201);
INSERT `blade_role_menu` (`id`, `menu_id`, `role_id`) VALUES (1977345456189423623, 1123598815738675315, 1123598816738675201);
INSERT `blade_role_menu` (`id`, `menu_id`, `role_id`) VALUES (1977345456189423624, 1123598815738675316, 1123598816738675201);
INSERT `blade_role_menu` (`id`, `menu_id`, `role_id`) VALUES (1977345456189423625, 1123598815738675317, 1123598816738675201);
