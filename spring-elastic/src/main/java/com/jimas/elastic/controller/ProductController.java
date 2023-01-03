package com.jimas.elastic.controller;

import com.jimas.elastic.common.SearchCache;
import com.jimas.elastic.dao.entity.ProductEntity;
import com.jimas.elastic.dao.mapper.ProductRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liuqj
 */
@RestController
@RequestMapping("product")
public class ProductController {
    @Resource
    private ProductRepository productRepostity;
    @Resource
    private ElasticsearchRestTemplate restTemplate;

    @GetMapping("list")
    public Page<ProductEntity> list() {
        final PageRequest pageable = PageRequest.of(0, 10);

        return productRepostity.findAll(pageable);
    }

    @GetMapping("searchAfter")
    public Page<ProductEntity> searchAfter(@RequestParam(name = "pageSize", defaultValue = "3") int pageSize) {
        final QueryBuilder queryBuilder = QueryBuilders.boolQuery();
        List<SortBuilder<?>> sorts = new ArrayList<>();
        sorts.add(SortBuilders.fieldSort("price").order(SortOrder.DESC));
        sorts.add(SortBuilders.fieldSort("_id").order(SortOrder.DESC));
        final NativeSearchQuery query = new NativeSearchQuery(queryBuilder, null, sorts);
        final List<Object> searchAfter = SearchCache.getSearchAfter(sorts);
        //`from` parameter must be set to 0 when `search_after` is used
        PageRequest pageable = PageRequest.of(0, pageSize);
        if (searchAfter != null) {
            query.setSearchAfter(searchAfter);
        }
        query.setPageable(pageable);
        query.setTrackTotalHits(true);
        final SearchHits<ProductEntity> hits = restTemplate.search(query, ProductEntity.class);
        final List<SearchHit<ProductEntity>> searchHits = hits.getSearchHits();
        final List<ProductEntity> entities = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        //把最后一条的sortValues 赋值给下一次searchAfter
        if (!CollectionUtils.isEmpty(searchHits)) {
            SearchCache.setSearchAfter(sorts, searchHits.get(searchHits.size() - 1).getSortValues());
        }
        return new PageImpl<>(entities, pageable, hits.getTotalHits());
    }
}
