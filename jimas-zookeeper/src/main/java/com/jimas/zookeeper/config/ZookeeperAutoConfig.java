package com.jimas.zookeeper.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author liuqj
 */
@Slf4j
@Configuration
public class ZookeeperAutoConfig {
    @Value("${spring.zookeeper.address}")
    private String connectionInfo;

    @PostConstruct
    public void init() {
        log.info("zk notify load");
    }

    @Bean
    public CuratorFramework curatorFramework() {
        ExponentialBackoffRetry backoffRetry = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework framework = CuratorFrameworkFactory.builder().connectString(connectionInfo)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(backoffRetry)
                .build();
        framework.start();
        return framework;
    }

}
