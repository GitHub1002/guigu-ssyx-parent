/**
 * @Classname RolePermissionService
 * @Date 2023/9/20 12:30
 * @Created by Mingkai Feng
 * @Description TODO
 */
package com.atguigu.ssyx.acl.service;

import com.atguigu.ssyx.model.acl.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

public interface RolePermissionService extends IService<RolePermission> {
    void doAssignByRoleId(Long roleId, Long[] permissionId);
}
