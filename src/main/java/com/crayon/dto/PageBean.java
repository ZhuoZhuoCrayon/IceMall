package com.crayon.dto;

import java.util.List;

public class PageBean<T> {
    private Integer currentPage;
    private Integer pageSize;
    private Integer totalPage;
    private List<T> itemList;

    /**
     * 根据item数量及当前页索引，初始化PageBean
     * @param itemNum
     * @param pageSize
     * @param currentPage
     * @return 返回页开始索引
     */
    public int initPageBean(int itemNum,int pageSize,int currentPage ){
        this.totalPage = (int) Math.ceil((itemNum*1.0)/pageSize);
        this.currentPage = currentPage;
        this.pageSize = (currentPage == totalPage && itemNum%pageSize != 0)?
                (itemNum%pageSize):pageSize;

        int start = pageSize*(this.currentPage-1);
        return start;
    }
    public void setCurrentPage(Integer currPage) {
        this.currentPage = currPage;
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

    public Integer getCurrentPage() {
        return currentPage;
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
