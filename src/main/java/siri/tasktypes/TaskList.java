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
        assert tasks != null : "Task list should be initialized";
    }

    /**
     * Constructs a TaskList initialized with the list of tasks provided by the user.
     *
     * @param tasks the initial list of tasks to be filled in the TaskList.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
        assert tasks != null : "Task list provided should not be null";
    }

    /**
     * Adds a task to the end of the task list.
     *
     * @param task the task to be added to the list.
     */
    public void add(Task task) {
        assert task != null : "Cannot add a null task";
        int sizeBefore = tasks.size();
        tasks.add(task);
        assert tasks.size() == sizeBefore + 1 : "Task should be added to the task list";
        assert tasks.get(tasks.size() - 1) == task : "Last task should be the one just added";

    }

    /**
     * Removes the task from the list and returns the task
     * at the specified position in the list.
     *
     * @param index the 0-based index of the task to be removed.
     * @return the task that was removed from the list.
     */
    public Task remove(int index) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds for remove";
        Task removed = tasks.remove(index);
        assert removed != null : "Removed task should not be null";
        return removed;
    }

    /**
     * Returns the task at the specified position in the list.
     *
     * @param index the 0-based index of the task to return.
     * @return the task at the specified position.
     */
    public Task get(int index) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds for get";
        Task task = tasks.get(index);
        assert task != null : "Task retrieved should not be null";
        return task;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the number of tasks in this list.
     */
    public int size() {
        int size = tasks.size();
        assert size >= 0 : "Task list size should never be negative";
        return size;
    }

    /**
     * Returns the list of all the tasks.
     *
     * @return the list of all the tasks.
     */
    public List<Task> getAll() {
        assert tasks != null : "Task list should not be null when returning all tasks";
        return tasks;
    }

    /**
     * Finds and returns all tasks whose description contains the given keyword.
     *
     * @param keyword this is the search term.
     * @return a list of tasks that matches the search term.
     */
    public List<Task> findTasks(String keyword) {
        assert keyword != null : "Search keyword should not be null";
        List<Task> matchingTasks = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        for (Task task : tasks) {
            assert task != null : "Task in list should not be null";
            if (task.getDescription().toLowerCase().contains(lowerKeyword)) {
                matchingTasks.add(task);
            }
        }
        assert matchingTasks != null : "Matching task list should not be null";
        return matchingTasks;
    }
}
