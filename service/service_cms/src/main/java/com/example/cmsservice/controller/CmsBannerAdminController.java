package com.example.cmsservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.CommonUtil.R;
import com.example.cmsservice.entity.CmsBanner;
import com.example.cmsservice.service.CmsBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api("后台banner操作")
@RestController
@RequestMapping("/cmsservice/cms-banner-admin")
@CrossOrigin
public class CmsBannerAdminController {
    @Autowired
    private CmsBannerService cmsBannerService;

    @ApiOperation("分页查询banner")
    @GetMapping("pageBanner/{current}/{size}")
    public R pageBanner(@PathVariable("current") long current,
                        @PathVariable("size") long size){
        Page<CmsBanner> page=new Page<>(current,size);
        IPage<CmsBanner> res = cmsBannerService.page(page, null);
        long total=res.getTotal();
        List<CmsBanner> list=res.getRecords();
        return R.ok().data("banners",list).data("total",total);
    }
    @ApiOperation("banner添加")
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CmsBanner cmsBanner){
        boolean flag = cmsBannerService.save(cmsBanner);
        return flag?R.ok():R.error();
    }
    @ApiOperation("banner修改")
    @PostMapping("updateBanner")
    public R updateBanner(@RequestBody CmsBanner cmsBanner){
        boolean flag = cmsBannerService.updateById(cmsBanner);
        return flag?R.ok():R.error();
    }
    @ApiOperation("banner删除")
    @DeleteMapping("deleteBanner/{bannerId}")
    public R deleteBanner(@PathVariable("bannerId") String bannerId){
        boolean flag = cmsBannerService.removeById(bannerId);
        return flag?R.ok():R.error();
    }
}

