package com.jimas.clickhouse.mapper;

import com.alibaba.fastjson2.JSON;
import com.jimas.clickhouse.BaseTest;
import com.jimas.clickhouse.entity.Person;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class PersonMapperTests extends BaseTest {

    @Resource
    private PersonMapper personMapper;

    @Test
    public void testQuery() {
        List<Person> personList = personMapper.queryList();
        for (Person person : personList) {
            System.out.println(JSON.toJSONString(person));
        }
    }
}
