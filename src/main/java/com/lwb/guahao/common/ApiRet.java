package com.lwb.guahao.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Function:
 * api调用反馈
 * @autor:Lu Weibiao Date: 2015/3/18
 * Time: 22:50
 */
public class ApiRet<T> {
    public static final int RET_SUCCESS = 1;
    public static final int RET_FAIL = 0;

    private int ret = RET_SUCCESS; //返回码
    private String msg; //返回信息
    private T data; //返回的主数据

    public ApiRet(){};
    public ApiRet(int ret, String msg, T data) {
        this.ret = ret;
        this.msg = msg;
        this.data = data;
    }
    public ApiRet(T data) {
        this.ret = RET_SUCCESS;
        this.msg = null;
        this.data = data;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
