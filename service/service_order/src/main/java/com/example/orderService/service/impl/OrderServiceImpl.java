package com.example.orderService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.CommonUtil.orderInfo.OrderCourseInfo;
import com.example.CommonUtil.orderInfo.OrderMemberInfo;
import com.example.orderService.client.EduClient;

import com.example.orderService.client.MemberClient;
import com.example.orderService.entity.Order;
import com.example.orderService.mapper.OrderMapper;
import com.example.orderService.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.orderService.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-03
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private MemberClient memberClient;
    //根据课程id和用户id生成订单
    @Override
    public String createOrder(String courseId, String memberId) {
        //获得用户信息
        OrderMemberInfo memberInfo = memberClient.getInfoByMemberId(memberId);
        System.out.println(memberInfo);
        //或得课程信息
        OrderCourseInfo courseInfo = eduClient.findCourseInfoByCourseId(courseId);
        memberClient.getInfoTest();
        //设置值
        Order order=new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(courseInfo.getTitle());
        order.setCourseCover(courseInfo.getCover());
        order.setTeacherName(courseInfo.getTeacherName());
        order.setTotalFee(courseInfo.getPrice());
        order.setMemberId(memberId);
        order.setMobile(memberInfo.getMobile());
        order.setNickname(memberInfo.getNickname());
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1
        baseMapper.insert(order);
        return order.getOrderNo();
    }

    @Override
    public Order getOrderInfoByOrderNo(String orderNo) {
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("order_no",orderNo);
        Order order = baseMapper.selectOne(queryWrapper);
        return order;
    }
}
