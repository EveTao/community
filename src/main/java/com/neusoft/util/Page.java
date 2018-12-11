package com.neusoft.util;

public class Page {
    private int page;
    private int maxPage;
    private int n=5;
    private int count=0;
    public Page() {
        // TODO Auto-generated constructor stub
    }
    public Page(int page,int count) {
        this.count=count;
        this.setMaxPage(count);
        this.setPage(page);
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
        if (page<=0) {
            this.page=1;
        }else if (page>this.maxPage) {
            page=this.maxPage;
        }
    }
    public int getMaxPage() {
        return maxPage;
    }
    public void setMaxPage(int maxPage) {
        if(this.count%this.n==0){
            this.maxPage=this.count/this.n;
        }else {
            this.maxPage=this.count/this.n+1;
        }
    }
    public int getN() {
        return n;
    }
    public void setN(int n) {
        this.n = n;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
