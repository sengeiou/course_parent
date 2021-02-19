package com.example.oss.service.serviceImpl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.example.oss.service.OssService;
import com.example.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadOssFileByAvatar(MultipartFile file) {
        //通过工具类获得属性
        String endpoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.KEY_SECRET;
        String bucketName=ConstantPropertiesUtil.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //获得文件上传输出流
            InputStream inputStream=file.getInputStream();
            //获得文件原始名
            String filename=file.getOriginalFilename();
            //将文件名添加随机值
            String uuid= UUID.randomUUID().toString().replaceAll("-","");
            filename=uuid+filename;
            //文件按照日期进行分类(通过添加依赖一个工具类joda）
            String datePath = new DateTime().toString("yyyy/MM/dd");
            filename=datePath+"/"+filename;
            //第一个参数:bucketName
            //第二个参数: <yourObjectName>表示上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
            //第三个参数:文件输出流
            ossClient.putObject(bucketName,filename,inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //获得上传后的文件路径
            //edu-1314520.oss-cn-shanghai.aliyuncs.com/QQ%E5%9B%BE%E7%89%8720161003120729.jpg
            String url="https://"+bucketName+"."+endpoint+"/"+filename;
            return url;
        }catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }
}
