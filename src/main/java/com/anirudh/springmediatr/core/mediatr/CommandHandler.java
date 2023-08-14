package com.anirudh.springmediatr.core.mediatr;

/**
 * Contract for a handler responsible for processing a given {@link Command}.
 * Typically, there should be only one handler per request type to avoid ambiguity during processing.
 * In cases where multiple handlers are needed, specifying the intended handler explicitly is recommended.
 *
 * @param <P> The type of the request to be processed. It must implement the {@link Command} interface.
 * @author Anirudh Pandita
 * @since 1.0
 */
public interface CommandHandler<P extends Command> {
    /**
     * Handles the provided request and produces a response.
     *
     * @param request The request to be processed.
     */
    void handleCommand(P request);
}
