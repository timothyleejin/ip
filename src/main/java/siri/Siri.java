package siri;

import siri.exceptions.SiriException;
import siri.exceptions.TaskNotFoundException;
import siri.exceptions.InvalidCommandException;
import siri.storage.Storage;
import siri.tasktypes.Deadline;
import siri.tasktypes.Event;
import siri.tasktypes.Task;
import siri.tasktypes.ToDo;
import siri.tasktypes.TaskList;
import siri.util.Ui;

import java.io.IOException;
import java.util.Scanner;

public class Siri {
    private static final String FILE_PATH = "./data/siri.txt";

    private TaskList tasks;
    private Storage storage;
    private Ui ui;

    public Siri(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (Exception e) {
            tasks = new TaskList();
        }
    }
    public void run() {
        ui.sayWelcome();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String command = scanner.nextLine();
            try {
                executeCommand(command);
                storage.save(tasks.getAll());
            } catch (SiriException | IOException e) {
                ui.sayError(e.getMessage());
            }
        }
    }

    private void executeCommand(String command) throws SiriException {
            if (command.equalsIgnoreCase("bye")) {
                ui.sayGoodbye();
                System.exit(0);
            } else if (command.equalsIgnoreCase("list")) {
                ui.sayTaskList(tasks.getAll());
            } else if (command.startsWith("mark ")) {
                try {
                    String[] word = command.split(" ");
                    int index = Integer.parseInt(word[1]) - 1;
                    Task t = tasks.get(index);
                    t.markDone();
                    ui.sayTaskMarked(t, true);
                } catch (IndexOutOfBoundsException e) {
                    throw new TaskNotFoundException("Oops!! The task number provided does not exist :(");
                } catch (NumberFormatException e) {
                    throw new InvalidCommandException("Sorry, please key in a valid task number!");
                }
            } else if (command.startsWith("unmark ")) {
                try {
                    String[] word = command.split(" ");
                    int index = Integer.parseInt(word[1]) - 1;
                    Task t = tasks.get(index);
                    t.markUndone();
                    ui.sayTaskMarked(t, false);
                } catch (IndexOutOfBoundsException e) {
                    throw new TaskNotFoundException("Oops!! The task number provided does not exist :(");
                } catch (NumberFormatException e) {
                    throw new InvalidCommandException("Sorry, please key in a valid task number!");
                }
            } else if (command.startsWith("todo")) {
                String description = command.substring(4).trim();;
                if (description.isEmpty()) {
                    throw new TaskNotFoundException("What is your todo task?");
                }
                Task task = new ToDo(description);
                tasks.add(task);
                ui.sayTaskAdded(task, tasks.size());
            } else if (command.startsWith("event")) {
                String[] word = command.split("/from|/to");
                String description = word[0].substring(5).trim();
                if (description.isEmpty()) {
                    throw new InvalidCommandException("What is your event?");
                }
                if (word.length < 3) {
                    throw new InvalidCommandException("Please specify the event duration using /from and /to.");
                }
                String from = word[1].trim();
                String to = word[2].trim();
                Task task = new Event(description, from, to);
                tasks.add(task);
                ui.sayTaskAdded(task, tasks.size());
            } else if (command.startsWith("deadline")) {
                String deadlineTask = command.substring(8).trim();
                if (deadlineTask.isEmpty()) {
                    throw new InvalidCommandException("What is your deadline task?");
                }
                String[] words = deadlineTask.split("/by", 2);
                if (words.length < 2) {
                    throw new InvalidCommandException(
                            "Please specify the deadline using /by. Example: deadline return book /by 2025-12-29 1800"
                    );
                }
                String description = words[0].trim();
                String by = words[1].trim();
                try {
                    Task task = new Deadline(description, by);
                    tasks.add(task);
                    ui.sayTaskAdded(task, tasks.size());
                } catch (java.time.format.DateTimeParseException e) {
                    throw new InvalidCommandException("Please enter a valid date/time format (yyyy-MM-dd HHmm). Example: 2025-12-29 1800");
                }
            } else if (command.startsWith("delete")) {
                try {
                    int index = Integer.parseInt(command.split(" ")[1]) - 1;
                    Task removedTask = tasks.remove(index);
                    ui.sayTaskDeleted(removedTask, tasks.size());
                } catch (IndexOutOfBoundsException e) {
                    throw new TaskNotFoundException("Oops!! The task number provided does not exist :(");
                } catch (NumberFormatException e) {
                    throw new InvalidCommandException("Sorry, please key in a valid task number!");
                }
            } else {
                throw new InvalidCommandException("Sorry :((( I don't know what that means");
            }
    }

    public static void main(String[] args) {
        new Siri(FILE_PATH).run();
    }
}
