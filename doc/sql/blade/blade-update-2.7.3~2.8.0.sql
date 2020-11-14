-- ----------------------------
-- 报表文件表
-- ----------------------------
CREATE TABLE `blade_report_file`  (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名',
  `content` mediumblob NULL COMMENT '文件内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除',
  PRIMARY KEY (`id`)
) COMMENT = '报表文件表';

-- ----------------------------
-- 插入报表文件表菜单数据
-- ----------------------------
INSERT INTO `blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1164733399669962301', '0', 'report', '报表管理', 'menu', '/report', 'iconfont icon-shujuzhanshi2', 5, 1, 0, 1, NULL, 0);
INSERT INTO `blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1164733399669962302', '1164733399669962301', 'report_setting', '报表配置', 'menu', 'http://localhost:8108/ureport/designer', 'iconfont icon-rizhi', 1, 1, 0, 1, NULL, 0);
INSERT INTO `blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1164733399669962303', '1164733399669962301', 'report_list', '报表列表', 'menu', '/report/reportlist', 'iconfont icon-biaodan', 2, 1, 0, 1, NULL, 0);

-- ----------------------------
-- 增加报表文件表菜单权限数据
-- ----------------------------
INSERT INTO `blade_role_menu`(`id`,`menu_id`,`role_id`)
VALUES ('1161272893875228001', '1164733399669962301', '1123598816738675201');
INSERT INTO `blade_role_menu`(`id`,`menu_id`,`role_id`)
VALUES ('1161272893875228002', '1164733399669962302', '1123598816738675201');
INSERT INTO `blade_role_menu`(`id`,`menu_id`,`role_id`)
VALUES ('1161272893875228003', '1164733399669962303', '1123598816738675201');
