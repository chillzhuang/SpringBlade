# CLAUDE.md

欢迎来到 SpringBlade 开源微服务版（master 分支）。本文档汇总了微服务拓扑、API/Service 分层、Feign 调用、网关与配置中心等常见约定，帮助 Claude Code 快速上手，产出与项目风格一致的代码。

作为 AI 助手参与本项目开发时，你必须：

- 动手前先看一看目标服务及其 API 模块的现有实现，模仿它的结构与风格通常是最快的路径
- 需要外部知识时，优先查阅 Spring Boot、Spring Cloud、MyBatis-Plus、Nacos 等官方文档，而不是凭记忆作答
- 需求不够清晰时，先复述已知信息、抛出关键澄清问题，再动手
- 面对跨服务或跨模块改动，拆成可验证的小步骤逐步推进比一气呵成更稳妥

> 所有开发内容必须建立在深度思考过的基础之上，禁止机械生成与错误填充。
> 如果你已了解所有规范，请在用户第一次对话时说明："我已充分了解 SpringBlade 开源微服务版开发规范。"

---

## 1. 项目概述

SpringBlade 是 BladeX 平台的**开源微服务版本**（master 分支），基于 Spring Cloud + Spring Boot 构建，由 Nacos 做服务发现与配置中心，Sentinel 做熔断限流，Spring Cloud Gateway 做统一入口，依赖 blade-tool 核心库。

---

## 2. 项目架构

```
SpringBlade/                            # Maven 父工程（pom 聚合）
├── blade-auth/                         # 认证授权服务（多 granter：password/refresh/social/captcha）
├── blade-gateway/                      # Spring Cloud Gateway 网关（WebFlux + JWT 鉴权过滤器）
├── blade-common/                       # 公共模块（常量、启动器、工具、缓存键）
├── blade-ops/                          # 运维服务父模块
│   ├── blade-admin/                    #   Spring Boot Admin 监控
│   ├── blade-develop/                  #   代码生成（Velocity 模板）
│   ├── blade-report/                   #   报表服务（UReport2）
│   ├── blade-resource/                 #   资源 / OSS 管理
│   ├── blade-seata-order/              #   Seata 分布式事务演示：订单
│   └── blade-seata-storage/            #   Seata 分布式事务演示：库存
├── blade-service/                      # 业务服务父模块
│   ├── blade-system/                   #   系统管理（用户/角色/菜单/字典/租户/部门/岗位）
│   ├── blade-desk/                     #   工作台（通知公告）
│   ├── blade-log/                      #   日志服务（API/Error/Usual 日志落库）
│   └── blade-demo/                     #   业务演示模块
├── blade-service-api/                  # 业务契约父模块（Entity / VO / DTO / Feign）
│   ├── blade-system-api/               #   ISysClient（部门、租户、客户端信息）
│   ├── blade-user-api/                 #   IUserClient（用户核心信息）
│   ├── blade-desk-api/                 #   桌面 API 契约
│   ├── blade-dict-api/                 #   IDictClient（字典翻译）
│   ├── blade-scope-api/                #   数据权限契约
│   └── blade-demo-api/                 #   演示模块契约
├── doc/
│   ├── nacos/                          # Nacos 配置脚本（blade.yaml、各环境 datasource）
│   └── sql/                            # 多数据库建表 & 升级脚本
├── script/                             # Docker / FatJar / 服务脚本
└── pom.xml                             # 父 POM（blade-tool 依赖管理）
```

> **与开源 Boot 版的关键区别**：Cloud 为多模块 Maven 工程，存在 **API/Service 分离**，跨服务调用通过 Feign，Controller 路由**不带** `AppConstant` 前缀（网关按服务名路由）。

### 2.1 分层架构

每个业务域遵循 **API 模块 + Service 模块**双层划分：

| 层次 | 包路径 | 所在模块 |
| --- | --- | --- |
| Controller | `controller/` | Service 模块（`blade-service/*` 或 `blade-ops/*`） |
| Service / ServiceImpl | `service/` + `service/impl/` | Service 模块 |
| Mapper（接口 + XML 同包） | `mapper/` | Service 模块 |
| Wrapper | `wrapper/`（Entity → VO 转换） | Service 模块 |
| Excel 导入导出 | `excel/` | Service 模块 |
| Feign 实现类 | `feign/` | Service 模块（实现对应 API 模块的 Feign 接口） |
| Entity | `entity/` | **API 模块（`blade-*-api`）** |
| VO / DTO | `vo/` `dto/` | **API 模块** |
| Feign Client + Fallback | `feign/` | **API 模块** |

### 2.2 API / Service 分离原则

- **API 模块**（`blade-*-api`）：定义 Entity、VO、DTO、Feign 接口 + Fallback，供其他服务依赖；**不含**具体业务逻辑
- **Service 模块**（`blade-service/*`、`blade-ops/*`）：实现 Controller、ServiceImpl、Mapper、Wrapper 与 Feign 接口的服务端实现
- 跨服务调用**必须**通过 Feign Client，禁止直接依赖其他 Service 模块
- 公共常量与工具放在 `blade-common`

---

## 3. 技术栈

| 类别 | 技术 |
| --- | --- |
| JDK | Java 17+ |
| 基础框架 | Spring Boot / Spring Cloud / Spring Cloud Alibaba / Maven |
| 核心库 | blade-core-bom、blade-core-cloud、blade-core-boot |
| 注册中心 / 配置中心 | Nacos |
| 熔断限流 | Sentinel（`spring-cloud-starter-alibaba-sentinel`） |
| 分布式事务 | Seata（`blade-starter-transaction`，见 `blade-seata-order`、`blade-seata-storage`） |
| RPC | OpenFeign（与 Sentinel / OkHttp 集成） |
| 网关 | Spring Cloud Gateway（WebFlux） |
| ORM | MyBatis-Plus + dynamic-datasource-spring-boot3-starter |
| 安全 | blade-core-secure（JWT + 可选 AES 加密 + SM2 国密登录） |
| 缓存 | BladeRedis（Protostuff 序列化，响应式 Gateway 侧用 Reactive Redis） |
| 数据权限 | blade-starter-datascope（DataScopeHandler 行级过滤） |
| 监控 | Spring Boot Admin（`blade-admin`）+ Actuator |
| 文档 | Knife4j（网关聚合 + 各服务 OpenAPI 3） |
| 报表 | UReport2（`blade-report`） |
| 代码生成 | blade-starter-develop + Apache Velocity（`blade-develop`） |
| 验证码 | easy-captcha |
| JWT | jjwt-impl + jjwt-jackson |
| 数据库 | MySQL / PostgreSQL / Oracle / SQL Server / 达梦 / 人大金仓 / 崖山 |

---

## 4. 开发规范

### 4.1 编写新功能前

1. 先阅读目标服务中已有的类，理解其结构、命名和风格
2. 标准参考模块：`blade-service/blade-system`（Service 最完整）、`blade-service-api/blade-system-api`（API 契约最完整）、`blade-service/blade-desk` + `blade-desk-api`（最简样板）
3. 主动模仿现有代码风格，包括缩进（Java 用 Tab，YAML/JSON 用 2 空格）、注解顺序、Javadoc 格式
4. 新建 Java 源文件必须包含 Apache 2.0 许可证头部和 Javadoc 类注释（`@author Chill`）

### 4.2 编写完成后

1. 使用 `mvn clean compile -DskipTests` 编译验证（父工程根目录执行会编译所有子模块），编译不通过必须修复
2. 引入模块依赖前先检查循环依赖；**跨服务依赖必须经由 `blade-*-api` + Feign**，禁止 `blade-service/A` 直接依赖 `blade-service/B`
3. 编译通过后将测试交由用户执行，**不得自行执行任何测试**（包括 `mvn test`、启动 Nacos/服务、访问接口）
4. 除非用户明确要求，不应撰写示例或额外文档

### 4.3 代码生成

当需要生成 CRUD 全套代码（Entity、VO、Service、Controller、Wrapper、Mapper、Feign、建表语句等）时，优先使用 **`/blade-design`** skill。该 skill 可根据模块名、实体名和字段列表，自动生成符合 BladeX 框架规范的后端代码和多数据库建表语句，确保生成结果与项目风格完全一致。

本工程亦内置 `blade-ops/blade-develop` 代码生成服务（Velocity 模板），可在运行时通过后台界面生成。

---

## 5. 命名规范

### 5.1 包名

统一前缀 `org.springblade`，按业务域划分：`system`、`system.user`、`desk`、`auth`、`gateway`、`develop`、`report`、`resource`、`common` 等。API 与 Service 两侧使用相同业务域包名，彼此仅通过模块边界隔离。

### 5.2 类名

| 类型 | 规则 | 示例 |
| --- | --- | --- |
| Entity | 直接类名 | `Post`、`User`、`Notice` |
| VO / DTO | `XxxVO` / `XxxDTO` | `MenuVO`、`RoleDTO` |
| Service 接口 | `IXxxService` | `IParamService`、`IUserService` |
| Service 实现 | `XxxServiceImpl` | `ParamServiceImpl` |
| Controller | `XxxController` | `ParamController` |
| Mapper | `XxxMapper` | `ParamMapper` |
| Wrapper | `XxxWrapper` | `UserWrapper` |
| Feign 接口 / 降级 | `IXxxClient` / `IXxxClientFallback` | `ISysClient` / `ISysClientFallback`、`IUserClient` / `IUserClientFallback` |
| 表名 | `blade_` + 下划线 | `blade_user`、`blade_post` |

### 5.3 变量命名

- 必须具有明确语义：`Exception exception`（✅）`Exception e`（❌）；`List<Post> postList`（✅）`List<Post> list`（❌）
- 冲突时提升语义：`Cache cache; Cache postCache`（✅）`Cache cache1; Cache cache2`（❌）

---

## 6. 编码规范

### 6.1 注解顺序

- **Controller 类**：`@RestController` → Lombok（`@AllArgsConstructor`） → `@RequestMapping("/resource")` → `@Tag`
- **Controller 方法**：HTTP 方法注解（`@GetMapping`/`@PostMapping`）→ `@ApiOperationSupport(order = N)` → `@Operation` → `@PreAuth`（如需角色控制）
- **Feign 接口**：`@FeignClient(value = AppConstant.APPLICATION_XXX_NAME, fallback = IXxxClientFallback.class)`
- **Feign Fallback**：`@Component` → 实现对应接口
- **Entity 类**：`@Data` → `@TableName` → `@EqualsAndHashCode(callSuper = true)`（继承 BaseEntity/TenantEntity 时必须） → `@Schema`（字段级使用）
- **VO 类**：`@Data` → `@EqualsAndHashCode(callSuper = true)` → `@Schema`

### 6.2 Entity-Service 继承体系（核心）

Entity 基类的选择**直接决定** Service 和 ServiceImpl 的继承方式，三者必须配套：

| 场景 | Entity | Service 接口 | ServiceImpl | 示例 |
| --- | --- | --- | --- | --- |
| **多租户业务表** | `extends TenantEntity` | `extends BaseService<T>` | `extends BaseServiceImpl<M, T>` | `User`、`Post`、`Notice` |
| **非租户业务表** | `extends BaseEntity` | `extends BaseService<T>` | `extends BaseServiceImpl<M, T>` | `Tenant` |
| **轻量级/关系表** | `implements Serializable` | `extends IService<T>` | `extends ServiceImpl<M, T>` | `RoleMenu`、`Dict`、`Role` |

- `TenantEntity` 继承自 `BaseEntity` 并额外包含 `tenantId`
- `BaseEntity` 包含：`id`、`createUser`、`createDept`、`createTime`、`updateUser`、`updateTime`、`status`、`isDeleted`
- `BaseService`/`BaseServiceImpl` 是 BladeX 增强版，提供 `deleteLogic(List<Long>)` 等方法，**要求 Entity 继承 BaseEntity 或 TenantEntity**
- `IService`/`ServiceImpl` 是 MyBatis-Plus 原生版，用于 `implements Serializable` 的轻量实体，删除用 `removeByIds()`

### 6.3 Controller 约定

- 视需要继承 `BladeController`（获取 `getUser()` 等便捷方法），使用 `@AllArgsConstructor` 注入依赖
- **路由直接使用资源路径**（如 `@RequestMapping("/param")`），网关按服务名路由，**无需** `AppConstant` 前缀（与 Boot 版区别点）
- 统一返回 `R<T>` 响应体（`R.data(...)` / `R.status(...)` / `R.success(...)`）
- Entity → VO 转换通过 `XxxWrapper.build().entityVO(entity)` / `.pageVO(page)` / `.listVO(list)`
- 权限控制使用 `@PreAuth(RoleConstant.HAS_ROLE_ADMIN)`（角色级）；**本版本不支持** 菜单级 `@PreAuth(menu = "xxx")`、`@PreAuth(AuthConstant.PERMIT_ALL)` 写法，也不含 ApiScopeHandler 接口权限
- 需要跳过 Swagger 展示时使用 `@Hidden`

### 6.4 Feign Client（Cloud 特有）

- **接口定义在 API 模块** 的 `feign/` 包中，由所有调用方共享依赖
- 注解签名：`@FeignClient(value = AppConstant.APPLICATION_XXX_NAME, fallback = IXxxClientFallback.class)`
- 路径常量定义在接口内部：`String API_PREFIX = "/xxx"`；方法路径使用 `API_PREFIX + "/yyy"` 拼接
- 返回类型统一使用 `R<T>`（除非明确只返回原始对象，如 `ISysClient#getDept` 返回 `Dept`）
- **降级类**：与接口同包，命名 `IXxxClientFallback`，标注 `@Component` 并实现全部方法，方法返回 `null` 或安全缺省值
- Feign 实现（服务端）位于对应 Service 模块的 `feign/` 包，`@RestController` 实现接口并复用内部 Service
- Feign + Sentinel 默认开启（`feign.sentinel.enabled=true`）+ OkHttp 连接池（`feign.okhttp.enabled=true`）

### 6.5 依赖注入

- Controller 使用 `@AllArgsConstructor` + `private` 字段
- Service 需要额外依赖时使用 `@AllArgsConstructor` 或 `@RequiredArgsConstructor` + `private final` 字段
- Feign 客户端通过构造器注入，Spring Cloud 自动代理

### 6.6 Lombok

禁止手写 getter/setter，统一使用 `@Data`、`@EqualsAndHashCode(callSuper = true)`、`@AllArgsConstructor`、`@RequiredArgsConstructor`、`@Slf4j`、`@SneakyThrows` 等

### 6.7 Java 17 特性

- 可使用增强 switch、Text Blocks、Pattern Matching for instanceof、`@Serial`
- **禁止** `var` 类型推断，所有变量显式声明类型
- 优先使用 Stream API，Lambda 保持简洁
- `serialVersionUID` 必须加 `@Serial` 注解

### 6.8 MyBatis-Plus

- 简单查询使用 `LambdaQueryWrapper` 或 `Wrappers.<T>query()`
- 复杂查询写在 Mapper XML 中，Mapper XML 与接口**同包**放置（位于 `src/main/java` 下，由 `pom.xml` `<resources>` 声明打包）
- 分页统一使用 `Condition.getPage(query)` + `Condition.getQueryWrapper(param, Entity.class)`
- 禁止 JDBC 直连查询
- 主键使用雪花算法 `IdType.ASSIGN_ID`，Long 类型主键必须标注 `@JsonSerialize(using = ToStringSerializer.class)` 防止前端精度丢失
- 多数据源切换通过 dynamic-datasource 的 `@DS("...")` 注解，数据源定义在 Nacos 配置

### 6.9 日志

- 使用 `@Slf4j`，占位符传参 `log.info("user {} login", userId)`（禁止字符串拼接）
- 包含关键业务标识，异常必须携带堆栈 `log.error("...", e)`，禁止打印敏感信息（密码、Token、手机号等）
- API 调用日志、错误日志、业务日志由 `blade-service/blade-log` 异步落库，事件通过 `AsyncEventDispatcher` 发布

---

## 7. 数据库规范

- **表名前缀**：`blade_`（如 `blade_user`、`blade_role_menu`）
- **业务表必含字段**：`id`（BIGINT）、`tenant_id`（VARCHAR，多租户表）、`create_user`、`create_dept`、`create_time`、`update_user`、`update_time`、`status`、`is_deleted`
- **主键策略**：雪花算法（`IdType.ASSIGN_ID`），Long 字段用 `@JsonSerialize(using = ToStringSerializer.class)` 防精度丢失
- **逻辑删除**：`is_deleted`（INT），`0` = 未删除，`1` = 已删除
- **索引命名**：唯一索引 `uk_` 前缀，普通索引 `idx_` 前缀
- **多数据库**：支持 7 种数据库，SQL 脚本位于 `doc/sql/`（全量建表 + 版本升级脚本），修改表结构时需同步所有脚本

---

## 8. 多租户与安全

### 8.1 多租户

- 默认**字段隔离模式**（`tenant_id`），MP 内置方法自动注入
- 租户表通过 Nacos `blade.yaml` 中 `blade.tenant.tables` 配置声明
- 自定义 SQL 需手动过滤：`AuthUtil.getTenantId()` 或 `SecureUtil.getTenantId()`
- **本版本不支持** `@TenantDS` / `@NonDS` 数据源切换注解；如需物理隔离，可结合 dynamic-datasource 的 `@DS` 配合业务逻辑实现

### 8.2 权限体系

- **角色权限**：`@PreAuth(RoleConstant.HAS_ROLE_ADMIN)`（或自定义 SpEL 表达式）
- **数据权限**：`blade-starter-datascope` DataScopeHandler，行级过滤，通过 `RoleScope` 配置
- **本版本不含**：接口权限（ApiScopeHandler）、菜单级权限表达式 `@PreAuth(menu = "xxx")`、`@PreAuth(AuthConstant.PERMIT_ALL)` 通配放行
- 接口放行请在 Nacos `blade.secure.skip-url` 中配置 URL 通配，或在 `blade-gateway` 的 `AuthFilter` 白名单中配置

### 8.3 登录与 Token

- 登录密码使用 **SM2 国密**加解密（`blade.auth.public-key` / `blade.auth.private-key`，由 `blade-auth` 模块的 `Sm2KeyGenerator` 生成）
- Token 使用 JWT 签名（`blade.token.sign-key`）+ 可选 AES 加密（`blade.token.aes-key`）
- 多 Granter 支持（`blade-auth/granter/`）：`PasswordTokenGranter`、`CaptchaTokenGranter`、`RefreshTokenGranter`、`SocialTokenGranter`
- **网关统一鉴权**：`blade-gateway/filter/AuthFilter` 作为 `GlobalFilter` 验证 JWT，下游服务通过 `exchange attributes` 透传用户上下文
- 获取当前用户：`SecureUtil.getUser()` / `SecureUtil.getUserId()` / `SecureUtil.getTenantId()` / `SecureUtil.isAdministrator()`

### 8.4 分布式事务

- 由 `blade-starter-transaction`（Seata）提供支持，参考 `blade-ops/blade-seata-order` + `blade-ops/blade-seata-storage` 双服务示例
- 需要跨服务事务的场景标注 `@GlobalTransactional`

---

## 9. 缓存规范

- `CacheUtil` 统一操作，常量命名在 `blade-common/cache/CacheNames`
- 数据变更后清除缓存：`CacheUtil.clear(CACHE_NAME, Boolean.FALSE)`
- 字典翻译：推荐跨服务通过 `IDictClient.getValue(code, dictKey)`（Feign）；服务本地可用 `DictCache.getValue(...)`
- Redis 序列化使用 Protostuff（默认配置），键分布式作用域；Gateway 侧使用 Reactive Redis

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

### 11.1 Maven 构建

```bash
mvn clean compile -DskipTests                      # 根目录执行：编译所有子模块
mvn clean package -DskipTests                      # 打包所有服务为 FatJar
mvn clean package -DskipTests -pl blade-service/blade-system -am  # 只打包 system 服务及其依赖模块
```

- 父 POM 使用 `${revision}` 占位符管理版本，由 `flatten-maven-plugin` 在 `process-resources` 阶段展开
- 各服务打包产物通过 `directory-maven-plugin` 汇总复制到根目录 `target/`
- `finalName` 使用 `${project.name}`，产物命名与 artifactId 一致

### 11.2 Nacos 配置

- 配置文件位于 `doc/nacos/`：`blade.yaml`（公共配置）+ `blade-dev.yaml`/`blade-test.yaml`/`blade-prod.yaml`（环境差异）
- 数据源、密钥、Swagger、Knife4j、Sentinel、Feign 等共享参数统一在 Nacos 配置
- 各服务 `src/main/resources/application-{env}.yml` 仅保留 `server.port` 与 `spring.datasource.url` 等少量本地覆盖

### 11.3 Docker 部署

- `script/docker/docker-compose.yml` 提供一体化编排：Nacos + Sentinel + Redis + Nginx + 全部微服务
- 默认网段 `blade_net`，Nacos `172.30.0.48`，Sentinel `172.30.0.58`
- Harbor 镜像推送通过父 POM 的 `docker.registry.url` 属性控制

### 11.4 必备环境变量

```bash
BLADE_OAUTH2_PUBLIC_KEY    # SM2 公钥
BLADE_OAUTH2_PRIVATE_KEY   # SM2 私钥
BLADE_TOKEN_SIGN_KEY       # JWT 签名密钥
BLADE_TOKEN_CRYPTO_KEY     # Token AES 加密密钥
```

### 11.5 服务端口（默认）

| 服务 | 端口 |
| --- | --- |
| blade-gateway | 80 |
| blade-auth | 8100 |
| blade-admin | 7002 |
| blade-system | 8106 |
| blade-desk | 8103 |
| blade-log | 8102 |
| blade-develop | 8108 |
| blade-report | 8109 |
| blade-resource | 8110 |

> 实际端口以 `src/main/resources/application-{env}.yml` 中 `server.port` 为准。

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
| `BladeCloudApplication` | Cloud 服务启动注解（`blade-auth`、业务服务使用） |
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
| `@FeignClient` + `AppConstant.APPLICATION_XXX_NAME` | 服务间调用 |
| `ISysClient` / `IUserClient` / `IDictClient` | 常用 Feign 契约 |
| `@GlobalTransactional` | Seata 分布式事务 |
| `@ApiOperationSupport` / `@Operation` / `@Tag` / `@Schema` | Knife4j + OpenAPI 3 文档注解 |
| `LauncherConstant` / `AppConstant` / `CommonConstant` | 启动常量 / 应用名 / 公共常量 |

---

## 14. 风格一致性

1. 风格不确定时，优先查阅现有服务并模仿当前业务域的实现方式
2. 新增业务域时，**先建 API 模块再建 Service 模块**；API 模块只放契约，不引入 Service 依赖
3. 新服务参考 `blade-service/blade-system` + `blade-service-api/blade-system-api`（最完整） 或 `blade-service/blade-desk` + `blade-desk-api`（最简样板）
4. 现有服务或契约已满足需求时，禁止自写替代实现；跨服务使用数据请走 Feign，不要重复落库
5. 与用户交互全程使用**中文**，代码注释和 Javadoc 亦使用中文
6. 本工程为开源版，**不要**引用商业版特有能力（Flowable、PowerJob、`@TenantDS`、`@BladeView`、接口权限、`blade-ops-api`、`blade-plugin` 等）；如确实需要，请先向用户确认是否切换到商业版代码库
