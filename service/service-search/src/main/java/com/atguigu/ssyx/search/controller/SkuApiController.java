/**
 * @Classname SkuApiController
 * @Date 2023/11/10 15:23
 * @Created by Mingkai Feng
 * @Description TODO
 */
package com.atguigu.ssyx.search.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.search.service.SkuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/search/sku")
public class SkuApiController {

    @Autowired
    private SkuService skuService;

    @ApiOperation(value = "上架商品")
    @GetMapping("inner/upperSku/{skuId}")
    public Result upperGoods (@PathVariable Long skuId){
        skuService.upperSku(skuId);
        return Result.ok(null);
    }

    @ApiOperation(value = "下架商品")
    @GetMapping("inner/lowerSku/{skuId}")
    public Result lowerGoods (@PathVariable Long skuId){
        skuService.lowerSku(skuId);
        return Result.ok(null);
    }
}
