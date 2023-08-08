package com.anirudh.springmediatr.core;

import com.anirudh.springmediatr.core.mediatr.Mediator;
import com.anirudh.springmediatr.core.mediatr.Request;
import com.anirudh.springmediatr.core.mediatr.RequestHandler;
import com.anirudh.springmediatr.core.registry.MediatorRegistry;
import jakarta.annotation.Nullable;
import jdk.jfr.Experimental;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link Mediator} interface that serves as a central communication hub
 * for handling requests and managing associated handlers.
 *
 * @author Anirudh Pandita
 * @since 1.0
 */
@Component
@Slf4j
@Experimental
class SpringMediator implements Mediator, ApplicationListener<ContextClosedEvent> {

    private final MediatorRegistry registry;

    // Constructor to initialize SpringMediator
    public SpringMediator(MediatorRegistry registry) {
        this.registry = registry;
        log.info("Initializing Mediatr. Scanning for handlers.");
        registry.configureHandlers(); // Initialize and configure handlers during startup
    }

    // Implementation of the 'send' method from the Mediator interface
    @Override
    @SuppressWarnings("unchecked")
    public <P extends Request<Response>, Response> Response send(P request) {
        RequestHandler<P, Response> handler = (RequestHandler<P, Response>) registry.getHandler(request.getClass());
        return handler.handle(request); // Delegate handling of the request to the appropriate handler
    }

    // ApplicationListener method that runs when the application context is closed
    @Override
    public void onApplicationEvent(@Nullable ContextClosedEvent event) {
        registry.clearHandlers(); // Clear handlers during application shutdown
    }
}