package com.anirudh.springmediatr.spring;


import com.anirudh.springmediatr.core.exception.NoHandlerFoundException;
import com.anirudh.springmediatr.core.exception.NoUniqueHandlerException;
import com.anirudh.springmediatr.core.mediatr.Command;
import com.anirudh.springmediatr.core.mediatr.CommandHandler;
import com.anirudh.springmediatr.core.mediatr.Query;
import com.anirudh.springmediatr.core.mediatr.QueryHandler;
import com.anirudh.springmediatr.core.registry.MediatorRegistry;
import jdk.jfr.Experimental;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    private synchronized void configureHandlers() {
        if (!initialized) {
            log.info("Configuring MediatR lazily: Scanning {} for handlers.", context.getApplicationName());
            loadCommandHandlers();
            loadQueryHandlers();
            initialized = true;
            log.info("MediatR configured successfully.");
        }
    }

    @SuppressWarnings("unchecked")
    private void loadQueryHandlers() {
        log.info("MediatR Command Handlers: Scanning for handlers");
        var commandHandlers = context.getBeanNamesForType(CommandHandler.class);
        if (commandHandlers.length > 0) {
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
            return;
        }
        log.info("MediatR Command Handlers: Scanned and found 0 handlers");
    }

    @SuppressWarnings("unchecked")
    private void loadCommandHandlers() throws NoUniqueHandlerException {
        log.info("MediatR Query Handlers: Scanning for handlers");
        var queryHandlers = context.getBeanNamesForType(QueryHandler.class);
        if (queryHandlers.length > 0) {
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
            return;
        }
        log.info("MediatR Query Handlers: Scanned and found 0 handlers");
    }

    @Override
    public void clearHandlers() {
        if (initialized) {
            log.info("Initiating MediatR Shutdown: Cleaning Up All Handlers.");
            commandRegistry.clear();
            queryRegistry.clear();
            log.info("MediatR shutdown successful: Cleared all handlers");
        }
    }
}
