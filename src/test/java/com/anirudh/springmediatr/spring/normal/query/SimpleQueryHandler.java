package com.anirudh.springmediatr.spring.normal.query;

import com.anirudh.springmediatr.core.mediatr.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class SimpleQueryHandler implements QueryHandler<SimpleQuery, String> {
    @Override
    public String handleQuery(SimpleQuery query) {
        return "Hello " + query.getName();
    }
}
