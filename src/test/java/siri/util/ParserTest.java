package siri.util;

import org.junit.jupiter.api.Test;
import siri.exceptions.SiriException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    void parserTest1() throws SiriException {
        String[] result = Parser.parseCommand("list");
        assertEquals("list", result[0]);
        assertEquals(1, result.length);
    }

    @Test
    void parserTest2() throws SiriException {
        String[] result = Parser.parseCommand("todo read book");
        assertEquals("todo", result[0]);
        assertEquals("read book", result[1]);
    }

    @Test
    void parserTest3() throws SiriException {
        String[] result = Parser.parseCommand("   deadline return book /by 2025-12-29 1800   ");
        assertEquals("deadline", result[0]);
        assertEquals("return book /by 2025-12-29 1800", result[1]);
    }
}