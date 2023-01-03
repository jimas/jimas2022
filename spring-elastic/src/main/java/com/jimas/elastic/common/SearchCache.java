package com.jimas.elastic.common;

import org.elasticsearch.search.sort.SortBuilder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 不支持跳页，仅支持下一页
 *
 * @author liuqj
 */
public class SearchCache {
    private static final Map<String, List<Object>> searchAfterMap = new ConcurrentHashMap<>();

    public static List<Object> getSearchAfter(List<SortBuilder<?>> sorts) {
        final String key = getKey(sorts);
        return searchAfterMap.get(key);
    }

    public static void setSearchAfter(List<SortBuilder<?>> sorts, List<Object> searchAfter) {
        final String key = getKey(sorts);
        searchAfterMap.put(key, searchAfter);
    }

    private static String getKey(List<SortBuilder<?>> sorts) {
        final List<String> collect = sorts.stream().map(SortBuilder::toString).collect(Collectors.toList());
        return String.join(",", collect);
    }

}
