package com.anirudh.springmediatr.normal.command;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SampleDatabase {
    private final List<String> names = new ArrayList<>() {{
        add("Anirudh");
    }};

    public void addNewName(String name) {
        names.add(name);
    }

    public int getCount() {
        return names.size();
    }
}
