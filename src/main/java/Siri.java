import java.util.Scanner;

public class Siri {
    private static Task[] tasks = new Task[100];
    private static int taskNumber = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello! I'm Siri!\n");
        System.out.println("What can I do for you?\n");

        while (true) {
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (command.equalsIgnoreCase("list")) {
                print();
            } else if (command.startsWith("mark ")) {
                String[] word = command.split(" ");
                int index = Integer.parseInt(word[1]) - 1;
                tasks[index].markDone();
                System.out.println("Nice! I've marked this task as done:\n " + tasks[index]);
            } else if (command.startsWith("unmark ")) {
                String[] word = command.split(" ");
                int index = Integer.parseInt(word[1]) - 1;
                tasks[index].markUndone();
                System.out.println("OK, I've marked this task as not done yet:\n " + tasks[index]);
            } else if (command.startsWith("todo")) {
                String description = command.substring(5);
                Task task = new ToDo(description);
                addTask(task);
            } else if (command.startsWith("event")) {
                String[] word = command.split("/from|/to");
                String description = word[0].substring(6).trim();
                String from = word[1].trim();
                String to = word[2].trim();
                Task task = new Event(description, from, to);
                addTask(task);
            } else if (command.startsWith("deadline")) {
                String[] words = command.split("/by", 2);
                String description = words[0].substring(9).trim();
                String by = words[1].trim();
                Task task = new Deadline(description, by);
                addTask(task);
            } else {
                System.out.println("Sorry, I don't understand that command.");
            }
        }
    }

    private static void print() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskNumber; i++) {
            System.out.println((i + 1) + "." + tasks[i]);
        }
    }

    private static void addTask(Task task) {
        tasks[taskNumber] = task;
        taskNumber++;
        System.out.println("Got it. I've added this task:\n " + task);
        System.out.println("Now you have " + taskNumber + " tasks in the list.");
    }
}
