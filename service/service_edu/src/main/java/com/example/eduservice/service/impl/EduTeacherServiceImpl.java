package com.example.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.eduservice.entity.EduTeacher;
import com.example.eduservice.entity.vo.TeacherQuery;
import com.example.eduservice.mapper.EduTeacherMapper;
import com.example.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-21
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public QueryWrapper<EduTeacher> getCondition(TeacherQuery teacherQuery) {
        QueryWrapper<EduTeacher> query=new QueryWrapper<>();
        //组合查询?判断条件是否为空
        if (!StringUtils.isEmpty(teacherQuery.getName())){
            query.eq("name",teacherQuery.getName());
        }else if (!StringUtils.isEmpty(teacherQuery.getLevel())){
            query.eq("level",teacherQuery.getLevel());
        }else if (!StringUtils.isEmpty(teacherQuery.getBegin())){
            query.ge("gmt_create",teacherQuery.getBegin());
        }else if (!StringUtils.isEmpty(teacherQuery.getEnd())){
            query.le("gmt_create",teacherQuery.getEnd());
        }
        //排序
        query.orderByDesc("gmt_create");
        return query;
    }
    //前台教师分页列表
    @Override
    public Map<String, Object> getTeacherPageFrontList(Page<EduTeacher> page) {
        QueryWrapper<EduTeacher> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        baseMapper.selectPage(page, queryWrapper);
        //把分页数据获取出来，放到map集合
        Map<String,Object> map=new HashMap<>();
        map.put("items", page.getRecords());
        map.put("current", page.getCurrent());
        map.put("pages", page.getPages());
        map.put("size", page.getSize());
        map.put("total", page.getTotal());
        map.put("hasNext", page.hasNext());
        map.put("hasPrevious", page.hasPrevious());

        //map返回
        return map;
    }
}
