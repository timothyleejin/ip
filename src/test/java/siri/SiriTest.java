package siri;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import siri.exceptions.InvalidCommandException;
import siri.tasktypes.Deadline;
import siri.tasktypes.TaskList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Siri} chatbot.
 * <p>
 * These tests verify that the {@code performDeadlineAction} method behaves
 * correctly under valid and invalid input scenarios.
 * </p>
 */
public class SiriTest {

    /** Instance of {@link Siri} used for testing. */
    private Siri siri;

    /**
     * Sets up a new {@link Siri} instance before each test.
     * <p>
     * A separate storage file ({@code ./data/test-siri.txt}) is used
     * to avoid interference with the main application data.
     * </p>
     */
    @BeforeEach
    void setUp() {
        siri = new Siri("./data/test-siri.txt");
    }

    /**
     * Verifies that a valid deadline command adds a {@link Deadline}
     * task to the task list with the correct description.
     *
     * @throws Exception if the command parsing or execution fails unexpectedly
     */
    @Test
    void deadlineTest1() throws Exception {
        siri.performDeadlineAction("submit assignment /by 2025-12-29 1800");
        TaskList tasks = siri.tasks;
        assertEquals(1, tasks.size());
        assertTrue(tasks.get(0) instanceof Deadline);
        assertEquals("submit assignment", tasks.get(0).getDescription());
    }

    /**
     * Verifies that an exception is thrown when the {@code /by}
     * keyword is missing in the deadline command.
     */
    @Test
    void deadlineTest2_missingBy() {
        Exception exception = assertThrows(
                InvalidCommandException.class,
                () -> siri.performDeadlineAction("submit assignment")
        );
        assertTrue(exception.getMessage().contains("Please specify the deadline using /by"));
    }

    /**
     * Verifies that an exception is thrown when the deadline command
     * uses an invalid date/time format.
     */
    @Test
    void deadlineTest3_invalidDateFormat() {
        Exception exception = assertThrows(
                InvalidCommandException.class,
                () -> siri.performDeadlineAction("return book /by not-a-date")
        );
        assertTrue(exception.getMessage().contains("Please enter a valid date & time format (yyyy-MM-dd HHmm). Example: /by 2025-12-29 1800"));
    }
}

