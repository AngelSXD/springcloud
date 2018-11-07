package com.swapping.springcloud.ms.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常
 */
@Getter
@Setter
public class MyException extends  RuntimeException {

    private int code;

    private Object object;

    public MyException(String msg){
        super(msg);
    }

    public MyException(String msg,int code){
        super(msg);
        this.code = code;
    }

    public MyException(String msg,int code,Object object){
        super(msg);
        this.code = code;
        this.object = object;
    }


}
