package com.jimas.zookeeper.register;

import com.alibaba.fastjson.JSON;
import com.jimas.zookeeper.register.bean.RegisterBean;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author liuqj
 */
@Component
@Slf4j
public class InstanceRegisterService {
    private static final String BASE_PATH = "/register";
    private static final int CONNECT_SIZE = 1;
    private static final int TRY_SIZE = 2;
    @Resource
    private CuratorFramework curatorFramework;
    @Resource
    private ServiceConfig serviceConfig;

    @SneakyThrows
    public List<RegisterBean> getAllRegister() {
        List<RegisterBean> registerBeans = new ArrayList<>();
        List<String> allRegisterNode = getAllRegisterNode();
        for (String registerNode : allRegisterNode) {
            byte[] bytes = curatorFramework.getData().forPath(BASE_PATH + "/" + registerNode);
            registerBeans.add(JSON.parseObject(bytes, RegisterBean.class));
        }
        return registerBeans;
    }

    public void register() {
        RegisterBean registerBean = new RegisterBean(serviceConfig.getServerHost(), serviceConfig.getServerPort());
        for (int i = 0; i < CONNECT_SIZE; i++) {
            try {
                String result = curatorFramework.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.EPHEMERAL)
                        .forPath(BASE_PATH + "/" + i, JSON.toJSONBytes(registerBean));
                log.info("register service success ,{}", result);
                break;
            } catch (Exception e) {
                log.error("register fail", e);
            }
        }
    }

    @SneakyThrows
    private List<String> getAllRegisterNode() {
        int count = 0;
        do {
            count++;
            List<String> list = curatorFramework.getChildren().forPath(BASE_PATH);
            if (CollectionUtils.isEmpty(list)) {
                register();
            } else {
                return list;
            }
        } while (count < TRY_SIZE);
        return Collections.emptyList();
    }
}
