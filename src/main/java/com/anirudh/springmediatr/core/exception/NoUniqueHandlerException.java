package com.anirudh.springmediatr.core.exception;


import com.anirudh.springmediatr.core.mediatr.CommandHandler;
import com.anirudh.springmediatr.core.mediatr.QueryHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;

/**
 * Exception indicating that no unique {@link CommandHandler} or {@link QueryHandler} was found for a specific request class.
 * This exception is thrown when attempting to retrieve a handler for a request class,
 * but multiple handlers are registered for the same request type.
 * To avoid such a scenario, it's best to have 1 handler or mark the required handler with {@link Primary}.
 *
 * @author Anirudh Pandita
 * @since 1.0
 */
@Slf4j
public class NoUniqueHandlerException extends RuntimeException {
    /**
     * Constructs a new NoUniqueHandlerException with a message indicating the request class
     * for which no unique handler was found.
     *
     * @param requestClassName The name of the request class for which no unique handler was found.
     */
    public NoUniqueHandlerException(String requestClassName) {
        super("No unique handler found for request class: " + requestClassName + ".Please remove or specify which handler to invoke with @Primary");
        log.error("No unique handler found for request class: " + requestClassName + ".Please remove or specify which handler to invoke with @Primary", this);
    }
}