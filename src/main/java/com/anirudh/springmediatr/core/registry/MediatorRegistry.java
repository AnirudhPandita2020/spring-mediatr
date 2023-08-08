package com.anirudh.springmediatr.core.registry;

import com.anirudh.springmediatr.core.exception.NoHandlerFoundException;
import com.anirudh.springmediatr.core.exception.NoUniqueHandlerException;
import com.anirudh.springmediatr.core.mediatr.Request;
import com.anirudh.springmediatr.core.mediatr.RequestHandler;
import org.springframework.stereotype.Component;

/**
 * Contract for a Mediatr Registry. This contract specifies how to retrieve a {@link RequestHandler}
 * which will be used to process the given {@link Request}.
 * <p>
 * The purpose of this registry is to maintain track of all available handlers within the Spring context.
 * Handlers annotated with {@link Component} will be accessible within
 * the Spring IoC container for injection purposes.
 * <p>
 * In the future, additional methods will be incorporated into the registry, including obtaining pipelines or processors.
 *
 * @author Anirudh Pandita
 * @since 1.0
 */
public interface MediatorRegistry {
    /**
     * Retrieves a {@link RequestHandler} based on the provided request class.
     *
     * @param <C>          The type of the request class
     * @param <R>          The response type of the request
     * @param requestClass The class of the request for which to retrieve the handler
     * @return The corresponding request handler
     */
    <C extends Request<R>, R> RequestHandler<C, R> getHandler(Class<? extends C> requestClass);

    /**
     * Configures and initializes request handlers during application startup.
     *
     * @throws NoHandlerFoundException  If no handler is found for a request type
     * @throws NoUniqueHandlerException If multiple handlers are found for the same request type
     */
    void configureHandlers() throws NoHandlerFoundException, NoUniqueHandlerException;

    /**
     * Clears all registered request handlers during application shutdown.
     */
    void clearHandlers();
}