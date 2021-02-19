package com.example.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
//当启动时，交给spring管理，spring加载后，执行接口的一个方法
//读取oss对应配置文件
@Component
public class ConstantPropertiesUtil implements InitializingBean {
    //读取配置文件内容
    //地域节点
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;
    //key
    @Value("${aliyun.oss.file.keyid}")
    private String keyId;
    //password
    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;
    //bucketName
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    //定义对应公开静态类
    public static  String END_POINT;
    public static  String KEY_ID;
    public static  String KEY_SECRET;
    public static  String BUCKET_NAME;
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT=endpoint;
        KEY_ID=keyId;
        KEY_SECRET=keySecret;
        BUCKET_NAME=bucketName;

    }
}
