package com.jimas.apollo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Date: 2022/10/11
 * Time: 23:24
 *
 * @author jimas
 */
@RestController
public class ApolloController {
    @Value("${boyName}")
    private String boyName;

    @Value("${redis.host}")
    private String redisHost;

    @GetMapping("/show")
    public String show() {

        return boyName + "===" + redisHost;
    }


}
