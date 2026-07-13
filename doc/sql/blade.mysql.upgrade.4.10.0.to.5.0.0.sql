/*
 SpringBlade 数据库升级脚本：4.10.0 → 5.0.0（Spring Boot 4 升级）

 适用范围：已按 4.10.0 初始化的存量库。全新安装从 blade.mysql.all.create.sql 建库即含新值，无需执行本脚本。
 变更说明：本次升级移除 Knife4j、改用 springdoc 原生 UI，接口文档入口由 /doc.html 改为 /swagger-ui.html，
           故同步更新 blade_menu 中「接口文档」菜单地址。语句以旧值为匹配条件，可安全重复执行。
*/

-- 接口文档菜单：/doc.html → /swagger-ui.html
UPDATE `blade_menu` SET `path` = 'http://localhost/swagger-ui.html' WHERE `path` = 'http://localhost/doc.html';
