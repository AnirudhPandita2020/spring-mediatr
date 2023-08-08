package com.anirudh.springmediatr.core;

import com.anirudh.springmediatr.core.exception.NoHandlerFoundException;
import com.anirudh.springmediatr.core.exception.NoUniqueHandlerException;
import com.anirudh.springmediatr.core.mediatr.Request;
import com.anirudh.springmediatr.core.mediatr.RequestHandler;
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

    private final Map<Class<? extends Request<?>>, RequestHandler<?, ?>> handlerRegistry = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <C extends Request<R>, R> RequestHandler<C, R> getHandler(Class<? extends C> requestClass) throws NoHandlerFoundException{
        if (!handlerRegistry.containsKey(requestClass)) {
            throw new NoHandlerFoundException(requestClass.getCanonicalName());
        }
        return (RequestHandler<C, R>) handlerRegistry.get(requestClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void configureHandlers() throws NoHandlerFoundException, NoUniqueHandlerException {
        synchronized (this) {
            if (!initialized) {
                var handlerNames = context.getBeanNamesForType(RequestHandler.class);
                log.info("Scanned {} handlers.", handlerNames.length);
                Arrays.stream(handlerNames).forEach(name -> {
                    var handler = (RequestHandler<?, ?>) context.getBean(name);
                    var generics = GenericTypeResolver.resolveTypeArguments(handler.getClass(), RequestHandler.class);
                    if (generics != null) {
                        var requestType = (Class<? extends Request<?>>) generics[0];
                        if (handlerRegistry.containsKey(requestType)) {
                            log.error("Error while registry handler. See the exception for more details");
                            throw new NoUniqueHandlerException(requestType.getSimpleName());
                        }
                        var handlerProvider = context.getBean(handler.getClass());
                        handlerRegistry.put(requestType, handlerProvider);
                    }
                });
                log.info("Successfully registered {} handlers.", handlerNames.length);
                initialized = true;
                return;
            }
            throw new RuntimeException("Handlers configuration already initialized.");
        }
    }

    @Override
    public void clearHandlers() {
        log.info("Initiating MediatR Shutdown: Cleaning Up All Handlers.");
        handlerRegistry.clear();
        log.info("MediatR shutdown successful: Cleared all handlers");
    }
}
