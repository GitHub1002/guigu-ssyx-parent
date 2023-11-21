package com.atguigu.ssyx.activity.service.impl;

import com.atguigu.ssyx.enums.CouponRangeType;
import com.atguigu.ssyx.model.activity.CouponInfo;
import com.atguigu.ssyx.activity.mapper.CouponInfoMapper;
import com.atguigu.ssyx.activity.service.CouponInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 优惠券信息 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-11-15
 */
@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements CouponInfoService {

    @Override
    public IPage<CouponInfo> selectList(Long page, Long limit) {
        Page<CouponInfo> pageParam = new Page<>(page, limit);
        IPage<CouponInfo> couponInfoPage = baseMapper.selectPage(pageParam, null);
        List<CouponInfo> couponInfoList = couponInfoPage.getRecords();
        couponInfoList.stream().forEach(item -> {
            item.setCouponTypeString(item.getCouponType().getComment());
            CouponRangeType rangeType = item.getRangeType();
            if (rangeType != null) {
                item.setRangeTypeString(rangeType.getComment());
            }
        });
        return couponInfoPage;
    }

    @Override
    public CouponInfo getCouponInfo(Long id) {
        CouponInfo couponInfo = baseMapper.selectById(id);
        couponInfo.setCouponTypeString(couponInfo.getCouponType().getComment());
        if (couponInfo.getRangeTypeString() != null) {
            couponInfo.setRangeTypeString(couponInfo.getRangeType().getComment());
        }
        return couponInfo;
    }

    @Override
    public Map<String, Object> findCouponRuleList(Long id) {
        return null;
    }
}
