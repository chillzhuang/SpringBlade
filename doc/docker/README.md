#使用docker构建工程步骤
###1. 使用docker-harbor作为私有库,需要配置maven,找到setting.xml( `linux可以使用find / -name settings.xml`)加入以下配置

```
<servers>
  <server>
    <id>10.211.55.5</id>
    <username>admin</username>
    <password>Harbor12345</password>
    <configuration>
      <email>smallchill@163.com</email>
    </configuration>
  </server>
</servers>

<pluginGroups>
  <pluginGroup>com.spotify</pluginGroup>  
</pluginGroups>
```

###2. docker开启远程访问

如果没有远程访问,会报 `Connect to 10.211.55.5:2375 [/10.211.55.5] failed: Connection refused: connect`

在`/usr/lib/systemd/system/docker.service`,配置远程访问。主要是在[Service]这个部分，加上下面两个参数：

```
cd /usr/lib/systemd/system

vi docker.service

ExecStart=
ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2375 -H unix://var/run/docker.sock
```



###3. 在每个需要构建子项目的pom.xml下加入配置,内容可参考如下

```
<build>
  <plugins>
    <plugin>
      <groupId>com.spotify</groupId>
      <artifactId>docker-maven-plugin</artifactId>
      <version>${docker.plugin.version}</version>
      <configuration>
        <imageName>${docker.registry.url}/blade/${project.artifactId}:${project.version}</imageName>
        <dockerDirectory>${project.basedir}</dockerDirectory>
        <dockerHost>${docker.registry.host}</dockerHost>
        <resources>
          <resource>
            <targetPath>/</targetPath>
            <directory>${project.build.directory}</directory>
            <include>${project.build.finalName}.jar</include>
          </resource>
        </resources>
        <registryUrl>${docker.registry.url}</registryUrl>
        <serverId>${docker.registry.url}</serverId>
        <pushImage>true</pushImage>
      </configuration>
    </plugin>
  </plugins>
</build>
```

###4. 在每个需要构建子项目的根目录下加入Dockerfile,内容可参考如下

```
FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER smallchill@163.com

RUN mkdir -p /blade/gateway

WORKDIR /blade/gateway

EXPOSE 80

ADD ./target/blade-gateway.jar ./app.jar

CMD java -Djava.security.egd=file:/dev/./urandom -jar app.jar --spring.profiles.active=test

```

###5. 在工程根目录的docker-compose.yml下加入配置，内容可参考如下
```
blade-gateway:
  image: "${REGISTER}/blade/blade-gateway:${TAG}"
  ports:
  - 80:80
  networks:
    blade_net:
      ipv4_address: 192.168.2.1
```
