package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.PermissionService;
import com.atguigu.ssyx.acl.service.RolePermissionService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.acl.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/acl/permission")
@Api(tags = "菜单管理")
//@CrossOrigin //跨域
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionService rolePermissionService;

    //查询所有菜单
//    url: `${api_name}`,
//    method: 'get'
    @ApiOperation("查询所有菜单")
    @GetMapping
    public Result list() {
        List<Permission> list = permissionService.queryAllPermission();
        return Result.ok(list);
    }

    //添加菜单
//    url: `${api_name}/save`,
//    method: "post",
//    data: permission
    @ApiOperation("添加菜单")
    @PostMapping("save")
    public Result save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.ok(null);
    }

    //修改菜单
//    url: `${api_name}/update`,
//    method: "put",
//    data: permission
    @ApiOperation("修改菜单")
    @PutMapping("update")
    public Result update(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.ok(null);
    }

    //递归删除菜单
//    url: `${api_name}/remove/${id}`,
//    method: "delete"
    @ApiOperation("递归删除菜单")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        permissionService.removeChildById(id);
        return Result.ok(null);
    }

//    url: `${api_name}/toAssign/${roleId}`,
//    method: 'get'
    @ApiOperation("查看某个角色的权限列表")
    @GetMapping("toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId){
        Map<String, Object> map = new HashMap<>();
        map = permissionService.getPermissionByRoleId(roleId);
        return Result.ok(map.get("allPermissions"));
    }

//    url: `${api_name}/doAssign`,
//    method: "post",
//    params: {roleId, permissionId}
    @ApiOperation("为用户分配菜单")
    @PostMapping
    public Result toAssign (@PathVariable Long roleId,
                            @PathVariable Long[] permissionId){
        rolePermissionService.doAssignByRoleId(roleId, permissionId);
        return Result.ok(null);
    }
}
