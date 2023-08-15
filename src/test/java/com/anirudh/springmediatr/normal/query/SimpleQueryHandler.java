package com.anirudh.springmediatr.normal.query;

import com.anirudh.springmediatr.core.mediatr.Mediator;
import com.anirudh.springmediatr.core.mediatr.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class SimpleQueryHandler implements QueryHandler<SimpleQuery, String> {

    private final Mediator mediator;

    public SimpleQueryHandler(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String handleQuery(SimpleQuery query) {
        var ans =  "Hello " + query.getName();
        var event = new SimpleNotification();
        event.setMessage("Test notification");
        mediator.publish(event);
        return ans;
    }
}
