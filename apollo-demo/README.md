##[官方首页](https://www.apolloconfig.com/#/zh/README)

### 部署
> 单点部署 参考quick-start-docker
> 分布式部署（多个环境 admin config 部署多套，portal一套即可）
#### docker-compose up
> https://www.apolloconfig.com/#/zh/deployment/quick-start-docker
> client 要连接测试 必须要在同一个网络下  --net=apollo-quick-start_default

### 核心模块

#### admin
> 后端针对数据库的CRUD
#### config
> 集成了Eureka,用于通知 client 配置变化
#### portal
> UI 界面

#### client
> 核心参数app.id、evn、idc等
```yml
apollo:
  meta: http://192.168.64.133:8080
  bootstrap:
    enabled: true
    namespaces: jimas.redis,application
app:
  id: spring-demo
```
- env,指定环境
> -Denv=DEV
- idc，区分集群
> -Didc=dev-01
- app.id
> 标识项目
- apollo.meta 
> configService 地址
- apollo.bootstrap.namespaces
> 命名空间，默认存在application,也可以自定义。