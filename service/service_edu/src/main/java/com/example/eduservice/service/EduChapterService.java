package com.example.eduservice.service;

import com.example.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eduservice.entity.chapter.ChapterData;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-25
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterData> findAllChapterByCourseId(String id);

    boolean deleteChapter(String chapterId);

    boolean removeByCourseId(String courseId);


}
