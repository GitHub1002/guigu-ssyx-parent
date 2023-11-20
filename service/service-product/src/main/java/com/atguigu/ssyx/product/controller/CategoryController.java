package com.atguigu.ssyx.product.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.product.service.CategoryService;
import com.atguigu.ssyx.vo.product.CategoryQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品三级分类 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-11-03
 */
@Api(value = "CategoryController", tags = "商品分类管理")
@RestController
@RequestMapping("/admin/product/category")
@CrossOrigin
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    //    url: `${api_name}/${page}/${limit}`,
    //    method: 'get',
    //    params: searchObj
    @ApiOperation("获取商品分类分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(
            @ApiParam(name = "paeg", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "categoryQueryVo", value = "查询对象", required = false)
            CategoryQueryVo categoryQueryVo){
        Page<Category> pageParam = new Page<>(page, limit);
        IPage<Category> pageModel = categoryService.selectPageCategory(pageParam, categoryQueryVo);
        return Result.ok(pageModel);
    }

//    url: `${api_name}/get/${id}`,
//    method: 'get'
    @ApiOperation("获取商品分类信息")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){
        Category category = categoryService.getById(id);
        return Result.ok(category);
    }

//    url: `${api_name}/save`,
//    method: 'post',
//    data: role
    @ApiOperation("新增商品分类")
    @PostMapping("save")
    public Result save(@RequestBody Category category){
        categoryService.save(category);
        return Result.ok(null);
    }

//    url: `${api_name}/update`,
//    method: 'put',
//    data: role
    @ApiOperation("修改商品分类")
    @PutMapping("update")
    public Result updateById(@RequestBody Category category){
        categoryService.updateById(category);
        return Result.ok(null);
    }

//    url: `${api_name}/remove/${id}`,
//    method: 'delete'
    @ApiOperation("删除商品分类")
    @DeleteMapping("remove/{id}")
    public Result remove (@PathVariable Long id){
        categoryService.removeById(id);
        return Result.ok(null);
    }

//    url: `${api_name}/batchRemove`,
//    method: 'delete',
//    data: idList
    @ApiOperation("根据 id 列表删除商品分类")
    @DeleteMapping("batchRemove")
    public Result batchRemove (@RequestBody List<Long> idList){
        categoryService.removeByIds(idList);
        return Result.ok(null);
    }

//    url: `${api_name}/findAllList`,
//    method: 'get'
    @ApiOperation("获取全部商品分类")
    @GetMapping("findAllList")
    public Result findAllList (){
        categoryService.findAllList();
        return Result.ok(null);
    }
}
