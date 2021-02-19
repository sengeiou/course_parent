package com.example.eduservice.service;

import com.example.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-24
 */
public interface EduSubjectService extends IService<EduSubject> {
    //上传excel文件，并处理
    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    //获得课程分类结构
    List<OneSubject> getAllSubject();

}
