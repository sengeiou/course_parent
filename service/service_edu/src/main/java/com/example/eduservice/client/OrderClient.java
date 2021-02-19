package com.example.eduservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-order")
public interface OrderClient {
    //远程调用查询 订单状态
    @GetMapping("/orderService/order/GetStatusByMemberIdAndCourseId/{memberId}/{courseId}")
    public boolean  GetStatusByMemberIdAndCourseId(@PathVariable("memberId")String memberId,
                                                   @PathVariable("courseId")String courseId);
}
