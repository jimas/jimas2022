## grpc
> 采用http/2.0 协议
> 序列化&反序列化 采用 protobuf 二进制协议
> 如果需要搞分布式 需要单独集成注册中心 比如 consul、zookeeper等
> https://blog.csdn.net/Dkisser/article/details/125466926



- springboot 集成 方式一
```xml
<dependency>
    <groupId>io.github.lognet</groupId>
    <artifactId>grpc-spring-boot-starter</artifactId>
    <version>4.9.1</version>
</dependency>
```

- springboot 集成 方式二
```xml
<dependency>
    <groupId>net.devh</groupId>
    <artifactId>grpc-spring-boot-starter</artifactId>
    <version>2.14.0.RELEASE</version>
</dependency>
```