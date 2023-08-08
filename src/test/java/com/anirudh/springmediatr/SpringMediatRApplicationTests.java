package com.anirudh.springmediatr;

import com.anirudh.springmediatr.core.mediatr.Mediator;
import com.anirudh.springmediatr.test.SimpleRequest;
import com.anirudh.springmediatr.test.SpringMediatRApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = SpringMediatRApplication.class)
@ActiveProfiles("default")
@AutoConfigureMockMvc
class SpringMediatRApplicationTests {

    @Autowired
    Mediator mediator;
    @Test
    public void Test() {
        mediator.send(new SimpleRequest());
    }

}
