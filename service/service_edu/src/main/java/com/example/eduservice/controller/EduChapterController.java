package com.example.eduservice.controller;


import com.example.CommonUtil.R;
import com.example.eduservice.entity.EduChapter;
import com.example.eduservice.entity.chapter.ChapterData;
import com.example.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags ="课程章节管理" )
@RestController
@RequestMapping("/eduservice/edu-chapter")
//@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;
    @ApiOperation("根据课程id获得章节")
    @GetMapping("getAllChapterById/{courseId}")
    public R getAllChapter(@PathVariable("courseId") String courseId){
        List<ChapterData> res=eduChapterService.findAllChapterByCourseId(courseId);
        return R.ok().data("chapters",res);
    }

    @ApiOperation("添加课程章节")
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter chapter){
        boolean flag = eduChapterService.save(chapter);
        return flag? R.ok():R.error();
    }

    @ApiOperation("(跳转)修改课程章节")
    @GetMapping("editChapter/{chapterId}")
    public R updateChapter(@PathVariable("chapterId") String chapterId){
        EduChapter chapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter",chapter);
    }

    @ApiOperation("修改课程章节")
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        boolean flag = eduChapterService.updateById(eduChapter);
        return flag? R.ok():R.error();
    }

    @ApiOperation("删除课程章节")
    @DeleteMapping("deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable("chapterId") String chapterId){
        boolean flag = eduChapterService.deleteChapter(chapterId);
        return flag? R.ok():R.error();
    }
}

