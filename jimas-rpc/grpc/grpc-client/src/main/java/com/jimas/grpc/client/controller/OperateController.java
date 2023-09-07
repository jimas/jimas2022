package com.jimas.grpc.client.controller;

import com.jimas.grpc.client.service.CalculateService;
import com.jimas.grpc.common.helloworld.OperateGrpc;
import com.jimas.grpc.common.helloworld.OperateType;
import com.jimas.grpc.common.helloworld.Request;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liuqj
 */
@RestController
@RequestMapping("grpc/")
public class OperateController {
    @Resource
    private CalculateService calculateService;
    /**
     * 要与配置文件一致
     * grpc.client.grpc-operate-service.address = 'static://127.0.0.1:5015'
     * grpc.client.grpc-operate-service.negotiation-type= plaintext
     */
    @GrpcClient("grpc-operate-service")
    private OperateGrpc.OperateBlockingStub operateBlockingStub;

    @PostMapping("operate")
    public Double operate() {
        return calculateService.operate(10D, 20D, OperateType.Addition);
    }

    @PostMapping("operate2")
    public Double operate2() {
        Request request = Request.newBuilder().setNum1(10D).setNum2(20D).setOpType(OperateType.Addition).build();
        return operateBlockingStub.calculate(request).getResult();
    }
}
