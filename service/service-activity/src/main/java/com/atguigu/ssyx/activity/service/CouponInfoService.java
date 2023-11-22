package com.atguigu.ssyx.activity.service;

import com.atguigu.ssyx.model.activity.CouponInfo;
import com.atguigu.ssyx.vo.activity.CouponRuleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 优惠券信息 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-11-15
 */
public interface CouponInfoService extends IService<CouponInfo> {

    IPage<CouponInfo> selectList(Long page, Long limit);

    CouponInfo getCouponInfo(Long id);

    Map<String, Object> findCouponRuleList(Long id);

    void saveCouponRule(CouponRuleVo couponRuleVo);
}
