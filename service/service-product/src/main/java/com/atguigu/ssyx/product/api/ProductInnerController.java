/**
 * @Classname ProductInnerController
 * @Date 2023/11/11 14:25
 * @Created by Mingkai Feng
 * @Description TODO 用于 search 模块远程调用
 */
package com.atguigu.ssyx.product.api;

import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.product.service.CategoryService;
import com.atguigu.ssyx.product.service.SkuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductInnerController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SkuInfoService skuInfoService;

    // 根据 skuid 获取分类信息
    @GetMapping("inner/getCategory/{categoryId}")
    public Category getCategory (@PathVariable(name = "categoryOd") Long categoryId){
        Category category = categoryService.getById(categoryId);
        return category;
    }

    // 根据 skuid 获取 sku 信息
    @GetMapping("inner/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo (@PathVariable(name = "skuId") Long skuId){
        return skuInfoService.getById(skuId);
    }

    // 根据 skuId 列表得到 sku 信息列表
    @PostMapping("inner/findSkuInfoList")
    public List<SkuInfo> findSkuInfoList(@RequestBody List<Long> skuIdList){
        return skuInfoService.findSkuInfoList(skuIdList);
    }

    @PostMapping("inner/findSkuInfoByKeyword/{keyword}")
    public List<SkuInfo> findSkuInfoByKeyword(@PathVariable("keyword") String keyword){
        return skuInfoService.findSkuInfoByKeyword(keyword);
    }

    // 根据分类 id 获取分类列表
    @PostMapping("inner/findCategoryList")
    public List<Category> findCategoryList (@RequestBody List<Long> categoryIdList){
        return categoryService.listByIds(categoryIdList);
    }
}
