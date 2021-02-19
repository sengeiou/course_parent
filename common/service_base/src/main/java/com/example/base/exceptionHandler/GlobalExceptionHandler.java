package com.example.base.exceptionHandler;



import com.example.CommonUtil.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;;
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler  {
    //全局处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行全局异常处理");
    }
    //自定义异常处理
    @ExceptionHandler(ExceptionInfo.class)
    @ResponseBody
    public R customError(ExceptionInfo e){
        e.printStackTrace();
        log.error(e.getMessage());
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
