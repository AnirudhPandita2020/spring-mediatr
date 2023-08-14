package com.anirudh.springmediatr.spring.normal.query;

import com.anirudh.springmediatr.core.mediatr.Query;

public class SimpleQuery implements Query<String> {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
