package siri.tasktypes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a specific start and end time attached.
 * It is a task that contains a description, time period information indicating when the event occurs
 * and completion status, inheriting all basic task functionality
 * from the {@link Task} base class.
 *
 * <p>Event tasks are stored in the file format: {@code E | status | description | yyyy-MM-dd HHmm | yyyy-MM-dd HHmm}</p>
 *
 * @see Task
 * @see #toFileString()
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy h:mma");

    /**
     * Constructs a new event task with the description and time period.
     * It is initially marked as not done.
     *
     * @param description the description of the event task.
     * @param from the start time string in "yyyy-MM-dd HHmm" format.
     * @param to the end time string in "yyyy-MM-dd HHmm" format.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = LocalDateTime.parse(from, INPUT_FORMAT);
        this.to = LocalDateTime.parse(to, INPUT_FORMAT);
        if (this.to.isBefore(this.from)) {
            throw new IllegalArgumentException("Heyyy your end time cannot be before start time.");
        }
    }

    /**
     * Returns the start time of the event.
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Returns the end time of the event.
     */
    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Converts the task to a representation suited for file storage.
     *
     * @return a string representation of the task for file storage.
     * @see Task#fromFileString(String)
     */
    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | "
                + from.format(INPUT_FORMAT) + " | " + to.format(INPUT_FORMAT);
    }

    /**
     * Returns a string representation of the task.
     *
     * @return a formatted string showing the task's type, status icon, description and time period.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMAT)
                + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }
}
