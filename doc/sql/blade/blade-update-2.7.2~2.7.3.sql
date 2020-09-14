ALTER TABLE `blade_tenant`
    ADD COLUMN `domain` varchar(255) NULL COMMENT '域名地址' AFTER `tenant_name`;
