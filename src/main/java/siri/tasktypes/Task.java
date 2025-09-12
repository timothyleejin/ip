package siri.tasktypes;

/**
 * Represents an abstract task with basic properties and behavior.
 * This serves as the base class for all specific task types in the chatbot.
 *
 * <p>Concrete task types must implement the {@link #toFileString()} method to provide
 * their specific file storage format.</p>
 *
 * @see ToDo
 * @see Deadline
 * @see Event
 * @see #fromFileString(String)
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task with the specified description.
     * The task is initially marked as not done.
     *
     * @param description the description of the task.
     */
    public Task(String description) {
        assert description != null : "Task description should not be null";
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the description of this task.
     *
     * @return the task description text.
     */
    public String getDescription() {
        assert description != null : "Task description should not be null";
        return description;
    }

    /**
     * Returns the status icon depending on whether the task is done.
     *
     * @return "X" if the task is done and " " if the task is not done.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks this task done.
     */
    public void markDone() {
        isDone = true;
    }

    /**
     * Unmarks this task.
     */
    public void markUndone() {
        isDone = false;
    }

    /**
     * Converts the task to a representation suited for file storage.
     * The format should allow reconstruction of the task using {@link #fromFileString(String)}.
     *
     * @return a string representation of the task for file storage.
     */
    public abstract String toFileString();

    /**
     * Creates a Task object from its file storage representation
     * based the appropriate task type (ToDo, Deadline, or Event).
     *
     * @param line the file string representation of a task read from storage.
     * @return a Task object reconstructed from the file representation or null
     * if the format is invalid.
     * @see #toFileString()
     */
    public static Task fromFileString(String line) {
        assert line != null : "File line passed to Task.fromFileString should not be null";
        String[] parts = line.split(" \\| ");
        assert parts.length >= 3 : "File line should have at least 3 parts";
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        switch (type) {
            case "T":
                Task t = new ToDo(description);
                if (isDone) {
                    t.markDone();
                }
                return t;
            case "D":
                assert parts.length >= 4 : "Deadline line must have 4 parts";
                Task d = new Deadline(description, parts[3]);
                if (isDone) {
                    d.markDone();
                }
                return d;
            case "E":
                assert parts.length >= 5 : "Event line must have 5 parts";
                Task e = new Event(description, parts[3], parts[4]);
                if (isDone) {
                    e.markDone();
                }
                return e;
            default:
                return null;
        }
    }

    /**
     * Returns a string representation of the task.
     *
     * @return a formatted string showing the task's status icon and description.
     */
    @Override
    public String toString() {
        assert description != null : "Task description should never be null";
        return "[" + getStatusIcon() + "] " + description;
    }
}
