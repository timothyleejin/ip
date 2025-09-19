package siri.util;

import org.junit.jupiter.api.Test;
import siri.exceptions.SiriException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link Parser} utility class.
 * <p>
 * These tests verify that the {@code parseCommand} method
 * correctly splits user input into command keywords and arguments.
 * </p>
 */
public class ParserTest {

    /**
     * Tests parsing a single-word command without arguments.
     * <p>
     * Input: {@code "list"} <br>
     * Expected: {@code ["list"]}
     * </p>
     *
     * @throws SiriException if command parsing fails unexpectedly
     */
    @Test
    void parserTest1() throws SiriException {
        String[] result = Parser.parseCommand("list");
        assertEquals("list", result[0]);
        assertEquals(1, result.length);
    }

    /**
     * Tests parsing a command followed by an argument.
     * <p>
     * Input: {@code "todo read book"} <br>
     * Expected: {@code ["todo", "read book"]}
     * </p>
     *
     * @throws SiriException if command parsing fails unexpectedly
     */
    @Test
    void parserTest2() throws SiriException {
        String[] result = Parser.parseCommand("todo read book");
        assertEquals("todo", result[0]);
        assertEquals("read book", result[1]);
    }

    /**
     * Tests parsing a command with extra whitespace and
     * a multi-part argument (deadline with date).
     * <p>
     * Input: {@code "   deadline return book /by 2025-12-29 1800   "} <br>
     * Expected: {@code ["deadline", "return book /by 2025-12-29 1800"]}
     * </p>
     *
     * @throws SiriException if command parsing fails unexpectedly
     */
    @Test
    void parserTest3() throws SiriException {
        String[] result = Parser.parseCommand("   deadline return book /by 2025-12-29 1800   ");
        assertEquals("deadline", result[0]);
        assertEquals("return book /by 2025-12-29 1800", result[1]);
    }
}