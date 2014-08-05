package com.example.listmaker.common.domain;

import java.util.List;

/**
 * Created by david on 7/5/14.
 */
public class ListWrapper<T> {

    private List<T> list;

    public ListWrapper(List<T> list) {
        this.list = list;
    }
    public List<T> getList() {
        return list;
    }

}
