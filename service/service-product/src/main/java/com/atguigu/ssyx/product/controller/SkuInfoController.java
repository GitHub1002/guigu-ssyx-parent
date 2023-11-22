package com.atguigu.ssyx.product.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.product.service.SkuInfoService;
import com.atguigu.ssyx.vo.product.SkuInfoQueryVo;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * sku信息 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-11-03
 */
@Api(value = "skuInfoController", tags = "sku信息管理")
@RestController
@RequestMapping("/admin/product/skuInfo")
//@CrossOrigin
public class SkuInfoController {
    @Autowired
    private SkuInfoService skuInfoService;

//    url: `${api_name}/${page}/${limit}`,
//    method: 'get',
//    params: searchObj
    @ApiOperation(value = "获取sku分页列表")
    @GetMapping("{page}/{limit}")
    public Result<IPage<SkuInfo>> index (
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "skuInfoQueryVo", value = "查询对象", required = false)
            SkuInfoQueryVo skuInfoQueryVo){
        Page<SkuInfo> pageParam = new Page<>(page, limit);
        IPage<SkuInfo> pageModel = skuInfoService.selectPage(pageParam, skuInfoQueryVo);
        return Result.ok(pageModel);
    }

//    url: `${api_name}/save`,
//    method: 'post',
//    data: role
    @ApiOperation(value = "新增商品sku信息")
    @PostMapping("save")
    public Result save (@RequestBody SkuInfoVo skuInfoVo){
        skuInfoService.saveSkuInfo(skuInfoVo);
        return Result.ok(null);
    }

//    url: `${api_name}/get/${id}`,
//    method: 'get'
    @ApiOperation(value = "获取 sku 信息")
    @GetMapping("get/{id}")
    public Result<SkuInfoVo> get (@PathVariable Long id){
        SkuInfoVo skuInfoVo = skuInfoService.getSkuInfo(id);
        return Result.ok(skuInfoVo);
    }

//    url: `${api_name}/update`,
//    method: 'put',
//    data: role
    @ApiOperation(value = "修改 sku 信息")
    @PutMapping("update")
    public Result<SkuInfoVo> update (@RequestBody SkuInfoVo skuInfoVo){
        skuInfoService.updateSkuInfo(skuInfoVo);
        return Result.ok(null);
    }

//    url: `${api_name}/check/${id}/${status}`,
//    method: 'get'
    @ApiOperation(value = "商品审核")
    @GetMapping("check/{id}/{status}")
    public Result check (@PathVariable Long id, @PathVariable Integer status){
        skuInfoService.check(id, status);
        return Result.ok(null);
    }

//    url: `${api_name}/publish/${id}/${status}`,
//    method: 'get'
    @ApiOperation(value = "商品上架")
    @GetMapping("publish/{id}/{status}")
    public Result publish (@PathVariable Long id, @PathVariable Integer status){
        skuInfoService.publish(id, status);
        return Result.ok(null);
    }

    //    url: `${api_name}/isNewPerson/${id}/${status}`,
//    method: 'get'
    @ApiOperation(value = "新人专享")
    @GetMapping("isNewPerson/{id}/{status}")
    public Result isNewPerson (@PathVariable Long id, @PathVariable Integer status){
        skuInfoService.isNewPerson(id, status);
        return Result.ok(null);
    }
}

