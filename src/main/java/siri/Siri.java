package siri;

import siri.exceptions.SiriException;
import siri.tasktypes.Deadline;
import siri.tasktypes.Event;
import siri.tasktypes.Task;
import siri.tasktypes.ToDo;

import java.util.ArrayList;
import java.util.Scanner;

public class Siri {
    private static ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello! I'm Siri!\n");
        System.out.println("What can I do for you?\n");

        while (true) {
            String command = scanner.nextLine();
            try {
                executeCommand(command);
            } catch (SiriException e) {
                System.out.println("____________________________________________________________");
                System.out.println(" " + e.getMessage());
                System.out.println("____________________________________________________________");
            }
        }
    }

    private static void executeCommand(String command) throws SiriException {
            if (command.equalsIgnoreCase("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                System.exit(0);
            } else if (command.equalsIgnoreCase("list")) {
                print();
            } else if (command.startsWith("mark ")) {
                System.out.println("____________________________________________________________");
                String[] word = command.split(" ");
                int index = Integer.parseInt(word[1]) - 1;
                tasks.get(index).markDone();
                System.out.println("Nice! I've marked this task as done:\n " + tasks.get(index));
                System.out.println("____________________________________________________________");
            } else if (command.startsWith("unmark ")) {
                System.out.println("____________________________________________________________");
                String[] word = command.split(" ");
                int index = Integer.parseInt(word[1]) - 1;
                tasks.get(index).markUndone();
                System.out.println("OK, I've marked this task as not done yet:\n " + tasks.get(index));
                System.out.println("____________________________________________________________");
            } else if (command.startsWith("todo")) {
                String description = command.substring(4).trim();;
                if (description.isEmpty()) {
                    throw new SiriException("What is your todo task?");
                }
                Task task = new ToDo(description);
                addTask(task);
            } else if (command.startsWith("event")) {
                String[] word = command.split("/from|/to");
                String description = word[0].substring(5).trim();
                if (description.isEmpty()) {
                    throw new SiriException("What is your event task?");
                }
                String from = word[1].trim();
                String to = word[2].trim();
                Task task = new Event(description, from, to);
                addTask(task);
            } else if (command.startsWith("deadline")) {
                String[] words = command.split("/by", 2);
                String description = words[0].substring(8).trim();
                if (description.isEmpty()) {
                    throw new SiriException("What is your deadline task?");
                }
                String by = words[1].trim();
                Task task = new Deadline(description, by);
                addTask(task);
            } else if (command.startsWith("delete")) {
                System.out.println("____________________________________________________________");
                int index = Integer.parseInt(command.split(" ")[1]) - 1;
                Task removedTask = tasks.remove(index);
                System.out.println("Gotcha :) I've removed this task:\n " + removedTask);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                System.out.println("____________________________________________________________");
            } else {
                throw new SiriException("Sorry :((( I don't know what that means");
            }
    }

    private static void print() {
        System.out.println("____________________________________________________________");
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        System.out.println("____________________________________________________________");
    }

    private static void addTask(Task task) {
        System.out.println("____________________________________________________________");
        tasks.add(task);
        System.out.println("Got it :)) I've added this task:\n " + task);
        System.out.println("Now you have " + tasks.size() + " task(s) in the list.");
        System.out.println("____________________________________________________________");
    }
}
