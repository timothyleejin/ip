package siri.util;

import siri.exceptions.SiriException;
import siri.exceptions.InvalidCommandException;

/**
 * Utility class for parsing user commands and extracting structured information.
 * <p>
 * This class provides methods to parse general commands, as well as specific
 * command types like deadlines and events. It ensures commands are valid
 * and throws {@link SiriException} or {@link InvalidCommandException} on errors.
 * </p>
 *
 * @see SiriException
 * @see InvalidCommandException
 */
public class Parser {

    /**
     * Parses a raw user command into an array containing the command keyword
     * and arguments.
     *
     * @param command the raw input command string
     * @return a string array where the first element is the command keyword,
     *         and the second element (if present) is the arguments
     * @throws SiriException if the command is invalid or empty
     */
    public static String[] parseCommand(String command) throws SiriException {
        validateCommand(command);
        return splitCommand(command);
    }

    /**
     * Validates that the command is not null or empty.
     *
     * @param command the command string to validate
     * @throws InvalidCommandException if the command is null or empty
     */
    private static void validateCommand(String command) throws InvalidCommandException {
        assert command != null : "Command passed to Parser.parse should not be null";
        if (command.trim().isEmpty()) {
            throw new InvalidCommandException("Nothing was entered!");
        }
    }

    /**
     * Splits the command string into the keyword and the arguments.
     *
     * @param command the command string to split
     * @return a string array containing the command keyword and optional arguments
     */
    private static String[] splitCommand(String command) {
        return command.trim().split(" ", 2);
    }

    /**
     * Parses an event string in the format: "description /from start /to end"
     */
    public static String[] parseEvent(String arguments) throws SiriException {
        String[] parts = arguments.split("/from|/to");
        if (parts.length < 3) {
            throw new InvalidCommandException("Please specify the event duration using /from and /to.");
        }

        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
            assert !parts[i].isEmpty() : "Event part should not be empty after trimming";
        }

        assert parts.length == 3 : "Parsed event should contain exactly 3 parts - description, from, to";
        return parts;
    }

    /**
     * Parses a deadline string in the format: "description /by date"
     */
    public static String[] parseDeadline(String arguments) throws SiriException {
        String[] parts = arguments.split("/by", 2);
        if (parts.length < 2 || parts[1].isEmpty()) {
            throw new InvalidCommandException(
                    "Please specify the deadline using /by. Example: deadline return book /by 2025-12-29 1800"
            );
        }

        parts[0] = parts[0].trim();
        parts[1] = parts[1].trim();

        assert !parts[0].isEmpty() : "Deadline description should not be empty";
        assert !parts[1].isEmpty() : "Deadline date or time should not be empty";
        assert parts.length == 2 : "Parsed deadline should contain exactly 2 parts - description and by";
        return parts;
    }
}
