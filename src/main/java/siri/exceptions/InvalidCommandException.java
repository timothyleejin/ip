package siri.exceptions;

/**
 * Represents an exception thrown when an invalid command is entered by the user.
 * It is a type of SiriException that handles cases where the argument by the user
 * contains invalid syntax or does not match any known command format.
 *
 * @see SiriException
 */

public class InvalidCommandException extends SiriException {

    /**
     * Constructs a new InvalidCommandException with a detailed message.
     * The message should clearly describe why the command is invalid and provide
     * guidance on the correct command format when appropriate.
     *
     * @param message the message that explains the reason for the exception.
     *                The message should be clear and helpful for troubleshooting.
     */

    public InvalidCommandException(String message) {
        super(message);
    }
}
