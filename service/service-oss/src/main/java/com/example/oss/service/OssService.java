package com.example.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    //上传头像到oss返回url
    String uploadOssFileByAvatar(MultipartFile file);
}
