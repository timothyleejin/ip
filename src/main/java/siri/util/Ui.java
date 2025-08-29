package siri.util;

import siri.tasktypes.Task;
import java.util.List;

public class Ui {
    public void sayWelcome() {
        System.out.println("Hello! I'm Siri!\n");
        System.out.println("What can I do for you?\n");
    }

    public void sayGoodbye() {
        System.out.println("____________________________________________________________");
        System.out.println("Bye bye :( Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }

    public void sayError(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(" " + message);
        System.out.println("____________________________________________________________");
    }

    public void sayTaskAdded(Task task, int size) {
        System.out.println("____________________________________________________________");
        System.out.println("Got it :)) I've added this task:\n " + task);
        System.out.println("Now you have " + size + " task(s) in the list.");
        System.out.println("____________________________________________________________");
    }

    public void sayTaskList(List<Task> tasks) {
        System.out.println("____________________________________________________________");
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        System.out.println("____________________________________________________________");
    }

    public void sayTaskMarked(Task task, boolean isDone) {
        System.out.println("____________________________________________________________");
        if (isDone) {
            System.out.println("Nice! I've marked this task as done:\n " + task);
        } else {
            System.out.println("OK, I've marked this task as not done yet:\n " + task);
        }
        System.out.println("____________________________________________________________");
    }

    public void sayTaskDeleted(Task task, int size) {
        System.out.println("____________________________________________________________");
        System.out.println("Gotcha :) I've removed this task:\n " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }
}
