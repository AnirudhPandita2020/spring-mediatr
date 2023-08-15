package com.anirudh.springmediatr.exception.query;

import com.anirudh.springmediatr.core.mediatr.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class SimpleHandler2 implements QueryHandler<SimpleErrorQuery,String> {
    @Override
    public String handleQuery(SimpleErrorQuery query) {
        return null;
    }
}
