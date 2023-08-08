package com.anirudh.springmediatr.core.mediatr;

/**
 * Contract for a handler responsible for processing a given {@link Request}.
 * Typically, there should be only one handler per request type to avoid ambiguity during processing.
 * In cases where multiple handlers are needed, specifying the intended handler explicitly is recommended.
 *
 * @param <P>        The type of the request to be processed. It must implement the {@link Request} interface.
 * @param <Response> The expected result type after processing the request.
 * @author Anirudh Pandita
 * @since 1.0
 */
public interface RequestHandler<P extends Request<Response>,Response> {
    /**
     * Handles the provided request and produces a response.
     *
     * @param request The request to be processed.
     * @return The response produced as a result of handling the request.
     */
    Response handle(P request);
}
