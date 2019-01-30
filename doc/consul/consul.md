# 启动步骤
## 安装consul
下载地址：https://www.consul.io/downloads.html

安装启动然后访问consul web端：http://localhost:8500
## 安装（mac）
```bash
brew install consul
```

## 启动
### 命令方式：

```bash
consul agent -server -bootstrap -bind 127.0.0.1 -client 0.0.0.0 -data-dir ./data -ui -datacenter=blade
```

### brew服务
> 修改consul启动参数：
```bash
vim /usr/local/opt/consul/homebrew.mxcl.consul.plist
```

> 修改ProgramArguments部分：
```html
<key>ProgramArguments</key>
<array>
  <string>/usr/local/opt/consul/bin/consul</string>
  <string>agent</string>
  <string>-server</string>
  <string>-bootstrap</string>
  <string>-advertise</string>
  <string>127.0.0.1</string>
  <string>-data-dir</string>
  <string>./data</string>
  <string>-ui</string>
</array>
```

> 启动：
```bash
brew services start consul
```


