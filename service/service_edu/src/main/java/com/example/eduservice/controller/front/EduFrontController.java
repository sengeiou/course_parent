package com.example.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.CommonUtil.R;
import com.example.eduservice.entity.EduCourse;
import com.example.eduservice.entity.EduTeacher;
import com.example.eduservice.service.EduCourseService;
import com.example.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags ="edu前台管理" )
@RestController
@RequestMapping("/eduservice/edu-front")
//@CrossOrigin
public class EduFrontController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;
    //查询4条名师记录和8条课程记录
    @ApiOperation("查询4条名师记录和8条课程记录")
    @GetMapping("findIndex")
    public R findIndex(){
        //8条课程记录
        QueryWrapper<EduCourse> queryCourse=new QueryWrapper<>();
        queryCourse.orderByDesc("id");
        queryCourse.last("limit 8");
        List<EduCourse> courseList=courseService.list(queryCourse);
        //查询4条名师记录
        QueryWrapper<EduTeacher> queryTeacher=new QueryWrapper<>();
        queryTeacher.orderByDesc("id");
        queryTeacher.last("limit 4");
        List<EduTeacher> teacherList=teacherService.list(queryTeacher);
        return R.ok().data("courses",courseList).data("teachers",teacherList);
    }

}
