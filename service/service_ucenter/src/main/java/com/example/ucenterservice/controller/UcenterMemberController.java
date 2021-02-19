package com.example.ucenterservice.controller;


import com.example.CommonUtil.R;
import com.example.CommonUtil.orderInfo.OrderMemberInfo;
import com.example.ucenterservice.entity.UcenterMember;
import com.example.ucenterservice.entity.vo.RegisterVo;
import com.example.ucenterservice.service.UcenterMemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-30
 */
@RestController
@RequestMapping("/ucenterservice/ucenter-member")
//@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;
    //登录
    @ApiOperation("用户登录")
    @PostMapping("UserLogin")
    public R UserLogin(@RequestBody UcenterMember member){
        String token= memberService.login(member);
        return R.ok().data("token",token);
    }
    //注册
    @ApiOperation("用户注册")
    @PostMapping("UserRegister")
    public R UserRegister(@RequestBody RegisterVo registerVo){
       boolean flag= memberService.register(registerVo);
       return  flag?R.ok():R.error();
    }
    //根据token获取用户信息
    @ApiOperation("根据token用户信息")
    @GetMapping("getInfoByToken")
    public R getInfoByToken(HttpServletRequest request){
        UcenterMember member=memberService.getInfoByToken(request);
        return R.ok().data("user",member);
    }
    //远程调用 根据用户id获得用户信息
    @ApiOperation("远程调用 根据用户id获得用户信息")
    @PostMapping("getInfoByMemberId/{memberId}")
    public OrderMemberInfo getInfoByMemberId(@PathVariable("memberId") String memberId){
        OrderMemberInfo member = memberService.getByMemberId(memberId);
        return member;
    }

    @GetMapping("getInfoTest")
    public void getInfoTest(){
        System.out.println("获得nacos链接，得到接口");
    }
}

