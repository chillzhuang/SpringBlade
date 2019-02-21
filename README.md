## 简介
* SpringBlade 2.0 是如梦技术团队作品，是一个基于 Spring Boot 2 & Spring Cloud Finchley & Mybatis 等核心技术，用于快速构建中大型系统的基础框架。
* SpringBlade 致力于创造新颖的开发模式，将开发中遇到的痛点、生产中所踩的坑整理归纳，并将解决方案都融合到框架中。

## 官网
* 官网地址：[https://bladex.vip](https://bladex.vip)

## 在线演示
* Sword演示地址：[https://sword.bladex.vip](https://sword.bladex.vip)
* Saber演示地址：[https://saber.avue.top](https://saber.avue.top)

## 后端项目地址
* [Gitee地址](https://gitee.com/smallc/SpringBlade)
* [Github地址](https://github.com/chillzhuang/SpringBlade)

## 前端项目地址
* [Sword--基于React](https://gitee.com/smallc/Sword)
* [Saber--基于Vue](https://gitee.com/smallc/Saber)

## 主要特性&&变化
* 采用前后端分离的模式，前端开源出一个基于React的框架：[Sword](https://gitee.com/smallc/Sword)，主要选型技术为React、Ant Design、Umi、Dva
* 采用前后端分离的模式，前端开源出一个基于Vue的框架：[Saber](https://gitee.com/smallc/Saber)，主要选型技术为Vue、VueX、Avue、Element-UI
* 后端采用SpringCloud全家桶，并同时对其基础组件做了高度的封装，单独开源出一个框架：[BladeTool](https://github.com/chillzhuang/blade-tool)
* [BladeTool](https://github.com/chillzhuang/blade-tool)已推送至Maven中央库，直接引入即可，减少了工程的臃肿，也可更注重于业务开发
* 集成Sentinel从流量控制、熔断降级、系统负载等多个维度保护服务的稳定性。
* 注册中心、配置中心选型Nacos，为工程瘦身的同时加强各模块之间的联动。
* 使用Traefik进行反向代理，监听后台变化自动化应用新的配置文件。
* 部署使用Docker或K8s + Jenkins
* 踩了踩Kong的坑，有个基本的使用方案，但不深入，因为涉及到OpenResty。
* 封装了简单的Secure模块，采用JWT做Token认证，可拓展集成Redis等细颗粒度控制方案
* 在2.0诞生之前，已经稳定生产了近一年，经历了从Camden -> Finchley的技术架构，也经历了从fat jar -> docker -> k8s + jenkins的部署架构
* 项目分包明确，规范微服务的开发模式，使包与包之间的分工清晰。

## 会员计划及交流群
* [会员计划及交流群](https://gitee.com/smallc/SpringBlade/wikis/SpringBlade会员计划)

## 技术文档
* [SpringBlade开发手册](https://gitee.com/smallc/SpringBlade/wikis/SpringBlade开发手册)

## 单工程SpringBoot版
* [SpringBoot版](https://gitee.com/smallc/SpringBlade/tree/2.0-boot/)

## 工程结构
``` 
SpringBlade
├── blade-auth -- 授权服务提供
├── blade-common -- 常用工具封装包
├── blade-gateway -- Spring Cloud 网关
├── blade-ops -- 运维中心
├    ├── blade-admin -- spring-cloud后台管理
├    ├── blade-develop -- 代码生成
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
* 代码文件需保留相关license信息。

## 禁止事项
* 直接将本项目挂淘宝等商业平台出售。
* 非界面代码50%以上相似度的二次开源，二次开源需先联系作者。

注意：若禁止条款被发现有权追讨19999的授权费。

## 注
* 前端UI项目地址(基于React)：[Sword](https://gitee.com/smallc/Sword)
* 前端UI项目地址(基于Vue)：[Saber](https://gitee.com/smallc/Saber)
* 核心框架项目地址：[BladeTool](https://github.com/chillzhuang/blade-tool.git)
* 交流群：`477853168`


# 界面
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

## Sword界面一览
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

## Saber界面一览
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