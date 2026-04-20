# 数据库建表语句生成参考

本文档包含 SpringBlade 开源版 MySQL 建表模板与菜单数据 SQL。所有模板均来源于开源工程 `doc/sql/` 下的真实 SQL 脚本。

> SpringBlade 开源版仅官方支持 **MySQL**，生成的建表脚本固定为 MySQL 方言。

## 目录
- [一、通用审计字段](#一通用审计字段)
- [二、字段类型映射表](#二字段类型映射表)
- [三、MySQL 建表模板](#三mysql-建表模板)
- [四、菜单 SQL 生成](#四菜单-sql-生成)

---

## 一、通用审计字段

所有继承 `TenantEntity` 或 `BaseEntity` 的业务表都包含以下固定审计字段：

| 字段名 | 说明 | Java 类型 | 是否可空 | 默认值 |
|--------|------|-----------|----------|--------|
| `id` | 主键（雪花 ID） | Long | NOT NULL | 无 |
| `tenant_id` | 租户 ID（仅 TenantEntity） | String | NULL | '000000' |
| `create_user` | 创建人 | Long | NULL | NULL |
| `create_dept` | 创建部门 | Long | NULL | NULL |
| `create_time` | 创建时间 | Date | NULL | NULL |
| `update_user` | 修改人 | Long | NULL | NULL |
| `update_time` | 修改时间 | Date | NULL | NULL |
| `status` | 业务状态 | Integer | NULL | 1 |
| `is_deleted` | 逻辑删除标记 | Integer | NULL | 0 |

- **BaseEntity 模式**不包含 `tenant_id` 字段
- **Raw Serializable 模式**手动定义全部字段（结构相同）

---

## 二、字段类型映射表

### Java 类型 → MySQL 类型映射

| Java 类型 | MySQL 类型 | 说明 |
|-----------|-----------|------|
| `String` (短) | `varchar(n)` | `n` 为字符数，通常 `varchar(255)` 以内 |
| `String` (长) | `text` | 超过 255 字符或富文本 |
| `Integer` | `int` | 32 位整型 |
| `Long` | `bigint` | 64 位整型（雪花 ID 必须用） |
| `Double` | `double` | 双精度浮点 |
| `BigDecimal` | `decimal(m,n)` | 金额、精确数值 |
| `Date` | `datetime` | 日期 + 时间 |
| `Boolean` | `tinyint(1)` | 0 = false，1 = true |

### 审计字段各字段定义（MySQL）

| 字段 | MySQL 定义 |
|------|-----------|
| id | `bigint NOT NULL` |
| tenant_id | `varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000'` |
| create_user | `bigint NULL DEFAULT NULL` |
| create_dept | `bigint NULL DEFAULT NULL` |
| create_time | `datetime NULL DEFAULT NULL` |
| update_user | `bigint NULL DEFAULT NULL` |
| update_time | `datetime NULL DEFAULT NULL` |
| status | `int NULL DEFAULT 1` |
| is_deleted | `int NULL DEFAULT 0` |

---

## 三、MySQL 建表模板

### TenantEntity 模式（多租户业务表）

```sql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blade_{table_name}
-- ----------------------------
DROP TABLE IF EXISTS `blade_{table_name}`;
CREATE TABLE `blade_{table_name}`  (
  `id` bigint NOT NULL COMMENT '主键',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',
  -- 业务字段
  `{column_name}` {mysql_type} {null_constraint} {default_value} COMMENT '{字段注释}',
  -- 审计字段
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint NULL DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `status` int NULL DEFAULT 1 COMMENT '状态',
  `is_deleted` int NULL DEFAULT 0 COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '{表注释}';

SET FOREIGN_KEY_CHECKS = 1;
```

### BaseEntity 模式（跨租户业务表）

与 TenantEntity 模板一致，**仅删除 `tenant_id` 这一行**：

```sql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `blade_{table_name}`;
CREATE TABLE `blade_{table_name}`  (
  `id` bigint NOT NULL COMMENT '主键',
  -- 业务字段
  `{column_name}` {mysql_type} {null_constraint} {default_value} COMMENT '{字段注释}',
  -- 审计字段
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint NULL DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `status` int NULL DEFAULT 1 COMMENT '状态',
  `is_deleted` int NULL DEFAULT 0 COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '{表注释}';

SET FOREIGN_KEY_CHECKS = 1;
```

### Raw Serializable 模式（系统 / 关系表）

结构同 BaseEntity 模板（同样无 `tenant_id`）。差异在于 Java 侧手动定义全部字段，数据库层面建表语句本身不变。

### 索引建议

对于查询频繁的字段：

```sql
-- 普通索引
CREATE INDEX idx_{table_name}_{column} ON blade_{table_name} ({column_name});

-- 唯一索引
CREATE UNIQUE INDEX uk_{table_name}_{column} ON blade_{table_name} ({column_name});

-- 复合索引（多字段）
CREATE INDEX idx_{table_name}_{col1}_{col2} ON blade_{table_name} ({col1}, {col2});
```

**命名约定**：
- 唯一索引前缀 `uk_`
- 普通索引前缀 `idx_`
- 字段名用下划线拼接

### MySQL 约定

- 列名使用反引号 `` ` `` 包裹
- 注释直接用 `COMMENT` 关键字写在列定义后
- 表注释在 `CREATE TABLE` 末尾
- 字符集固定 `utf8mb4`，排序规则 `utf8mb4_general_ci`
- 存储引擎 `InnoDB`
- 主键索引类型 `USING BTREE`
- 租户 ID 列 `varchar(12)` 默认 `'000000'`

---

## 四、菜单 SQL 生成

每个新模块需要插入 5 条菜单记录：1 个主菜单 + 4 个操作按钮（新增、修改、删除、查看）。

### MySQL 菜单 SQL

```sql
-- ----------------------------
-- {中文名}菜单数据
-- ----------------------------
INSERT INTO `blade_menu` (`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ({menuId}, {parentMenuId}, '{modelCode}', '{中文名}', 'menu', '/{module}/{modelCode}', NULL, 1, 1, 0, 1, NULL, 0);

INSERT INTO `blade_menu` (`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ({addMenuId}, {menuId}, '{modelCode}_add', '新增', 'add', '/{module}/{modelCode}/add', 'plus', 1, 2, 1, 1, NULL, 0);

INSERT INTO `blade_menu` (`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ({editMenuId}, {menuId}, '{modelCode}_edit', '修改', 'edit', '/{module}/{modelCode}/edit', 'form', 2, 2, 2, 1, NULL, 0);

INSERT INTO `blade_menu` (`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ({removeMenuId}, {menuId}, '{modelCode}_delete', '删除', 'delete', '/{module}/{modelCode}/delete', 'delete', 3, 2, 3, 1, NULL, 0);

INSERT INTO `blade_menu` (`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ({viewMenuId}, {menuId}, '{modelCode}_view', '查看', 'view', '/{module}/{modelCode}/view', 'eye-open', 4, 2, 2, 1, NULL, 0);
```

### 菜单字段说明

| 字段 | 说明 | 值 |
|------|------|-----|
| `id` | 主菜单 ID | 生成雪花 ID 或自定义（如 1123598812738675301） |
| `parent_id` | 父菜单 ID | 由用户指定或放在顶级（0） |
| `code` | 权限编码 | 主菜单: `{modelCode}`，按钮: `{modelCode}_{action}` |
| `name` | 显示名称 | 主菜单: `{中文名}`，按钮: 新增/修改/删除/查看 |
| `alias` | 别名 | 主菜单: `menu`，按钮: add/edit/delete/view |
| `path` | 路由路径 | `/{module}/{modelCode}` |
| `source` | 图标 | 按钮: plus/form/delete/eye-open |
| `sort` | 排序 | 按钮从 1 到 4 |
| `category` | 类别 | 1=菜单，2=按钮 |
| `action` | 操作类型 | 0=无，1=新增，2=修改，3=删除 |
| `is_open` | 是否打开 | 1=是 |
| `is_deleted` | 是否删除 | 0=否 |

---

## 附：无租户表模式速查

如果实体不需要 `tenant_id`（BaseEntity 或 Raw 模式），从建表模板中直接移除 `tenant_id` 行即可，其余审计字段保持不变。
