package siri.tasktypes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task with a date/time attached.
 * It is a task that contains a description, deadline time and date,
 * and completion status, inheriting all basic task functionality
 * from the {@link Task} base class.
 *
 * <p>Todo tasks are stored in the file format: {@code D | status | description | yyyy-MM-dd HHmm}</p>
 *
 * @see Task
 * @see LocalDateTime
 * @see #toFileString()
 */
public class Deadline extends Task {
    protected LocalDateTime by;

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy h:mma");

    /**
     * Constructs a new Deadline task with the description and deadline.
     * It is initially marked as not done.
     *
     * @param description the description of the deadline task.
     * @param by the due date and time string in "yyyy-MM-dd HHmm" format.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDateTime.parse(by, INPUT_FORMAT);
    }

    /**
     * Converts the task to a representation suited for file storage.
     *
     * @return a string representation of the task for file storage.
     * @see Task#fromFileString(String)
     */
    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by.format(INPUT_FORMAT);
    }

    /**
     * Returns a string representation of the task.
     *
     * @return a formatted string showing the task's type, status icon, description and deadline.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}
