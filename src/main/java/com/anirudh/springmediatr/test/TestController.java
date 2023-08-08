package com.anirudh.springmediatr.test;

import com.anirudh.springmediatr.core.mediatr.Mediator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final Mediator mediator;

    @GetMapping
    public String hello() {
        var request = new SimpleRequest();
        request.setName("Anirudh");
        return mediator.send(request);
    }

}
