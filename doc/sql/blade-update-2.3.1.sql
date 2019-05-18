ALTER TABLE `blade_log_error`
    ADD COLUMN `remote_ip` varchar(255) NULL COMMENT '操作IP地址' AFTER `line_number`,
    ADD COLUMN `time` varchar(64) NULL COMMENT '执行时间' AFTER `params`;

ALTER TABLE `blade_log_usual`
    ADD COLUMN `remote_ip` varchar(255) NULL COMMENT '操作IP地址' AFTER `request_uri`,
    ADD COLUMN `method_class` varchar(255) NULL COMMENT '方法类' AFTER `remote_ip`,
    ADD COLUMN `method_name` varchar(255) NULL COMMENT '方法名' AFTER `method_class`,
    ADD COLUMN `time` datetime(0) NULL COMMENT '执行时间' AFTER `params`;
