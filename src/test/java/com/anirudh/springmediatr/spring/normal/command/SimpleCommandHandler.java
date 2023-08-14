package com.anirudh.springmediatr.spring.normal.command;

import com.anirudh.springmediatr.core.mediatr.CommandHandler;
import org.springframework.stereotype.Component;

@Component
public class SimpleCommandHandler implements CommandHandler<SimpleCommand> {

    private final SampleDatabase database;

    public SimpleCommandHandler(SampleDatabase database) {
        this.database = database;
    }

    @Override
    public void handleCommand(SimpleCommand request) {
        database.addNewName(request.getName());
    }
}
