package com.jimas.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * http 1.0 默认短链接
 * http 1.1 默认长链接
 * http 本身是基于请求响应模式的，并没有长短之说，这里的长短连接 是基于tcp，是否复用tcp连接 而言的，
 * 短连接比如：开启tcp连接-->开启http连接1-->关闭http连接1-->关闭tcp连接
 * 长连接比如：开启tcp连接-->开启http连接1-->关闭http连接1，开启http连接2-->关闭http连接2-->关闭tcp连接
 *
 * 服务端+客户端 同时开启 Connection：keep-alive 才算 长连接
 * @author liuqj
 */
@RestController
public class ConnectionController {
    @RequestMapping("connect/01")
    public String connect01(HttpServletResponse response) {

        response.setHeader("Connection", "keep-alive");
        return "long link";

    }

    @RequestMapping("connect/02")
    public String connect02(HttpServletResponse response) {

        response.setHeader("Connection", "close");
        return "short link";

    }
}
