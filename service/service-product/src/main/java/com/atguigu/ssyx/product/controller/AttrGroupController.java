package com.atguigu.ssyx.product.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.AttrGroup;
import com.atguigu.ssyx.product.service.AttrGroupService;
import com.atguigu.ssyx.vo.product.AttrGroupQueryVo;
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
 * 属性分组 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-11-03
 */
@Api(value = "AttrGroup管理", tags = "平台属性分组管理")
@RestController
@RequestMapping("/admin/product/attrGroup")
@CrossOrigin
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

//    url: `${api_name}/${page}/${limit}`,
//    method: 'get',
//    params: searchObj
    @ApiOperation("获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index (
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "attrGroupQueryVo", value = "查询对象", required = false)
            AttrGroupQueryVo attrGroupQueryVo){
        Page<AttrGroup> pageParam = new Page<>(page, limit);
        IPage<AttrGroup> pageModel = attrGroupService.selectPage(pageParam, attrGroupQueryVo);
        return Result.ok(pageModel);
    }

//    url: `${api_name}/get/${id}`,
//    method: 'get'
    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(
            @ApiParam(name = "id", value = "商品id")
            @PathVariable Long id){
        AttrGroup attrGroup = attrGroupService.getById(id);
        return Result.ok(attrGroup);
    }

//    url: `${api_name}/save`,
//    method: 'post',
//    data: role
    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save (@RequestBody AttrGroup attrGroup){
        attrGroupService.save(attrGroup);
        return Result.ok(null);
    }

//    url: `${api_name}/update`,
//    method: 'put',
//    data: role
    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById (@RequestBody AttrGroup attrGroup){
        attrGroupService.updateById(attrGroup);
        return Result.ok(null);
    }

//    url: `${api_name}/remove/${id}`,
//    method: 'delete'
    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove (@PathVariable Long id){
        attrGroupService.removeById(id);
        return Result.ok(null);
    }

//    url: `${api_name}/batchRemove`,
//    method: 'delete',
//    data: idList
    @ApiOperation(value = "根据 id 列表批量删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove (@RequestBody List<Long> idList){
        attrGroupService.removeByIds(idList);
        return Result.ok(null);
    }

//    url: `${api_name}/findAllList`,
//    method: 'get'
    @ApiOperation(value = "获取全部属性分组")
    @GetMapping("findAllList")
    public Result findAllList (){
        List<AttrGroup> allList = attrGroupService.findAllList();
        return Result.ok(allList);
    }
}

