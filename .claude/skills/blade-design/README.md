# blade-design - SpringBlade 全栈代码生成器

基于 SpringBlade 开源版框架规范的智能代码生成 Skill，覆盖后端、前端、数据库三层，支持 Boot 单体和 Cloud 微服务两种架构模式。

## 功能概览

```
blade-design
├── 后端子技能 (backend)
│   ├── Entity / DTO / VO / Excel    ← 数据模型层
│   ├── Mapper / Mapper.xml          ← 数据访问层
│   ├── Service / ServiceImpl        ← 业务逻辑层
│   ├── Controller                   ← 接口控制层
│   ├── Wrapper                      ← 数据转换层
│   └── Feign Client (Cloud)         ← 微服务通信层
│
├── 前端子技能 (frontend)
│   ├── API 模块                      ← Axios 请求封装
│   ├── Option 配置                   ← Avue CRUD 列/表单配置
│   └── Vue 页面                      ← Options API / Composition API
│
└── 数据库子技能 (database)
    ├── MySQL 建表 SQL                ← 开源版官方支持方言
    └── 菜单 SQL                      ← 权限菜单数据
```

## 支持的架构模式

### Boot 单体架构
- 所有代码在同一模块内
- 包名：`org.springblade.modules.{module}`
- Controller 路由含服务名前缀：`AppConstant.APPLICATION_XXX_NAME + "/path"`
- 无 Feign Client

### Cloud 微服务架构
- API 模块（契约）与 Service 模块（实现）分离
- 包名：`org.springblade.{module}`
- Controller 路由无前缀（Gateway 自动路由）
- 含 Feign Client 接口 + 实现

## 支持的实体模式

| 模式 | 基类 | 适用场景 | 审计字段 | 租户隔离 |
|------|------|---------|---------|---------|
| **tenant** | `TenantEntity` | 多租户业务表 | 自动继承 | 有 |
| **base** | `BaseEntity` | 跨租户业务表 | 自动继承 | 无 |
| **raw** | `Serializable` | 系统 / 关系表 | 手动定义 | 无 |

## 支持的数据库

SpringBlade 开源版**仅官方支持 MySQL**，建表脚本固定输出 MySQL 方言。

| 方言 | 引号 | 大小写 | 注释方式 | 引擎 |
|------|------|--------|---------|------|
| MySQL | `` ` `` | 小写 | `COMMENT` 内联 | InnoDB + utf8mb4 |

## 支持的前端风格

| 风格 | 说明 | 适用场景 |
|------|------|---------|
| **Options API** | Vue 3 传统选项式写法 | 与现有代码保持一致 |
| **Composition API** | Vue 3 组合式 `<script setup>` | 新功能开发推荐 |

## 支持的模板类型

| 类型 | 说明 | 适用场景 |
|------|------|---------|
| **crud** | 标准增删改查 | 通用业务管理 |
| **tree** | 树形结构 CRUD | 分类、部门、菜单等层级数据 |
| **sub** | 主子表 CRUD | 订单-订单项、合同-条款等主从关系 |

## 使用方式

### 快速开始
```
帮我生成一个订单管理模块的代码
```

### 指定参数
```
帮我生成一个产品管理的 CRUD：
- 模块名：product
- 实体名：Product
- 架构：boot
- 字段：
  - productName (String) 产品名称，必填，搜索
  - category (Integer) 产品分类，字典 product_category
  - price (BigDecimal) 单价
  - description (String/text) 产品描述
  - status (Integer) 上架状态
```

### 仅生成部分
```
帮我生成 Order 的建表语句（MySQL）
帮我生成前端页面，用 Composition API
只需要后端代码，Cloud 架构
```

## 文件结构

```
blade-design/
├── SKILL.md                  ← 主技能文件（路由、配置、工作流）
├── README.md                 ← 本文档
└── references/
    ├── backend.md            ← 后端代码模板（Entity/DTO/VO/Mapper/Service/Controller/Wrapper/Feign）
    ├── frontend.md           ← 前端代码模板（API/Option/Vue 页面）
    └── database.md           ← MySQL 建表模板 + 菜单 SQL
```

## Boot vs Cloud 核心差异速查

| 维度 | Boot | Cloud |
|------|------|-------|
| 基础包名 | `org.springblade.modules.{module}` | `org.springblade.{module}` |
| Controller 路由 | `AppConstant.APPLICATION_XXX_NAME + "/path"` | `"/path"` |
| Entity 位置 | 同模块 | API 模块 |
| 服务间调用 | 直接注入 | Feign Client |
| Feign Client | 无 | 接口在 API 模块，实现在 Service 模块 |
| POM 结构 | 单一 pom.xml | API + Service 各一个 |
| 服务注册 | 不需要 | Nacos |
| 网关路由 | 不需要 | Spring Cloud Gateway |

## 代码规范要点

1. Long 类型 ID 必须 `@JsonSerialize(using = ToStringSerializer.class)` 防精度丢失
2. Date 字段同时添加 `@DateTimeFormat` 和 `@JsonFormat`
3. 逻辑删除字段 `isDeleted` 添加 `@TableLogic`（基类自动处理）
4. Swagger 使用 OpenAPI 3 注解 + Knife4j `@ApiOperationSupport`
5. 响应统一使用 `R<T>` 包装
6. Wrapper 中通过 `DictCache.getValue()` 翻译字典字段
7. 前端权限标识格式：`{modelCode}_{action}`
8. 表名统一 `blade_` 前缀
9. 主键使用雪花算法（ASSIGN_ID）
10. 所有表包含标准审计字段
11. Entity 类名不带 `Entity` 后缀（如 `User` 而非 `UserEntity`）
12. 权限控制使用 `@PreAuth(RoleConstant.HAS_ROLE_ADMIN)`，开源版仅支持角色级权限
