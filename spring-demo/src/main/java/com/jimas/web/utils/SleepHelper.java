package com.jimas.web.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author liuqj
 */
@Slf4j
public class SleepHelper {
    public static void sleepSafe(int time, TimeUnit timeUnit) {
        try {
            Thread.sleep(timeUnit.toMillis(time));
        } catch (InterruptedException e) {
            log.error("sleepSafe", e);
        }
    }
}
