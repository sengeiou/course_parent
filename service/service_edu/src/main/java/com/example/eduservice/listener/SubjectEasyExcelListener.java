package com.example.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.base.exceptionHandler.ExceptionInfo;
import com.example.base.exceptionHandler.GlobalExceptionHandler;
import com.example.eduservice.entity.EduSubject;
import com.example.eduservice.entity.excel.Subject;
import com.example.eduservice.service.EduSubjectService;

public class SubjectEasyExcelListener extends AnalysisEventListener<Subject> {
    //由于SubjectEasyExcelListener不能交给spring管理，不能注入其他对象，需要自己注入
    private EduSubjectService subjectService;

    public SubjectEasyExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(Subject subject, AnalysisContext analysisContext) {
        //判断excel是否为空
        if (subject==null){
            throw new ExceptionInfo(200001,"文件数据为空");
        }
        //行级读取,
        //1.判断一级分类
        EduSubject existOneSubject = this.existSubject(subject.getOneSubjectName(), "0");
        if (existOneSubject==null){
            existOneSubject=new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subject.getOneSubjectName());
            subjectService.save(existOneSubject);
        }
        //1.判断二级分类
        EduSubject existTwoSubject = this.existSubject(subject.getTwoSubjectName(),existOneSubject.getId());
        if (existTwoSubject==null){
            existTwoSubject =new EduSubject();
            existTwoSubject.setParentId(existOneSubject.getId());
            existTwoSubject.setTitle(subject.getTwoSubjectName());
            subjectService.save(existTwoSubject);
        }
    }
    //判断不能重复添加
    private EduSubject existSubject(String name,String level){
        QueryWrapper<EduSubject> query=new QueryWrapper<>();
        query.eq("title",name);
        query.eq("parent_id",level);
        EduSubject res = subjectService.getOne(query);
        return res;
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
