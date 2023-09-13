package com.jimas.grpc.demo.helloworld;

import io.grpc.ManagedChannel;

/**
 * @author liuqj
 */
public class DemoClient {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                DemoClient demoClient = new DemoClient();
                String name = "飒飒";
                demoClient.execute(name);
            }).start();
        }
        Thread.sleep(1000000);
    }

    private void execute(String name) {
        ManagedChannel channel = null;
        DemoProto.DemoInput request = DemoProto.DemoInput.newBuilder().setKey(name).build();
        try {
            //从池子里获取连接
            channel = GrpcPool.getInstance().borrowConnect();
            DemoProto.DemoOutput response = DemoServiceGrpc.newBlockingStub(channel).demoFunction(request);
            System.out.println(channel.hashCode() + ",,,Greeting:" + response.getKey());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //把连接放回池子里
            if (channel != null) {
                GrpcPool.getInstance().returnConnect(channel);
            }
        }


    }
}
