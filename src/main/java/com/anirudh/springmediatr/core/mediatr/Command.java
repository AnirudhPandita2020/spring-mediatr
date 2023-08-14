package com.anirudh.springmediatr.core.mediatr;

/**
 * Marker interface representing a command in the MediatR pattern.
 * Commands are used to request actions that cause changes in the system.
 * Implementing classes define the specific behavior of the command.
 * <p>
 * This interface serves as a contract for classes that intend to represent commands.
 * They should implement this interface to be recognized as commands by the application.
 * <p>
 * Commands are typically handled by {@link CommandHandler} in the application.
 *
 * @author Anirudh Pandita
 * @see CommandHandler
 * @since 1.0
 */
public interface Command {

}
