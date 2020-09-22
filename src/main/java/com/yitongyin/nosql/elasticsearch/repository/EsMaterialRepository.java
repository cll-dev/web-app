package com.yitongyin.nosql.elasticsearch.repository;

import com.yitongyin.nosql.elasticsearch.document.EsMaterial;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsMaterialRepository extends ElasticsearchRepository<EsMaterial, Long> {
}
