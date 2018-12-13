package com.neusoft.domain;
/**
 * Created by Administrator on 2018/12/11.
 */
public class PageInfo {
    int pageIndex;
    int pageSize;
    int pageStart;
    int key;
    int type;
    int category;

    public int getCategor() {
        return category;
    }

    public void setCategor(int category) {
        this.category = category;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getPageStart() {
        return pageStart;
    }
    public int getPageIndex() {
        return pageIndex;
    }
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
        this.pageStart = this.pageSize * (this.pageIndex - 1);
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        this.pageStart = this.pageSize * (this.pageIndex - 1);
    }
}