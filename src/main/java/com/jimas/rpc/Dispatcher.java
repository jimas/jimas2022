package com.jimas.rpc;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuqj
 */
public class Dispatcher {

    private ConcurrentHashMap<String, Object> objMap = new ConcurrentHashMap<>();

    public void register(String key, Object object) {
        objMap.put(key, object);
    }
    public Object getObj(String key) {
        return objMap.get(key);
    }
}
