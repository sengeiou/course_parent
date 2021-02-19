package com.example.orderService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.CommonUtil.R;
import com.example.CommonUtil.jwtUtil.JwtUtils;
import com.example.orderService.entity.Order;
import com.example.orderService.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-03
 */
@Api("订单功能")
@RestController
@RequestMapping("/orderService/order")
//@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;
    @ApiOperation("创建订单")
    @PostMapping("createOrder/{courseId}")
    public R  createOrder(@PathVariable("courseId") String courseId,
                          HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        System.out.println("memberId:"+memberId);
        String orderNo=orderService.createOrder(courseId,memberId);
        return R.ok().data("orderNo",orderNo);
    }
    @ApiOperation("根据订单id查询信息")
    @GetMapping("getOrderInfoByOrderNo/{orderNo}")
    public R getOrderInfoByOrderNo(@PathVariable("orderNo")String orderNo){
        Order orderInfo= orderService.getOrderInfoByOrderNo(orderNo);
        return R.ok().data("order",orderInfo);
    }
    @ApiOperation("远程调用查询 订单状态")
    @GetMapping("GetStatusByMemberIdAndCourseId/{memberId}/{courseId}")
    public boolean  GetStatusByMemberIdAndCourseId(@PathVariable("memberId")String memberId,
                                                   @PathVariable("courseId")String courseId){
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.eq("member_id",memberId);
        queryWrapper.eq("status",1);
        int count = orderService.count(queryWrapper);
        return count > 0;
    }


}

