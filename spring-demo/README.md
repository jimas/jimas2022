### 前提（docker docker-compose 已经安装好）

### 打包
```shell script
mvn clean install -Ptest
```

### 部署
- 将 target目录下的spring-demo.jar 与 Dockerfile  docker-compose.yml 文件上传到机器同一个目录下
- 执行构建&后台启动命令，首次可移除-d 观察日志
    ```shell script
    docker-compose up --build -d
    ```
- 暂停服务
    - Stop and remove containers, networks and volumes
    ```shell script
    docker-compose down
    ```