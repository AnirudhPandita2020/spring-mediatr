package com.anirudh.springmediatr;


import com.anirudh.springmediatr.core.exception.NoHandlerFoundException;
import com.anirudh.springmediatr.core.mediatr.Mediator;
import com.anirudh.springmediatr.core.registry.MediatorRegistry;
import com.anirudh.springmediatr.spring.normal.SimpleQuery;
import com.anirudh.springmediatr.spring.normal.SimpleQuery2;
import com.anirudh.springmediatr.spring.normal.SpringMediatRTestApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;

@SpringBootTest(classes = SpringMediatRTestApplication.class)
public class SpringMediatRTest {

    @Autowired
    Mediator mediator;

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

}
