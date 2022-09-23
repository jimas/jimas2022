package com.jimas.rpc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Date: 2022/8/18
 * Time: 23:43
 *
 * @author com.jimas
 */
public class ResponseMappingCallback {

    private static ConcurrentHashMap<Long, CompletableFuture> futureMap = new ConcurrentHashMap<>();

    public static void addCallback(Long requestId, CompletableFuture future) {
        futureMap.putIfAbsent(requestId, future);
    }

    public static void runCallback(MyPackage myPackage) {
        CompletableFuture future = futureMap.get(myPackage.getMyHeader().getRequestId());
        future.complete(myPackage.getMyContext().getResponse());
        removeCallBack(myPackage.getMyHeader().getRequestId());
    }

    public static void removeCallBack(long requestId) {
        futureMap.remove(requestId);
    }
}
