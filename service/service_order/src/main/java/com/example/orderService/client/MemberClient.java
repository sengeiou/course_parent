package com.example.orderService.client;

import com.example.CommonUtil.orderInfo.OrderMemberInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-ucenter")
public interface MemberClient {

    //根据用户id获取用户信息
    @PostMapping ("/ucenterservice/ucenter-member/getInfoByMemberId/{memberId}")
    public OrderMemberInfo getInfoByMemberId(@PathVariable("memberId") String memberId);

    @GetMapping("/ucenterservice/ucenter-member/getInfoTest")
    public String  getInfoTest();
}
