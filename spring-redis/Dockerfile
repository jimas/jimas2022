FROM xiehaijun/jdk11

ADD spring-redis.jar /opt/app.jar

EXPOSE 8080

WORKDIR /opt

ENTRYPOINT ["java","-jar","/opt/app.jar"]