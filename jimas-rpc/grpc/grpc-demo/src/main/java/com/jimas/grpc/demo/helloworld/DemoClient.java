package com.jimas.grpc.demo.helloworld;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author liuqj
 */
public class DemoClient {

    public static void main(String[] args) {
        DemoClient demoClient = new DemoClient();
        String name = "飒飒";
        demoClient.execute(name);
    }

    private void execute(String name) {
        DemoProto.DemoInput request = DemoProto.DemoInput.newBuilder().setKey(name).build();
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", DemoServer.PORT)
                .usePlaintext().build();
        DemoProto.DemoOutput response = DemoServiceGrpc.newBlockingStub(channel).demoFunction(request);
        System.out.println("Greeting:" + response.getKey());


    }
}
