package com.example.eduservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.CommonUtil.orderInfo.OrderCourseInfo;
import com.example.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eduservice.entity.vo.*;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-25
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseData courseData);

    CourseData getCourseDataById(String courseId);

    boolean updateCourseDataById(CourseData courseData);

    CourseDetail findCourseDetailByCourseId(String courseId);

    boolean updateCourseStatus(String courseId);

    QueryWrapper<EduCourse> getCondition(CourseQuery courseQuery);

    boolean removeCourseInfo(String courseId);

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);

    OrderCourseInfo findCourseInfoByCourseId(String courseId);
}
