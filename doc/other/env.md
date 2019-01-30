## 环境变量
#### 环境划分

> dev（开发）、test（测试）、prod（正式），默认dev
#### 添加环境变量

##### java命令行：

```
java -jar gateWay.jar --spring.profiles.active=dev
```

##### JAVA_OPS

```
set JAVA_OPTS="-Dspring.profiles.active=test"
```

##### 标注方式（代码层面，junit单元测试非常实用）

```
@ActiveProfiles({"junittest","productprofile"})
```

##### ENV方式

```
系统环境变量SPRING_PROFILES_ACTIVE（注意：是大写）
```
