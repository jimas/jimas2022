package com.jimas.class06;

import com.jimas.rpc.ClientPool;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jimas.class06.MeRpcTest.HOST;
import static com.jimas.class06.MeRpcTest.PORT;

/**
 * @author liuqj
 */
public class MeRpcTest {
    public static final String HOST = "localhost";
    public static final Integer PORT = 9080;

    @Test
    public void testRpc() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MeNettyServer.startServer();
            }
        }).start();
        System.out.println("server started....");
        AtomicInteger sand = new AtomicInteger(0);
        for (int i = 0; i < 10; i++) {
            MeCar meCar = MeProxy.getInstance().obj(MeCar.class);
            String param = "fast" + sand.getAndIncrement();
            String res = meCar.run(param);
            System.out.println("request param:" + param + "== server response:" + res);
        }
    }
}

class MeNettyServer {
    public static void startServer() {
        Dispatcher dispatcher = Dispatcher.getInstance();
        dispatcher.register(MeCar.class.getName(), new MeCarService());
        NioEventLoopGroup boss = new NioEventLoopGroup(10);
        NioEventLoopGroup worker = boss;
        ChannelFuture future = new ServerBootstrap().group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        System.out.println("client port:" + ch.remoteAddress().getPort());
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new MeDecodeHandler());
                        pipeline.addLast(new MeServerHandler(dispatcher));
                    }
                }).bind(HOST, PORT);
        try {
            future.sync().channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

interface MeCar {
    String run(String speed);
}

class MeCarService implements MeCar {

    @Override
    public String run(String speed) {
        return "remote response:" + speed;
    }
}

class MeClientProxy {
    private static final Integer POOL_SIZE = 1;
    private static final ConcurrentHashMap<InetSocketAddress, ClientPool> clientPoolMap = new ConcurrentHashMap<>();
    private static MeClientProxy meClientProxy;
    private static Random random = new Random();

    private MeClientProxy() {
    }

    public static MeClientProxy getInstance() {
        if (meClientProxy != null) {
            return meClientProxy;
        }
        synchronized (MeClientProxy.class) {
            if (meClientProxy == null) {
                meClientProxy = new MeClientProxy();
            }
        }
        return meClientProxy;
    }

    public static NioSocketChannel client(InetSocketAddress address) {
        ClientPool clientPool = clientPoolMap.get(address);
        if (clientPool == null) {
            synchronized (clientPoolMap) {
                if (clientPool == null) {
                    clientPool = new ClientPool(POOL_SIZE);
                    clientPoolMap.put(address, clientPool);
                }
            }
        }
        int index = random.nextInt(POOL_SIZE);
        if (clientPool.getClients()[index] == null || !clientPool.getClients()[index].isActive()) {
            synchronized (clientPool.getLock()[index]) {
                if (clientPool.getClients()[index] == null || !clientPool.getClients()[index].isActive()) {
                    clientPool.getClients()[index] = createClient(address);
                }
            }
        }
        return clientPool.getClients()[index];
    }

    private static NioSocketChannel createClient(InetSocketAddress address) {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        ChannelFuture future = new Bootstrap().group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new MeDecodeHandler());
                        pipeline.addLast(new MeClientHandler());
                    }
                }).connect(address);

        try {
            return (NioSocketChannel) future.sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class MeProxy {
    private static MeProxy meProxy;

    static {
        meProxy = new MeProxy();
    }

    private MeProxy() {
    }

    public static MeProxy getInstance() {
        return meProxy;
    }


    public <T> T obj(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                NioSocketChannel client = MeClientProxy.client(new InetSocketAddress(HOST, PORT));
                MeBody meBody = new MeBody();
                meBody.setArgs(args);
                meBody.setMethodName(method.getName());
                meBody.setName(clazz.getName());
                meBody.setParameterTypes(method.getParameterTypes());
                byte[] bodyBytes = MeUtils.objToBytes(meBody);
                MeHeader header = new MeHeader(0x14141414, Math.abs(UUID.randomUUID().getLeastSignificantBits()), bodyBytes.length);
                byte[] headBytes = MeUtils.objToBytes(header);
                System.out.println("head length:" + headBytes.length);
                CompletableFuture future = new CompletableFuture();
                MeMappingCallbackFuture.addCallback(header.getRequestId(), future);
                ByteBuf buf = UnpooledByteBufAllocator.DEFAULT.directBuffer(headBytes.length + bodyBytes.length);
                buf.writeBytes(headBytes);
                buf.writeBytes(bodyBytes);
                client.writeAndFlush(buf);
                return future.get();
            }
        });

    }
}

class MeClientPool {
    Object[] lock;
    NioSocketChannel[] clients;

    public MeClientPool(int poolSize) {
        clients = new NioSocketChannel[poolSize];
        lock = new Object[poolSize];
        for (int i = 0; i < poolSize; i++) {
            lock[i] = new Object();
        }
    }
}

class MeDecodeHandler extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >= 96) {
            byte[] headBytes = new byte[96];
            in.getBytes(in.readerIndex(), headBytes);
            MeHeader header = (MeHeader) MeUtils.bytesToObj(headBytes);
            if (in.readableBytes() - 96 >= header.getDataLen()) {
                in.readBytes(headBytes.length);
                byte[] bodyBytes = new byte[header.getDataLen()];
                in.readBytes(bodyBytes);
                MeBody body = (MeBody) MeUtils.bytesToObj(bodyBytes);
                MePackage mePackage = new MePackage(header, body);
                out.add(mePackage);
            } else {
                break;
            }
        }
    }
}

class MeServerHandler extends ChannelInboundHandlerAdapter {
    private Dispatcher dis;

    public MeServerHandler(Dispatcher dispatcher) {
        this.dis = dispatcher;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MePackage mePackage = (MePackage) msg;
        ctx.executor().parent().next().execute(() -> {
            try {
                Object bean = dis.getBean(mePackage.getBody().getName());
                Method method = bean.getClass().getMethod(mePackage.getBody().getMethodName(), mePackage.getBody().getParameterTypes());
                Object res = method.invoke(bean, mePackage.getBody().getArgs());
                MeBody meBody = new MeBody();
                meBody.setRes(res.toString());
                byte[] bodyBytes = MeUtils.objToBytes(meBody);
                MeHeader header = new MeHeader(0x14141424, mePackage.getHeader().getRequestId(), bodyBytes.length);
                byte[] headBytes = MeUtils.objToBytes(header);
                ByteBuf buf = UnpooledByteBufAllocator.DEFAULT.directBuffer(headBytes.length + bodyBytes.length);
                buf.writeBytes(headBytes);
                buf.writeBytes(bodyBytes);
                ctx.writeAndFlush(buf);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}

class MeClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MePackage mePackage = (MePackage) msg;
        MeMappingCallbackFuture.runCallback(mePackage);
    }
}

class MeMappingCallbackFuture {
    static ConcurrentHashMap<Long, CompletableFuture> futureMap = new ConcurrentHashMap<>();

    public static void addCallback(Long requestId, CompletableFuture future) {
        futureMap.put(requestId, future);
    }

    public static void runCallback(MePackage mePackage) {
        CompletableFuture future = futureMap.get(mePackage.getHeader().getRequestId());
        future.complete(mePackage.getBody().getRes());
        futureMap.remove(mePackage.getHeader().getRequestId());
    }
}

class Dispatcher {
    private ConcurrentHashMap<String, Object> beans = new ConcurrentHashMap<>();
    private static Dispatcher dis;

    static {
        dis = new Dispatcher();
    }

    private Dispatcher() {
    }

    public static Dispatcher getInstance() {
        return dis;
    }

    public void register(String beanName, Object obj) {
        beans.put(beanName, obj);
    }

    public Object getBean(String beanName) {
        return beans.get(beanName);
    }
}

class MePackage {
    private MeHeader header;
    private MeBody body;

    public MePackage(MeHeader header, MeBody body) {
        this.header = header;
        this.body = body;
    }

    public MeHeader getHeader() {
        return header;
    }

    public void setHeader(MeHeader header) {
        this.header = header;
    }

    public MeBody getBody() {
        return body;
    }

    public void setBody(MeBody body) {
        this.body = body;
    }
}

class MeUtils {
    public static byte[] objToBytes(Object obj) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object bytesToObj(byte[] bytes) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}

class MeHeader implements Serializable {
    private long flag;
    private long requestId;
    private int dataLen;

    public MeHeader() {
    }

    public MeHeader(long flag, long requestId, int dataLen) {
        this.flag = flag;
        this.requestId = requestId;
        this.dataLen = dataLen;
    }

    public long getFlag() {
        return flag;
    }

    public void setFlag(long flag) {
        this.flag = flag;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public int getDataLen() {
        return dataLen;
    }

    public void setDataLen(int dataLen) {
        this.dataLen = dataLen;
    }


}

class MeBody implements Serializable {
    private String name;
    private String methodName;
    private Object[] args;
    private Class<?>[] parameterTypes;
    private String res;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }
}