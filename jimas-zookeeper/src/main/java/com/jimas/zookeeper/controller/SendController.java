package com.jimas.zookeeper.controller;

import com.alibaba.fastjson.JSON;
import com.jimas.zookeeper.register.InstanceRegisterService;
import com.jimas.zookeeper.register.bean.RegisterBean;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liuqj
 */
@RestController
@RequestMapping("/sms")
public class SendController {
    @Resource
    private InstanceRegisterService registerService;

    @PostMapping("/send")
    public String send() {
        List<RegisterBean> allRegister = registerService.getAllRegister();
        if (CollectionUtils.isEmpty(allRegister)) {
            return "send error,no service";
        }

        int index = (int) (Math.random() * allRegister.size());
        RegisterBean registerBean = allRegister.get(index);

        return "send success," + JSON.toJSON(registerBean);

    }
}
