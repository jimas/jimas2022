package com.jimas.grpc.client.controller;

import com.jimas.grpc.common.helloworld.OperateGrpc;
import com.jimas.grpc.common.helloworld.OperateType;
import com.jimas.grpc.common.helloworld.Request;
import com.jimas.grpc.common.helloworld.Response;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liuqj
 */
@RestController
@RequestMapping("grpc/")
public class OperateController {
    /**
     * 要与配置文件一致
     * grpc.client.grpc-operate-service.address = 'static://127.0.0.1:5015'
     * grpc.client.grpc-operate-service.negotiation-type= plaintext
     */
    @GrpcClient("grpc-operate-service")
    private OperateGrpc.OperateBlockingStub operateBlockingStub;
    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("listUrl")
    public String listUrl() {
        StringBuilder result = new StringBuilder();
        List<ServiceInstance> instances = discoveryClient.getInstances("grpc-server");
        for (ServiceInstance instance : instances) {
            result.append(instance.getUri().toString());
        }
        return result.toString();
    }

    @PostMapping("operate")
    public String operate() {
        Request request = Request.newBuilder().setNum1(10D).setNum2(20D).setOpType(OperateType.Addition).build();
        Response response = operateBlockingStub.calculate(request);
        return String.format("port:%d,result:%f", response.getResponsePort(), response.getResult());
    }
}
