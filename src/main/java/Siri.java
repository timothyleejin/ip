import java.util.Scanner;

public class Siri {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskNumber = 0;

        System.out.println("Hello! I'm Siri!\n");
        System.out.println("What can I do for you?\n");

        while (true) {
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

            else if (command.equalsIgnoreCase("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskNumber; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
            }

            else if (command.startsWith("mark ")) {
                String[] word = command.split(" ");
                int index = Integer.parseInt(word[1]) - 1;
                tasks[index].markDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks[index]);
            }

            else if (command.startsWith("unmark ")) {
                String[] word = command.split(" ");
                int index = Integer.parseInt(word[1]) - 1;
                tasks[index].markUndone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks[index]);
            }

            else {
                tasks[taskNumber] = new Task(command);
                taskNumber++;
                System.out.println("added: " + command);
            }

        }
        scanner.close();
    }
}
