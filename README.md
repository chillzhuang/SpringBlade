<p align="center">
  <img src="https://img.shields.io/badge/Release-V4.8.0-blue.svg" alt="Downloads">
  <img src="https://img.shields.io/badge/JDK-17+-green.svg" alt="Build Status">
  <img src="https://img.shields.io/badge/license-Apache%202-blue.svg" alt="Build Status">
  <img src="https://img.shields.io/badge/Spring%20Cloud-2025-blue.svg" alt="Coverage Status">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.5-blue.svg" alt="Downloads">
  <a href="https://central.sonatype.com/artifact/org.springblade/blade-core-bom/versions">
    <img src="https://img.shields.io/nexus/r/https/oss.sonatype.org/org.springblade/blade-core-bom.svg?style=flat-square" alt="Downloads">
  </a>
  <br/>
  <a target="_blank" href="https://bladex.cn">
    <img src="https://img.shields.io/badge/Author-Small%20Chill-ff69b4.svg" alt="Downloads">
  </a>
  <a target="_blank" href="https://bladex.cn">
    <img src="https://img.shields.io/badge/Copyright%20-@BladeX-%23ff3f59.svg" alt="Downloads">
  </a>
</p>  

## SpringBlade微服务开发平台
* 采用前后端分离的模式，前端开源两个框架：[Sword](https://gitee.com/smallc/Sword) (基于 React、Ant Design)、[Saber](https://gitee.com/smallc/Saber) (基于 Vue、Element-UI)
* 后端采用SpringCloud全家桶，并同时对其基础组件做了高度的封装，单独开源出一个框架：[BladeTool](https://gitee.com/smallc/blade-tool)
* [BladeTool](https://github.com/chillzhuang/blade-tool)已推送至Maven中央库，直接引入即可，减少了工程的臃肿，也可更注重于业务开发
* 集成Sentinel从流量控制、熔断降级、系统负载等多个维度保护服务的稳定性。
* 注册中心、配置中心选型Nacos，为工程瘦身的同时加强各模块之间的联动。
* 极简封装了多租户底层，用更少的代码换来拓展性更强的SaaS多租户系统。
* 借鉴OAuth2，自研多终端认证系统，可控制子系统的token权限互相隔离。
* 借鉴Security，自研Secure模块，采用JWT做Token认证，可拓展集成Redis等细颗粒度控制方案。
* 稳定生产了六年，经历了从 Camden -> Hoxton -> 2025 的技术架构，也经历了从fat jar -> docker -> k8s + jenkins的部署架构。
* 项目分包明确，规范微服务的开发模式，使包与包之间的分工清晰。

## 架构图
<img src="https://xbladex.oss-cn-hangzhou.aliyuncs.com/upload/springblade-framework.png"/>

## 核心技术栈

| 技术栈                  | 版本         |
|----------------------|------------|
| Java                 | 17+        |
| NodeJS               | 18+        |
| Spring               | 6.2.15     |
| Spring Boot          | 3.5.9      |
| Spring Cloud         | 2025.0.1   |
| Spring Cloud Alibaba | 2025.0.0.0 |
| Nacos Alibaba        | 3.1.1      |
| Mybatis Plus         | 3.5.19     |


## 工程结构
``` 
SpringBlade
├── src/main/java/org/springblade
│   ├── Application.java -- 启动类
│   ├── common -- 公共模块
│   │   ├── cache -- 缓存配置
│   │   ├── config -- 系统配置
│   │   ├── constant -- 常量定义
│   │   ├── launch -- 启动配置
│   │   └── tool -- 工具类
│   ├── core -- 核心模块
│   │   ├── log -- 日志拓展
│   │   └── secure -- 安全拓展
│   └── modules -- 业务模块
│       ├── auth -- 认证授权模块
│       ├── desk -- 工作台模块
│       ├── develop -- 代码生成模块
│       ├── resource -- 资源管理模块
│       └── system -- 系统管理模块
├── src/main/resources
│   ├── application.yml -- 主配置文件
│   ├── application-dev.yml -- 开发环境配置
│   ├── application-test.yml -- 测试环境配置
│   ├── application-prod.yml -- 生产环境配置
│   └── config -- 其他配置文件
├── doc -- 文档目录
│   ├── script -- 启动脚本
│   └── sql -- 数据库脚本
└── pom.xml -- Maven配置文件
```

## 官方信息

| 简介   | 内容                                                                            |
|------|-------------------------------------------------------------------------------|
| 官网地址 | [https://bladex.cn](https://bladex.cn)                                        |
| 问答社区 | [https://sns.bladex.cn](https://sns.bladex.cn)                                |
| 会员计划 | [SpringBlade会员计划](https://gitee.com/smallc/SpringBlade/wikis/SpringBlade会员计划) |
| 交流一群 | `477853168` (满)                                                               |
| 交流二群 | `751253339` (满)                                                               |
| 交流三群 | `784729540` (满)                                                               |
| 交流四群 | `1034621754` (满)                                                              |
| 交流五群 | `946350912` (满)                                                               |
| 交流六群 | `511624269` (满)                                                               |
| 交流七群 | `298061704`                                                                   |

## 官方产品

| 简介              | 演示地址                                                 |
|-----------------|------------------------------------------------------|
| BladeX企业级开发平台   | [https://saber3.bladex.cn](https://saber3.bladex.cn) |
| BladeX可视化数据大屏   | [https://data.bladex.cn](https://data.bladex.cn)     |
| BladeX物联网开发平台   | [https://iot.bladex.cn](https://iot.bladex.cn)       |
| BladeXAI大模型平台 | [https://ai.bladex.cn/](https://ai.bladex.cn/)      |

## 前端项目

| 简介                 | 地址                                                                           |
|--------------------|------------------------------------------------------------------------------|
| 前端框架Saber3(基于Vue3) | [https://gitee.com/smallc/Saber3](https://gitee.com/smallc/Saber)            |
| 前端框架Saber(基于Vue2)  | [https://gitee.com/smallc/Saber2](https://gitee.com/smallc/Saber/tree/vue2/) |
| 前端框架Sword(基于React) | [https://gitee.com/smallc/Sword](https://gitee.com/smallc/Sword)             |

## 后端项目
| 简介            | 地址                                                                                                 |
|---------------|----------------------------------------------------------------------------------------------------|
| 核心框架项目地址      | [https://gitee.com/smallc/blade-tool](https://gitee.com/smallc/blade-tool)                         |
| 后端Gitee地址     | [https://gitee.com/smallc/SpringBlade](https://gitee.com/smallc/SpringBlade)                       |
| 后端Github地址    | [https://github.com/chillzhuang/SpringBlade](https://github.com/chillzhuang/SpringBlade)           |
| 后端SpringBoot版 | [https://gitee.com/smallc/SpringBlade/tree/boot/](https://gitee.com/smallc/SpringBlade/tree/boot/) |

## 安全手册
| 简介        | 地址                                                                                                 |
|-----------|----------------------------------------------------------------------------------------------------|
| Blade安全手册 | [https://www.kancloud.cn/smallchill/blade-safety](https://www.kancloud.cn/smallchill/blade-safety) |

## 技术文档

| 简介                        | 地址                                                                                                                       |
|---------------------------|--------------------------------------------------------------------------------------------------------------------------|
| SpringBlade开发手册一览         | [https://gitee.com/smallc/SpringBlade/wikis/SpringBlade开发手册](https://gitee.com/smallc/SpringBlade/wikis/SpringBlade开发手册) |
| SpringBlade常见问题集锦         | [https://sns.bladex.cn/article-14966.html](https://sns.bladex.cn/article-14966.html)                                     |
| SpringBlade基于Kuboard部署K8S | [https://kuboard.cn/learning/k8s-practice/spring-blade/](https://kuboard.cn/learning/k8s-practice/spring-blade/)         |
| SpringBlade基于Rainbond部署   | [https://www.rainbond.com/docs/micro-service/example/blade](https://www.rainbond.com/docs/micro-service/example/blade)   |


## 免费公开课
<table>
    <tr>
        <td><a href="https://space.bilibili.com/525525/channel/seriesdetail?sid=2740449" target="_blank"><img style="width: 300px; height: 170px" src="https://xbladex.oss-cn-hangzhou.aliyuncs.com/upload/springblade-course.jpg"/></a></td>
        <td><img style="width: 300px; height: 170px" src="https://xbladex.oss-cn-hangzhou.aliyuncs.com/upload/springblade-bilibili.jpg"/></td>
    </tr>
</table>

## 开源协议
Apache Licence 2.0 （[英文原文](http://www.apache.org/licenses/LICENSE-2.0.html)）
Apache Licence是著名的非盈利开源组织Apache采用的协议。该协议和BSD类似，同样鼓励代码共享和尊重原作者的著作权，同样允许代码修改，再发布（作为开源或商业软件）。
需要满足的条件如下：
* 需要给代码的用户一份Apache Licence
* 如果你修改了代码，需要在被修改的文件中说明。
* 在延伸的代码中（修改和有源代码衍生的代码中）需要带有原来代码中的协议，商标，专利声明和其他原来作者规定需要包含的说明。
* 如果再发布的产品中包含一个Notice文件，则在Notice文件中需要带有Apache Licence。你可以在Notice中增加自己的许可，但不可以表现为对Apache Licence构成更改。
  Apache Licence也是对商业应用友好的许可。使用者也可以在需要的时候修改代码来满足需要并作为开源或商业产品发布/销售。

## 用户权益
* 允许免费用于学习、毕设、公司项目、私活等，但请保留源码作者信息。
* 对未经过授权和不遵循 Apache 2.0 协议二次开源或者商业化我们将追究到底。
* 参考请注明：参考自 SpringBlade：https://gitee.com/smallc/SpringBlade。