package com.swapping.springcloud.ms.core.exception;

import com.swapping.springcloud.ms.core.response.UniVerResponse;
import org.springframework.web.bind.annotation.*;

/**
 * controller加强器
 *
 */
@ControllerAdvice
public class MyControllerAdvice {

    /**
     * 系统异常 拦截器
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public UniVerResponse<String> errorHandler(Exception ex) {
        ex.printStackTrace();
        return MyErrorResultBean.create(ex);
    }

    /**
     * 自定义异常  拦截器
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MyException.class)
    public MyErrorResultBean myErrorHandler(MyException ex) {
        ex.printStackTrace();
        return MyErrorResultBean.create(ex);
    }
}
