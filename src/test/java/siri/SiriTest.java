package siri;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import siri.exceptions.InvalidCommandException;
import siri.tasktypes.Deadline;
import siri.tasktypes.TaskList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SiriTest {

    private Siri siri;

    @BeforeEach
    void setUp() {
        siri = new Siri("./data/test-siri.txt");
    }

    @Test
    void deadlineTest1() throws Exception {
        siri.performDeadlineAction("submit assignment /by 2025-12-29 1800");
        TaskList tasks = siri.tasks;
        assertEquals(1, tasks.size());
        assertTrue(tasks.get(0) instanceof Deadline);
        assertEquals("submit assignment", tasks.get(0).getDescription());
    }

    @Test
    void deadlineTest2_missingBy() {
        Exception exception = assertThrows(
                InvalidCommandException.class,
                () -> siri.performDeadlineAction("submit assignment")
        );
        assertTrue(exception.getMessage().contains("Please specify the deadline using /by"));
    }

    @Test
    void deadlineTest3_invalidDateFormat() {
        Exception exception = assertThrows(
                InvalidCommandException.class,
                () -> siri.performDeadlineAction("return book /by not-a-date")
        );
        assertTrue(exception.getMessage().contains("Please enter a valid date/time format"));
    }
}

