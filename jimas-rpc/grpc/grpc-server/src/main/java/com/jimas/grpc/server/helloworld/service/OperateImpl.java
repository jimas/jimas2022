package com.jimas.grpc.server.helloworld.service;

import com.jimas.grpc.common.helloworld.OperateGrpc;
import com.jimas.grpc.common.helloworld.Request;
import com.jimas.grpc.common.helloworld.Response;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Value;

/**
 * 实现接口服务
 *
 * @author liuqj
 */
@GrpcService
public class OperateImpl extends OperateGrpc.OperateImplBase {
    @Value("${server.port}")
    private int port;

    @Override
    public void calculate(Request request, StreamObserver<Response> responseObserver) {
        Response response = Response.newBuilder().setResult(Math.random() * (request.getNum1() + request.getNum2()))
                .setResponsePort(port).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        System.out.println(Thread.currentThread().getName());
    }
}
