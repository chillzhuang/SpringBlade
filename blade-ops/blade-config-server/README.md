# bladex-config

##bladex配置文件仓库部署步骤

1.建好git仓库，并建好文件夹，如：respo

2.将/config下的文件放到respo文件夹下，并push到git

3.到/resources/bootstrap.yml文件，修改spring.cloud.config.git节点下对应的配置

4.启动工程,开启config-server服务

##访问请求

在浏览器里输入:http://localhost:7005/application/dev/master
           或 http://localhost:7005/application-dev.yml
           或 http://localhost:7005/application-dev.properties

证明配置服务中心可以从远程程序获取配置信息，http请求地址和资源文件映射如下:
·        /{application}/{profile}/{label}
·        /{application}-{profile}.yml
·        /{label}/{application}-{profile}.yml
·        /{application}-{profile}.properties
·        /{label}/{application}-{profile}.properties
