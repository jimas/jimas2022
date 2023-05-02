package com.jimas.hive.service;

import com.jimas.hive.BaseTests;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.List;

public class HiveQueryServiceTest extends BaseTests {
    @Resource(name = "hiveJdbcTemplate")
    public JdbcTemplate jdbcTemplate;

    @Test
    public void testQuery() {
        List<Object> id = jdbcTemplate.query("select * from psn", (rs, rowNum) -> rs.getInt("id"));
        System.out.println(id);
    }
}
