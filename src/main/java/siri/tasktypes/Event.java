package siri.tasktypes;

import java.time.LocalDateTime;

/**
 * Represents an event task with a specific start and end time attached.
 * It is a task that contains a description, time period information indicating when the event occurs
 * and completion status, inheriting all basic task functionality
 * from the {@link Task} base class.
 *
 * <p>Event tasks are stored in the file format: {@code E | status | description | from | to}</p>
 *
 * @see Task
 * @see #toFileString()
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Constructs a new event task with the description and time period, all in Strings.
     * It is initially marked as not done.
     *
     * @param description the description of the event task.
     * @param from the start time or date of the event.
     * @param to the end time or date of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Converts the task to a representation suited for file storage.
     *
     * @return a string representation of the task for file storage.
     * @see Task#fromFileString(String)
     */
    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from + " | " + to;
    }

    /**
     * Returns a string representation of the task.
     *
     * @return a formatted string showing the task's type, status icon, description and time period.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
