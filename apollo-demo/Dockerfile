FROM xiehaijun/jdk11

ADD apollo-demo.jar /opt/client.jar

EXPOSE 8080

WORKDIR /opt
## 没有idc 参数 就默认 default 集群
ENTRYPOINT ["java","-jar","-Denv=DEV","/opt/client.jar"]
#同一个环境 可以分多个集群
# ENTRYPOINT ["java","-jar","-Denv=DEV","-Didc=dev-01","/opt/client.jar"]