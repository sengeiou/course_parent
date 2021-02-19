package com.example.eduservice.controller;

import com.example.CommonUtil.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Api(tags ="登录管理" )
@RestController
@RequestMapping("/eduservice/user")
//@CrossOrigin
public class EduLoginController {
    //1.用户登录
    @ApiOperation("用户登录")
    @PostMapping ("login")
    public R login(){
        return R.ok().data("token","admin");
    }
    //2.用户信息
    @ApiOperation("获得用户信息")
    @GetMapping("info")
    public R info(){
        HashMap<String,Object> map=new HashMap<>();
        map.put("roles","[admin]");
        map.put("name","admin");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return R.ok().data(map);
    }
}
