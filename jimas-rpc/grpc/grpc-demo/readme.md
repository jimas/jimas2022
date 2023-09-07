## grpc

> 根据 protoc --java_out . .\demo.proto

1. pom文件
```xml
<properties>
    <grpc.version>1.57.2</grpc.version>
</properties>
<dependencies>
    <dependency>
        <groupId>io.grpc</groupId>
        <artifactId>grpc-netty</artifactId>
        <version>${grpc.version}</version>
    </dependency>
    <dependency>
        <groupId>io.grpc</groupId>
        <artifactId>grpc-netty-shaded</artifactId>
        <version>${grpc.version}</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.grpc</groupId>
        <artifactId>grpc-protobuf</artifactId>
        <version>${grpc.version}</version>
    </dependency>
    <dependency>
        <groupId>io.grpc</groupId>
        <artifactId>grpc-stub</artifactId>
        <version>${grpc.version}</version>
    </dependency>
    <dependency>
        <groupId>io.grpc</groupId>
        <artifactId>grpc-testing</artifactId>
        <version>${grpc.version}</version>
    </dependency>
    <dependency> <!-- necessary for Java 9+ -->
        <groupId>org.apache.tomcat</groupId>
        <artifactId>annotations-api</artifactId>
        <version>6.0.53</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
<build>
    <extensions>
        <extension>
            <groupId>kr.motd.maven</groupId>
            <artifactId>os-maven-plugin</artifactId>
            <version>1.7.1</version>
        </extension>
    </extensions>
    <plugins>
        <plugin>
            <groupId>org.xolstice.maven.plugins</groupId>
            <artifactId>protobuf-maven-plugin</artifactId>
            <version>0.6.1</version>
            <configuration>
                <protocArtifact>com.google.protobuf:protoc:3.22.3:exe:${os.detected.classifier}</protocArtifact>
                <pluginId>grpc-java</pluginId>
                <pluginArtifact>io.grpc:protoc-gen-grpc-java:1.57.2:exe:${os.detected.classifier}</pluginArtifact>
            </configuration>
            <executions>
                <execution>
                    <goals>
                        <goal>compile</goal>
                        <goal>compile-custom</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```
2. mvn clean install
3. 在srs/main/proto 下 编写.proto文件
4. mvn clean install
5. copy target/generated-sources/protobuf/java 下的bean对象
6. copy target/generated-sources/protobuf/grpc-java 下的接口对象
7. 编写服务端代码
```java
package com.jimas.grpc.server.helloworld;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
public class TestServer {
    private static Server server = null;
    public static void main(String[] args) throws Exception {
        TestServer worldServer = new TestServer();
        worldServer.start();
        worldServer.blockUntilShutdown();
    }
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
    private void start() throws IOException {
        int port = 30051;
        server = ServerBuilder.forPort(port)
                .addService(new TestImpl())
                .build().start();
    }
}
```
8. 编写客户端代码
```java
package com.jimas.grpc.server.helloworld;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
public class TestClient {
    public static void main(String[] args) {
        TestClient testClient = new TestClient();
        testClient.test("飒飒");
    }
    private void test(String name) {
        TestProto.TestInput request = TestProto.TestInput.newBuilder().setKey(name).build();
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 30051).usePlaintext().build();
        TestServiceGrpc.TestServiceBlockingStub blockingStub = TestServiceGrpc.newBlockingStub(channel);

        TestProto.TestOutput response = blockingStub.testFunction(request);

        System.out.println("Greeting:" + response.getKey());
    }
}
```