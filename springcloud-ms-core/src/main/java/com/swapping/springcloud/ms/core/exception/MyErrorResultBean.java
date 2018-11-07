package com.swapping.springcloud.ms.core.exception;

import com.swapping.springcloud.ms.core.response.UniVerResponse;
import lombok.Getter;
import lombok.Setter;


/**
 * 自定义  异常结果类
 *
 * 用于返回 系统接口调用失败时,返回给接口调用者
 */
@Getter
@Setter
public class MyErrorResultBean extends UniVerResponse {

    public static final String SYS_EX = "系统异常";

    public static final String MY_EX = "自定义异常";

    private String errorType;

    private Object obj;

    public static MyErrorResultBean create(Exception e){
        MyErrorResultBean myErrorResultBean = new MyErrorResultBean();

        myErrorResultBean.setSuccess(false);
        myErrorResultBean.setCode(UniVerResponse.ERROR_SYS_EXCPTION);
        myErrorResultBean.setMsg("大爷，出错拉！");

        if (e instanceof MyException){

            MyException myException = (MyException) e;
            myErrorResultBean.setErrorType(MY_EX);
            myErrorResultBean.setMsg(myException.getMessage());
            myErrorResultBean.setCode(UniVerResponse.ERROR_BUSINESS);
            myErrorResultBean.setObj(myException.getObject());
        }else {
            myErrorResultBean.setErrorType(SYS_EX);
        }

        return myErrorResultBean;
    }
}
