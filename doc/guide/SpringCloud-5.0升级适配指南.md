# SpringBlade Cloud(微服务版)Spring Boot 4 升级适配指南

> 适用工程:`SpringBlade`(Cloud 微服务版,23 模块:blade-gateway / blade-auth / blade-ops / blade-service / blade-service-api / blade-common)
> 依赖基座:`blade-tool` 已升级到 Spring Boot 4.1.0 / Spring Cloud 2025.1.2 并发布为 `5.0.0`
> 文档性质：升级指导 / 迁移规范。既供工程师直接阅读执行，也供 AI 依此拆解任务、逐文件推进迁移。

---

## 0. 核心前提:版本继承自 blade-tool

Cloud 工程通过 `blade-core-bom` 继承 blade-tool 的全套版本管理。因此**升级的主杠杆只有一个**:把 `blade.tool.version` 指向已升级的 blade-tool。Spring Boot 4.1.0 / Spring Cloud 2025.1.2 / Jackson 2 降级 / Web 容器 / 全局异常等框架级适配**全部由 blade-tool 承担**,Cloud 工程只需处理:

1. 版本号与自身直接依赖(dynamic-datasource、maven 插件)
2. 网关(WebFlux 容器 + 反应式错误处理 + 去 knife4j 聚合)
3. MyBatis-Plus `IService`/`ServiceImpl` 包迁移(业务代码)
4. Knife4j 彻底移除(依赖 + 配置 + 注解)

> ⚠️ 前置动作:先在 blade-tool 工程执行 `mvn clean install`,把 `5.0.0` 装到本地/私服,Cloud 才能拉到升级后的基座。

---

## 1. 根 POM(`pom.xml`)

### 1.1 版本号指向升级后的 blade-tool

```xml
<!-- 改前 -->
<blade.tool.version>4.10.0</blade.tool.version>
<!-- 改后 -->
<blade.tool.version>5.0.0</blade.tool.version>
```

### 1.2 dynamic-datasource 换 Boot4 版

`dependencyManagement` 里:

```xml
<!-- 改前 -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>dynamic-datasource-spring-boot3-starter</artifactId>
    <version>4.3.1</version>
</dependency>
<!-- 改后 -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>dynamic-datasource-spring-boot4-starter</artifactId>
    <version>4.5.0</version>
</dependency>
```

> 若 `dynamic-datasource-spring-boot3-starter` 还散落在子模块(如 `blade-demo`)pom 里,一并改名。

### 1.3 spring-boot-maven-plugin 升到 Boot4

`pluginManagement` 里写死的插件版本必须升,否则可执行模块 `repackage` 阶段会用 Boot3 插件打包 Boot4 应用而失败:

```xml
<!-- 改前 -->
<artifactId>spring-boot-maven-plugin</artifactId>
<version>3.2.10</version>
<!-- 改后 -->
<version>4.1.0</version>
```

---

## 2. 网关(blade-gateway)

网关是 WebFlux 反应式应用,Boot4 下有四处必改。

### 2.1 容器排除项修正(`blade-gateway/pom.xml`)

blade-tool 已把默认 Servlet 容器从 `spring-boot-starter-web` 换成 `spring-boot-starter-webmvc`。网关原先排除的是 `spring-boot-starter-web` + 已下线的 `spring-boot-starter-undertow`,升级后**排除目标要改成 `spring-boot-starter-webmvc`**,否则会把 Servlet 容器带进反应式网关:

```xml
<!-- 改前 -->
<dependency>
    <groupId>org.springblade</groupId>
    <artifactId>blade-core-launch</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </exclusion>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-undertow</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<!-- 改后 -->
<dependency>
    <groupId>org.springblade</groupId>
    <artifactId>blade-core-launch</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webmvc</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

> `spring-cloud-starter-gateway-server-webflux`(SCG 2025.1 的网关坐标)保持不变。

### 2.2 反应式错误处理器迁包(`handler/ErrorExceptionHandler.java`)

Boot4 把 WebFlux 错误处理类从 `web.reactive.error` 迁到了 `webflux.error`:

```java
// 改前
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
// 改后
import org.springframework.boot.webflux.error.ErrorWebExceptionHandler;
```

### 2.3 删除 Knife4j 网关聚合(见第 4 节;替代的自研 springdoc 聚合见 §2.5)

### 2.4 网关补 `com.fasterxml` ObjectMapper Bean(否则启动失败)

**背景**:Boot 4 默认 JSON 底座切到 Jackson 3(`tools.jackson`),`com.fasterxml.jackson.databind.ObjectMapper` 这个 Bean 在 Boot 4 里**只由 `spring-boot-jackson2` 的自动配置提供**。servlet 侧微服务经 `blade-core-tool` 带了 `spring-boot-jackson2`,不受影响;但网关排除了 servlet 栈、也不含 `blade-core-tool`,上下文里**没有** `com.fasterxml ObjectMapper` Bean。而 `AuthFilter` / `ErrorExceptionHandler` 以构造注入方式依赖它写 JSON 响应 → 启动即抛 `NoSuchBeanDefinitionException`。SB3 能跑只因当年 Boot 默认自建了该 Bean,SB4 默认切 Jackson 3 后消失。

**改法**:网关内显式提供该 Bean(**不引** `spring-boot-jackson2`,以免其 Jackson 2 编解码器干扰 WebFlux 默认的 Jackson 3 编解码):

```java
// blade-gateway/src/main/java/org/springblade/gateway/config/JacksonConfiguration.java(新增)
@Configuration(proxyBeanMethods = false)
public class JacksonConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
```

> ⚠️ 极隐蔽:缺的是 **Bean**(容器里没有 `com.fasterxml` 的 `ObjectMapper`)而非类,故**编译通过、运行期启动失败**。网关是三工程里唯一的反应式应用,其他 servlet 微服务不受影响。
>
> 配套:`blade-gateway/pom.xml` 显式声明 `com.fasterxml.jackson.core:jackson-databind`(版本由 BOM 托管),使该 ObjectMapper 的 Jackson 2 依赖成为一等声明,不再仅由 `jjwt-jackson` 间接传递而随其依赖图漂移。

### 2.5 网关接口文档聚合(自研:springdoc + Nacos 自动发现)

§4.1 删掉 knife4j 网关聚合后,网关文档门户由自研的 springdoc 聚合替代:从 Nacos 拉取在册服务,动态生成 Swagger-UI 下拉聚合,无需手工维护 urls。

**① `blade-gateway/pom.xml`** 加反应式 UI(版本由 BOM 托管 3.0.3,**必须 webflux-ui、不能 webmvc-ui**):

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webflux-ui</artifactId>
</dependency>
```

**② 新增两个类**:
- `props/SwaggerDiscoveryProperties.java`(`@ConfigurationProperties("blade.swagger.discovery")`):`apiDocsPath` 默认 `/v3/api-docs`、`refreshInterval` 默认 30000、`excludedServices` 默认排除 `blade-gateway` / `blade-admin` / `blade-log`(共 **3** 项,勿漏 `blade-log`,否则其文档会默认出现在聚合下拉里)。
- `config/SwaggerAggregationConfiguration.java`:注入 `ReactiveDiscoveryClient`(Nacos 反应式,`getServices()` 已 `subscribeOn(boundedElastic)`,不阻塞网关事件循环)与单例 `SwaggerUiConfigProperties`;`ApplicationReadyEvent` 首刷 + `@Scheduled` 定时刷;每个服务拼 `new SwaggerUrl(id, "/" + id + "/v3/api-docs", id)`,**整表 `setUrls(新 Set)`** 原子替换(读侧每请求取快照,规避就地增删的并发修改)。

**③ 放行**(`provider/AuthProvider.java`):`DEFAULT_SKIP_URL` 新增 `/*/v3/api-docs/**`——各服务文档经网关同源路由 `/{serviceId}/v3/api-docs` 取回,需匿名可达。

**④ 配置**(`application.yml` / Nacos `blade.yaml`):
- `springdoc.api-docs.enabled: true`——**必须开启**,关闭会连锁禁用聚合所依赖的 `SwaggerUiConfigProperties`。
- `springdoc.swagger-ui.disable-swagger-default-url: true`——去掉默认 petstore 项。
- `blade.swagger.discovery.excluded-services`——排除网关自身、监控等无业务文档的服务。

**⑤ 前置**:网关须开启 `spring.cloud.gateway.server.webflux.discovery.locator.enabled: true`,由服务发现自动生成 `/{serviceId}/**` 路由,聚合 url `/{serviceId}/v3/api-docs` 才能转发到对应服务(此即已删 knife4j 聚合当年依赖的同一开关)。各微服务经 blade-tool 的 webmvc-ui 暴露 `/v3/api-docs`,`SecureRegistry` 默认已放行 `/v3/api-docs/**`。

> **机制**:springdoc 的 `/v3/api-docs/swagger-config` 端点每次请求都会重新拷贝单例 `SwaggerUiConfigProperties` 的 urls,故定时刷新时整表替换即在下一次拉取生效,无需重启。仅当控制器用带 description 的 `@Tag` 时其分组才进顶层 tags(与 blade-tool 指南 §9⑧ @ApiOrder 的分组排序相关)。

---

## 3. MyBatis-Plus `IService`/`ServiceImpl` 包迁移(业务代码)

MyBatis-Plus 3.5.17 把 `IService`/`ServiceImpl` 从 `com.baomidou.mybatisplus.extension.service.*` 迁到了 `com.baomidou.mybatisplus.spring.service.*`(该迁移为 3.5.17 的破坏性变更,3.5.16 仍在旧 `extension.service` 包)。Cloud 工程业务 Service 层大量继承它们,须全量改 import:

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

> `com.baomidou.mybatisplus.extension.plugins.*`(分页/租户/拦截器)**未迁包**,不要动。

---

## 4. Knife4j 彻底移除

Knife4j 无 Boot4 版本,依赖、配置、注解全部删除,改用 blade-tool 已内置的 springdoc 3.0.3。

### 4.1 网关聚合依赖与配置

- `blade-gateway/pom.xml`:删除 `com.github.xiaoymin:knife4j-gateway-spring-boot-starter` 依赖。
- 删除 `blade-gateway/.../config/ReactiveDiscoveryConfiguration.java`(该类只为 knife4j 文档聚合服务,依赖 `com.github.xiaoymin.knife4j.spring.gateway.*`,无 knife4j 即无意义)。
- `blade-gateway/src/main/resources/application.yml`:删除整段 `knife4j:` 网关聚合配置。
- `filter/GatewayFilter.java`:CORS `ALLOWED_HEADERS` 常量里去掉 `knfie4j-gateway-request`、`knife4j-gateway-code` 两个专供 knife4j 的请求头。

### 4.2 业务控制器注解

删除各控制器里的 `@ApiOperationSupport(order = N)` 注解及对应 import(Cloud 工程仅用到 `@ApiOperationSupport`,不涉及 `@ApiSort`):

```java
// 删除 import
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
// 删除方法上的注解行
@ApiOperationSupport(order = 1)
```

批量删除命令(macOS,注解均为单行):

```bash
find . -name '*.java' -not -path '*/target/*' -exec sed -i '' \
  -e '/^[[:space:]]*import com\.github\.xiaoymin\.knife4j\.annotations\./d' \
  -e '/^[[:space:]]*@ApiOperationSupport(/d' {} +
```

> **不要**引入 `knife4j-annotations` 兼容包保留注解——这些注解在 springdoc 原生 UI 下不生效,属无效元数据。接口与分组排序改用 BladeX 的 `@ApiOrder`(见 blade-tool 指南 §9⑧)。

> ⚠️ 上面的 `find … '*.java'` 覆盖不到**代码生成器模板**:`blade-ops/blade-develop/src/{main,test}/resources/templates/controller.java.vm` 里同样有 `@ApiOperationSupport` 的 import 与注解,须一并删除,否则该生成器产出的 Controller 引用已移除的 knife4j 包、编译不过。

> ✅ **删完 knife4j 排序注解后须补 `@ApiOrder`**:仅删不补会让接口在 springdoc 原生 UI 下按字母序、丢失原有顺序。本次统一在**每个带 `@Tag` 的控制器**(23 处,含 `OssEndpoint` 及 `blade-demo` 示例)`@Tag` 上方加一行**无参** `@ApiOrder`(import `org.springblade.core.swagger.annotation.ApiOrder`),分组内接口即回落到**源码声明顺序**;方法体保留 `@Operation` 即可,无需逐个标 `@ApiOrder(n)`。代码生成器模板 `controller.java.vm`(main/test 两份)同样在 `@Tag` 上方补 `@ApiOrder`。机制与「方法级精确覆盖」用法详见 blade-tool 指南 §9⑧。

### 4.3 Nacos 配置模板

`doc/nacos/blade.yaml` 里的 `knife4j:` 段一并删除(Nacos 上已下发的对应配置也需同步清理)。

### 4.4 springdoc 放行与接口文档入口

去掉 knife4j、改用 springdoc 原生 UI 后,需放行其静态资源与规格端点,并更新数据库里的接口文档菜单地址:

- **网关放行**(`blade-gateway/.../provider/AuthProvider.java`):`DEFAULT_SKIP_URL` 新增 `/swagger-ui/**` 与 `/swagger-ui.html`(springdoc UI 静态资源与其跳转入口);`/v3/api-docs/**` 原已在放行清单,仅调整了位置。
- **接口文档菜单**(`doc/sql/blade/blade.mysql.all.create.sql`):`blade_menu` 里「接口文档」菜单地址由 `http://localhost/doc.html` 改为 `http://localhost/swagger-ui.html`;已初始化的库执行升级脚本 `doc/sql/blade/blade.mysql.upgrade.4.10.0.to.5.0.0.sql`(内含该 `UPDATE`,以旧值为条件、可重复执行)。

---

## 5. 容器与 Feign 配置适配

### 5.1 Undertow → Tomcat(自身 yml)
Boot 4 移除了 Undertow(详见 blade-tool 指南 §6),本工程自己的 yml 里若有 `server.undertow.*`,须换成 `server.tomcat.*`,否则是被忽略的死配置、原线程调优静默失效。涉及 `blade-ops/blade-admin/…/bootstrap.yml` 与 `doc/nacos/blade.yaml`(Nacos 共享模板):

```yaml
# 改前
server:
  undertow:
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

### 5.2 OpenFeign 5.x 失效配置清理
Cloud 2025.1 的 OpenFeign 升到 5.x:`feign.*` 前缀早已迁到 `spring.cloud.openfeign.*`,且 **OkHttp 支持被整体移除**。`doc/nacos/blade.yaml` 里的 `feign.okhttp.enabled` / `feign.httpclient.enabled` 已是静默无效的死配置,删除即可(保留仍有效的 `feign.sentinel.enabled`);Nacos 服务端已下发的对应配置也需同步清理。

> `blade-core-cloud` 里的 `io.github.openfeign:feign-okhttp` 依赖在 OpenFeign 5.x 下已无自动装配、成为孤儿,可按需清理(非必须,不影响运行)。

### 5.3 Servlet 编码前缀迁移(排查项)
Boot 4.0 把 Servlet 编码属性从 `server.servlet.encoding.*` 迁到 `spring.servlet.encoding.*`(元数据 `level: error`,硬迁移,旧键失效;详见 blade-tool 指南 §6④)。**Cloud 各微服务及 Nacos 模板当前均未显式配置 Servlet 编码**,依赖 Boot 默认(UTF-8),故本次无实际改动;后续若在任何 `application.yml` / Nacos 配置里新增编码项,须直接用新前缀:

```yaml
spring:
  servlet:
    encoding:
      charset: UTF-8
      force: true
```

### 5.4 Zipkin/Sleuth 残留清理(blade-common)

Sleuth/Zipkin 链路模型自 Boot 3 起已被 Micrometer Tracing 取代,`spring.zipkin.*` 命名空间在 SB4 / Cloud 2025.1 下已不存在。`blade-common` 一并清掉残留:

- `LauncherConstant.java`:移除 `ZIPKIN_DEV_ADDR` / `ZIPKIN_PROD_ADDR` / `ZIPKIN_TEST_ADDR` 常量与 `zipkinAddr(profile)` 方法。
- `LauncherServiceImpl.java`:移除 `spring.zipkin.base-url` 属性注入(在新栈上为死配置)。

> 如需分布式链路追踪,改用 Micrometer Tracing(桥接 + OTLP/Zipkin 上报);此处为清理而非功能替换。

### 5.5 Nacos 3.2.2 镜像与配置适配(`script/docker/nacos/`)

nacos-client 由 blade-tool §11.1 **主动**锁定为 3.2.2(⚠️ 注:Spring Cloud Alibaba 2025.1.0.0 的 BOM 本身托管的是 nacos-client **3.1.1**,3.2.2 是通过 `alibaba.nacos.version` 显式覆盖的,并非 Cloud 2025.1 自带),服务端镜像同步 `v3.1.0 → v3.2.2`(§8 已列)。但**升级远不止改一个 tag**:Nacos 3.2.x 引入了 AI 控制面(MCP / Skill / AgentSpec),会显著改变开箱行为,需配套处理以下几处。

#### ① 镜像 tag(`script/docker/docker-compose.yml`)

```yaml
# 改前
image: nacos/nacos-server:v3.1.0
# 改后(与 nacos-client 3.2.2 对齐)
image: nacos/nacos-server:v3.2.2
```

#### ② application.properties 必须替换为 3.2.2 官方版(`script/docker/nacos/conf/application.properties`)

**背景**:该文件经 volume **整体替换**镜像内 `/home/nacos/conf/application.properties`。原挂载的是 **Nacos 2.x 风格**的精简配置(忽略路径还是 2.x 的 `/console-fe/`、缺 3.x 认证结构与 AI 开关),在 3.2.2 镜像上会丢失大量新默认值,并使 AI 模块按代码默认(开)运行。

**改法**:从 v3.2.2 镜像导出官方默认文件作为新模板,再追加 AI 关闭配置:

```bash
# 从运行中的 3.2.2 容器导出官方默认 application.properties(319 行)
docker exec <nacos容器> cat /home/nacos/conf/application.properties > application.properties
```

> 数据源与认证的环境变量名(`MYSQL_SERVICE_*` / `NACOS_AUTH_*`)在 3.2.2 官方默认文件里保持一致,compose 既有 env 无需改动。

#### ③ 关闭 AI 发布流水线——否则控制台自动出现数千条配置(核心坑)

**现象**:v3.2.2 镜像**自带** `skills-data.zip`(~7.6MB)+ `agentspec-data.zip`,由默认开启的 `nacos.plugin.ai-pipeline` / `skill-scanner` 在启动时解包灌入配置中心,控制台配置列表瞬间多出数千条。**这不是联网导入,而是镜像内置数据**;单机 Derby 下表现为「每次启动重新生成」,删容器重建也不消失。

**关键**:3.2.2 镜像启动参数为 `--spring.config.additional-location=file:/home/nacos/conf/` + `--spring.config.name=application`,**只加载 `/home/nacos/conf/application.properties`**;2.x 的 `/home/nacos/init.d/custom.properties` 覆盖机制在该镜像已失效,挂了也不读。因此关闭开关必须写进 `application.properties`:

```properties
# 关闭 AI 发布流水线:阻止解包加载内置 skills-data.zip / agentspec-data.zip
nacos.plugin.ai-pipeline.enabled=${NACOS_AI_PIPELINE_ENABLED:false}
nacos.plugin.ai-pipeline.skill-scanner.enabled=${NACOS_AI_PIPELINE_SKILL_SCANNER_ENABLED:false}
# 关闭 AI 模块总开关:隐藏控制台 AI 菜单及相关 API
nacos.extension.ai.enabled=${NACOS_EXTENSION_AI_ENABLED:false}
```

> `nacos.plugin.ai.importer.mcp.official.*` 等联网导入器属性在该镜像默认文件里**并不存在**,真正的开关是上面的 `ai-pipeline` / `skill-scanner`;`nacos.istio.mcp.server.enabled` / `nacos.k8s.sync.enabled` 官方默认已是 `false`,无需重复。

#### ④ 鉴权 token 必须填写(`NACOS_AUTH_TOKEN`)

3.x 开启鉴权后,`NACOS_AUTH_TOKEN` 必须是 **Base64 编码、≥32 字节**的密钥,**空值会导致启动失败**。compose 模板保留空占位(供部署方自填),但**部署前必须填写**:

```bash
openssl rand -base64 32   # 生成合规密钥
```

#### ⑤ 数据持久化(推荐)

单机 Derby 数据默认在容器内,重建即丢。compose 新增数据卷,`deploy.sh` 的 `mount()` 同步创建该目录:

```yaml
# docker-compose.yml nacos.volumes 新增
- /docker/nacos/data:/home/nacos/data
```

```bash
# deploy.sh mount() 新增
if test ! -d "/docker/nacos/data" ;then
    mkdir -p /docker/nacos/data
fi
```

> 空的 data 目录挂载还会**遮蔽镜像自带的 AI zip**,等于给「不加载数千条」上了第二道保险。首次启用持久化前须确保 data 目录为空,避免把旧脏数据带入。

#### ⚠️ 部署注意(老环境更新)

`deploy.sh mount()` 对宿主机文件是「不存在才拷贝」(`if test ! -f`)。**已部署过的机器上 `/docker/nacos/conf/application.properties` 已存在,`deploy.sh mount` 不会用新模板覆盖它**。更新 nacos 配置须手动覆盖后重建容器:

```bash
cp nacos/conf/application.properties /docker/nacos/conf/application.properties
docker-compose rm -sf nacos && docker-compose up -d nacos
```

### 5.6 Feign 接口统一 `/feign/client` 前缀 + 网关内部接口隔离

**背景**:Provider 以 `@RestController implements IXxxClient` 实现 Feign 接口后,这些方法即成为真实 HTTP 端点——持有合法令牌的外部请求可像调用普通 Controller 一样直接访问服务间接口。为把 Feign 接口收敛为「仅供服务间调用」,做两件事:① 所有 Feign 接口统一 `/feign/client/<模块>` 路径前缀;② 网关对外拦截 `feign` 保留段。二者配合即闭环:服务间调用经注册中心直连、不过网关,不受影响;外部流量只能经网关,命中保留段即拒。

#### ① Feign 前缀统一

改各 `IXxxClient` 的 `API_PREFIX` 常量即可——Provider 控制器均以该常量(或其派生常量)拼接映射,**改接口一处,Feign 客户端与服务端映射两端同步**:

| 接口 | 改前 | 改后 |
|------|------|------|
| `ISysClient` | `/sys` | `/feign/client/system` |
| `IUserClient` | `/user` | `/feign/client/user` |
| `IDictClient` | `/dict` | `/feign/client/dict` |
| `IDataScopeClient` | `/client/data-scope` | `/feign/client/data-scope` |
| `IApiScopeClient` | `/feign/client/api-scope` | 已合规,免改 |
| `INoticeClient`(desk) | `/dashboard` | `/feign/client/notice` |
| `INoticeClient`(demo) | `/client` | `/feign/client/notice` |
| `IStorageClient`(seata) | `/storage/deduct` | `/feign/client/storage/deduct` |

> `IStorageClient` 未用 `API_PREFIX` 常量、直接写在 `@GetMapping` 上,除接口外还需同步改 `blade-seata-storage` 的 `StorageController` 类级 `@RequestMapping("storage")` → `@RequestMapping("/feign/client/storage")`,保持两端一致。
>
> 同时删除网关 `AuthProvider.DEFAULT_SKIP_URL` 中失效的 `/storage/deduct/**` 放行项:该端点改名后原放行已匹配不到;新路径 `/feign/client/storage/deduct` 由 `InnerFilter` 对外拦截、内部走服务间直连,无需网关放行(`/order/create/**` 是订单服务对外入口、非 Feign 端点,保留)。
>
> `ILogClient` 的 `API_PREFIX` 定义在框架 `blade-tool` 内,本工程不可改;其日志上报端点前缀由框架侧统一收敛,不在本次范围。

#### ② 网关内部接口隔离(网关侧原生增强)

- **新增** `provider/RequestProvider.java`:`getOriginalRequestPath(exchange)` 取裁剪服务名前缀**之前**的原始 `rawPath`(避免依赖裁剪结果),不解码、不含查询串,原样交由判定方处理。
- **新增** `filter/InnerFilter.java`(`GlobalFilter`,order **-150**):原始路径中存在连续的 `feign`、`client` 路径段即返回 **403**,与是否持有合法令牌无关。order 晚于 `RequestFilter(-1000)`(以取到原始路径)、早于 `AuthFilter(-100)`(被拒路径不做无谓鉴权)。

判定的核心原则是**与下游容器映射前的路径归一化等价**:容器在接口映射前会对路径做归一化与一次百分号解码,判定若停留在原始串的子串匹配,与容器的实际映射依据就存在差异。

`/feign/client` 由连续两段构成,判定依赖段间的相邻关系,而相邻关系对归一化的**具体操作与执行顺序**都敏感——任一环节与容器不一致,都可能被路径变形把两段撑开而失配。故归一化严格按容器顺序实现:**切分 → 剥离矩阵参数 → 解码 → 再次切分 → 消解相对段**,随后在归一化后的段列表上做连续段比对;编码非法时无法推断容器解码结果,从严拒绝。

> 单段保留段(仅判 `feign`)不依赖相邻关系,对归一化顺序完全免疫、实现也更短,但会把 `/feign/**` 整体变为内部专用命名空间。本工程选择保留两段前缀语义,相应地必须完整实现上述归一化;**若后续调整该判定,须同步确认 `..` 消解与「剥离矩阵参数先于解码」两点不被简化掉**。

> **本方案以网关路径隔离为准**:全部 Feign 接口已统一落在 `feign` 保留段下,网关对外拦截即可挡住所有经网关的外部访问,无需在接口上额外加注解或做请求头处理。其防护边界在网关入口——绕过网关、直连微服务实例的调用不在拦截范围,故仍以「微服务只部署在内网、对外仅暴露网关」为前提。**因此凡是接受外部可控标识入参的 Feign 接口,服务侧仍必须自行做归属与租户校验,不可把网关拦截当作唯一防线**;若要覆盖「直连微服务」场景的纵深防御,可另行在服务侧增加内部标记校验。

---

## 6. 编译验证

> ⚠️ 升级后 `<java.version>` 为 21,**构建需 JDK 21**(JDK 17 会报 `invalid source release: 21`)。

```bash
# 先确保 blade-tool 5.0.0 已 install
cd blade-tool && mvn clean install -DskipTests

# 再构建 Cloud(跳过 docker 镜像构建)
cd SpringBlade && mvn clean install -DskipTests -Dmaven.test.skip=true -Ddocker.skip=true
```

通过标准:23 个模块全部 `SUCCESS`,可执行模块 `repackage` 成功。

---

## 7. 运行期验证要点(交由真实环境执行)

1. 网关:反应式路由、鉴权过滤、跨域、限流(Sentinel)是否正常;springdoc 网关聚合文档(§2.5):访问 `/swagger-ui.html`,服务下拉应随服务上下线自动增减。
2. 各微服务:MyBatis-Plus + dynamic-datasource 多数据源装配、分页/租户拦截器。
3. blade-admin 监控控制台(Spring Boot Admin 4.1.1 server)与各服务的 admin client 上报。
4. Nacos 配置拉取(bootstrap 模式)、服务注册发现。
5. Swagger-UI 入口从 `doc.html` 改为 `/swagger-ui.html`。

---

## 8. 受影响清单速览

| 类别 | 位置 | 动作 |
|------|------|------|
| 版本 | 根 pom `blade.tool.version` | 4.10.0 → 5.0.0 |
| 依赖 | 根 pom dynamic-datasource | boot3-starter:4.3.1 → boot4-starter:4.5.0 |
| 插件 | 根 pom spring-boot-maven-plugin | 3.2.10 → 4.1.0 |
| 容器 | blade-gateway/pom.xml 排除项 | starter-web/undertow → starter-webmvc |
| 源码 | blade-gateway ErrorExceptionHandler | web.reactive.error → webflux.error |
| 删除 | blade-gateway ReactiveDiscoveryConfiguration.java | 整文件删除(knife4j 聚合) |
| 源码 | blade-gateway GatewayFilter | 去 knife4j CORS 头 |
| 配置 | blade-gateway application.yml / Nacos blade.yaml | 删 knife4j 段 |
| 容器 | blade-admin bootstrap.yml / Nacos blade.yaml | server.undertow.* → server.tomcat.* |
| 配置 | Nacos blade.yaml | 删 OpenFeign 5.x 失效的 feign.okhttp/httpclient 键 |
| 源码 | blade-common LauncherConstant/LauncherServiceImpl | 移除 Zipkin 地址常量 / zipkinAddr() / spring.zipkin.base-url 注入 |
| 业务 | 全部 Service(约 30 处,15 对接口 + 实现) | IService/ServiceImpl 迁 spring.service |
| 业务 | 全部 Controller(17 处) | 删 @ApiOperationSupport + import |
| 业务 | 全部带 @Tag 的控制器(23 处,含 blade-demo)+ controller.java.vm 模板 | `@Tag` 上方新增无参 `@ApiOrder`(类级,接口按源码声明序)+ import org.springblade.core.swagger.annotation.ApiOrder(见 §4.2、blade-tool 指南 §9⑧) |
| 注释 | Controller / Service / Mapper / ServiceImpl 方法注释 | 分层收敛:Controller 精简为摘要行、Service/Mapper 补全 `@param`/`@return`、ServiceImpl private 方法补全(规范与改造记录见 blade-tool 指南 §17) |
| 依赖 | blade-gateway/pom.xml | 删 knife4j-gateway-spring-boot-starter |
| 新增 | blade-gateway 文档聚合(pom webflux-ui + SwaggerAggregationConfiguration + SwaggerDiscoveryProperties + AuthProvider 放行 `/*/v3/api-docs/**`) | Nacos 自动发现聚合 springdoc 文档(见 §2.5) |
| 空值注解 | blade-gateway GatewayFilter | org.springframework.lang → org.jspecify.annotations |
| 空值注解 | blade-gateway JwtCrypto | reactor.util.annotation → org.jspecify.annotations |
| 源码 | blade-gateway JacksonConfiguration | 新增 `com.fasterxml` ObjectMapper @Bean(见 §2.4) |
| 依赖 | blade-gateway/pom.xml | 显式声明 `com.fasterxml` jackson-databind(自建 ObjectMapper 所需) |
| 安全 | blade-gateway AuthProvider | 新增放行 /swagger-ui/**、/swagger-ui.html(/v3/api-docs/** 原已放行,仅移位) |
| 数据 | doc/sql blade_menu 接口文档菜单 | http://localhost/doc.html → http://localhost/swagger-ui.html |
| 环境 | 根 pom `java.version` + README | 17 → 21(**构建需 JDK 21**) |
| 部署 | 9 个 `Dockerfile` 基础镜像(blade-auth、blade-gateway、blade-ops×4、blade-service×3) | `bladex/alpine-java:openjdk17_cn_slim` → `openjdk21_cn_slim`(与 JDK 21 对齐;blade-auth 另含注释的阿里云备用镜像行 `openjdk17_cn_slim` 同步改 21) |
| 文档 | CLAUDE.md 开发规范 | 同步刷新:文档栈 Knife4j→springdoc、Controller 约定去 @ApiOperationSupport、去 OkHttp 提法、JDK/Java 17→21 |
| 插件 | 根 pom maven-compiler / flatten | 3.11.0→3.15.0 / 1.3.0→1.7.3 |
| 部署 | `script/docker/docker-compose.yml` nacos-server 镜像 | `v3.1.0` → `v3.2.2`(与 nacos-client 3.2.2 对齐,详见 §5.5) |
| 配置 | `script/docker/nacos/conf/application.properties` | 2.x 精简模板 → 3.2.2 官方默认 + AI 关闭配置(§5.5②③) |
| 配置 | 同上 AI 开关 | 新增 `nacos.plugin.ai-pipeline.enabled` / `skill-scanner.enabled` / `nacos.extension.ai.enabled` = false,消除数千条内置 AI 配置(§5.5③) |
| 部署 | `script/docker/docker-compose.yml` nacos.volumes | 新增 `/docker/nacos/data` 持久化 + `application.properties` 挂载改 `:ro`(§5.5⑤) |
| 部署 | `script/docker/deploy.sh` `mount()` | 新增创建 `/docker/nacos/data` 目录(§5.5⑤) |
| 业务 | 全部 Feign 接口 `IXxxClient`(7 个,`IApiScopeClient` 已合规免改)+ seata `StorageController` | `API_PREFIX` 统一为 `/feign/client/<模块>`,Provider 映射随常量同步(§5.6①) |
| 新增 | blade-gateway `provider/RequestProvider.java` + `filter/InnerFilter.java` | 网关对外拦截含 `feign` 保留段的请求、返回 403;判定只认首段 `feign`,采用「解码 + 逐段精确比对」对齐容器归一化,杜绝 `//`、`/./`、`;params`、`%编码` 变形绕过(§5.6②) |

---

## 9. Spring 7 空值注解迁移(JSpecify)

> 本次已完成迁移(含其他库空值注解归一)。官方依据(弃用 javadoc 直指 JSpecify、Spring 博客建议全生态统一)见同目录 `blade-tool-SpringBoot4-升级适配指南.md` §15。

Spring 7 把 `org.springframework.lang.@Nullable/@NonNull` 标记为 `@Deprecated(since=7.0)`(**未** forRemoval),官方转 **JSpecify**(`org.jspecify.annotations.*`,1.0.0 已随 spring-core 在 classpath,**无需新增依赖**);官方并建议 Reactor 等全生态统一到 JSpecify。

- **Spring 弃用注解**:`blade-gateway/.../filter/GatewayFilter.java`(`@NonNull` 于 `filter(...)` 入参)由 `org.springframework.lang.NonNull` → `org.jspecify.annotations.NonNull`。
- **其他库归一**:`blade-gateway/.../utils/JwtCrypto.java` 的 `reactor.util.annotation.Nullable`(3 处:`decryptToString` 返回值 / 入参)→ `org.jspecify.annotations.Nullable`,与全生态统一。均为标量、无数组,不涉及 TYPE_USE 位移。
- **验证**:Cloud 23 模块 `mvn clean install` `BUILD SUCCESS`;全工程空值注解仅存 `org.jspecify.annotations.*`,零 `org.springframework.lang.*` / `reactor.util.annotation` 残留。完整方式与 TYPE_USE 细节见 blade-tool 指南 §15。
