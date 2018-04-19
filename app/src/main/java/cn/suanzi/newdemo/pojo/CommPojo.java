package cn.suanzi.newdemo.pojo;

import java.io.Serializable;

/**
 * 首页小模块
 * Created by liyanfang on 2016/7/4.
 */
public class CommPojo<T extends CommonResponseResult> extends CommonResponseResult implements Serializable {
    /** 数据*/
    private T data;
    /** api返回数据对应的code*/
    private String code;
    /** code对应的信息*/
    private String message;
    /** 当前服务器的时间*/
    private String timestamp;

    public CommPojo() {
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
