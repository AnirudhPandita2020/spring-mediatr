package com.anirudh.springmediatr;

import com.anirudh.springmediatr.core.exception.NoUniqueHandlerException;
import com.anirudh.springmediatr.core.mediatr.Mediator;
import com.anirudh.springmediatr.exception.query.SimpleErrorQuery;
import com.anirudh.springmediatr.exception.SpringMediatRExceptionApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = SpringMediatRExceptionApplication.class)
public class SpringMediatRExceptionTest {

    @Autowired
    Mediator mediator;

    @Test
    public void test_duplicate_handler_exception() {
        var simpleQuery = new SimpleErrorQuery();
        var exception = assertThrows(NoUniqueHandlerException.class, () -> mediator.send(simpleQuery));
        assertEquals(exception.getMessage(), "No unique handler found for request class: " + simpleQuery.getClass().getCanonicalName() + ".Please remove or specify which handler to invoke with @Primary");
    }
}
