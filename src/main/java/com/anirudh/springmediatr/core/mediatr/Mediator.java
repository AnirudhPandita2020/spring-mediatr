package com.anirudh.springmediatr.core.mediatr;

import com.anirudh.springmediatr.core.registry.MediatorRegistry;
import jdk.jfr.Experimental;

/**
 * This interface defines the contract for a mediator that handles communication between various components
 * by sending requests and receiving responses.
 *
 * @author Anirudh Pandita
 * @since 1.0
 */
@Experimental
public interface Mediator {
    /**
     * Sends a request to the mediator for processing and returns the corresponding response.
     *
     * @param <P>        The specific type of request to be sent. It must extend the {@link Request<Response>} interface.
     * @param <Response> The type of response expected from the request.
     * @param request    The request instance to be sent to the mediator for processing.This will be sent to {@link RequestHandler} which will be defined in the {@link MediatorRegistry}
     * @return The response produced by processing the request.
     */
    <P extends Request<Response>, Response> Response send(final P request);
}
