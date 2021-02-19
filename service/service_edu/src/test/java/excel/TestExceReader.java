package excel;

import com.alibaba.excel.EasyExcel;

public class TestExceReader {
    public static void main(String[] args) {
        String filename="D:\\write.xlsx";

        EasyExcel.read(filename,DemoData.class,new ExcelReadListener()).sheet().doRead();
    }
}
