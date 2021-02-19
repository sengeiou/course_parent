package com.example.vod.controller;

import com.aliyuncs.exceptions.ClientException;
import com.example.CommonUtil.R;
import com.example.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api("阿里视频操作")
@RestController
@RequestMapping("/eduVod/video")
//@CrossOrigin
public class VodController {
    @Autowired
    private VideoService videoService;
    //1.上传视频到阿里云
    @ApiOperation("上传视频到阿里云")
    @PostMapping("uploadAlVideo")
    public R uploadAlVideo(MultipartFile file){
       String videoId= videoService.uploadFileToAl(file);
       return R.ok().data("videoId",videoId);
    }
    @ApiOperation("删除视频从阿里云")
    @DeleteMapping ("deleteVideoFromAl/{videoId}")
    public R deleteVideoFromAl(@PathVariable("videoId") String videoId)  {
        boolean  flag = videoService.deleteVideoById(videoId);
        return flag? R.ok():R.error();
    }
    @ApiOperation("删除多个视频从阿里云")
    @DeleteMapping ("deleteBatchFromAl")
    public R deleteBatchFromAl(@RequestParam("videoIdList") List<String> videoIdList){
        boolean flag = videoService.deleteMoreVideo(videoIdList);;
        return flag? R.ok():R.error();
    }
    @ApiOperation("获得视频凭证")
    @GetMapping("getPlayAuth/{vid}")
    public R getPlayAuth(@PathVariable("vid") String vid){
        String PlayAuth=videoService.getPlayAuth(vid);
        return R.ok().data("playAuth",PlayAuth);
    }

}
