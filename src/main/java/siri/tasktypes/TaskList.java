package siri.tasktypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the task list and provides operations to manage them
 * while maintaining encapsulation of the underlying list.
 * This class wraps a {@link List} of {@link Task} objects.
 *
 * @see Task
 * @see List
 */
public class TaskList {
    private List<Task> tasks;

    /**
     * Constructs an empty TaskList with no tasks.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList initialized with the list of tasks provided by the user.
     *
     * @param tasks the initial list of tasks to be filled in the TaskList.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the end of the task list.
     *
     * @param task the task to be added to the list.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes the task from the list and returns the task
     * at the specified position in the list.
     *
     * @param index the 0-based index of the task to be removed.
     * @return the task that was removed from the list.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the task at the specified position in the list.
     *
     * @param index the 0-based index of the task to return.
     * @return the task at the specified position.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the number of tasks in this list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the list of all the tasks.
     *
     * @return the list of all the tasks.
     */
    public List<Task> getAll() {
        return tasks;
    }
}
