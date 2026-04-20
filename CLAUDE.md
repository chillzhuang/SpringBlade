# CLAUDE.md

欢迎来到 SpringBlade 开源单体版（boot 分支）。本文档汇总了项目的工程结构、编码约定与常见实践，帮助 Claude Code 快速上手，产出与项目风格一致的代码。

作为 AI 助手参与本项目开发时，你必须：

- 动手前先看一看目标模块的现有实现，模仿它的结构与风格通常是最快的路径
- 需要外部知识时，优先查阅 Spring Boot、MyBatis-Plus、Knife4j 等官方文档，而不是凭记忆作答
- 需求不够清晰时，先复述已知信息、抛出关键澄清问题，再动手
- 面对复杂改动，拆成可验证的小步骤逐步推进比一气呵成更稳妥

> 所有开发内容必须建立在深度思考过的基础之上，禁止机械生成与错误填充。
> 如果你已了解所有规范，请在用户第一次对话时说明："我已充分了解 SpringBlade 开源版开发规范。"

---

## 1. 项目概述

SpringBlade 是 BladeX 平台的**开源单体版本**（boot 分支），基于 Spring Boot 构建，已排除 Spring Cloud 相关组件，所有功能模块合并在一个 JAR 中运行，依赖 blade-tool 核心库。

---

## 2. 项目架构

```
SpringBlade/
├── src/main/java/org/springblade/
│   ├── Application.java                  # 主启动类（BladeApplication.run）
│   ├── common/                           # 本工程通用模块（cache, config, constant, launch, tool）
│   ├── core/                             # 本工程核心扩展（log 事件监听, secure 安全适配）
│   └── modules/                          # 业务模块
│       ├── auth/                         # 认证授权（OAuth2 多 granter, 社交登录）
│       ├── desk/                         # 工作台（仪表盘、通知公告）
│       ├── develop/                      # 开发工具（代码生成、数据源管理）
│       ├── resource/                     # 资源管理（OSS 附件端点）
│       └── system/                       # 系统管理（用户/角色/菜单/字典/租户/部门/岗位/日志/顶部菜单）
├── src/main/resources/                   # 配置文件（application.yml + dev/test/prod 多环境 + log/config/templates）
├── doc/
│   ├── sql/                              # 多数据库建表脚本 + 版本升级脚本
│   └── script/                           # 启动脚本
├── pom.xml                               # Maven POM（blade-tool 依赖管理）
└── Dockerfile                            # Docker 镜像定义
```

### 2.1 分层架构

每个业务模块遵循标准分层，所有层次都在**同一模块**内：

| 层次 | 包路径 |
| --- | --- |
| Controller | `controller/` |
| Service / ServiceImpl | `service/` + `service/impl/` |
| Mapper（接口 + XML 同包） | `mapper/` |
| Entity / VO / DTO | `entity/` `vo/` `dto/` |
| Wrapper | `wrapper/`（Entity → VO 转换） |
| 可选 | `excel/`（导入导出监听） |

> **与开源 Cloud 版的关键区别**：Boot 为单体架构，所有模块合并在一个 JAR 中运行，不存在 API/Service 模块分离与 Feign 跨服务调用，模块间直接通过 Java 依赖协作；Controller 路由统一使用 `AppConstant.APPLICATION_XXX_NAME` 前缀（而非网关按服务名路由）；不依赖 Nacos / Sentinel / Gateway / Seata 等 Spring Cloud 组件。如需微服务拓扑能力，请切换到 master 分支。

---

## 3. 技术栈

| 类别 | 技术 |
| --- | --- |
| JDK | Java 17+ |
| 基础框架 | Spring Boot / Maven |
| 核心库 | blade-core-boot、blade-core-bom |
| Starter | develop / oss / report / social / datascope / excel |
| ORM | MyBatis-Plus（禁止 JDBC 直连） |
| Web 服务器 | Undertow |
| 安全 | blade-core-secure（JWT + 可选 AES 加密 + SM2 国密登录） |
| 缓存 | BladeRedis（Protostuff 序列化） + Ehcache 本地 |
| 数据权限 | blade-starter-datascope（DataScopeHandler 行级过滤） |
| 文档 | Knife4j OpenAPI 3 UI |
| 报表 | UReport2（blade-starter-report） |
| 导入导出 | FastExcel（blade-starter-excel） |
| 验证码 | easy-captcha |
| 社交登录 | blade-starter-social（GitHub / Gitee / WeChat / QQ / DingTalk） |
| 数据库 | MySQL / PostgreSQL / Oracle / SQL Server / 达梦 / 人大金仓 / 崖山 |

> **注意**：本工程仅使用 `blade-core-boot`（已在 pom.xml `dependencyManagement` 中 `exclude blade-core-cloud`），**不使用** Spring Cloud / Nacos / Feign 等组件。

---

## 4. 开发规范

### 4.1 编写新功能前

1. 先阅读目标模块中已有的类，理解其结构、命名和风格
2. 标准参考模块：`modules/desk`（通知公告，最简 CRUD 样板）、`modules/system`（最完整的系统管理实现）
3. 主动模仿现有代码风格，包括缩进（Java 用 Tab，YAML/JSON 用 2 空格）、注解顺序、Javadoc 格式
4. 新建 Java 源文件必须包含 Apache 2.0 许可证头部和 Javadoc 类注释（`@author Chill`）

### 4.2 编写完成后

1. 使用 `mvn clean compile -DskipTests` 编译验证，编译不通过必须修复
2. 引入模块依赖前先检查循环依赖，若存在则采用更优方案
3. 编译通过后将测试交由用户执行，**不得自行执行任何测试**（包括 `mvn test`、启动应用、访问接口）
4. 除非用户明确要求，不应撰写示例或额外文档

### 4.3 代码生成

当需要生成 CRUD 全套代码（Entity、VO、Service、Controller、Wrapper、Mapper、建表语句等）时，优先使用 **`/blade-design`** skill。该 skill 可根据模块名、实体名和字段列表，自动生成符合 BladeX 框架规范的后端代码和多数据库建表语句，确保生成结果与项目风格完全一致。

本工程亦内置 `modules/develop` 代码生成器（`CodeController` + Velocity 模板位于 `src/main/resources/templates/`），可在运行时通过后台界面生成。

---

## 5. 命名规范

### 5.1 包名

统一前缀 `org.springblade`，按模块划分：`modules.system`、`modules.desk`、`modules.auth`、`modules.resource`、`modules.develop`、`common`、`core` 等

### 5.2 类名

| 类型 | 规则 | 示例 |
| --- | --- | --- |
| Entity | 直接类名 | `Notice`、`User`、`Tenant` |
| VO / DTO | `XxxVO` / `XxxDTO` | `NoticeVO`、`UserVO`、`DeptDTO` |
| Service 接口 | `IXxxService` | `INoticeService`、`IUserService` |
| Service 实现 | `XxxServiceImpl` | `NoticeServiceImpl` |
| Controller | `XxxController` | `NoticeController` |
| Mapper | `XxxMapper` | `NoticeMapper` |
| Wrapper | `XxxWrapper` | `NoticeWrapper` |
| Excel 导入导出 | `XxxExcel` / `XxxImportListener` | `UserExcel`、`UserImportListener` |
| 表名 | `blade_` + 下划线 | `blade_notice`、`blade_user` |

### 5.3 变量命名

- 必须具有明确语义：`Exception exception`（✅）`Exception e`（❌）；`List<Notice> noticeList`（✅）`List<Notice> list`（❌）
- 冲突时提升语义：`Cache cache; Cache noticeCache`（✅）`Cache cache1; Cache cache2`（❌）

---

## 6. 编码规范

### 6.1 注解顺序

- **Controller 类**：`@RestController` → Lombok（`@AllArgsConstructor`） → 安全注解（`@PreAuth(RoleConstant.HAS_ROLE_ADMIN)`，如果整类受限）→ `@RequestMapping(AppConstant.APPLICATION_XXX_NAME + "/yyy")` → `@ApiSort` → `@Tag`
- **Controller 方法**：HTTP 方法注解（`@GetMapping`/`@PostMapping`）→ `@ApiOperationSupport(order = N)` → `@Operation` → `@PreAuth`（如果方法级受限）
- **Entity 类**：`@Data` → `@EqualsAndHashCode(callSuper = true)`（继承 BaseEntity/TenantEntity 时必须） → `@TableName` → `@Schema`（字段级使用）
- **VO 类**：`@Data` → `@EqualsAndHashCode(callSuper = true)` → `@Schema`

### 6.2 Entity-Service 继承体系（核心）

Entity 基类的选择**直接决定** Service 和 ServiceImpl 的继承方式，三者必须配套：

| 场景 | Entity | Service 接口 | ServiceImpl | 示例 |
| --- | --- | --- | --- | --- |
| **多租户业务表** | `extends TenantEntity` | `extends BaseService<T>` | `extends BaseServiceImpl<M, T>` | `User`、`Post` |
| **非租户业务表** | `extends BaseEntity` | `extends BaseService<T>` | `extends BaseServiceImpl<M, T>` | `Notice`、`Tenant` |
| **轻量级/关系表** | `implements Serializable` | `extends IService<T>` | `extends ServiceImpl<M, T>` | `RoleMenu`、`Dict`、`Role`、`Dept` |

- `TenantEntity` 继承自 `BaseEntity` 并额外包含 `tenantId`
- `BaseEntity` 包含：`id`、`createUser`、`createDept`、`createTime`、`updateUser`、`updateTime`、`status`、`isDeleted`
- `BaseService`/`BaseServiceImpl` 是 BladeX 增强版，提供 `deleteLogic(List<Long>)` 等方法，**要求 Entity 继承 BaseEntity 或 TenantEntity**
- `IService`/`ServiceImpl` 是 MyBatis-Plus 原生版，用于 `implements Serializable` 的轻量实体，删除用 `removeByIds()`
- 多租户表通过 `application.yml` 中 `blade.tenant.tables` 列出实现字段隔离（如 `blade_notice`），**不必强制继承 TenantEntity**；是否继承 TenantEntity 取决于业务是否需要在 Entity 层显式携带 `tenantId` 字段

### 6.3 Controller 约定

- 视需要继承 `BladeController`（获取 `getUser()` 等便捷方法），使用 `@AllArgsConstructor` 注入依赖
- **路由前缀使用 `AppConstant.APPLICATION_XXX_NAME` 常量**（单体架构特有，与商业版 Boot 写法一致；Cloud 版无需前缀）
- 统一返回 `R<T>` 响应体（`R.data(...)` / `R.status(...)` / `R.success(...)`）
- Entity → VO 转换通过 `XxxWrapper.build().entityVO(entity)` / `.pageVO(page)` / `.listVO(list)`
- 权限控制使用 `@PreAuth(RoleConstant.HAS_ROLE_ADMIN)`（角色级）；**本版本不支持** 菜单级 `@PreAuth(menu = "xxx")` 和 `@PreAuth(AuthConstant.PERMIT_ALL)` 写法
- 需要跳过 Swagger 展示时使用 `@Hidden`（如 `UserController`）

### 6.4 依赖注入

- Controller 使用 `@AllArgsConstructor` + `private` 字段（参见 `NoticeController`）
- Service 需要额外依赖时使用 `@AllArgsConstructor` 或 `@RequiredArgsConstructor` + `private final` 字段
- 禁止字段注入 `@Autowired`（除非与第三方类协同时必要）

### 6.5 Lombok

禁止手写 getter/setter，统一使用 `@Data`、`@EqualsAndHashCode(callSuper = true)`、`@AllArgsConstructor`、`@RequiredArgsConstructor`、`@Slf4j`、`@SneakyThrows` 等

### 6.6 Java 17 特性

- 可使用增强 switch、Text Blocks、Pattern Matching for instanceof、`@Serial`（参见 `Notice`/`User`）
- **禁止** `var` 类型推断，所有变量显式声明类型
- 优先使用 Stream API，Lambda 保持简洁
- `serialVersionUID` 必须加 `@Serial` 注解

### 6.7 MyBatis-Plus

- 简单查询使用 `LambdaQueryWrapper` 或 `Wrappers.<T>query()`
- 复杂查询写在 Mapper XML 中，Mapper XML 与接口**同包**放置（位于 `src/main/java` 下，由 `pom.xml` `<resources>` 声明打包）
- 分页统一使用 `Condition.getPage(query)` + `Condition.getQueryWrapper(param, Entity.class)`
- 禁止 JDBC 直连查询
- 主键使用雪花算法 `IdType.ASSIGN_ID`，Long 类型主键必须标注 `@JsonSerialize(using = ToStringSerializer.class)` 防止前端精度丢失

### 6.8 日志

- 使用 `@Slf4j`，占位符传参 `log.info("user {} login", userId)`（禁止字符串拼接）
- 包含关键业务标识，异常必须携带堆栈 `log.error("...", e)`，禁止打印敏感信息（密码、Token、手机号等）
- 系统内置三类日志事件：`ApiLogListener`、`ErrorLogListener`、`UsualLogListener`（位于 `core/log/event/`），通过 `AsyncEventDispatcher` 异步落库

---

## 7. 数据库规范

- **表名前缀**：`blade_`（如 `blade_notice`、`blade_user`、`blade_role_menu`）
- **业务表必含字段**：`id`（BIGINT）、`tenant_id`（VARCHAR，多租户表）、`create_user`、`create_dept`、`create_time`、`update_user`、`update_time`、`status`、`is_deleted`
- **主键策略**：雪花算法（`IdType.ASSIGN_ID`），Long 字段用 `@JsonSerialize(using = ToStringSerializer.class)` 防精度丢失
- **逻辑删除**：`is_deleted`（INT），`0` = 未删除，`1` = 已删除（`application.yml` 中 `logic-delete-value: 1` / `logic-not-delete-value: 0`）
- **索引命名**：唯一索引 `uk_` 前缀，普通索引 `idx_` 前缀
- **多数据库**：支持 7 种数据库，修改表结构时需同步所有脚本
- **SQL 脚本目录**：`doc/sql/`，包含全量建表脚本与版本升级脚本

---

## 8. 多租户与安全

### 8.1 多租户

- 默认**字段隔离模式**（`tenant_id`），MP 内置方法自动注入
- 租户表通过 `application.yml` `blade.tenant.tables` 配置声明（当前仅声明 `blade_notice`，如需扩展请在 yml 中追加表名）
- 自定义 SQL 需手动过滤：`AuthUtil.getTenantId()` 或 `SecureUtil.getTenantId()`
- **本版本不支持** `@TenantDS` / `@NonDS` 数据源切换注解

### 8.2 权限体系

- **角色权限**：`@PreAuth(RoleConstant.HAS_ROLE_ADMIN)`（或自定义 SpEL 表达式）
- **数据权限**：`blade-starter-datascope` DataScopeHandler，行级过滤，通过 `RoleScope` 配置
- **本版本不含**：接口权限（ApiScopeHandler）、菜单级权限表达式 `@PreAuth(menu = "xxx")`、`@PreAuth(AuthConstant.PERMIT_ALL)` 通配放行（如需放行接口，请在 `blade.secure.skip-url` 中配置 URL 通配）

### 8.3 登录与 Token

- 登录密码使用 **SM2 国密**加解密（`blade.auth.public-key` / `blade.auth.private-key`，由 `Sm2KeyGenerator` 生成）
- Token 使用 JWT 签名（`blade.token.sign-key`）+ 可选 AES 加密（`blade.token.aes-key`）
- 多 Granter 支持：`PasswordTokenGranter`、`CaptchaTokenGranter`、`RefreshTokenGranter`、`SocialTokenGranter`
- 获取当前用户：`SecureUtil.getUser()` / `SecureUtil.getUserId()` / `SecureUtil.getTenantId()` / `SecureUtil.isAdministrator()`

---

## 9. 缓存规范

- `CacheUtil` 统一操作，常量命名在 `CacheNames`（`common/cache/`）
- 数据变更后清除缓存：`CacheUtil.clear(CACHE_NAME, Boolean.FALSE)`
- 字典翻译：`DictCache.getValue("dict-code", value)` 或通过 `IDictService.getValue(code, dictKey)`
- Redis 序列化使用 Protostuff（默认配置），键分布式作用域；本地小数据使用 Ehcache

---

## 10. 开源许可头部

所有新建的 Java 源文件必须在文件顶部包含以下 Apache 2.0 许可头部：

```java
/**
 * Copyright (c) 2018-2099, Chill Zhuang 庄骞 (bladejava@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
```

> **重要**：本工程为开源版本，**不要**写入 BladeX 商业许可证头部。若需要迁移为商业版本，请以商业项目代码样例为准。

---

## 11. 构建与部署

```bash
mvn clean compile -DskipTests                  # 编译验证
mvn clean package -DskipTests                  # 打包为 SpringBlade.jar（finalName 已配置）
mvn clean package docker:build -DskipTests     # 构建并推送 Docker 镜像（Harbor：${docker.registry.url}）
java -jar target/SpringBlade.jar               # 本地启动
java -Dspring.profiles.active=prod -jar target/SpringBlade.jar   # 指定环境启动
```

启动前需配置环境变量（由 `application.yml` 读取）：

```bash
BLADE_OAUTH2_PUBLIC_KEY    # SM2 公钥
BLADE_OAUTH2_PRIVATE_KEY   # SM2 私钥
BLADE_TOKEN_SIGN_KEY       # JWT 签名密钥
BLADE_TOKEN_CRYPTO_KEY     # Token AES 加密密钥
```

---

## 12. Git 提交规范

当需要提交代码时，优先使用 **`/blade-commit`** skill，它会自动分析变更内容并生成符合项目规范的 Gitmoji 提交信息。

采用 **Gitmoji** 风格，中文描述。格式：`:<gitmoji>: 简要描述`

| Gitmoji | 场景 | 示例 |
| --- | --- | --- |
| `:sparkles:` | 新增功能 | `:sparkles: 登录认证新增IP锁定逻辑` |
| `:zap:` | 优化重构 | `:zap: 优化字典查询排序逻辑` |
| `:bug:` | 修复缺陷 | `:bug: 修复用户查询未过滤已删除数据` |
| `:tada:` | 版本发布 | `:tada: x.x.x.RELEASE` |
| `:recycle:` | 代码重构 | `:recycle: 重构租户删除逻辑` |

---

## 13. 框架组件速查

| 组件 | 用途 |
| --- | --- |
| `R<T>` | 统一 API 响应（`org.springblade.core.tool.api.R`） |
| `BladeController` | Controller 基类（`org.springblade.core.boot.ctrl`） |
| `TenantEntity` / `BaseEntity` | 实体基类（`org.springblade.core.mp.base`） |
| `BaseService` / `BaseServiceImpl` | BladeX 增强 Service（含 `deleteLogic`） |
| `BaseEntityWrapper` | Entity→VO 转换基类 |
| `BladeUser` | 当前登录用户（含租户信息） |
| `Condition` / `Query` | 查询条件构建 / 分页参数 |
| `CacheUtil` / `DictCache` | 缓存工具 / 字典缓存 |
| `SecureUtil` / `AuthUtil` | 获取当前用户/租户信息 |
| `BladeRedis` | Redis 操作封装 |
| `Func` / `BeanUtil` / `StringUtil` | 通用工具类 |
| `ServiceException` | 业务异常 |
| `@PreAuth` | 角色权限注解（`RoleConstant.HAS_ROLE_ADMIN`） |
| `@ApiOperationSupport` / `@Operation` / `@Tag` / `@Schema` | Knife4j + OpenAPI 3 文档注解 |
| `@Hidden` | 隐藏 Swagger 接口展示 |
| `LauncherConstant` / `AppConstant` | 应用常量（模块名前缀、应用名） |

---

## 14. 风格一致性

1. 风格不确定时，优先查阅现有代码并模仿当前模块的实现方式
2. 新模块参考 `modules/desk`（最简样板） 或 `modules/system`（完整样板）
3. 现有模块已满足需求时，禁止自写替代实现
4. 与用户交互全程使用**中文**，代码注释和 Javadoc 亦使用中文
5. 本工程为开源版，**不要**引用商业版特有能力（Flowable、PowerJob、`@TenantDS`、`@BladeView`、接口权限等）；如确实需要，请先向用户确认是否切换到商业版代码库
