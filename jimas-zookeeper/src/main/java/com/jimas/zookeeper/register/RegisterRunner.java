package com.jimas.zookeeper.register;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 首次初始化注册
 *
 * @author liuqj
 */
@Component
@Slf4j
public class RegisterRunner implements ApplicationRunner {
    @Resource
    private InstanceRegisterService registerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        registerService.register();
    }
}
