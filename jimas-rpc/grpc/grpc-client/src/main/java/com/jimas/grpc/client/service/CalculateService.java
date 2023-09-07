package com.jimas.grpc.client.service;

import com.jimas.grpc.common.helloworld.OperateGrpc;
import com.jimas.grpc.common.helloworld.OperateType;
import com.jimas.grpc.common.helloworld.Request;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author liuqj
 */
@Service
public class CalculateService implements InitializingBean, DisposableBean {
    @Value("${grpc.server.url}")
    private String serverUrl;
    private ManagedChannel channel;
    private OperateGrpc.OperateBlockingStub blockingStub;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.channel = ManagedChannelBuilder.forTarget(serverUrl).usePlaintext().build();
        this.blockingStub = OperateGrpc.newBlockingStub(channel);
    }

    public Double operate(Double num1, Double num2, OperateType operateType) {
        Request request = Request.newBuilder().setNum1(num1).setNum2(num2).setOpType(operateType).build();
        return blockingStub.calculate(request).getResult();
    }
    @Override
    public void destroy() throws Exception {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
