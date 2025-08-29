package siri.util;

import siri.exceptions.SiriException;
import siri.exceptions.InvalidCommandException;

public class Parser {
    public static String[] parse(String command) throws SiriException {
        if (command.trim().isEmpty() || command == null) {
            throw new InvalidCommandException("Nothing was entered!");
        }
        return command.trim().split(" ", 2);
    }
}
