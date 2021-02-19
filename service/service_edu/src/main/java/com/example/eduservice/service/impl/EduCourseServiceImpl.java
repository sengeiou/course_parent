package com.example.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.CommonUtil.PublishCode;
import com.example.CommonUtil.orderInfo.OrderCourseInfo;
import com.example.base.exceptionHandler.ExceptionInfo;
import com.example.eduservice.entity.EduCourse;
import com.example.eduservice.entity.EduCourseDescription;
import com.example.eduservice.entity.EduSubject;
import com.example.eduservice.entity.vo.*;
import com.example.eduservice.mapper.EduCourseMapper;
import com.example.eduservice.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-25
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService descriptionService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduVideoService eduVideoService;
    /*
    添加课程基本信息
     */
    @Override
    public String saveCourseInfo(CourseData courseData) {
        //1.向课程表添加信息
        EduCourse course=new EduCourse();
        BeanUtils.copyProperties(courseData,course);

        int insert = baseMapper.insert(course);
        if (insert==0){
            throw new ExceptionInfo(20001,"添加课程信息失败!");
        }
        //2.向课程简介表添加信息
        EduCourseDescription description=new EduCourseDescription();
        //获得课程表的id，作为课程简介表的id;
        String id=course.getId();
        description.setId(id);
        description.setDescription(courseData.getDescription());
        descriptionService.save(description);
        return id;
    }
    //根据课程id获得课程信息
    @Override
    public CourseData getCourseDataById(String courseId) {
        //查询课程表
        EduCourse course = this.baseMapper.selectById(courseId);
        //查询描述
        EduCourseDescription description = descriptionService.getById(courseId);
        //封装数据
        CourseData courseData=new CourseData();
        BeanUtils.copyProperties(course,courseData);
        courseData.setDescription(description.getDescription());
        return courseData;
    }

    @Override
    public boolean updateCourseDataById(CourseData courseData) {
        //分解数据
        EduCourse course=new EduCourse();
        BeanUtils.copyProperties(courseData,course);
        EduCourseDescription description=new EduCourseDescription();
        description.setId(courseData.getId());
        description.setDescription(courseData.getDescription());
        //修改Course表
        int flagC = this.baseMapper.updateById(course);
        //修改简介表
        boolean flagD = descriptionService.updateById(description);
        if (flagC==0||!flagD){
            throw  new ExceptionInfo(20001,"修改课程信息失败");
        }
        return true;
    }
    //根据课程id获得课程详细信息
    @Override
    public CourseDetail findCourseDetailByCourseId(String courseId) {
        return  this.baseMapper.findOneByCourseId(courseId);
    }

    @Override
    public boolean updateCourseStatus(String courseId) {
        EduCourse course=new EduCourse();
        course.setId(courseId);
        course.setStatus(PublishCode.PUBLISH_SUCCESS);
        int i = this.baseMapper.updateById(course);
        return i>0;
    }
    //获得组合条件
    @Override
    public QueryWrapper<EduCourse> getCondition(CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper=new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseQuery.getTitle())){
            queryWrapper.eq("title",courseQuery.getTitle());
        }else if (!StringUtils.isEmpty(courseQuery.getStatus())){
            queryWrapper.eq("status",courseQuery.getStatus());
        }
        //排序
        queryWrapper.orderByDesc("gmt_create");
        return queryWrapper;
    }
    //删除课程相关信息
    @Override
    public boolean removeCourseInfo(String courseId) {
         //1.删除对应小节
        boolean resVideo = eduVideoService.removeByCourseId(courseId);
        //2.删除对应章节
        boolean resChapter = chapterService.removeByCourseId(courseId);
        //3.删除对应简介
        boolean resDescription = descriptionService.removeById(courseId);
        //4.删除课程本身
        int resCourse = this.baseMapper.deleteById(courseId);
        return resCourse > 0;
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        //2 根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不为空拼接
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) { //一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) { //二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageCourse,wrapper);

        List<EduCourse> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        boolean hasNext = pageCourse.hasNext();//下一页
        boolean hasPrevious = pageCourse.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }
    //查询前台课程详情
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }

    @Override
    public OrderCourseInfo findCourseInfoByCourseId(String courseId) {
        CourseWebVo baseCourseInfo = baseMapper.getBaseCourseInfo(courseId);
        OrderCourseInfo orderCourseInfo=new OrderCourseInfo();
        BeanUtils.copyProperties(baseCourseInfo,orderCourseInfo);
        return orderCourseInfo;
    }
}
