package com.example.eduservice.controller;


import com.example.CommonUtil.R;
import com.example.base.exceptionHandler.ExceptionInfo;
import com.example.eduservice.client.VodClient;
import com.example.eduservice.entity.EduVideo;
import com.example.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-25
 */
@Api(tags ="小节管理" )
@RestController
@RequestMapping("/eduservice/edu-video")
//@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    //注入vod
    @Autowired
    private VodClient vodClient;
    @ApiOperation("添加小节")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        boolean flag=eduVideoService.save(eduVideo);
        return flag?R.ok():R.error();
    }

    @ApiOperation("删除小节,对应的视频")
    @DeleteMapping("deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable("videoId") String videoId){
        EduVideo video = eduVideoService.getById(videoId);
        String videoSourceId=video.getVideoSourceId();
        //根据视频id通过远程调用vod来删除视频
        if (!StringUtils.isEmpty(videoSourceId)){
            R res = vodClient.deleteVideoFromAl(videoSourceId);//传入视频id。不是小节id
            System.out.println("删除调用。。。。。。。。。。。");
            if (res.getCode()==20001){
                throw new ExceptionInfo(20001,"删除失败，熔断器开启");
            }
        }
        boolean flag = eduVideoService.removeById(videoId);
        return flag?R.ok():R.error();
    }

    @ApiOperation("根据id获得小节信息")
    @GetMapping("getVideoByVideoId/{videoId}")
    public R getVideoByVideoId(@PathVariable("videoId") String videoId){
        EduVideo video = eduVideoService.getById(videoId);
        return R.ok().data("video",video);
    }

    @ApiOperation("修改小节信息")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        boolean flag=eduVideoService.updateById(eduVideo);
        return flag?R.ok():R.error();
    }
}

