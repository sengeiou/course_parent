package com.example.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.eduservice.client.VodClient;
import com.example.eduservice.entity.EduVideo;
import com.example.eduservice.mapper.EduVideoMapper;
import com.example.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-25
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodClient vodClient;
    //删除小节+视频
    @Override
    public boolean removeByCourseId(String courseId) {
        QueryWrapper<EduVideo> queryVideo=new QueryWrapper<>();
        queryVideo.eq("course_id",courseId);
        queryVideo.select("video_source_id");
        //视频id查找删除
        List<EduVideo> eduVideos = this.baseMapper.selectList(queryVideo);
        List<String> videoIdList=new ArrayList<>();
        for (int i = 0; i <eduVideos.size() ; i++) {
            if (!StringUtils.isEmpty(eduVideos.get(i).getVideoSourceId())){
                videoIdList.add(eduVideos.get(i).getVideoSourceId());
            }
        }
        if (!videoIdList.isEmpty()){
            vodClient.deleteBatchFromAl(videoIdList);
        }
        //删除小节信息
        QueryWrapper<EduVideo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        int i = this.baseMapper.delete(queryWrapper);
        return i>0;
    }
}
