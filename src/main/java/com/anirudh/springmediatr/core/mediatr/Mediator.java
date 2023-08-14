package com.anirudh.springmediatr.core.mediatr;

import jdk.jfr.Experimental;

/**
 * This interface defines the contract for a mediator that handles communication between various components
 * by sending requests and receiving responses.
 * <p>
 * The mediator acts as an intermediary for sending different types of requests and receiving their responses.
 * Implementing classes will provide the actual behavior of request processing.
 * </p>
 *
 * @author Anirudh Pandita
 * @since 1.0
 */
@Experimental
public interface Mediator {

    /**
     * Sends a command request to the mediator for processing and returns the corresponding response.
     *
     * @param <C>     The specific type of command request to be sent.
     * @param request The command request instance to be sent to the mediator for processing.
     *                It should implement the {@link Command} interface.
     */
    <C extends Command> void send(final C request);

    /**
     * Sends a query request to the mediator for processing and returns the corresponding response.
     *
     * @param <Q>        The specific type of query request to be sent.
     * @param <Response> The type of response expected from the query request.
     * @param query      The query request instance to be sent to the mediator for processing.
     *                   It should implement the {@link Query} interface.
     * @return The response produced by processing the query request.
     */
    <Q extends Query<Response>, Response> Response send(final Q query);
}
