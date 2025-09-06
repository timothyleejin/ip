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
        if (tasks.isEmpty()) {
            return "Your task list is empty!";
        }

        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1) + "." + tasks.get(i) + "\n");
        }
        return sb.toString();
    }

    public String getTaskMarkedMessage(Task task, boolean isMarked) {
        return "Nice! I've marked this task as " + (isMarked ? "done:\n" : "not done yet:\n") + task;
    }

    public String getTaskAddedMessage(Task task, int totalTasks) {
        return "Got it. I've added this task:\n" + task + "\nNow you have " + totalTasks + " task(s) in the list.";
    }

    public String getTaskDeletedMessage(Task task, int totalTasks) {
        return "Noted. I've removed this task:\n" + task + "\nNow you have " + totalTasks + " task(s) in the list.";
    }

    public String getMatchingTasksMessage(List<Task> matchingTasks, String keyword) {
        if (matchingTasks.isEmpty()) {
            return "No tasks found containing: " + keyword;
        }

        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matchingTasks.size(); i++) {
            sb.append((i + 1) + "." + matchingTasks.get(i) + "\n");
        }
        return sb.toString();
    }

    public void sayWelcome() {
        System.out.println(getWelcomeMessage());
    }
}