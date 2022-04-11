 <p align="center">
      <img src="https://img.shields.io/badge/Release-V3.3.1-green.svg" alt="Downloads">
      <img src="https://img.shields.io/badge/JDK-1.8+-green.svg" alt="Build Status">
  <img src="https://img.shields.io/badge/license-Apache%202-blue.svg" alt="Build Status">
   <img src="https://img.shields.io/badge/Spring%20Cloud-2021-blue.svg" alt="Coverage Status">
   <img src="https://img.shields.io/badge/Spring%20Boot-2.6.6-blue.svg" alt="Downloads">
   <a target="_blank" href="https://bladex.vip">
   <img src="https://img.shields.io/badge/Author-Small%20Chill-ff69b4.svg" alt="Downloads">
 </a>
 <a target="_blank" href="https://bladex.vip">
   <img src="https://img.shields.io/badge/Copyright%20-@BladeX-%23ff3f59.svg" alt="Downloads">
 </a>
 </p>  

## SpringBlade微服务开发平台
* 采用前后端分离的模式，前端开源两个框架：[Sword](https://gitee.com/smallc/Sword) (基于 React、Ant Design)、[Saber](https://gitee.com/smallc/Saber) (基于 Vue、Element-UI)
* 后端采用SpringCloud全家桶，并同时对其基础组件做了高度的封装，单独开源出一个框架：[BladeTool](https://gitee.com/smallc/blade-tool)
* [BladeTool](https://github.com/chillzhuang/blade-tool)已推送至Maven中央库，直接引入即可，减少了工程的臃肿，也可更注重于业务开发
* 集成Sentinel从流量控制、熔断降级、系统负载等多个维度保护服务的稳定性。
* 注册中心、配置中心选型Nacos，为工程瘦身的同时加强各模块之间的联动。
* 使用Traefik进行反向代理，监听后台变化自动化应用新的配置文件。
* 极简封装了多租户底层，用更少的代码换来拓展性更强的SaaS多租户系统。
* 借鉴OAuth2，实现了多终端认证系统，可控制子系统的token权限互相隔离。
* 借鉴Security，封装了Secure模块，采用JWT做Token认证，可拓展集成Redis等细颗粒度控制方案。
* 稳定生产了三年，经历了从 Camden -> Hoxton -> 2021 的技术架构，也经历了从fat jar -> docker -> k8s + jenkins的部署架构。
* 项目分包明确，规范微服务的开发模式，使包与包之间的分工清晰。

## 架构图
<img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-framework.png"/>

## 趋势图
<a href="https://whnb.wang/smallc/SpringBlade" rel="nofollow"><img src="https://whnb.wang/img/smallc/SpringBlade" alt="Stargazers over time"></a>

## 工程结构
``` 
SpringBlade
├── blade-auth -- 授权服务提供
├── blade-common -- 常用工具封装包
├── blade-gateway -- Spring Cloud 网关
├── blade-ops -- 运维中心
├    ├── blade-admin -- spring-cloud后台管理
├    ├── blade-develop -- 代码生成
├    ├── blade-resource -- 资源管理
├    ├── blade-seata-order -- seata分布式事务demo
├    ├── blade-seata-storage -- seata分布式事务demo
├── blade-service -- 业务模块
├    ├── blade-desk -- 工作台模块 
├    ├── blade-log -- 日志模块 
├    ├── blade-system -- 系统模块 
├    └── blade-user -- 用户模块 
├── blade-service-api -- 业务模块api封装
├    ├── blade-desk-api -- 工作台api 
├    ├── blade-dict-api -- 字典api 
├    ├── blade-system-api -- 系统api 
└──  └── blade-user-api -- 用户api 
```

## 官网
* 官网地址：[https://bladex.vip](https://bladex.vip)
* 问答社区：[https://sns.bladex.vip](https://sns.bladex.vip)
* 会员计划：[SpringBlade会员计划](https://gitee.com/smallc/SpringBlade/wikis/SpringBlade会员计划)
* 交流一群：`477853168`(满)
* 交流二群：`751253339`(满)
* 交流三群：`784729540`(满)
* 交流四群：`1034621754`(满)
* 交流五群：`946350912`(满)
* 交流六群：`511624269`

## 在线演示
* Saber-基于Vue：[https://saber.bladex.vip](https://saber.bladex.vip)
* Sword-基于React：[https://sword.bladex.vip](https://sword.bladex.vip)
* Archer-全能代码生成系统：[https://archer.bladex.vip](https://archer.bladex.vip)
* Caster-数据大屏展示系统：[https://data.avuejs.com](https://data.avuejs.com)

## 技术文档
* [SpringBlade常见问题集锦](https://sns.bladex.vip/article-14966.html)
* [SpringBlade开发手册一览](https://gitee.com/smallc/SpringBlade/wikis/SpringBlade开发手册)
* [SpringBlade基于Kuboard部署K8S](https://kuboard.cn/learning/k8s-practice/spring-blade/)

## 项目地址
* 后端Gitee地址：[https://gitee.com/smallc/SpringBlade](https://gitee.com/smallc/SpringBlade)
* 后端Github地址：[https://github.com/chillzhuang/SpringBlade](https://github.com/chillzhuang/SpringBlade)
* 后端SpringBoot版：[https://gitee.com/smallc/SpringBlade/tree/boot/](https://gitee.com/smallc/SpringBlade/tree/boot/)
* 前端框架Sword(基于React)：[https://gitee.com/smallc/Sword](https://gitee.com/smallc/Sword)
* 前端框架Saber(基于Vue)：[https://gitee.com/smallc/Saber](https://gitee.com/smallc/Saber)
* 核心框架项目地址：[https://gitee.com/smallc/blade-tool](https://gitee.com/smallc/blade-tool)

# 开源协议
Apache Licence 2.0 （[英文原文](http://www.apache.org/licenses/LICENSE-2.0.html)）
Apache Licence是著名的非盈利开源组织Apache采用的协议。该协议和BSD类似，同样鼓励代码共享和尊重原作者的著作权，同样允许代码修改，再发布（作为开源或商业软件）。
需要满足的条件如下：
* 需要给代码的用户一份Apache Licence
* 如果你修改了代码，需要在被修改的文件中说明。
* 在延伸的代码中（修改和有源代码衍生的代码中）需要带有原来代码中的协议，商标，专利声明和其他原来作者规定需要包含的说明。
* 如果再发布的产品中包含一个Notice文件，则在Notice文件中需要带有Apache Licence。你可以在Notice中增加自己的许可，但不可以表现为对Apache Licence构成更改。
Apache Licence也是对商业应用友好的许可。使用者也可以在需要的时候修改代码来满足需要并作为开源或商业产品发布/销售。

## 用户权益
* 允许免费用于学习、毕设、公司项目、私活等。
* 对未经过授权和不遵循 Apache 2.0 协议二次开源或者商业化我们将追究到底。
* 参考请注明：参考自 SpringBlade：https://gitee.com/smallc/SpringBlade 。另请遵循 Apache 2.0 协议。
* `注意`：若禁止条款被发现有权追讨 **19999** 的授权费。

# 界面

## [BladeX](https://bladex.vip/#/vip) 工作流一览
<table>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/bladex-flow1.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/bladex-flow2.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/bladex-flow3.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/bladex-flow4.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/bladex-flow5.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/bladex-flow6.png"/></td>
    </tr>
</table>

## [Sword](https://gitee.com/smallc/Sword) 界面一览
<table>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/sword-main.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/sword-menu.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/sword-menu-edit.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/sword-menu-icon.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/sword-role.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/sword-user.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/sword-dict.png "/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/sword-log.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/sword-locale-cn.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/sword-locale-us.png"/></td>
    </tr>
</table>

## [Saber](https://gitee.com/smallc/Saber) 界面一览
<table>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/saber-user.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/saber-role.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/saber-dict.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/saber-dict-select.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/saber-log.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/saber-code.png"/></td>
    </tr>
</table>

## 监控界面一览
<table>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-k8s1.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-k8s2.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-grafana.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-harbor.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-traefik.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-traefik-health.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-nacos.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-sentinel.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-admin1.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-admin2.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-swagger1.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-swagger2.png"/></td>
    </tr>
</table>
