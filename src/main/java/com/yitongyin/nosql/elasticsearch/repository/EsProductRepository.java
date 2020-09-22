package com.yitongyin.nosql.elasticsearch.repository;

import com.yitongyin.nosql.elasticsearch.document.EsProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsProductRepository extends ElasticsearchRepository<EsProduct, Long> {
}
