package com.example.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.example.CommonUtil.jwtUtil.RandomUtil;
import com.example.msm.service.MsmService;
import com.example.msm.utils.ConstantPropertiesUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean getMessage(String phone) {
        //生成四位code
        String code= RandomUtil.getFourBitRandom();
        //将code传入map
        Map<String,Object> map=new HashMap<>();
        map.put("code",code);
        //调用阿里云接口
        DefaultProfile profile = DefaultProfile.getProfile(ConstantPropertiesUtil.REGION_ID, ConstantPropertiesUtil.KEY_ID
                        , ConstantPropertiesUtil.KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        //设置参数
        //设置相关固定的参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置发送相关的参数
        request.putQueryParameter("PhoneNumbers",phone); //手机号
        request.putQueryParameter("SignName","我的课程中心在线教育网站"); //申请阿里云 签名名称
        request.putQueryParameter("TemplateCode","SMS_207974625"); //申请阿里云 模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map)); //验证码数据，转换json数据传递

        try {
            //最终发送
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
