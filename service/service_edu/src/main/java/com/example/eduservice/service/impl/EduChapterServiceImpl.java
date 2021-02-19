package com.example.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.base.exceptionHandler.ExceptionInfo;
import com.example.eduservice.entity.EduChapter;
import com.example.eduservice.entity.EduVideo;
import com.example.eduservice.entity.chapter.ChapterData;
import com.example.eduservice.entity.chapter.VideoData;
import com.example.eduservice.mapper.EduChapterMapper;
import com.example.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-25
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterData> findAllChapterByCourseId(String id) {
        //EduChapter表查询条件
        QueryWrapper<EduChapter> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",id);
        List<EduChapter> list = this.baseMapper.selectList(queryWrapper);
        //创建ChapterData集合
        List<ChapterData> res=new ArrayList<>();

        for (int i = 0; i <list.size() ; i++) {
            //获得章节赋值给ChapterData
            ChapterData chapterData=new ChapterData();
            BeanUtils.copyProperties(list.get(i),chapterData);
            String cid=list.get(i).getId();
            System.out.println("cid:"+cid);
            //根据章节id获得对应小结条件
            QueryWrapper<EduVideo> query=new QueryWrapper<>();
            //获得对应小结赋值给List<VideoData>
            query.eq("chapter_id",cid);
            List<EduVideo> VideoList= eduVideoService.list(query);

            System.out.println(VideoList.size());
            List<VideoData> videoRes=new ArrayList<>();
            for (int j = 0; j <VideoList.size() ; j++) {
                VideoData data=new VideoData();
                BeanUtils.copyProperties(VideoList.get(j),data);
                videoRes.add(data);
            }
            //将List<VideoData>传入chapterData
            chapterData.setChildren(videoRes);
            //将chapterData对象添加到res集合中
            res.add(chapterData);
        }

        return res;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("chapter_id",chapterId);
        List<EduVideo> list = eduVideoService.list(queryWrapper);
        if (!list.isEmpty()){
            throw new ExceptionInfo(20001,"不能删除");
        }else {
            int flag = this.baseMapper.deleteById(chapterId);
            return flag>0;
        }

    }

    @Override
    public boolean removeByCourseId(String courseId) {
        QueryWrapper<EduChapter> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        int i = this.baseMapper.delete(queryWrapper);
        return i>0;
    }


}
