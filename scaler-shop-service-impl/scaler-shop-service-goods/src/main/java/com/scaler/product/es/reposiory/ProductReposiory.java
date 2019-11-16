package com.scaler.product.es.reposiory;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.scaler.product.es.entity.ProductEntity;

public interface ProductReposiory extends ElasticsearchRepository<ProductEntity, Long> {

}
 