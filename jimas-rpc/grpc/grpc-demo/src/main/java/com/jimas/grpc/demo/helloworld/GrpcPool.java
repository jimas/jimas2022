package com.jimas.grpc.demo.helloworld;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author liuqj
 */
public class GrpcPool {
    private GenericObjectPool<ManagedChannel> pool;

    private volatile static GrpcPool grpcPool;

    public static GrpcPool getInstance() {
        if (grpcPool == null) {
            synchronized (GrpcPool.class) {
                if (grpcPool != null) {
                    return grpcPool;
                }
                grpcPool = new GrpcPool();
            }
        }
        return grpcPool;
    }

    private GrpcPool() {
        BasePooledObjectFactory<ManagedChannel> factory = new BasePooledObjectFactory<>() {
            @Override
            public ManagedChannel create() throws Exception {
                return ManagedChannelBuilder.forAddress("localhost", DemoServer.PORT).usePlaintext().build();
            }

            @Override
            public PooledObject<ManagedChannel> wrap(ManagedChannel channel) {
                return new DefaultPooledObject<>(channel);
            }

            @Override
            public void destroyObject(PooledObject<ManagedChannel> p) {
                p.getObject().shutdown();
            }

            @Override
            public boolean validateObject(PooledObject<ManagedChannel> p) {
                return !p.getObject().isShutdown();
            }
        };
        GenericObjectPoolConfig<ManagedChannel> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(5);
        poolConfig.setMaxIdle(4);
        poolConfig.setMinIdle(1);
        poolConfig.setMaxWait(Duration.of(1, TimeUnit.MINUTES.toChronoUnit()));
        poolConfig.setLifo(true);
        poolConfig.setMinEvictableIdleTime(Duration.ofSeconds(60 * 30));
        poolConfig.setBlockWhenExhausted(true);
        this.pool = new GenericObjectPool<>(factory, poolConfig);
    }

    public ManagedChannel borrowConnect() throws Exception {
        return pool.borrowObject();
    }

    public void returnConnect(ManagedChannel channel) {
        pool.returnObject(channel);
    }

    public void close() {
        pool.close();
    }
}
