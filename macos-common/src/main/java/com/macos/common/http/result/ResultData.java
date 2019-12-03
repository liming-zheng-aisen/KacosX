package com.macos.common.http.result;

/**
 * @author zheng.liming
 * @date 2019/8/21
 * @description
 */
public class ResultData<T> {
    private int code;
    private T data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultData(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }
}
