package com.anirudh.springmediatr.spring;


import com.anirudh.springmediatr.core.mediatr.*;
import com.anirudh.springmediatr.core.notification.Event;
import com.anirudh.springmediatr.core.notification.NotificationHandler;
import com.anirudh.springmediatr.core.registry.MediatorRegistry;
import jakarta.annotation.Nullable;
import jdk.jfr.Experimental;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

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
@RequiredArgsConstructor
class SpringMediator implements Mediator, ApplicationListener<ContextClosedEvent> {

    private final MediatorRegistry registry;

    // Implementation of the 'send' method from the Mediator interface
    @Override
    @SuppressWarnings("unchecked")
    public <P extends Command> void send(P request) {
        var commandHandler = (CommandHandler<P>) registry.getCommandHandler(request.getClass());
        log.info("MediatR Command Dispatcher: Dispatching {} to handler {}", request.getClass().getSimpleName(), commandHandler.getClass().getSimpleName());
        commandHandler.handleCommand(request); // Delegate handling of the request to the appropriate command handler
    }

    @Override
    @SuppressWarnings("unchecked")
    public <Q extends Query<Response>, Response> Response send(Q query) {
        var queryHandler = (QueryHandler<Q, Response>) registry.getQueryHandler(query.getClass());
        log.info("MediatR Query Dispatcher: Dispatching {} to handler {}", query.getClass().getSimpleName(), queryHandler.getClass().getSimpleName());
        return queryHandler.handleQuery(query); //Delegate handling of query to the appropriate query handler
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends Event> void publish(E event) {
        var notificationHandlers = registry.getNotificationHandlers(event.getClass());
        notificationHandlers.forEach(notificationHandler -> {
            var handlers = (NotificationHandler<E>) notificationHandler;
            log.info("MediatR Notification Dispatcher: Emitting event {} to {}",event.getClass().getSimpleName(),handlers.getClass().getSimpleName());
            handlers.handle(event);
        });
    }


    // ApplicationListener method that runs when the application context is closed
    @Override
    public void onApplicationEvent(@Nullable ContextClosedEvent event) {
        registry.clearHandlers(); // Clear handlers during application shutdown
    }
}