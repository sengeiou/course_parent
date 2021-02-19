package com.example.cmsservice.controller;


import com.example.CommonUtil.R;
import com.example.cmsservice.entity.CmsBanner;
import com.example.cmsservice.service.CmsBannerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-29
 */
@Api("前台banner操作")
@RestController
@RequestMapping("/cmsservice/cms-banner-front")
@CrossOrigin
public class CmsBannerFrontController {
    @Autowired
    private CmsBannerService cmsBannerService;

    @Cacheable(value = "banner",key = "'selectIndexList'")
    @GetMapping("findAllBanner")
    public R findAllBanner(){
        List<CmsBanner> list=cmsBannerService.findAllBanners();
        return R.ok().data("banners",list);
    }
}

