package com.example.msm.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//当启动时，交给spring管理，spring加载后，执行接口的一个方法
//读取msm对应配置文件
@Component
public class ConstantPropertiesUtil implements InitializingBean {
    //key
    @Value("${aliyun.msm.file.keyid}")
    private String keyId;
    //password
    @Value("${aliyun.msm.file.keysecret}")
    private String keySecret;
    @Value("${aliyun.msm.file.regionId}")
    private String regionId;

    //定义对应公开静态类
    public static  String KEY_ID;
    public static  String KEY_SECRET;
    public static  String REGION_ID;
    @Override
    public void afterPropertiesSet() throws Exception {
        KEY_ID=keyId;
        KEY_SECRET=keySecret;
        REGION_ID=regionId;
    }
}
