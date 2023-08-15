package com.anirudh.springmediatr.normal.command;

import com.anirudh.springmediatr.core.mediatr.Command;

public class SimpleCommand implements Command {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
