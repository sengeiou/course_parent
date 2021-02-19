package com.example.msm.controller;

import com.example.CommonUtil.R;
import com.example.msm.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/msmService/msm")
//@CrossOrigin
public class msmController {

    @Autowired
    private MsmService msmService;


    @GetMapping("GetAlMessage/{phone}")
    public R  GetAlMessage(@PathVariable("phone") String phone){
        boolean flag=msmService.getMessage(phone);
        return flag? R.ok():R.error();
    }

}
