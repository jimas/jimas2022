package com.jimas.elastic.dao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author liuqj
 */
@Data
@Document(indexName = "product")
public class ProductEntity {
    @Id
    private String id;
    private String name;
    private Float price;
    private String tag;
    private String type;
    private String desc;
    @Field(type = FieldType.Date, name = "create_time", format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
