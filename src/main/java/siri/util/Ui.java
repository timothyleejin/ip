package siri.util;

import siri.tasktypes.Task;
import java.util.List;

public class Ui {
    public void sayWelcome() {
        System.out.println("Hello! I'm Siri!\n");
        System.out.println("What can I do for you?\n");
    }

    private void printborderLine() {
        System.out.println("____________________________________________________________");
    }

    public void sayGoodbye() {
        printborderLine();
        System.out.println("Bye bye :( Hope to see you again soon!");
        printborderLine();
    }

    public void sayError(String message) {
        printborderLine();
        System.out.println(" " + message);
        printborderLine();
    }

    public void sayTaskAdded(Task task, int size) {
        printborderLine();
        System.out.println("Got it :)) I've added this task:\n " + task);
        System.out.println("Now you have " + size + " task(s) in the list.");
        printborderLine();
    }

    public void sayTaskList(List<Task> tasks) {
        printborderLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        printborderLine();
    }

    public void sayTaskMarked(Task task, boolean isDone) {
        printborderLine();
        if (isDone) {
            System.out.println("Nice! I've marked this task as done:\n " + task);
        } else {
            System.out.println("OK, I've marked this task as not done yet:\n " + task);
        }
        printborderLine();
    }

    public void sayTaskDeleted(Task task, int size) {
        printborderLine();
        System.out.println("Gotcha :) I've removed this task:\n " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
        printborderLine();
    }

    /**
     * Displays the list of tasks that match the search term.
     *
     * @param matchingTasks the list of tasks that contain the search keyword.
     * @param keyword the search term that was used.
     */
    public void sayMatchingTasks(List<Task> matchingTasks, String keyword) {
        printborderLine();
        if (matchingTasks.isEmpty()) {
            System.out.println("Sorry! No tasks found containing: '" + keyword + "'");
        } else {
            System.out.println("Okay! Here are the matching task(s) in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println((i + 1) + "." + matchingTasks.get(i));
            }
        }
        printborderLine();
    }
}
