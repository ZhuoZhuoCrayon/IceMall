package com.crayon.dto;

import java.util.List;

public class PageBean<T> {
    private Integer currPage;
    private Integer pageSize;
    private Integer totalPage;
    private List<T> itemList;

    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    public void setItemList(List<T> itemList) {
        this.itemList = itemList;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurrPage() {
        return currPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public List<T> getItemList() {
        return itemList;
    }
}
