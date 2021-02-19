package com.example.oss.controller;

import com.example.CommonUtil.R;
import com.example.oss.service.OssService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduOss/fileOss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;
    //1.上传头像
    @ApiOperation("上传头像")
    @PostMapping("uploadOssFile")
    public R uploadOssFile(MultipartFile file){
        //获得上传文件，返回上传oss路径
        String url=ossService.uploadOssFileByAvatar(file);
        return  R.ok().data("url",url);
    }

}
