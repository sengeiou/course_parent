package com.example.eduservice.client;

import com.example.CommonUtil.R;
import com.example.eduservice.client.impl.VodClientImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod",fallback = VodClientImpl.class)
@Component
public interface VodClient {

    @ApiOperation("删除视频从阿里云")
    @DeleteMapping("/eduVod/video/deleteVideoFromAl/{videoId}")
    public R deleteVideoFromAl(@PathVariable("videoId") String videoId) ;

    @ApiOperation("删除多个视频从阿里云")
    @DeleteMapping ("/eduVod/video/deleteBatchFromAl")
    public R deleteBatchFromAl(@RequestParam("videoIdList") List<String> videoIdList);
}
