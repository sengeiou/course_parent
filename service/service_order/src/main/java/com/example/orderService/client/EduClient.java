package com.example.orderService.client;

import com.example.CommonUtil.orderInfo.OrderCourseInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient("service-edu")
@Component
public interface EduClient {
    @ApiOperation("远程调用 根据课程id查询详细信息")
    @PostMapping("/eduservice/edu-course/findCourseInfoByCourseId/{courseId}")
    public OrderCourseInfo findCourseInfoByCourseId(@PathVariable("courseId") String courseId);
}
