package com.jimas.zookeeper.controller;

import com.jimas.zookeeper.lock.ZkLock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liuqj
 */
@RestController
@RequestMapping("/lock")
public class LockController {
    @Resource
    private ZkLock zkLock;

    @GetMapping("/start")
    public boolean lock(@RequestParam(value = "pathKey") String pathKey) throws Exception {

        return zkLock.lock(pathKey);
    }
}
