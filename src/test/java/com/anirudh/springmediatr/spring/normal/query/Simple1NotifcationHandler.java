package com.anirudh.springmediatr.spring.normal.query;

import com.anirudh.springmediatr.core.notification.NotificationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Simple1NotifcationHandler implements NotificationHandler<SimpleNotification> {
    private static final Logger log = LoggerFactory.getLogger(Simple2NotificationHandler.class);
    @Override
    public void handle(SimpleNotification event) {
        log.info("Simple 1 notification called: {}",event.getMessage());
    }
}
