package com.jimas.rpc;

import java.io.Serializable;

/**
 * @author liuqj
 */
public class MyContext implements Serializable {
    private String methodName;
    private Object[] args;
    private String response;

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

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
