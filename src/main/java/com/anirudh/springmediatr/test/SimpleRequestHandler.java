package com.anirudh.springmediatr.test;

import com.anirudh.springmediatr.core.mediatr.RequestHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SimpleRequestHandler implements RequestHandler<SimpleRequest,String> {

    @Override
    public String handle(SimpleRequest request) {
        return "Hello "+request.getName();
    }
}
