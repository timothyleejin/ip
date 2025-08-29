package siri.util;

import siri.exceptions.SiriException;

public class Parser {
    public static String[] parse(String fullCommand) throws SiriException {
        if (fullCommand == null || fullCommand.trim().isEmpty()) {
            throw new SiriException("Empty command entered.");
        }
        return fullCommand.trim().split(" ", 2);
    }
}
