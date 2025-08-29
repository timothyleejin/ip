package siri.tasktypes;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markDone() {
        isDone = true;
    }

    public void markUndone() {
        isDone = false;
    }

    public abstract String toFileString();

    public static Task fromFileString(String line) {
        String[] parts = line.split(" \\| ");
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
                Task d = new Deadline(description, parts[3]);
                if (isDone) {
                    d.markDone();
                }
                return d;
            case "E":
                Task e = new Event(description, parts[3], parts[4]);
                if (isDone) {
                    e.markDone();
                }
                return e;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
