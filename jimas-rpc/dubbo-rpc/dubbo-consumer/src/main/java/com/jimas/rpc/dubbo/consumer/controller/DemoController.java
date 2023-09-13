package com.jimas.rpc.dubbo.consumer.controller;

import com.jimas.rpc.dubbo.demo.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuqj
 */
@RestController
@RequestMapping("demo")
public class DemoController {
    @DubboReference(version = "1.0.0")
    private DemoService demoService;
    @DubboReference(version = "2.0.0")
    private DemoService demoService2;

    @DubboReference(version = "*")
    private DemoService demoServiceAll;

    @PostMapping("say")
    public String sayHello(@RequestParam String name) {

        return demoService.sayHello(name);
    }

    @PostMapping("say-v2")
    public String sayHelloV2(@RequestParam String name) {

        return demoService2.sayHello(name);
    }

    @PostMapping("say-all")
    public String sayHelloAll(@RequestParam String name) {

        return demoServiceAll.sayHello(name);
    }

}
