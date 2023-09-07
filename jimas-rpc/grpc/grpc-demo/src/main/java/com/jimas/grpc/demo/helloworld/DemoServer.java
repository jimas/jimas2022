package com.jimas.grpc.demo.helloworld;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author liuqj
 */
public class DemoServer {
    public static final int PORT = 30051;
    Server server = null;

    public static void main(String[] args) throws Exception {
        DemoServer demoServer = new DemoServer();
        demoServer.start();
        demoServer.blockUntilShutdown();
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    private void start() throws IOException {
        server = ServerBuilder.forPort(PORT)
                .addService(new DemoImpl())
                .build().start();

        System.out.println("Server started... listening port " + PORT);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Server stop... ");
            if (server != null) {
                try {
                    server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }));
    }

    static class DemoImpl extends DemoServiceGrpc.DemoServiceImplBase {
        @Override
        public void demoFunction(DemoProto.DemoInput request, StreamObserver<DemoProto.DemoOutput> responseObserver) {
            DemoProto.DemoOutput reply = DemoProto.DemoOutput.newBuilder().setKey(request.getKey()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}
