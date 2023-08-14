package com.anirudh.springmediatr;


import com.anirudh.springmediatr.core.exception.NoHandlerFoundException;
import com.anirudh.springmediatr.core.mediatr.Mediator;
import com.anirudh.springmediatr.spring.normal.command.SampleDatabase;
import com.anirudh.springmediatr.spring.normal.command.SimpleCommand;
import com.anirudh.springmediatr.spring.normal.command.SimpleCommand2;
import com.anirudh.springmediatr.spring.normal.query.SimpleQuery;
import com.anirudh.springmediatr.spring.normal.query.SimpleQuery2;
import com.anirudh.springmediatr.spring.normal.SpringMediatRTestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = SpringMediatRTestApplication.class)
public class SpringMediatRTest {

    @Autowired
    Mediator mediator;

    @Autowired
    SampleDatabase database;

    @Test
    public void test_simple_query() {
        var simpleQuery = new SimpleQuery();
        simpleQuery.setName("Anirudh");
        assert mediator.send(simpleQuery).equals("Hello Anirudh");
    }

    @Test
    public void test_simple_query_throws_no_handler_exception() {
        var simpleQuery = new SimpleQuery2();
        var exception = assertThrows(NoHandlerFoundException.class, () -> mediator.send(simpleQuery));
        assertEquals(exception.getMessage(), "No handler found for request class: " + simpleQuery.getClass().getCanonicalName());
    }

    @Test
    public void test_simple_command() {
        var simpleCommand = new SimpleCommand();
        simpleCommand.setName("Anuj");
        var previousCount = database.getCount();
        mediator.send(simpleCommand);
        assertEquals(database.getCount(), previousCount + 1);
    }

    @Test
    public void test_simple_command_throws_no_handler_exception() {
        var simpleCommand = new SimpleCommand2();
        var exception = assertThrows(NoHandlerFoundException.class, () -> mediator.send(simpleCommand));
        assertEquals(exception.getMessage(), "No handler found for request class: " + simpleCommand.getClass().getCanonicalName());
    }

}
