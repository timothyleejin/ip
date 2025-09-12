package siri.util;

import siri.tasktypes.Task;
import java.util.List;

public class Ui {

    public String getWelcomeMessage() {
        return "Hello! I'm Siri\nWhat can I do for you?";
    }

    public String getGoodbyeMessage() {
        return "Bye. Hope to see you again soon!";
    }

    public String getErrorMessage(String errorMessage) {
        return errorMessage;
    }

    public String getTaskListMessage(List<Task> tasks) {
        assert tasks != null : "Task list should not be null";
        if (tasks.isEmpty()) {
            return "Your task list is empty!";
        }
        return buildTaskListMessage("Here are the tasks in your list:", tasks);
    }

    public String getTaskMarkedMessage(Task task, boolean isMarked) {
        assert task != null : "Task should not be null when marking";
        return "Nice! I've marked this task as " + (isMarked ? "done:\n" : "not done yet:\n") + task;
    }

    public String getTaskAddedMessage(Task task, int totalTasks) {
        assert task != null : "Task should not be null when adding";
        assert totalTasks >= 0 : "Total tasks should be non-negative";
        return "Got it. I've added this task:\n" + task + "\nNow you have " + totalTasks + " task(s) in the list.";
    }

    public String getTaskDeletedMessage(Task task, int totalTasks) {
        assert task != null : "Task should not be null when deleting";
        assert totalTasks >= 0 : "Total tasks should be non-negative";
        return "Noted. I've removed this task:\n" + task + "\nNow you have " + totalTasks + " task(s) in the list.";
    }

    public String getMatchingTasksMessage(List<Task> matchingTasks, String keyword) {
        assert matchingTasks != null : "Matching tasks list should not be null";
        assert keyword != null : "Keyword should not be null";
        if (matchingTasks.isEmpty()) {
            return "No tasks found containing: " + keyword;
        }
        return buildTaskListMessage("Here are the matching tasks in your list:", matchingTasks);
    }

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