package com.example.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.CommonUtil.R;
import com.example.CommonUtil.orderInfo.OrderCourseInfo;
import com.example.base.exceptionHandler.ExceptionInfo;
import com.example.eduservice.entity.EduCourse;
import com.example.eduservice.entity.EduTeacher;
import com.example.eduservice.entity.vo.CourseData;
import com.example.eduservice.entity.vo.CourseDetail;
import com.example.eduservice.entity.vo.CourseQuery;
import com.example.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-25
 */
@Api(tags ="课程管理" )
@RestController
@RequestMapping("/eduservice/edu-course")
//@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;
    @ApiOperation("添加课程基本信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseData courseData){
        String id=courseService.saveCourseInfo(courseData);
        return R.ok().data("courseId",id);
    }

    @ApiOperation("根据courseId查询课程")
    @GetMapping("findCourseByCourseId/{courseId}")
    public R findCourseByCourseId(@PathVariable("courseId") String courseId){
        CourseData courseData=courseService.getCourseDataById(courseId);
        return R.ok().data("course",courseData);
    }

    @ApiOperation("更新课程基本信息")
    @PostMapping("updateCourse")
    public R updateCourse(@RequestBody CourseData courseData){
        boolean flag = courseService.updateCourseDataById(courseData);
        return flag? R.ok():R.error();
    }
    @ApiOperation("根据课程id查询详细信息")
    @GetMapping("findCourseDetailByCourseId/{courseId}")
    public R findCourseDetailByCourseId(@PathVariable("courseId") String courseId){
        CourseDetail courseDetail=courseService.findCourseDetailByCourseId(courseId);
        return R.ok().data("courseDetail",courseDetail);
    }

    @ApiOperation("课程发布")
    @PostMapping("saveCourseInfo/{courseId}")
    public R saveCourseInfo(@PathVariable("courseId") String courseId){
        boolean flag= courseService.updateCourseStatus(courseId);
        return flag? R.ok():R.error();
    }
    @ApiOperation("分页查询课程信息")
    @GetMapping("pageCourse/{current}/{size}")
    public R pageCourse(@PathVariable("current")long current,
                        @PathVariable("size")long size){
        Page<EduCourse> page=new Page<EduCourse>(current,size);
        IPage<EduCourse> res=courseService.page(page,null);
        long total=res.getTotal();
        List<EduCourse> list=res.getRecords();
        return R.ok().data("total",total).data("item",list);
    }
    @ApiOperation("分页查询课程信息+模糊查询")
    @PostMapping("pageCourseByQuery/{current}/{size}")
    public R pageCourseByQuery(@PathVariable("current")long current,
                               @PathVariable("size")long size,
                               @RequestBody(required = false) CourseQuery courseQuery){
        QueryWrapper<EduCourse> queryWrapper=courseService.getCondition(courseQuery);
        Page<EduCourse> page=new Page<>(current,size);
        IPage<EduCourse> res = courseService.page(page, queryWrapper);
        long total=res.getTotal();
        List<EduCourse> list=res.getRecords();
        return R.ok().data("total",total).data("item",list);
    }

    @ApiOperation("根据courseId删除课程")
    @DeleteMapping("deleteCourseByCourseId/{courseId}")
    public R deleteCourseByCourseId(@PathVariable("courseId") String courseId){
        boolean flag = courseService.removeCourseInfo(courseId);
        return flag?R.ok():R.error();
    }
    @ApiOperation("远程调用 根据课程id查询详细信息")
    @PostMapping("findCourseInfoByCourseId/{courseId}")
    public OrderCourseInfo findCourseInfoByCourseId(@PathVariable("courseId") String courseId){
        OrderCourseInfo courseInfo=courseService.findCourseInfoByCourseId(courseId);
        return courseInfo;
    }
}

