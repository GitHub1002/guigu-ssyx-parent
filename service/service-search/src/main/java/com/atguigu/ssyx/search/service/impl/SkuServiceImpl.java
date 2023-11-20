/**
 * @Classname SkuServiceImpl
 * @Date 2023/11/10 17:08
 * @Created by Mingkai Feng
 * @Description TODO
 */
package com.atguigu.ssyx.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.enums.SkuType;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.model.search.SkuEs;
import com.atguigu.ssyx.search.repository.SkuRepository;
import com.atguigu.ssyx.search.service.SkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public void upperSku(Long skuId) {
        // 1.通过远程调用，根据 skuid 获取相关信息
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        if (skuInfo == null) {
            return;
        }
        Category category = productFeignClient.getCategory(skuInfo.getCategoryId());

        // 2.获取数据封装 SkuEs 对象
        SkuEs skuEs = new SkuEs();
        // 分装分类
        if (category != null) {
            skuEs.setCategoryId(category.getId());
            skuEs.setCategoryName(category.getName());
        }
        // 封装 sku 信息部分
        skuEs.setId(skuInfo.getId());
        skuEs.setKeyword(skuInfo.getSkuName() + "," + skuEs.getCategoryName());
        skuEs.setWareId(skuInfo.getWareId());
        skuEs.setIsNewPerson(skuInfo.getIsNewPerson());
        skuEs.setImgUrl(skuInfo.getImgUrl());
        skuEs.setTitle(skuInfo.getSkuName());
        if (skuInfo.getSkuType() == SkuType.COMMON.getCode()){
            skuEs.setSkuType(0);
            skuEs.setPrice(skuInfo.getPrice().doubleValue());
            skuEs.setStock(skuInfo.getStock());
            skuEs.setSale(skuInfo.getSale());
            skuEs.setPerLimit(skuInfo.getPerLimit());
        }
        else{

        }

        // 3.调用方法添加 ES
        SkuEs save = skuRepository.save(skuEs);
        log.info("upperSku: " + JSON.toJSONString(save));
    }

    @Override
    public void lowerSku(Long skuId) {
        skuRepository.deleteById(skuId);
    }
}
