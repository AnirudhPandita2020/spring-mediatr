package com.anirudh.springmediatr.spring.normal.query;

import com.anirudh.springmediatr.core.notification.Event;

public class SimpleNotification implements Event {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
