package com.jimas.zookeeper.register.bean;

import lombok.Data;

/**
 * @author liuqj
 */
@Data
public class RegisterBean {
    private String host;
    private int port;
    private String remark;

    public RegisterBean(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public RegisterBean() {
    }
}
