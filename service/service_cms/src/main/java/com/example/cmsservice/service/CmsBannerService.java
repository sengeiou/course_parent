package com.example.cmsservice.service;

import com.example.cmsservice.entity.CmsBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-29
 */
public interface CmsBannerService extends IService<CmsBanner> {

    List<CmsBanner> findAllBanners();
}
