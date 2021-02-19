package com.example.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.CommonUtil.R;
import com.example.eduservice.entity.EduCourse;
import com.example.eduservice.entity.EduTeacher;
import com.example.eduservice.entity.vo.TeacherQuery;
import com.example.eduservice.service.EduCourseService;
import com.example.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags ="教师前台管理" )
@RestController
@RequestMapping("/eduservice/teacher-front")
//@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @ApiOperation("教师前台列表")
    @PostMapping("getTeacherFrontList/{current}/{size}")
    public R getTeacherFrontList(@PathVariable("current") long current,
                                 @PathVariable("size") long size
                                 ){
        Page<EduTeacher> page=new Page<>(current,size);
        Map<String,Object> map=teacherService.getTeacherPageFrontList(page);
        return R.ok().data(map);
    }

    //2 讲师详情的功能
    @ApiOperation("讲师详情")
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId) {
        //1 根据讲师id查询讲师基本信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);
        //2 根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);
        return R.ok().data("teacher",eduTeacher).data("courseList",courseList);
    }
}
