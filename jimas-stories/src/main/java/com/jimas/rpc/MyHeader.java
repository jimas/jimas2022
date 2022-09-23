package com.jimas.rpc;

import java.io.Serializable;

/**
 * @author liuqj
 */
public class MyHeader implements Serializable {
    private int flag;
    private long contentLen;
    private long requestId;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public long getContentLen() {
        return contentLen;
    }

    public void setContentLen(long contentLen) {
        this.contentLen = contentLen;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }
}
