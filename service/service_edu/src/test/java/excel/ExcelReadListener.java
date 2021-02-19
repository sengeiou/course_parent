package excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelReadListener extends AnalysisEventListener<DemoData> {
    //一行一行读取
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {

    }
    //读取完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
    //读取表头方法
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {

    }
}
