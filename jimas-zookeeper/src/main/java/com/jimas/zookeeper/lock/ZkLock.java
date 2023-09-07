package com.jimas.zookeeper.lock;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liuqj
 */
@Component
@Slf4j
public class ZkLock {
    Lock lock = new ReentrantLock();
    private static final String LOCK = "lock-";
    @Resource
    private CuratorFramework curatorFramework;

    public boolean lock(String pathKey) throws Exception {
        String realPath = pathKey;
        if (pathKey.endsWith("/")) {
            realPath = realPath + LOCK;
        } else {
            realPath = realPath + "/" + LOCK;
        }
        //1、创建临时顺序节点
        String path = curatorFramework.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath(realPath);
        log.info(path);
        List<String> list = curatorFramework.getChildren().forPath(pathKey);
        list.sort(String::compareTo);

        /*curatorFramework.getData().usingWatcher(new CuratorWatcher() {
            @Override
            public void process(WatchedEvent watchedEvent) throws Exception {
                log.info(watchedEvent.toString());
            }
        }).forPath("");*/


        log.info(JSON.toJSONString(list));
        return true;

    }

}
