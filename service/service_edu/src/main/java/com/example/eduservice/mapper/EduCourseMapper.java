package com.example.eduservice.mapper;

import com.example.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.eduservice.entity.vo.CourseDetail;
import com.example.eduservice.entity.vo.CourseWebVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-12-25
 */

public interface EduCourseMapper extends BaseMapper<EduCourse> {
    //根据课程id查询课程的详细信息
   public CourseDetail findOneByCourseId(String courseId);
    //根据课程id查询课程的前台基本信息
    CourseWebVo getBaseCourseInfo(String courseId);
}
