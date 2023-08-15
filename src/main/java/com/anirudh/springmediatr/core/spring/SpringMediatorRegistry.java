package com.anirudh.springmediatr.core.spring;


import com.anirudh.springmediatr.core.exception.NoHandlerFoundException;
import com.anirudh.springmediatr.core.exception.NoUniqueHandlerException;
import com.anirudh.springmediatr.core.mediatr.Command;
import com.anirudh.springmediatr.core.mediatr.CommandHandler;
import com.anirudh.springmediatr.core.mediatr.Query;
import com.anirudh.springmediatr.core.mediatr.QueryHandler;
import com.anirudh.springmediatr.core.notification.Event;
import com.anirudh.springmediatr.core.notification.NotificationHandler;
import com.anirudh.springmediatr.core.registry.MediatorRegistry;
import jdk.jfr.Experimental;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Spring component that manages registration, retrieval, and clearing of request handlers.
 *
 * @author Anirudh Pandita
 * @see MediatorRegistry
 * @since 1.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
@Experimental
class SpringMediatorRegistry implements MediatorRegistry {

    private final ApplicationContext context;

    private boolean initialized = false;

    private final Map<Class<? extends Command>, CommandHandler<?>> commandRegistry = new HashMap<>();
    private final Map<Class<? extends Query<?>>, QueryHandler<?, ?>> queryRegistry = new HashMap<>();
    private final Map<Class<? extends Event>, Set<NotificationHandler<? extends Event>>> notificationRegistry = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <C extends Command> CommandHandler<C> getCommandHandler(Class<? extends C> requestClass) throws NoHandlerFoundException {
        if (!initialized) {
            configureHandlers();
        }
        if (!commandRegistry.containsKey(requestClass)) {
            throw new NoHandlerFoundException(requestClass.getCanonicalName());
        }
        return (CommandHandler<C>) commandRegistry.get(requestClass);
    }


    @Override
    @SuppressWarnings("unchecked")
    public <Q extends Query<R>, R> QueryHandler<Q, R> getQueryHandler(Class<? extends Q> queryClass) throws NoHandlerFoundException {
        if (!initialized) {
            configureHandlers();
        }
        if (!queryRegistry.containsKey(queryClass)) {
            throw new NoHandlerFoundException(queryClass.getCanonicalName());
        }
        return (QueryHandler<Q, R>) queryRegistry.get(queryClass);
    }

    @Override
    public <E extends Event> Set<NotificationHandler<? extends Event>> getNotificationHandlers(Class<E> eventClass) {
        if (!initialized) {
            configureHandlers();
        }
        return notificationRegistry.get(eventClass);
    }


    private synchronized void configureHandlers() {
        if (!initialized) {
            log.info("Configuring MediatR lazily: Scanning {} for handlers.", context.getApplicationName());
            loadCommandHandlers();
            loadQueryHandlers();
            loadNotificationHandlers();
            initialized = true;
            log.info("MediatR configured successfully.");
        }
    }

    @SuppressWarnings("unchecked")
    private void loadQueryHandlers() {
        log.info("MediatR Command Handlers: Scanning for handlers");
        var commandHandlers = context.getBeanNamesForType(CommandHandler.class);
        if (commandHandlers.length == 0) {
            log.info("MediatR Command Handlers: Scanned and found 0 handlers");
            return;
        }
        log.info("MediatR Command Handlers: Scanned and found {} handlers.Initializing...", commandHandlers.length);
        Arrays.stream(commandHandlers).forEach(command -> {
            var handler = (CommandHandler<? extends Command>) context.getBean(command);
            var genericsType = GenericTypeResolver.resolveTypeArguments(handler.getClass(), CommandHandler.class);
            if (genericsType != null) {
                var requestType = (Class<? extends Command>) genericsType[0];
                if (commandRegistry.containsKey(requestType)) {
                    throw new NoUniqueHandlerException(requestType.getCanonicalName());
                }
                commandRegistry.put(requestType, handler);
            }
        });
        log.info("MediatR Command Handlers: {} Handlers initialization success.", commandHandlers.length);
    }

    @SuppressWarnings("unchecked")
    private void loadCommandHandlers() throws NoUniqueHandlerException {
        log.info("MediatR Query Handlers: Scanning for handlers");
        var queryHandlers = context.getBeanNamesForType(QueryHandler.class);
        if (queryHandlers.length == 0) {
            log.info("MediatR Query Handlers: Scanned and found 0 handlers");
            return;
        }
        log.info("MediatR Query Handlers: Scanned and found {} handlers.Initializing...", queryHandlers.length);
        Arrays.stream(queryHandlers).forEach(query -> {
            var handler = (QueryHandler<? extends Query<?>, ?>) context.getBean(query);
            var genericsType = GenericTypeResolver.resolveTypeArguments(handler.getClass(), QueryHandler.class);
            if (genericsType != null) {
                var requestType = (Class<? extends Query<?>>) genericsType[0];
                if (queryRegistry.containsKey(requestType)) {
                    throw new NoUniqueHandlerException(requestType.getCanonicalName());
                }
                queryRegistry.put(requestType, handler);
            }
        });
        log.info("MediatR Query Handlers: {} Handlers initialization success.", queryHandlers.length);

    }

    @SuppressWarnings("unchecked")
    private void loadNotificationHandlers() {
        log.info("MediatR Notification Handlers: Scanning for handlers");
        var notificationHandlers = context.getBeanNamesForType(NotificationHandler.class);
        if (notificationHandlers.length == 0) {
            log.info("MediatR Notification Handlers: Scanned and found 0 handlers");
            return;
        }
        log.info("MediatR Notification Handlers: Scanned and found {} handlers. Initializing...", notificationHandlers.length);
        Arrays.stream(notificationHandlers).forEach(notification -> {
            var handler = (NotificationHandler<?>) context.getBean(notification);
            var genericType = GenericTypeResolver.resolveTypeArguments(handler.getClass(), NotificationHandler.class);
            if (genericType != null) {
                var eventType = (Class<? extends Event>) genericType[0];
                if (notificationRegistry.containsKey(eventType)) {
                    notificationRegistry.get(eventType).add(handler);
                } else {
                    notificationRegistry.put(eventType, new HashSet<>() {
                        {
                            add(handler);
                        }
                    });
                }
                log.info("MediatR Notification Handlers: Added {} for event {}", handler.getClass().getSimpleName(), eventType.getSimpleName());
            }
        });

    }

    @Override
    public void clearHandlers() {
        if (initialized) {
            log.info("Initiating MediatR Shutdown: Cleaning Up All Handlers.");
            commandRegistry.clear();
            queryRegistry.clear();
            notificationRegistry.clear();
            log.info("MediatR shutdown successful: Cleared all handlers");
        }
    }
}
