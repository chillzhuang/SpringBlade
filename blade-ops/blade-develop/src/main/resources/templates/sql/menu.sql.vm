INSERT INTO `blade_menu`(`parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES (0, '$!{cfg.entityKey}', '$!{cfg.codeName}', 'menu', '/$!{cfg.servicePackage}/$!{cfg.entityKey}', NULL, 1, 1, 0, 1, NULL, 0);
set @parentid = (SELECT LAST_INSERT_ID());
INSERT INTO `blade_menu`(`parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES (@parentid, '$!{cfg.entityKey}_add', '新增', 'add', '/$!{cfg.servicePackage}/$!{cfg.entityKey}/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `blade_menu`(`parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES (@parentid, '$!{cfg.entityKey}_edit', '修改', 'edit', '/$!{cfg.servicePackage}/$!{cfg.entityKey}/edit', 'form', 2, 2, 1, 2, NULL, 0);
INSERT INTO `blade_menu`(`parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES (@parentid, '$!{cfg.entityKey}_delete', '删除', 'delete', '/api/$!{cfg.serviceName}/$!{cfg.entityKey}/remove', 'delete', 3, 2, 1, 3, NULL, 0);
INSERT INTO `blade_menu`(`parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES (@parentid, '$!{cfg.entityKey}_view', '查看', 'view', '/$!{cfg.servicePackage}/$!{cfg.entityKey}/view', 'file-text', 4, 2, 1, 2, NULL, 0);
