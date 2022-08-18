package com.jimas.rpc;

import java.util.UUID;

/**
 * @author liuqj
 */
public class MyClient {

    private Object[] args;
    private String method;

    public MyClient(Object[] args, String method) {
        this.args = args;
        this.method = method;
    }

    public static MyHeader createHeader(byte[] bodyBytes) {
        MyHeader myHeader = new MyHeader();
        myHeader.setContentLen(bodyBytes.length);
        myHeader.setFlag(0x14141414);
        myHeader.setRequestId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
        return myHeader;
    }
}
