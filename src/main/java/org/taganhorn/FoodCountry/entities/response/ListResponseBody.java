package org.taganhorn.FoodCountry.entities.response;

import org.taganhorn.FoodCountry.entities.data.DishEntity;

import java.util.List;

public abstract class ListResponseBody<T> {
    private List<T> list;
    private int page;
    private int totalPage;
    private int limit;
    private long totalItems;

    public ListResponseBody() {
    }

    public ListResponseBody(List<T> list, int page, int totalPage, int limit, long totalItems) {
        this.list = list;
        this.page = page;
        this.totalPage = totalPage;
        this.limit = limit;
        this.totalItems = totalItems;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }
}
