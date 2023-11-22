package com.atguigu.ssyx.activity.mapper;

import com.atguigu.ssyx.model.activity.CouponInfo;
import com.atguigu.ssyx.model.activity.CouponRange;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 优惠券信息 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2023-11-15
 */
@Repository
public interface CouponInfoMapper extends BaseMapper<CouponInfo> {

}
