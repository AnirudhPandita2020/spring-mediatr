package com.anirudh.springmediatr.test;

import com.anirudh.springmediatr.core.mediatr.Request;
import lombok.Data;

@Data
public class SimpleRequest implements Request<String> {
    private String name;
}
