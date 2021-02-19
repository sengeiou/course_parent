package com.example.eduservice.client.impl;

import com.example.CommonUtil.R;
import com.example.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodClientImpl implements VodClient {
    //对应方法出错执行
    @Override
    public R deleteVideoFromAl(String videoId) {
        return R.error().data("error","time out");
    }

    @Override
    public R deleteBatchFromAl(List<String> videoIdList) {
        return R.error().data("error","time out");
    }
}
