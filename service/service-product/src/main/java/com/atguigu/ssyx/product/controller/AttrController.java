package com.atguigu.ssyx.product.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.Attr;
import com.atguigu.ssyx.product.service.AttrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品属性 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-11-03
 */
@Api(value = "AttrController", tags = "属性管理")
@RestController
@RequestMapping("/admin/product/attr")
//@CrossOrigin
public class AttrController {
    @Autowired
    private AttrService attrService;

//    url: `${api_name}/${groupId}`,
//    method: 'get'
    @ApiOperation(value = "获取列表")
    @GetMapping("{groupId}")
    public Result index (
            @ApiParam(name = "attrGroupId", value = "分组id", required = true)
            @PathVariable Long groupId){
        List<Attr> byAttrGroupId = attrService.findByAttrGroupId(groupId);
        return Result.ok(byAttrGroupId);
    }
//    url: `${api_name}/get/${id}`,
//    method: 'get'
    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result getById (
            @ApiParam(name = "attrId", value = "属性id", required = true)
            @PathVariable Long id){
        attrService.getById(id);
        return Result.ok(null);
    }

//    url: `${api_name}/save`,
//    method: 'post',
//    data: role
    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save (@RequestBody Attr attr){
        attrService.save(attr);
        return Result.ok(null);
    }

//    url: `${api_name}/update`,
//    method: 'put',
//    data: role
    @ApiOperation(value = "修改")
    @PostMapping("update")
    public Result updateById (@RequestBody Attr attr){
        attrService.updateById(attr);
        return Result.ok(null);
    }

//    url: `${api_name}/remove/${id}`,
//    method: 'delete'
    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove (@PathVariable Long id){
        attrService.removeById(id);
        return Result.ok(null);
    }

//    url: `${api_name}/batchRemove`,
//    method: 'delete',
//    data: idList
    public Result batchRemove (@RequestBody List<Long> idList){
        attrService.removeByIds(idList);
        return Result.ok(null);
    }
}
