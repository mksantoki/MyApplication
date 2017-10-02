package com.mk.androidtest.Models;

import java.util.List;

public class Model_VideosList {
    private List<Data> data;

    private Pagination pagination;

    private Meta meta;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "ClassPojo [data = " + data + ", pagination = " + pagination + ", meta = " + meta + "]";
    }
}

	