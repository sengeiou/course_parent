package com.example.eduservice.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.CommonUtil.R;
import com.example.CommonUtil.jwtUtil.JwtUtils;
import com.example.eduservice.client.OrderClient;
import com.example.eduservice.entity.EduCourse;
import com.example.eduservice.entity.chapter.ChapterData;
import com.example.eduservice.entity.vo.CourseFrontVo;
import com.example.eduservice.entity.vo.CourseWebVo;
import com.example.eduservice.service.EduChapterService;
import com.example.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
@Api(tags ="课程前台管理" )
@RestController
@RequestMapping("/eduservice/course-front")
//@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;
    //1 条件查询带分页查询课程
    @ApiOperation("条件查询带分页查询课程")
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVo);
        //返回分页所有数据
        return R.ok().data(map);
    }

    //2 课程详情的方法
    @ApiOperation("课程详情的方法")
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request) {
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //根据课程id查询章节和小节
        List<ChapterData> chapterVideoList = chapterService.findAllChapterByCourseId(courseId);
        //获得用户信息
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //远程调用 查询该课程状态
        boolean flag = orderClient.GetStatusByMemberIdAndCourseId(memberId, courseId);
        return R.ok().data("courseWebVo",courseWebVo)
                .data("chapterVideoList",chapterVideoList)
                .data("isBuy",flag);
    }
}












