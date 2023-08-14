package com.anirudh.springmediatr.core.exception;


import com.anirudh.springmediatr.core.mediatr.CommandHandler;
import com.anirudh.springmediatr.core.mediatr.QueryHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Exception indicating that no {@link CommandHandler} or {@link QueryHandler} was found for a specific request class.
 * This exception is typically thrown when attempting to retrieve a handler for a request
 * class, but no appropriate handler is registered in the system.
 *
 * @author Anirudh Pandita
 * @since 1.0
 */
@Slf4j
public class NoHandlerFoundException extends RuntimeException {
    /**
     * Constructs a new NoHandlerFoundException with a message indicating the request class for which no handler was found.
     *
     * @param requestClassName The name of the request class for which no handler was found.
     */
    public NoHandlerFoundException(String requestClassName) {
        super("No handler found for request class: " + requestClassName);
        log.error("No handler found for request class: " + requestClassName, this);
    }
}
