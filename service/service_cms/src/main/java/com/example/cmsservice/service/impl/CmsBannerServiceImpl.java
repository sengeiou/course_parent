package com.example.cmsservice.service.impl;

import com.example.cmsservice.entity.CmsBanner;
import com.example.cmsservice.mapper.CmsBannerMapper;
import com.example.cmsservice.service.CmsBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-29
 */
@Service
public class CmsBannerServiceImpl extends ServiceImpl<CmsBannerMapper, CmsBanner> implements CmsBannerService {

    @Cacheable(key = "'selectIndex'",value = "banner")
    @Override
    public List<CmsBanner> findAllBanners() {
        List<CmsBanner> list = baseMapper.selectList(null);
        return list;
    }
}
