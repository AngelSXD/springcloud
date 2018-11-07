package com.swapping.springcloud.ms.core.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页 统一响应体
 * @param <T>
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    private boolean success;

    private String msg;

    private int code;

    private int pageNum;

    private int pageSize;

    private long total;

    private List<T> rows = new ArrayList<T>();

    public  void beTrue(long total,List<T> rows){
        this.success = true;
        this.msg = "successful";
        this.code = UniVerResponse.SUCCESS_REQUEST;
        this.total = total;
        this.rows = rows;
    }

    public  void beTrue(int pageNum,int pageSize,long total,List<T> rows){
        this.success = true;
        this.msg = "successful";
        this.code = UniVerResponse.SUCCESS_REQUEST;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.rows = rows;
    }

    public void  beFalse(String msg,int code){
        this.success = false;
        this.msg = msg;
        this.code = code;
        this.total = 0;
        this.rows = null;
    }
}
