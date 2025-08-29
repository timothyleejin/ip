package siri.exceptions;

/**
 * Represents an exception thrown when an invalid command is entered by the user.
 * It is a type of SiriException that handles cases where the task number provided
 * by the user does not match any known task number.
 *
 * @see SiriException
 */

public class TaskNotFoundException extends SiriException {

    /**
     * Constructs a new TaskNotFoundException with a detailed message.
     * The message should clearly describe why the command is invalid and provide
     * guidance on the correct command format when appropriate.
     *
     * @param message the message that explains the reason for the exception
     */

    public TaskNotFoundException(String message) {
        super(message);
    }
}
