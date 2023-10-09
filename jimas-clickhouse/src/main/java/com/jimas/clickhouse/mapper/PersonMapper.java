package com.jimas.clickhouse.mapper;

import com.jimas.clickhouse.entity.Person;

import java.util.List;

/**
 * @author liuqj
 */
public interface PersonMapper {

    List<Person> queryList();
}
