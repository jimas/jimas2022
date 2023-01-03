package com.jimas.elastic.dao.mapper;

import com.jimas.elastic.dao.entity.ProductEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

/**
 * @author liuqj
 */
@Component
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, String> {
}
