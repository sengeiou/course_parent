package com.example.vod.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    String uploadFileToAl(MultipartFile file);

    boolean deleteVideoById(String videoId) ;

    boolean deleteMoreVideo(List<String> videoIdList);

    String getPlayAuth(String vid);
}
