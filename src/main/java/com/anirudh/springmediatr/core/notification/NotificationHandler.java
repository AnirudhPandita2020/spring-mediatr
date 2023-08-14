package com.anirudh.springmediatr.core.notification;

/**
 * This interface defines a contract for classes that handle notification events.
 * A notification handler is responsible for processing {@link Event}
 * and returning a result.
 *
 * @param <E> The type of event that this handler can process. It should be a subtype
 *            of the {@link Event} interface.
 */
public interface NotificationHandler<E extends Event> {

    /**
     * Handles the provided notification event and produces a result.
     *
     * @param event The event to be processed by this handler.
     */
    void handle(E event);
}
