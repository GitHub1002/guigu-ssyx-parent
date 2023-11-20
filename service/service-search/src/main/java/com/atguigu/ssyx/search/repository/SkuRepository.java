/**
 * @Classname Skurepository
 * @Date 2023/11/10 17:31
 * @Created by Mingkai Feng
 * @Description TODO
 */
package com.atguigu.ssyx.search.repository;

import com.atguigu.ssyx.model.search.SkuEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SkuRepository extends ElasticsearchRepository<SkuEs, Long> {
}
