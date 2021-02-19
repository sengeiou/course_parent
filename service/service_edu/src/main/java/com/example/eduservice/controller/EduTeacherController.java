package com.example.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.CommonUtil.R;
import com.example.eduservice.entity.EduTeacher;
import com.example.eduservice.entity.vo.TeacherQuery;
import com.example.eduservice.service.EduTeacherService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;

/**
 * 教师控制类
 * @author testjava
 * @since 2020-12-21
 */
@Api(tags ="教师管理" )
@RestController
@RequestMapping("/eduservice/edu-teacher")
//@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;
    //1.查询所有教师表的所有数据
    @ApiOperation("查询所有教师信息")
    @GetMapping("findAll")
    public R findAllTeacher(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("item",list);
    }
    //2.逻辑删除教师
    @ApiOperation("根据id(逻辑)删除教师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id" ,value = "教师id",required = true) @PathVariable("id") String id){
        boolean flag=eduTeacherService.removeById(id);
        return flag? R.ok():R.error();
    }
    //3.分页查询教师信息
    @ApiOperation("分页查询教师信息")
    @GetMapping("pageTeacher/{current}/{size}")
    public R pageListTeacher(@PathVariable("current") long current,
                             @PathVariable("size") long size ){
        //创建page对象
        /*
        current:当前页
        size:每页记录数
         */
        Page<EduTeacher> page=new Page<>(current,size);
        IPage<EduTeacher> pages = eduTeacherService.page(page, null);
        long total=pages.getTotal();
        List<EduTeacher> list=pages.getRecords();
        return R.ok().data("total",total).data("item",list);
    }
    //4.条件查询+模糊查询
    @ApiOperation("条件查询+模糊查询")
    @PostMapping("pageTeacherByQuery/{current}/{size}")
    public R pageListTeacherByQuery(@PathVariable("current") long current,
                                    @PathVariable("size") long size,
                                    @RequestBody(required = false)  TeacherQuery teacherQuery){
        //用@RequestBody 要用post提交 required = false:参数值可以为空
        //创建配置对象
        Page<EduTeacher> page=new Page<>(current,size);
        //获得组合查询条件
        QueryWrapper<EduTeacher> query=eduTeacherService.getCondition(teacherQuery);
        IPage<EduTeacher> pages = eduTeacherService.page(page, query);

        long total=pages.getTotal();
        List<EduTeacher> list=pages.getRecords();
        return R.ok().data("total",total).data("items",list);
    }
    //5.添加教师
    @ApiOperation("添加教师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag=eduTeacherService.save(eduTeacher);
        return flag? R.ok():R.error();
    }
    //6.查找需要修改的教师信息
    @ApiOperation("查找需要修改的教师信息")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable("id") String id){
        EduTeacher eduTeacher=eduTeacherService.getById(id);
        return R.ok().data("eduTeacher",eduTeacher);
    }
    //7.修改教师信息
    @ApiOperation("修改教师信息")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        return flag? R.ok():R.error();
    }



}

