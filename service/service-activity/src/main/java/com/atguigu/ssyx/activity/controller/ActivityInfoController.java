package com.atguigu.ssyx.activity.controller;


import com.atguigu.ssyx.activity.service.ActivityInfoService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.activity.ActivityInfo;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.activity.ActivityRuleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 活动表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-11-15
 */
@RestController
@RequestMapping("/admin/activity/activityInfo")
public class ActivityInfoController {

    @Autowired
    private ActivityInfoService activityInfoService;

    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index (
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit){
        Page<ActivityInfo> pageParam = new Page<>(page, limit);
        IPage<ActivityInfo> pageModel = activityInfoService.selectPage(pageParam);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取活动")
    @GetMapping("get/{id}")
    public Result get (@PathVariable Long id){
        ActivityInfo activityInfo = activityInfoService.getById(id);
        activityInfo.setActivityTypeString(activityInfo.getActivityType().getComment());
        return Result.ok(activityInfo);
    }

    @ApiOperation(value = "修改活动")
    @PutMapping("update")
    public Result updateById (@RequestBody ActivityInfo activityInfo){
        activityInfoService.updateById(activityInfo);
        return Result.ok(null);
    }


    @ApiOperation(value = "根据id列表删除活动")
    @DeleteMapping("batchRemove")
    public Result removeBatch (@RequestParam("ids") List<Long> ids){
        activityInfoService.removeByIds(ids);
        return Result.ok(null);
    }

    @ApiOperation(value = "新增活动")
    @PostMapping("save")
    public Result save (@RequestBody ActivityInfo activityInfo){
        activityInfoService.save(activityInfo);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除活动")
    @DeleteMapping("remove/{id}")
    public Result remove (@PathVariable Long id){
        activityInfoService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除活动")
    @DeleteMapping("batchRemove")
    public Result batchRemove (@RequestBody List<String> idList){
        activityInfoService.removeByIds(idList);
        return Result.ok(null);
    }

    @ApiOperation(value = "获取活动规则")
    @GetMapping("findActivityRuleList/{id}")
    public Result findActivityRuleList(@PathVariable Long id){
        return Result.ok(activityInfoService.findActivityRuleList(id));
    }

    @ApiOperation(value = "新增活动规则")
    @PostMapping("saveActivityRule")
    public Result saveActivityRule(@RequestBody ActivityRuleVo activityRuleVo){
        activityInfoService.saveActivityRule(activityRuleVo);
        return Result.ok(null);
    }

//    url: `${api_name}/findSkuInfoByKeyword/${keyword}`,
//    method: 'get'
    @ApiOperation(value = "根据关键词查询商品信息")
    @GetMapping("findSkuInfoByKeyword/{keyword}")
    public Result  findSkuInfoByKeyword(@PathVariable String keyword){
        List<SkuInfo> list = activityInfoService.findSkuInfoByKeyword(keyword);
        return Result.ok(list);
    }

}

