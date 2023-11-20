package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.model.product.Attr;
import com.atguigu.ssyx.model.product.AttrGroup;
import com.atguigu.ssyx.product.mapper.AttrGroupMapper;
import com.atguigu.ssyx.product.service.AttrGroupService;
import com.atguigu.ssyx.vo.product.AttrGroupQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jodd.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 属性分组 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-11-03
 */
@Service
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroup> implements AttrGroupService {
    @Override
    public IPage<AttrGroup> selectPage(Page<AttrGroup> pageParam, AttrGroupQueryVo attrGroupQueryVo) {
        String name = attrGroupQueryVo.getName();
        LambdaQueryWrapper<AttrGroup> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtil.isEmpty(name)){
            lambdaQueryWrapper.like(AttrGroup::getName, name);
        }
        IPage<AttrGroup> attrGroupIPage = baseMapper.selectPage(pageParam, lambdaQueryWrapper);
        return attrGroupIPage;
    }

    @Override
    public List<AttrGroup> findAllList() {
        LambdaQueryWrapper<AttrGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(AttrGroup::getSort);
        return this.list(wrapper);
    }
}
