package com.example.eduservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eduservice.entity.vo.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-21
 */
public interface EduTeacherService extends IService<EduTeacher> {
        QueryWrapper<EduTeacher> getCondition(TeacherQuery teacherQuery);

        Map<String, Object> getTeacherPageFrontList(Page<EduTeacher> page);
}
