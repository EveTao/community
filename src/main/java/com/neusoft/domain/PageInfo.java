package com.neusoft.domain;
/**
 * Created by Administrator on 2018/12/11.
 */
public class PageInfo {
    int pageIndex;
    int pageSize=2;
    int pageStart;
    int key;

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