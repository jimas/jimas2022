package com.jimas.web.execute;

import com.jimas.web.utils.RandomUtils;
import com.jimas.web.utils.SleepHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author liuqj
 */
@Component
@Slf4j
public class ConcurrentExecute {
    @Resource
    private ThreadPoolExecutor messageConsumeDynamicExecutor;
    @Resource
    private ThreadPoolExecutor messageProduceDynamicExecutor;
    private static final LongAdder num1 = new LongAdder();
    private static final LongAdder num2 = new LongAdder();

    private static final int PERSONS = 10;
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(PERSONS, () -> {
        log.info("{} 人已满，开始执行！！", PERSONS);
        num1.add(10);
        SleepHelper.sleepSafe(RandomUtils.random(100), TimeUnit.MILLISECONDS);
    });

    public void executeV1() {
        for (int i = 0; i < PERSONS * PERSONS; i++) {
            messageConsumeDynamicExecutor.execute(() -> {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    log.error("InterruptedException", e);
                } catch (BrokenBarrierException e) {
                    log.error("BrokenBarrierException", e);

                }
            });
        }
    }

    public void executeV2() {
        for (int i = 0; i < PERSONS * PERSONS; i++) {
            messageProduceDynamicExecutor.execute(() -> {
                log.info("新人签到 :" + Thread.currentThread().getName());
                num2.add(1);
                SleepHelper.sleepSafe(RandomUtils.random(100, 300), TimeUnit.MILLISECONDS);
            });
        }
    }

    public String display() {

        return "num1:" + num1.intValue() + ",num2:" + num2.intValue();
    }
}
