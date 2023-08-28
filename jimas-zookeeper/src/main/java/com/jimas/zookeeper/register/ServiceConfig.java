package com.jimas.zookeeper.register;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author liuqj
 */
@Component
public class ServiceConfig implements ApplicationListener<WebServerInitializedEvent> {
    private int serverPort;
    private String serverHost;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
        try {
            this.serverHost = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getServerHost() {
        return serverHost;
    }
}
