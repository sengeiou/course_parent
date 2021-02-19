package excel;

import com.alibaba.excel.EasyExcel;
import com.example.eduservice.entity.EduSubject;
import com.example.eduservice.entity.excel.Subject;
import com.example.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TestExcelWriter {
    @Autowired
    private EduSubjectService subjectService;


    public static void main(String[] args) {
        //实现excel写操作
        //1.设置写入文件地址和excel文件名称
        String filename="D:\\write.xlsx";
        //2.调用excel方法(参数:1.路径2.对于实体类)
        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getData());
    }
    private static List<DemoData> getData(){
        List<DemoData> list=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            DemoData data=new DemoData();
            data.setSno(String.valueOf(i));
            data.setSname("lisa"+i);
            list.add(data);
        }
        return list;
    }

}
