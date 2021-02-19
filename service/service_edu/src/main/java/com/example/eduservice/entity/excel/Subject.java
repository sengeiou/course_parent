package com.example.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/*
课程分类
 */
@Data
public class Subject {
    @ExcelProperty(value = "一级分类",index = 0)
    private String oneSubjectName;
    @ExcelProperty(value = "二级分类",index = 1)
    private String twoSubjectName;
}
