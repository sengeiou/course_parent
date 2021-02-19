package com.example.vod.service.serviceImpl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import com.example.base.exceptionHandler.ExceptionInfo;
import com.example.vod.service.VideoService;
import com.example.vod.utils.ConstantPropertiesUtil;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

import static com.example.vod.utils.initObject.initVodClient;

@Service
public class VideoServiceImpl implements VideoService {

    @Override
    public String uploadFileToAl(MultipartFile file) {
        try {
            InputStream inputStream=file.getInputStream();
            String fileName=file.getOriginalFilename();
            String title=fileName.substring(0,fileName.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.KEY_ID
                    ,ConstantPropertiesUtil.KEY_SECRET, title, fileName,inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response= uploader.uploadStream(request);
            String videoId=null;
            if (response.isSuccess()) {
                videoId= response.getVideoId();
            }
            return videoId;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteVideoById(String videoId)  {
        try {
            DefaultAcsClient client = initVodClient(ConstantPropertiesUtil.KEY_ID
                    ,ConstantPropertiesUtil.KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            client.getAcsResponse(request);
            return true;
        } catch (Exception e) {
            throw new ExceptionInfo(20001,"删除视频失败!");
        }
    }
    //删除多个视频
    @Override
    public boolean deleteMoreVideo(List<String> videoIdList) {
        try {
            DefaultAcsClient client = initVodClient(ConstantPropertiesUtil.KEY_ID
                    ,ConstantPropertiesUtil.KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            //传入多个视频
            request.setVideoIds(StringUtil.join(videoIdList.toArray(),","));
            client.getAcsResponse(request);
            return true;
        } catch (Exception e) {
            throw new ExceptionInfo(20001,"删除视频失败!");
        }
    }

    @Override
    public String getPlayAuth(String vid) {
        try {
            //1.创建对象
            DefaultAcsClient client = initVodClient(ConstantPropertiesUtil.KEY_ID
                    ,ConstantPropertiesUtil.KEY_SECRET);
            //2.创建获取凭证的request和response
            GetVideoPlayAuthRequest request=new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response=new GetVideoPlayAuthResponse();
            //3.向request对象传入id
            request.setVideoId(vid);
            //4.调用初始化对象方法，获得凭证
            response=client.getAcsResponse(request);
            return response.getPlayAuth();

        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }
}
