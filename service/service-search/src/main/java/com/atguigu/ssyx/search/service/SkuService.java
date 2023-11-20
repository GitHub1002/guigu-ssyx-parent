/**
 * @Classname SkuService
 * @Date 2023/11/10 15:23
 * @Created by Mingkai Feng
 * @Description TODO
 */
package com.atguigu.ssyx.search.service;

import org.springframework.stereotype.Service;


public interface SkuService {

    void upperSku(Long skuId);

    void lowerSku(Long skuId);
}
