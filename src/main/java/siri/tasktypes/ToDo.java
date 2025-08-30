package siri.tasktypes;

/**
 * Represents a todo task without any date/time attached.
 * It is a task that only contains a description
 * and completion status, inheriting all basic task functionality
 * from the {@link Task} base class.
 *
 * <p>Todo tasks are stored in the file format: {@code T | status | description}</p>
 *
 * @see Task
 * @see #toFileString()
 */
public class ToDo extends Task {

    /**
     * Constructs a new ToDo task with the description.
     * It is initially marked as not done.
     *
     * @param description the description of the todo task.
     */
    public ToDo(String description) {
        super(description);
    }


    /**
     * Converts the task to a representation suited for file storage.
     *
     * @return a string representation of the task for file storage.
     * @see Task#fromFileString(String)
     */
    @Override
    public String toFileString() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Returns a string representation of the task.
     *
     * @return a formatted string showing the task's type, status icon and description.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
