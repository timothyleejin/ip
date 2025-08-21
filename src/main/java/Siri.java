import java.util.Scanner;

public class Siri {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] tasks = new String[100];
        int taskNumber = 0;

        System.out.println("Hello! I'm Siri!\n");
        System.out.println("What can I do for you?\n");

        while (true) {
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (command.equalsIgnoreCase("list")) {
                for (int i = 0; i < taskNumber; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
            } else {
                tasks[taskNumber] = command;
                taskNumber++;
                System.out.println("added: " + command);
            }
        }
        scanner.close();
    }
}
