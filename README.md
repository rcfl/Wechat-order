##### springboot: 2.0.6
##### mysql: 5.7
##### jdk: 1.8


#### 运行之前请安装lombok 插件，启动redis

#### 记得填写自己的数据库密码

yml文件微信的 信息不能使用

#### 修改 application.yml 中 project-url 中的 sell: http://sellayucn.free.idcfengye.com 为 sell: 127.0.0.1
#### 并修改 /templates/order/list.ftl 中的 websocket = new WebSocket('ws://127.0.0.1:8080/sell/webSocket');(134行)
 