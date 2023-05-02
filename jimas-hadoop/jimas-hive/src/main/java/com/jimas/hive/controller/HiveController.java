package com.jimas.hive.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuqj
 */
@RestController
@RequestMapping("hive/")
public class HiveController {
    @RequestMapping("query")
    public String query() {

        return "ss";
    }
}
