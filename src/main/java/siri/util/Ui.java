package siri.util;

import siri.tasktypes.Task;
import java.util.List;

/**
 * Handles all user interface interactions for the Siri chatbot.
 * <p>
 * This class is responsible for generating user-facing messages
 * (e.g., welcome, goodbye, errors, task updates) that are displayed
 * in either the CLI or GUI.
 * </p>
 */
public class Ui {

    /**
     * Returns the welcome message shown when Siri starts.
     *
     * @return welcome message string
     */
    public String getWelcomeMessage() {
        return "Hello! I'm Siri\nWhat can I do for you?";
    }

    /**
     * Returns the goodbye message shown when the user exits Siri.
     *
     * @return goodbye message string
     */
    public String getGoodbyeMessage() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Returns a formatted error message.
     *
     * @param errorMessage the error message to display
     * @return formatted error message
     */
    public String getErrorMessage(String errorMessage) {
        return errorMessage;
    }

    /**
     * Returns a formatted list of all tasks.
     *
     * @param tasks list of tasks
     * @return formatted message containing task list,
     *         or a message stating the list is empty
     */
    public String getTaskListMessage(List<Task> tasks) {
        assert tasks != null : "Task list should not be null";
        if (tasks.isEmpty()) {
            return "Your task list is empty!";
        }
        return buildTaskListMessage("Here are the tasks in your list:", tasks);
    }

    /**
     * Returns a message when a task is marked or unmarked.
     *
     * @param task     the task being marked/unmarked
     * @param isMarked true if the task is marked as done, false if unmarked
     * @return formatted confirmation message
     */
    public String getTaskMarkedMessage(Task task, boolean isMarked) {
        assert task != null : "Task should not be null when marking";
        return "Okay. I've marked this task as " + (isMarked ? "done:\n" : "not done yet:\n") + task;
    }

    /**
     * Returns a message when a new task is added.
     *
     * @param task       the task added
     * @param totalTasks total number of tasks after addition
     * @return formatted confirmation message
     */
    public String getTaskAddedMessage(Task task, int totalTasks) {
        assert task != null : "Task should not be null when adding";
        assert totalTasks >= 0 : "Total tasks should be non-negative";
        return "Got it. I've added this task:\n" + task + "\nNow you have " + totalTasks + " task(s) in the list.";
    }

    /**
     * Returns a message when a task is deleted.
     *
     * @param task       the task deleted
     * @param totalTasks total number of tasks after deletion
     * @return formatted confirmation message
     */
    public String getTaskDeletedMessage(Task task, int totalTasks) {
        assert task != null : "Task should not be null when deleting";
        assert totalTasks >= 0 : "Total tasks should be non-negative";
        return "Noted. I've removed this task:\n" + task + "\nNow you have " + totalTasks + " task(s) in the list.";
    }

    /**
     * Returns a message showing all tasks that match a given keyword.
     *
     * @param matchingTasks list of matching tasks
     * @param keyword       keyword used to filter tasks
     * @return formatted message with matching tasks or
     *         a message indicating no matches found
     */
    public String getMatchingTasksMessage(List<Task> matchingTasks, String keyword) {
        assert matchingTasks != null : "Matching tasks list should not be null";
        assert keyword != null : "Keyword should not be null";
        if (matchingTasks.isEmpty()) {
            return "No tasks found containing: " + keyword;
        }
        return buildTaskListMessage("Here are the matching tasks in your list:", matchingTasks);
    }

    /**
     * Prints the welcome message to the standard output.
     */
    public void sayWelcome() {
        System.out.println(getWelcomeMessage());
    }

    /**
     * Method to format a list of tasks for display.
     */
    private String buildTaskListMessage(String header, List<Task> tasks) {
        StringBuilder sb = new StringBuilder(header).append("\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(".").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }
}