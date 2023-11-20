package com.atguigu.ssyx.acl.mapper;

import com.atguigu.ssyx.model.acl.AdminRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRoleMapper extends BaseMapper<AdminRole> {
}
