package com.neusoft.util;

import java.util.List;
import java.util.Map;

public class Respons {
    private Integer status;
    private String msg;
    private String action;
    private String url;
    private Integer count;
    private Data data;
    private List<List<Map<String ,Object>>> dataList;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<List<Map<String, Object>>> getDataList() {
        return dataList;
    }

    public void setDataList(List<List<Map<String, Object>>> dataList) {
        this.dataList = dataList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
