# SpringBlade Boot(单体版)Spring Boot 4 升级适配指南

> 适用工程:`SpringBlade`(Boot 单体版,单模块 jar 工程,`src/main/java/org/springblade/modules/*`)
> 依赖基座:`blade-tool` 已升级到 Spring Boot 4.1.0 / Spring Cloud 2025.1.2 并发布为 `5.0.0`
> 文档性质：升级指导 / 迁移规范。既供工程师直接阅读执行，也供 AI 依此拆解任务、逐文件推进迁移。

---

## 0. 核心前提:版本继承自 blade-tool

单体工程通过 `blade-core-bom` 继承 blade-tool 的全套版本管理。Spring Boot 4.1.0 / Jackson 2 降级 / Web 容器切 Tomcat / 全局异常等框架级适配**全部由 blade-tool 承担**,单体工程只需处理:版本号、maven 插件、MyBatis-Plus 包迁移、Knife4j 彻底移除。

> ⚠️ 前置动作:先在 blade-tool 工程执行 `mvn clean install`,把 `5.0.0` 装到本地/私服。

---

## 1. POM(`pom.xml`)

### 1.1 版本号指向升级后的 blade-tool

```xml
<!-- 改前 -->
<blade.tool.version>4.10.0</blade.tool.version>
<!-- 改后 -->
<blade.tool.version>5.0.0</blade.tool.version>
```

### 1.2 Swagger UI:Knife4j → springdoc

```xml
<!-- 改前 -->
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-openapi3-ui</artifactId>
</dependency>
<!-- 改后(版本由 blade-core-bom 托管为 3.0.3) -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
</dependency>
```

### 1.3 spring-boot-maven-plugin 升到 Boot4

```xml
<!-- 改前 -->
<artifactId>spring-boot-maven-plugin</artifactId>
<version>3.2.10</version>
<!-- 改后 -->
<version>4.1.0</version>
```

---

## 2. MyBatis-Plus `IService`/`ServiceImpl` 包迁移(业务代码)

MyBatis-Plus 3.5.17 把 `IService`/`ServiceImpl` 从 `com.baomidou.mybatisplus.extension.service.*` 迁到 `com.baomidou.mybatisplus.spring.service.*`(该迁移为 3.5.17 的破坏性变更,3.5.16 仍在旧 `extension.service` 包)。单体工程 Service 层大量继承,须全量改 import:

```java
// 改前
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
// 改后
import com.baomidou.mybatisplus.spring.service.IService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
```

批量替换命令(macOS):

```bash
find . -name '*.java' -not -path '*/target/*' -exec sed -i '' \
  's#com\.baomidou\.mybatisplus\.extension\.service\.#com.baomidou.mybatisplus.spring.service.#g' {} +
```

> `com.baomidou.mybatisplus.extension.plugins.*` 未迁包,不要动。

---

## 3. Knife4j 彻底移除

Knife4j 无 Boot4 版本,依赖、配置、注解全部删除,改用 springdoc 原生 Swagger-UI。

### 3.1 业务控制器注解

各控制器里的 `@ApiOperationSupport(order = N)`、`@ApiSort(N)` 及其 import 全部删除:

```java
// 删除 import
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
// 删除注解行
@ApiOperationSupport(order = 1)
@ApiSort(1)
```

批量删除命令(macOS,注解均为单行):

```bash
find . -name '*.java' -not -path '*/target/*' -exec sed -i '' \
  -e '/^[[:space:]]*import com\.github\.xiaoymin\.knife4j\.annotations\./d' \
  -e '/^[[:space:]]*@ApiOperationSupport(/d' \
  -e '/^[[:space:]]*@ApiSort(/d' {} +
```

> **不要**引入 `knife4j-annotations` 兼容包保留注解——springdoc 原生 UI 不识别,属无效元数据。接口排序改用 BladeX 的 `@ApiOrder`(见 blade-tool 指南 §9⑧)。

> ⚠️ 上面的 `find … '*.java'` 覆盖不到**代码生成器模板**:`src/{main,test}/resources/templates/controller.java.vm` 里同样有 `@ApiOperationSupport` 的 import 与注解,须一并删除,否则生成器产出的 Controller 引用已移除的 knife4j 包、编译不过。

> ✅ **删完 knife4j 排序注解后须补 `@ApiOrder`**:仅删不补会让接口在 springdoc 原生 UI 下按字母序、丢失原有顺序。本次统一在**每个带 `@Tag` 的控制器**(19 处,含 `OssEndpoint`)`@Tag` 上方加一行**无参** `@ApiOrder`(import `org.springblade.core.swagger.annotation.ApiOrder`),分组内接口即回落到**源码声明顺序**;方法体保留 `@Operation` 即可,无需逐个标 `@ApiOrder(n)`。代码生成器模板 `controller.java.vm`(main/test 两份)同样在 `@Tag` 上方补 `@ApiOrder`。机制与「方法级精确覆盖」用法详见 blade-tool 指南 §9⑧。

### 3.2 配置文件

删除 `knife4j:` 配置段:

- `src/main/resources/application.yml` 的 `#knife4j配置` 整段
- `src/test/resources/application.yml` 的 `#knife4j配置` 整段
- `springdoc:` 段保留,`swagger-ui` 的排序值若为 knife4j 私有的 `order`,改为 `alpha`
- 同一步顺带把 `swagger.version` 由 `4.10.0` 升到 `5.0.0`(接口文档页展示的版本号),`src/main` 与 `src/test` 两份 application.yml 均改

### 3.3 springdoc 放行与接口文档入口

改用 springdoc 原生 UI 后,放行其静态资源并更新数据库里的接口文档菜单地址:

- **应用放行**(`src/main/java/org/springblade/common/config/BladeConfiguration.java`):`excludePathPatterns` 新增 springdoc 三件套 `/v3/api-docs/**`、`/swagger-ui/**`、`/swagger-ui.html`,并删掉 knife4j / springfox 旧路径 `/doc.html`、`/swagger-resources/**`(这三项同时也由框架默认清单 `SecureRegistry.defaultExcludePatterns` 覆盖,此处显式声明便于自查,二者叠加不冲突)。
- **接口文档菜单**(`doc/sql/blade.mysql.all.create.sql`):`blade_menu` 里「接口文档」菜单地址由 `http://localhost/doc.html` 改为 `http://localhost/swagger-ui.html`;已初始化的库执行升级脚本 `doc/sql/blade.mysql.upgrade.4.10.0.to.5.0.0.sql`(内含该 `UPDATE`,以旧值为条件、可重复执行)。

---

## 4. Undertow → Tomcat 与 Servlet 编码配置迁移

Boot 4 移除了 Undertow(详见 blade-tool 指南 §6),容器回归 Tomcat。本工程自己的 yml 里若有 `server.undertow.*`,须换成 `server.tomcat.*`,否则是被忽略的死配置、原线程调优静默失效。

**① `src/main/resources/application.yml` 与 `src/test/resources/application.yml`**:

```yaml
# 改前
server:
  undertow:
    buffer-size: 1024
    direct-buffers: true
    threads:
      io: 16
      worker: 400

# 改后(buffer/io 无 Tomcat 对应项:worker→threads.max、io→threads.min-spare)
server:
  tomcat:
    # 线程配置
    threads:
      # 最大工作线程数(可同时处理请求的线程上限)
      max: 400
      # 最小空闲工作线程数(保持存活的常驻线程数)
      min-spare: 16
```

**②(可选)`src/main/resources/log/logback_dev.xml`**:`io.undertow` / `org.xnio.nio` 日志器换成 Tomcat 命名空间:

```xml
<logger name="org.apache.catalina" level="INFO"/>
<logger name="org.apache.coyote" level="INFO"/>
```

**③ Servlet 编码前缀迁移:`server.servlet.encoding` → `spring.servlet.encoding`**

Boot 4.0 把 Servlet 编码属性从 `server.servlet.encoding.*` 迁到新的 `spring.servlet.encoding.*`(见 blade-tool 指南 §6④)。旧前缀在 Boot 4.1.0 配置元数据里为 `level: error`(硬迁移,旧键失效)。`src/main/resources/application.yml` 与 `src/test/resources/application.yml` 均须迁移,并入各自已有的 `spring.servlet` 块(与 `multipart` 平级):

```yaml
# 改前
server:
  servlet:
    encoding:
      charset: UTF-8
      force: true

# 改后
spring:
  servlet:
    multipart:
      max-file-size: 256MB
      max-request-size: 1024MB
    # 编码配置
    encoding:
      charset: UTF-8
      force: true
```

> `force: true` 强制请求/响应用 UTF-8(Boot 默认 `false`),BladeX 刻意保留以防中文乱码,只改前缀不删配置。

---

## 5. 编译验证

> ⚠️ 升级后 `<java.version>` 为 21,**构建需 JDK 21**(JDK 17 会报 `invalid source release: 21`)。

```bash
# 先确保 blade-tool 5.0.0 已 install
cd blade-tool && mvn clean install -DskipTests

# 再构建单体工程
cd SpringBlade && mvn clean install -DskipTests -Dmaven.test.skip=true -Ddocker.skip=true
```

通过标准:`BUILD SUCCESS`,`repackage` 生成可执行 jar。

---

## 6. 运行期验证要点(交由真实环境执行)

1. 应用启动(Tomcat 容器),各业务模块接口可访问。
2. MyBatis-Plus + Druid 多数据源、分页/租户拦截器。
3. JSON 序列化(@BladeView 视图过滤、大数转字符串、null 转空)。
4. Swagger-UI 入口从 `doc.html` 改为 `/swagger-ui.html`。
5. OSS / 报表(UReport)/ 验证码(easy-captcha)等模块功能。

---

## 7. 受影响清单速览

| 类别 | 位置 | 动作 |
|------|------|------|
| 版本 | pom `blade.tool.version` | 4.10.0 → 5.0.0 |
| 依赖 | pom knife4j-openapi3-ui | → springdoc-openapi-starter-webmvc-ui |
| 插件 | pom spring-boot-maven-plugin | 3.2.10 → 4.1.0 |
| 业务 | 全部 Service | IService/ServiceImpl 迁 spring.service |
| 业务 | 14 个 Controller | 删 @ApiOperationSupport/@ApiSort + import |
| 业务 | 全部带 @Tag 的控制器(19 处)+ controller.java.vm 模板 | `@Tag` 上方新增无参 `@ApiOrder`(类级,接口按源码声明序)+ import org.springblade.core.swagger.annotation.ApiOrder(见 §3.1、blade-tool 指南 §9⑧) |
| 注释 | Controller / Service / Mapper / ServiceImpl 方法注释 | 分层收敛:Controller 精简为摘要行、Service/Mapper 补全 `@param`/`@return`、ServiceImpl private 方法补全(规范与改造记录见 blade-tool 指南 §17) |
| 配置 | main/test application.yml | 删 knife4j 段 |
| 配置 | main/test application.yml | swagger.version 4.10.0 → 5.0.0 |
| 安全 | common/config/BladeConfiguration | 放行补 /v3/api-docs/**、/swagger-ui/**、/swagger-ui.html,删死路径 /doc.html、/swagger-resources/** |
| 数据 | doc/sql blade_menu 接口文档菜单 | http://localhost/doc.html → http://localhost/swagger-ui.html |
| 容器 | main/test application.yml | server.undertow.* → server.tomcat.* |
| 配置 | main/test application.yml | server.servlet.encoding.* → spring.servlet.encoding.* |
| 可选 | logback_dev.xml | undertow 日志器换 tomcat |
| 环境 | pom `java.version` + README | 17 → 21(**构建需 JDK 21**) |
| 部署 | `Dockerfile` 基础镜像 | `bladex/alpine-java:openjdk17_cn_slim` → `openjdk21_cn_slim`(与 JDK 21 对齐;注释里的阿里云备用镜像行 `openjdk17_cn_slim` 同步改 21) |
| 文档 | CLAUDE.md 开发规范 | 同步刷新:Web 容器 Undertow→Tomcat、文档栈 Knife4j→springdoc、Controller 约定去 @ApiOperationSupport/@ApiSort、JDK/Java 17→21 |
| 插件 | pom maven-compiler-plugin | 3.11.0 → 3.15.0 |

---

## 8. Spring 7 空值注解迁移(JSpecify)

> blade-tool 与 Cloud 本次已完成迁移(含其他库空值注解归一);本工程无相关代码,无需改动。官方依据见同目录 `blade-tool-SpringBoot4-升级适配指南.md` §15。

Spring 7 把 `org.springframework.lang.@Nullable/@NonNull` 标记为 `@Deprecated(since=7.0)`(**未** forRemoval),官方转 **JSpecify**(`org.jspecify.annotations.*`,1.0.0 已随 spring-core 在 classpath,**无需新增依赖**),并建议全生态(Reactor 等)统一到 JSpecify。

- **本工程现状**:全工程**无**任何空值注解——既无 `org.springframework.lang.*`,也无 `jakarta.annotation` / `reactor.util.annotation` / `org.jetbrains.annotations` 等其他库的 `@Nullable/@NonNull`,本次零改动。
- **后续新增约定**:统一用 `org.jspecify.annotations.Nullable/NonNull`;JSpecify 注解为 `@Target(TYPE_USE)`,须紧贴类型,数组 / 可变参数写 `Type @Nullable []`;`lombok.@NonNull` 因会生成运行时判空、不属纯元数据,**不得**机械替换为 JSpecify。完整方式与 TYPE_USE 细节见 blade-tool 指南 §15。
