import java.util.Scanner;

public class Siri {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello! I'm Siri!\n");
        System.out.println("What can I do for you?\n");

        while (true) {
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else {
                System.out.println(command);
            }
        }
        scanner.close();
    }
}
