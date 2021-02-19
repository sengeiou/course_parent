package com.example.eduservice.controller;


import com.example.CommonUtil.R;
import com.example.eduservice.entity.subject.OneSubject;
import com.example.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-24
 */
@Api(tags ="课程分类管理" )
@RestController
@RequestMapping("/eduservice/edu-subject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //1.上传课程分类
    @ApiOperation("通过excel上传课程分类")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        //获得上传excel路径

        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }
    //2.获得课程分类信息
    @ApiOperation("获得课程分类信息")
    @GetMapping("getAllSubject")
    public R getAllSubject(){
       List<OneSubject> res=subjectService.getAllSubject();
       return R.ok().data("subjects",res);
    }
}

