/**
 * @Classname RolePermissionServiceImpl
 * @Date 2023/9/20 12:31
 * @Created by Mingkai Feng
 * @Description TODO
 */
package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.acl.mapper.RolePermissionMapper;
import com.atguigu.ssyx.acl.service.RolePermissionService;
import com.atguigu.ssyx.model.acl.RolePermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.security.RunAs;
import java.util.ArrayList;
import java.util.List;

@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    @Autowired
    RolePermissionService rolePermissionService;

    @Override
    public void doAssignByRoleId(Long roleId, Long[] permissionId) {
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId, roleId);
        baseMapper.delete(wrapper);

        List<RolePermission> rolePermissionList = new ArrayList<>();
        for (Long perId: permissionId) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(perId);
            rolePermissionList.add(rolePermission);
        }
        rolePermissionService.saveBatch(rolePermissionList);
    }
}
