package com.anirudh.springmediatr.core.mediatr;

/**
 * Marker interface representing a query in the MediatR pattern.
 * Queries are used to retrieve data and do not cause any changes in the system.
 * Implementing classes define the specific behavior of the query.
 * <p>
 * This interface serves as a contract for classes that intend to represent queries.
 * They should implement this interface to be recognized as queries by the application.
 * <p>
 * Queries are typically handled by query handlers in the application.
 *
 * @see QueryHandler
 * @since 1.0
 */
public interface Query<Response> {

}
