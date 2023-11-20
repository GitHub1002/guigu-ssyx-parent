package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.model.product.SkuAttrValue;
import com.atguigu.ssyx.model.product.SkuImage;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.model.product.SkuPoster;
import com.atguigu.ssyx.mq.constant.MqConst;
import com.atguigu.ssyx.mq.service.RabbitService;
import com.atguigu.ssyx.product.mapper.SkuInfoMapper;
import com.atguigu.ssyx.product.service.SkuAttrValueService;
import com.atguigu.ssyx.product.service.SkuImageService;
import com.atguigu.ssyx.product.service.SkuInfoService;
import com.atguigu.ssyx.product.service.SkuPosterService;
import com.atguigu.ssyx.vo.product.SkuInfoQueryVo;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * sku信息 服务实现类
 * </p>
 * @author atguigu
 * @since 2023-11-03
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {
    @Autowired
    private SkuPosterService skuPosterService;

    @Autowired
    private SkuImageService skuImageService;

    @Autowired
    private SkuAttrValueService skuAttrValueService;

    @Autowired
    private RabbitService rabbitService;

    @Override
    public IPage<SkuInfo> selectPage(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo) {
        //  获取条件值
        String skuType = skuInfoQueryVo.getSkuType();
        String keyword = skuInfoQueryVo.getKeyword();
        Long categoryId = skuInfoQueryVo.getCategoryId();
        //  封装条件
        LambdaQueryWrapper<SkuInfo> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)) {
            wrapper.like(SkuInfo::getSkuName, keyword);
        }
        if (!StringUtils.isEmpty(skuType)) {
            wrapper.eq(SkuInfo::getSkuType, skuType);
        }
        if (!StringUtils.isEmpty(categoryId)) {
            wrapper.eq(SkuInfo::getCategoryId, categoryId);
        }
        Page<SkuInfo> skuInfoPage = baseMapper.selectPage(pageParam, wrapper);
        return skuInfoPage;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void saveSkuInfo(SkuInfoVo skuInfoVo) {
        //  保存sku信息
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        this.save(skuInfo);

        //  保存sku海报
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            int sort = 1;
            for (SkuPoster skuPoster : skuPosterList) {
                skuPoster.setSkuId(skuInfo.getId());
                ++sort;
            }
            skuPosterService.saveBatch(skuPosterList);
        }

        //  保存sku图片
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImagesList)) {
            int sort = 1;
            for (SkuImage skuImage : skuImagesList) {
                skuImage.setSkuId(skuInfo.getId());
                skuImage.setSort(sort);
                ++sort;
            }
            skuImageService.saveBatch(skuImagesList);
        }

        //  保存sku平台属性
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            int sort = 1;
            for (SkuAttrValue skuAttrValue :
                    skuAttrValueList) {
                skuAttrValue.setSkuId(skuInfo.getId());
                skuAttrValue.setSort(sort);
                ++sort;
            }
            skuAttrValueService.saveBatch(skuAttrValueList);
        }
    }

    @Override
    public SkuInfoVo getSkuInfo (Long skuId) {
        SkuInfoVo skuInfoVo = new SkuInfoVo();
        // 根据 id 查询 sku 基本信息
        SkuInfo skuInfo = baseMapper.selectById(skuId);
        // 根据 id 查询商品图片列表
        List<SkuImage> skuImageList = skuImageService.getImageListBySkuId(skuId);
        // 根据 id 查询商品海报列表
        List<SkuPoster> skuPosterList = skuPosterService.getPosterListBySkuId(skuId);
        // 根据 id 查询商品属性信息列表
        List<SkuAttrValue> skuAttrValueList = skuAttrValueService.getSkuAttrValueListById(skuId);

        // 封装所有数据，返回
        BeanUtils.copyProperties(skuInfo, skuInfoVo);
        skuInfoVo.setSkuImagesList(skuImageList);
        skuInfoVo.setSkuPosterList(skuPosterList);
        skuInfoVo.setSkuAttrValueList(skuAttrValueList);

        return skuInfoVo;
//        return getSkuInfoDB(skuId);
    }

    private SkuInfoVo getSkuInfoDB (Long skuId){
        SkuInfoVo skuInfoVo = new SkuInfoVo();

//        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        return null;
    }

    @Override
    public void updateSkuInfo(SkuInfoVo skuInfoVo) {
        // 修改 sku 基本信息
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        baseMapper.updateById(skuInfo);
        Long skuId = skuInfoVo.getId();
        // 海报信息
        LambdaQueryWrapper<SkuPoster> wrapperSkuPoster =
                new LambdaQueryWrapper<>();
        wrapperSkuPoster.eq(SkuPoster::getSkuId, skuId);
        skuPosterService.remove(wrapperSkuPoster);

        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!skuPosterList.isEmpty()) {
            for (SkuPoster skuPoster : skuPosterList) {
                skuPoster.setSkuId(skuId);
            }
            skuPosterService.saveBatch(skuPosterList);
        }
        // 商品图片
        skuImageService.remove(new LambdaQueryWrapper<SkuImage>().eq(SkuImage::getSkuId, skuId));

        List<SkuImage> skuImageList = skuInfoVo.getSkuImagesList();
        if (!skuImageList.isEmpty()) {
            for (SkuImage skuImage : skuImageList) {
                skuImage.setSkuId(skuId);
            }
            skuImageService.saveBatch(skuImageList);
        }
        // 商品属性
        skuAttrValueService.remove(new LambdaQueryWrapper<SkuAttrValue>().eq(SkuAttrValue::getAttrId, skuId));

        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!skuAttrValueList.isEmpty()) {
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuId);
            }
            skuAttrValueService.saveBatch(skuAttrValueList);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void check(Long id, Integer status) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(id);
        skuInfo.setCheckStatus(status);
        baseMapper.updateById(skuInfo);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void publish(Long id, Integer status) {
        // 更新发布状态
        if (status == 1) {
            SkuInfo skuInfo = new SkuInfo();
            skuInfo.setId(id);
            skuInfo.setPublishStatus(1);
            baseMapper.updateById(skuInfo);
            // TODO 整合 MQ 把数据同步到 ES 中
            rabbitService.sendMessage(
                    MqConst.EXCHANGE_GOODS_DIRECT,
                    MqConst.ROUTING_GOODS_UPPER,
                    id
            );
        }
        else {
            SkuInfo skuInfo = new SkuInfo();
            skuInfo.setId(id);
            skuInfo.setPublishStatus(0);
            baseMapper.updateById(skuInfo);
            // TODO 整合 MQ 把数据同步到 ES 中
            rabbitService.sendMessage(
                    MqConst.EXCHANGE_GOODS_DIRECT,
                    MqConst.ROUTING_GOODS_LOWER,
                    id
            );
        }
    }

    @Override
    public void isNewPerson(Long id, Integer status) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(id);
        skuInfo.setIsNewPerson(status);
        baseMapper.updateById(skuInfo);
    }

    @Override
    public List<SkuInfo> findSkuInfoList(List<Long> skuIdList) {
        List<SkuInfo> skuInfoList = baseMapper.selectBatchIds(skuIdList);
        return skuInfoList;
    }

    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
        List<SkuInfo> skuInfoList = baseMapper.selectList(
                new LambdaQueryWrapper<SkuInfo>().like(SkuInfo::getSkuName, keyword)
        );
        return skuInfoList;
    }

}
