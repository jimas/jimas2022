package com.jimas.elastic.dao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author liuqj
 */
@Document(indexName = "geo_point")
@Data
public class GeoPoint {
    @Id
    private String id;
    private Location location;

    @Data
    public static class Location {
        private Double lat;
        private Double lon;
    }
}
