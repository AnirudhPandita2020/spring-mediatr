package com.anirudh.springmediatr.core.registry;


import com.anirudh.springmediatr.core.exception.NoHandlerFoundException;
import com.anirudh.springmediatr.core.mediatr.Command;
import com.anirudh.springmediatr.core.mediatr.CommandHandler;
import com.anirudh.springmediatr.core.mediatr.Query;
import com.anirudh.springmediatr.core.mediatr.QueryHandler;
import com.anirudh.springmediatr.core.notification.Event;
import com.anirudh.springmediatr.core.notification.NotificationHandler;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Contract for a Mediatr Registry. This contract specifies how to retrieve a {@link CommandHandler},
 * {@link com.anirudh.springmediatr.core.mediatr.QueryHandler} which will be used to process the given
 * {@link Command}, {@link Query}.
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
     * Retrieves a {@link CommandHandler} based on the provided request class.
     *
     * @param <C>          The type of the request class
     * @param requestClass The class of the request for which to retrieve the handler
     * @return The corresponding request handler
     * @throws NoHandlerFoundException If no appropriate query handler is found for the given command class.
     */
    <C extends Command> CommandHandler<C> getCommandHandler(Class<? extends C> requestClass) throws NoHandlerFoundException;

    /**
     * Retrieves a query handler for a specific query class.
     *
     * @param <Q>        The type of query for which a handler is sought.
     * @param <R>        The type of response produced by the query handler.
     * @param queryClass The class of the query for which a handler is requested.
     * @return The query handler instance capable of handling the specified query class.
     * @throws NoHandlerFoundException If no appropriate query handler is found for the given query class.
     */
    <Q extends Query<R>, R> QueryHandler<Q, R> getQueryHandler(Class<? extends Q> queryClass) throws NoHandlerFoundException;

    /**
     * Retrieves notification handlers for a specific event class.
     *
     * @param <E>        The type of event for which handlers are sought.
     * @param eventClass The class of the event for which handlers are requested.
     * @return A list of notification handler instances capable of handling the specified event class.
     * @throws NoHandlerFoundException If no appropriate notification handlers are found for the given event class.
     */
    <E extends Event> Set<NotificationHandler<? extends Event>> getNotificationHandlers(Class<E> eventClass);

    /**
     * Clears all registered request handlers during application shutdown.
     */
    void clearHandlers();
}
