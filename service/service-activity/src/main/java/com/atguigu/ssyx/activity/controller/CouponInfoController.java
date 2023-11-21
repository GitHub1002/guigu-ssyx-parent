package com.atguigu.ssyx.activity.controller;


import com.atguigu.ssyx.activity.service.CouponInfoService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.activity.CouponInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 优惠券信息 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-11-15
 */
@RestController
@RequestMapping("admin/activity/couponInfo")
@CrossOrigin
public class CouponInfoController {

    @Autowired
    private CouponInfoService couponInfoService;

//    url: `${api_name}/${page}/${limit}`,
//    method: 'get'
    public Result list (
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit
        ){
        IPage<CouponInfo> pageModel = couponInfoService.selectList(page, limit);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "新增优惠券")
    @PostMapping("save")
    public Result save(@RequestBody CouponInfo couponInfo){
        couponInfoService.save(couponInfo);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改优惠券")
    @PutMapping("update")
    public Result updateById(@RequestBody CouponInfo couponInfo){
        couponInfoService.updateById(couponInfo);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除优惠券")
    @DeleteMapping("remove/{id}")
    public Result remove (@PathVariable String id){
        couponInfoService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除优惠券")
    @DeleteMapping("batchRemove")
    public Result batchRemove (@RequestBody List<String> idList){
        couponInfoService.removeByIds(idList);
        return Result.ok(null);
    }

    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){
        CouponInfo couponInfo = couponInfoService.getCouponInfo(id);
        return Result.ok(couponInfo);
    }

//    url: `${api_name}/findCouponRuleList/${id}`,
//    method: 'get'
    @GetMapping("findCouponRuleList/{id}")
    public Result findCouponRuleList(@PathVariable Long id){
        return Result.ok(couponInfoService.findCouponRuleList(id));
    }

}

