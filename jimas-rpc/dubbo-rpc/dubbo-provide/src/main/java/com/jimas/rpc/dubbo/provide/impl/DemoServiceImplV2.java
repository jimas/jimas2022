package com.jimas.rpc.dubbo.provide.impl;

import com.jimas.rpc.dubbo.demo.DemoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author liuqj
 */
@DubboService(version = "2.0.0")
public class DemoServiceImplV2 implements DemoService {
    @Value("${server.port}")
    private int port;

    @Override
    public String sayHello(String name) {

        return "version:2.0.0 from port:" + port + ",hello " + name;
    }
}
