package com.atguigu.ssyx.activity.service.impl;

import com.atguigu.ssyx.activity.mapper.CouponRangeMapper;
import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.enums.CouponRangeType;
import com.atguigu.ssyx.model.activity.CouponInfo;
import com.atguigu.ssyx.activity.mapper.CouponInfoMapper;
import com.atguigu.ssyx.activity.service.CouponInfoService;
import com.atguigu.ssyx.model.activity.CouponRange;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.activity.CouponRuleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private CouponRangeMapper couponRangeMapper;

    @Autowired
    private CouponInfoMapper couponInfoMapper;

    @Autowired
    private ProductFeignClient productFeignClient;

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
        // 第一步 根据优惠券 id 查询优惠券基本信息
        CouponInfo couponInfo = baseMapper.selectById(id);
        // 第二步 根据优惠券 id 查询 coupon_range 查询里面对应 range_id
        List<CouponRange> couponRangeList = couponRangeMapper.selectList(
                new LambdaQueryWrapper<CouponRange>().eq(CouponRange::getCouponId, id)
        );
        // couponRangeList 获取所有 range_id
        List<Long> randIdList =
                couponRangeList.stream().map(CouponRange::getRangeId).collect(Collectors.toList());
        Map<String, Object> result = new HashMap<>();
        if (!CollectionUtils.isEmpty(randIdList)) {
            // 如果规则类型是 SKU,得到 skuId, 远程调用根据多个 skuId 值获取对应 sku 信息
            if (couponInfo.getRangeType() == CouponRangeType.SKU) {
                List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoList(randIdList);
                result.put("skuInfoList", skuInfoList);
            }
            // 如果规则类型是分类，得到分类 id， 远程调用根据多个分类 Id 值获取对应分类信息
            else if(couponInfo.getRangeType() == CouponRangeType.CATEGORY){
                List<Category> categoryInfoList = productFeignClient.findCategoryList(randIdList);
                result.put("categoryInfoList", categoryInfoList);
            }
            // 如果规则类型是品牌，得到品牌 id， 远程调用根据多个品牌 Id 值获取对应品牌信息
//            else if(couponInfo.getRangeType() == CouponRangeType.BRAND){
//                List<BrandInfo> brandInfoList = productFeignClient.findBrandInfoList(randIdList);
//            }
        }
        return result;
    }

    @Override
    public void saveCouponRule(CouponRuleVo couponRuleVo) {
        /*
        优惠券couponInfo 与 couponRange 要一起操作：先删除couponRange ，更新couponInfo ，再新增couponRange ！
         */
        QueryWrapper<CouponRange> couponRangeQueryWrapper = new QueryWrapper<>();
        couponRangeQueryWrapper.eq("coupon_id",couponRuleVo.getCouponId());
        couponRangeMapper.delete(couponRangeQueryWrapper);

        //  更新数据
        CouponInfo couponInfo = this.getById(couponRuleVo.getCouponId());
        // couponInfo.setCouponType();
        couponInfo.setRangeType(couponRuleVo.getRangeType());
        couponInfo.setConditionAmount(couponRuleVo.getConditionAmount());
        couponInfo.setAmount(couponRuleVo.getAmount());
        couponInfo.setConditionAmount(couponRuleVo.getConditionAmount());
        couponInfo.setRangeDesc(couponRuleVo.getRangeDesc());

        couponInfoMapper.updateById(couponInfo);

        //  插入优惠券的规则 couponRangeList
        List<CouponRange> couponRangeList = couponRuleVo.getCouponRangeList();
        for (CouponRange couponRange : couponRangeList) {
            couponRange.setCouponId(couponRuleVo.getCouponId());
            //  插入数据
            couponRangeMapper.insert(couponRange);
        }
    }
}
