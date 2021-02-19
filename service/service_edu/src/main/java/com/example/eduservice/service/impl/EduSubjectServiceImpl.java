package com.example.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.eduservice.entity.EduSubject;
import com.example.eduservice.entity.excel.Subject;
import com.example.eduservice.entity.subject.OneSubject;
import com.example.eduservice.entity.subject.TwoSubject;
import com.example.eduservice.listener.SubjectEasyExcelListener;
import com.example.eduservice.mapper.EduSubjectMapper;
import com.example.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-24
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        //获得文件路径
        try {
            //获得文件输出流
            InputStream inputStream=file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(inputStream, Subject.class,new SubjectEasyExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<OneSubject> getAllSubject() {
        //查询一级分类
        QueryWrapper<EduSubject> queryOne=new QueryWrapper<>();
        queryOne.eq("parent_id","0");
        List<EduSubject> listOne = baseMapper.selectList(queryOne);
        //查询二级分类
        QueryWrapper<EduSubject> queryTwo=new QueryWrapper<>();
        queryTwo.ne("parent_id","0");
        List<EduSubject> listTwo = baseMapper.selectList(queryTwo);
        //封装数据
        List<OneSubject> findOneList=new ArrayList<>();

        for (int i = 0; i <listOne.size() ; i++) {
             List<TwoSubject> findTwoList=new ArrayList<>();
             OneSubject oneSubject=new OneSubject();
             BeanUtils.copyProperties(listOne.get(i),oneSubject);
             for (int j = 0; j <listTwo.size() ; j++) {
                TwoSubject twoSubject=new TwoSubject();
                if (listOne.get(i).getId().equals(listTwo.get(j).getParentId())){
                    BeanUtils.copyProperties(listTwo.get(j),twoSubject);
                    findTwoList.add(twoSubject);
                }
             }
             oneSubject.setTwoSubjectList(findTwoList);
             findOneList.add(oneSubject);
        }

        return findOneList;
    }
}
