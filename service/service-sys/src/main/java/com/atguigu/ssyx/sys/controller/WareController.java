package com.atguigu.ssyx.sys.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.sys.service.WareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 仓库表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-09-23
 */
@Api(value = "Ware管理", tags = "Ware管理")
@RestController
@RequestMapping(value="/admin/sys/ware")
//@CrossOrigin
public class WareController {
    @Resource
    private WareService wareService;

    @ApiOperation(value = "获取全部仓库")
    @GetMapping("findAllList")
    public Result findAllList() {
        return Result.ok(wareService.list());
    }
}

