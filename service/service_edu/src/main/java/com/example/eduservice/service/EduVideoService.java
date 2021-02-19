package com.example.eduservice.service;

import com.example.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-25
 */
public interface EduVideoService extends IService<EduVideo> {

    boolean removeByCourseId(String courseId);
}
