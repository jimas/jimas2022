package com.jimas.elastic.controller;

import com.jimas.elastic.dao.entity.GeoPoint;
import com.jimas.elastic.dao.entity.GeoShape;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liuqj
 */
@RestController
@RequestMapping("geo")
public class GeoController {

    @Resource
    private ElasticsearchRestTemplate restTemplate;

    @GetMapping("point")
    public Page<GeoPoint> pointList() {
        final PageRequest pageable = PageRequest.of(0, 10);
        QueryBuilder query = QueryBuilders.geoDistanceQuery("location")
                .distance("1000", DistanceUnit.KILOMETERS)
                .geoDistance(GeoDistance.ARC)
                .point(40.73, -71.1);
        final SearchHits<GeoPoint> searchHits = restTemplate.search(new NativeSearchQuery(query, null, null), GeoPoint.class);
        final List<GeoPoint> points = searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
        return new PageImpl<>(points, pageable, searchHits.getTotalHits());
    }

    @GetMapping("shape")
    public Page<GeoShape> shapeList() {
        final PageRequest pageable = PageRequest.of(0, 10);
        QueryBuilder query = QueryBuilders.geoDistanceQuery("location")
                .distance("100000", DistanceUnit.KILOMETERS)
                .geoDistance(GeoDistance.ARC)
                .point(40.73, -71.1);
        final SearchHits<GeoShape> searchHits = restTemplate.search(new NativeSearchQuery(query, null, null), GeoShape.class);
        final List<GeoShape> points = searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
        return new PageImpl<>(points, pageable, searchHits.getTotalHits());
    }
}
