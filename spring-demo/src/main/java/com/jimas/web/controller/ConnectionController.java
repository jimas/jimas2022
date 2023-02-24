package com.jimas.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
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
