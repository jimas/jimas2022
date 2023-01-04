package com.jimas.elastic.dao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

/**
 * @author liuqj
 */
@Document(indexName = "geo_shape")
@Data
public class GeoShape {
    @Id
    private String id;
    private LocationShape location;
    @Data
    public static class LocationShape {
        private String type;
        private List<List<List<Double>>> coordinates;
    }

}
