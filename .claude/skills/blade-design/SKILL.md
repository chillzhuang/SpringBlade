---
name: blade-design
description: SpringBlade 全栈代码生成器。根据用户提供的模块名、实体名和字段列表，自动生成符合 SpringBlade 开源版框架规范的后端代码（Boot 单体/Cloud 微服务）、前端页面（Saber Options API/Composition API）和 MySQL 建表语句。当用户说"帮我生成代码"、"创建一个新模块"、"生成 CRUD"、"新建实体"、"建表语句"、"生成前端页面"、"生成后端接口"、"生成 SpringBlade 代码"、"写一个 xxx 管理功能"、"帮我设计 xxx 表"、"blade-design"等时触发。即使用户只是描述了业务字段或给出了表结构，也应触发此技能来生成对应的 SpringBlade 规范代码。
---

# SpringBlade 全栈代码生成器 (blade-design)

根据用户需求生成符合 SpringBlade 开源版框架规范的完整 CRUD 代码，覆盖后端、前端、数据库三层。生成的代码与 SpringBlade 现有模块（如 Notice、Dict、Post、User）风格完全一致。

## 信息收集协议

在生成代码前，必须确认以下信息。如用户未提供，主动询问：

### 必需信息
| 参数 | 说明 | 示例 |
|------|------|------|
| **模块名** | 业务模块标识（英文小写） | `desk`、`system`、`order` |
| **实体名** | 实体类名（PascalCase） | `Notice`、`Order`、`Product` |
| **中文名** | 业务描述 | `通知公告`、`订单管理` |
| **字段列表** | 业务字段（不含审计字段） | 见下方格式 |

### 可选信息（有默认值）
| 参数 | 默认值 | 可选值 |
|------|--------|--------|
| **架构类型** | `boot` | `boot`（单体）、`cloud`（微服务） |
| **实体模式** | `tenant` | `tenant`（租户隔离）、`base`（无租户）、`raw`（原生 Serializable） |
| **前端风格** | `options` | `options`（Options API）、`composition`（Composition API） |
| **模板类型** | `crud` | `crud`（基础增删改查）、`tree`（树形结构）、`sub`（主子表） |
| **是否含 Wrapper** | `true` | `true`、`false` |
| **生成范围** | `all` | `all`、`backend`、`frontend`、`database` |
| **服务名（Cloud）** | `blade-{模块名}` | 微服务名称 |
| **作者** | `BladeX` | 代码注释中的作者 |

> SpringBlade 开源版仅官方支持 MySQL，生成的建表脚本固定为 MySQL 方言。

### 字段定义格式

用户可以用表格或简洁文本描述字段，你需要解析为以下结构：

| 属性 | 说明 |
|------|------|
| `name` | Java 属性名（camelCase） |
| `column` | 数据库列名（snake_case） |
| `type` | Java 类型：`String`、`Integer`、`Long`、`Date`、`BigDecimal`、`Double` |
| `jdbcType` | 数据库类型：`varchar(n)`、`int`、`bigint`、`datetime`、`text`、`decimal(m,n)` |
| `comment` | 中文字段说明 |
| `required` | 是否必填（默认 false） |
| `query` | 是否为查询字段（默认 false） |
| `queryType` | 查询方式：`EQ`、`LIKE`、`BETWEEN`（默认 EQ） |
| `list` | 是否在列表显示（默认 true） |
| `form` | 是否在表单显示（默认 true） |
| `component` | 前端组件：`input`、`textarea`、`select`、`radio`、`checkbox`、`switch`、`date`、`datetime`、`number`、`upload`（默认 input） |
| `dictCode` | 字典编码（用于 select/radio 类型） |

## Boot vs Cloud 核心架构差异

生成代码时根据架构类型自动调整以下差异点：

### 模块结构

**Boot（单体应用）**：所有代码在同一模块内
```
src/main/java/org/springblade/modules/{module}/
├── controller/
├── service/ + impl/
├── mapper/
├── wrapper/
├── entity/ + dto/ + vo/
└── excel/
```

**Cloud（微服务）**：API 与实现分离为两个 Maven 模块
```
blade-service-api/blade-{module}-api/          ← 契约层
└── org/springblade/{module}/
    ├── entity/ + dto/ + vo/
    └── feign/I{Name}Client.java               ← Feign 接口

blade-service/blade-{module}/                  ← 实现层
└── org/springblade/{module}/
    ├── controller/
    ├── service/ + impl/
    ├── mapper/
    ├── wrapper/
    └── feign/{Name}Client.java                ← Feign 实现
```

### 关键差异速查

| 维度 | Boot | Cloud |
|------|------|-------|
| **基础包名** | `org.springblade.modules.{module}` | `org.springblade.{module}` |
| **Controller 路由** | `@RequestMapping(AppConstant.APPLICATION_XXX_NAME + "/{path}")` | `@RequestMapping("/{path}")` |
| **服务间调用** | 直接注入 Service | Feign Client 远程调用 |
| **Entity/VO 位置** | 与 Service 同模块 | 独立 API 模块中 |
| **Feign Client** | 无 | API 模块：接口定义；Service 模块：实现类 |
| **POM 依赖** | 单一 pom.xml | API 和 Service 各自的 pom.xml |
| **服务注册** | 不需要 | Nacos 服务注册与发现 |
| **Gateway 路由** | 不需要 | Gateway 自动按服务名路由 |

### Boot 路由规则
Boot 模式下 Controller 需要手动添加服务名前缀，因为没有 Gateway 路由：
```java
// Boot: 完整路径 /desk/notice/detail
@RequestMapping(AppConstant.APPLICATION_DESK_NAME + "/notice")

// Cloud: Gateway 自动添加 /blade-desk 前缀，只需 /notice/detail
@RequestMapping("/notice")
```

## 实体继承模式

根据业务场景选择合适的实体基类：

### 模式一：TenantEntity（默认，租户隔离业务表）
- 继承 `TenantEntity`（`org.springblade.core.mp.base.TenantEntity`），自动拥有 `tenantId` + 全套审计字段
- Service 继承 `BaseService<Entity>` / `BaseServiceImpl<Mapper, Entity>`
- 删除使用 `deleteLogic()`（逻辑删除）
- 适用：User、Post 等绝大多数多租户业务表

### 模式二：BaseEntity（无租户业务表）
- 继承 `BaseEntity`（`org.springblade.core.mp.base.BaseEntity`），拥有审计字段但无 `tenantId`
- Service 继承 `BaseService<Entity>` / `BaseServiceImpl<Mapper, Entity>`
- 适用：Tenant、Notice 等跨租户业务表
- 也可通过在 `application.yml`（Boot）或 Nacos `blade.yaml`（Cloud）的 `blade.tenant.tables` 中配置表名实现字段隔离，不强制继承 TenantEntity

### 模式三：Raw Serializable（原生模式）
- 实现 `Serializable`，所有字段手动定义
- Service 继承 `IService<Entity>` / `ServiceImpl<Mapper, Entity>`
- 不继承 SpringBlade 基类，使用 MyBatis-Plus 原生 CRUD
- 删除使用 `removeBatchByIds()`
- 适用：Menu、Role、Dept、Dict、RoleMenu 等跨租户系统/关系表

## 代码生成工作流

### Step 1: 收集信息
确认所有必需参数和可选参数，构建完整的生成上下文。

### Step 2: 生成代码
根据用户选择的生成范围，读取对应的参考文件并生成代码：

| 生成范围 | 参考文件 | 生成产物 |
|----------|----------|----------|
| **后端** | 读取 `references/backend.md` | Entity、DTO、VO、Mapper、Mapper.xml、Service、ServiceImpl、Controller、Wrapper、Excel、Feign（Cloud） |
| **前端** | 读取 `references/frontend.md` | API 模块、Option 配置、Vue 页面 |
| **数据库** | 读取 `references/database.md` | MySQL 建表 SQL、菜单 SQL |

### Step 3: 输出代码
按文件维度逐个输出，每个文件包含：
1. 完整文件路径（相对于项目根目录）
2. 完整可运行的代码内容
3. 必要的说明注释

## 输出文件清单

### 后端产物（Boot）
```
src/main/java/org/springblade/modules/{module}/entity/{Name}.java
src/main/java/org/springblade/modules/{module}/dto/{Name}DTO.java
src/main/java/org/springblade/modules/{module}/vo/{Name}VO.java
src/main/java/org/springblade/modules/{module}/excel/{Name}Excel.java
src/main/java/org/springblade/modules/{module}/mapper/{Name}Mapper.java
src/main/java/org/springblade/modules/{module}/mapper/{Name}Mapper.xml
src/main/java/org/springblade/modules/{module}/service/I{Name}Service.java
src/main/java/org/springblade/modules/{module}/service/impl/{Name}ServiceImpl.java
src/main/java/org/springblade/modules/{module}/controller/{Name}Controller.java
src/main/java/org/springblade/modules/{module}/wrapper/{Name}Wrapper.java
```

### 后端产物（Cloud）
```
blade-service-api/blade-{module}-api/.../entity/{Name}.java
blade-service-api/blade-{module}-api/.../dto/{Name}DTO.java
blade-service-api/blade-{module}-api/.../vo/{Name}VO.java
blade-service-api/blade-{module}-api/.../feign/I{Name}Client.java
blade-service/blade-{module}/.../excel/{Name}Excel.java
blade-service/blade-{module}/.../mapper/{Name}Mapper.java
blade-service/blade-{module}/.../mapper/{Name}Mapper.xml
blade-service/blade-{module}/.../service/I{Name}Service.java
blade-service/blade-{module}/.../service/impl/{Name}ServiceImpl.java
blade-service/blade-{module}/.../controller/{Name}Controller.java
blade-service/blade-{module}/.../wrapper/{Name}Wrapper.java
blade-service/blade-{module}/.../feign/{Name}Client.java
```

### 前端产物
```
src/api/{module}/{modelCode}.js
src/option/{module}/{modelCode}.js
src/views/{module}/{modelCode}.vue
```

### 数据库产物
```
{name}.mysql.sql              -- MySQL 建表语句
{name}.menu.sql               -- 菜单数据 SQL
```

## 命名约定

| 维度 | 规则 | 示例（实体名：OrderItem） |
|------|------|--------------------------|
| 实体类 | `{Name}` | `OrderItem` |
| VO 类 | `{Name}VO` | `OrderItemVO` |
| DTO 类 | `{Name}DTO` | `OrderItemDTO` |
| Excel 类 | `{Name}Excel` | `OrderItemExcel` |
| Mapper 接口 | `{Name}Mapper` | `OrderItemMapper` |
| Mapper XML | `{Name}Mapper.xml` | `OrderItemMapper.xml` |
| Service 接口 | `I{Name}Service` | `IOrderItemService` |
| Service 实现 | `{Name}ServiceImpl` | `OrderItemServiceImpl` |
| Controller | `{Name}Controller` | `OrderItemController` |
| Wrapper | `{Name}Wrapper` | `OrderItemWrapper` |
| Feign 接口 | `I{Name}Client` | `IOrderItemClient` |
| Feign 实现 | `{Name}Client` | `OrderItemClient` |
| 数据库表名 | `blade_{snake_case}` | `blade_order_item` |
| API 路径 | `/{modelCode}` | `/order-item` |
| 前端文件 | `{modelCode}` | `orderItem` |

> SpringBlade 开源版 Entity 统一**不带 `Entity` 后缀**（如 `User.java`、`Post.java`、`Notice.java`）。请严格遵循此命名。

## 重要规范

1. **所有 Long 类型的 ID 字段**必须添加 `@JsonSerialize(using = ToStringSerializer.class)` 防止前端精度丢失
2. **日期字段**必须同时添加 `@DateTimeFormat` 和 `@JsonFormat`，pattern 统一使用 `DateUtil.PATTERN_DATETIME`
3. **逻辑删除字段** `isDeleted` 必须添加 `@TableLogic` 注解（继承 BaseEntity / TenantEntity 时由基类自动处理）
4. **Swagger 文档**使用 OpenAPI 3 注解：`@Tag`、`@Operation`、`@Schema`、`@Parameter`
5. **Knife4j** 排序使用 `@ApiOperationSupport(order = n)`
6. **响应包装**统一使用 `R<T>` 类
7. **Wrapper 的 entityVO() 方法**中通过 `DictCache.getValue()` 翻译字典字段
8. **Controller 的 remove 方法**：TenantEntity / BaseEntity 用 `deleteLogic()`，Raw 模式用 `removeBatchByIds()`
9. **权限控制**：使用 `@PreAuth(RoleConstant.HAS_ROLE_ADMIN)` 做角色级控制（SpringBlade 开源版仅支持角色权限）
10. **前端权限标识**格式：`{modelCode}_{action}`，如 `notice_add`、`notice_edit`、`notice_delete`、`notice_view`

## 参考文件索引

| 生成任务 | 参考文件 | 说明 |
|----------|----------|------|
| 后端代码 | `references/backend.md` | 完整的后端各层代码模板，含 Boot/Cloud 变体 |
| 前端代码 | `references/frontend.md` | API、Option 配置、Vue 页面模板（Options/Composition API） |
| 数据库 SQL | `references/database.md` | MySQL 建表模板、字段类型映射与菜单 SQL |
