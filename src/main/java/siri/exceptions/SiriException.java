package siri.exceptions;

/**
 * The base exception class for all Siri exceptions.
 * This class extends the Java {@link Exception} class to provide
 * a common foundation for all custom exceptions in this chatbot.
 *
 * <p>All Siri-specific exceptions should extend this class to maintain
 * consistent exception handling throughout the application.</p>
 *
 * @see Exception
 * @see InvalidCommandException
 * @see TaskNotFoundException
 */

public class SiriException extends Exception {

    /**
     * Constructs a new SiriException with a detailed message.
     * The message should clearly describe why the command is invalid and provide
     * guidance on the correct command format when appropriate.
     *
     * @param message the message that explains the reason for the exception.
     *                The message should be clear and helpful for troubleshooting.
     */

    public SiriException(String message) {
        super(message);
    }
}
