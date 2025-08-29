package siri;

import siri.exceptions.SiriException;
import siri.storage.Storage;
import siri.tasktypes.Deadline;
import siri.tasktypes.Event;
import siri.tasktypes.Task;
import siri.tasktypes.ToDo;
import siri.tasktypes.TaskList;
import siri.util.Ui;
import siri.util.Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
                String[] word = command.split(" ");
                int index = Integer.parseInt(word[1]) - 1;
                Task t = tasks.get(index);
                t.markDone();
                ui.sayTaskMarked(t, true);
            } else if (command.startsWith("unmark ")) {
                String[] word = command.split(" ");
                int index = Integer.parseInt(word[1]) - 1;
                Task t = tasks.get(index);
                t.markUndone();
                ui.sayTaskMarked(t, false);
            } else if (command.startsWith("todo")) {
                String description = command.substring(4).trim();;
                if (description.isEmpty()) {
                    throw new SiriException("What is your todo task?");
                }
                Task task = new ToDo(description);
                tasks.add(task);
                ui.sayTaskAdded(task, tasks.size());
            } else if (command.startsWith("event")) {
                String[] word = command.split("/from|/to");
                String description = word[0].substring(5).trim();
                if (description.isEmpty()) {
                    throw new SiriException("What is your event task?");
                }
                String from = word[1].trim();
                String to = word[2].trim();
                Task task = new Event(description, from, to);
                tasks.add(task);
                ui.sayTaskAdded(task, tasks.size());
            } else if (command.startsWith("deadline")) {
                String[] words = command.split("/by", 2);
                if (words.length < 2) {
                    throw new SiriException(
                            "Please specify the deadline using /by. Example: deadline return book /by 2025-12-29 1800"
                    );
                }
                String description = words[0].substring(8).trim();
                if (description.isEmpty()) {
                    throw new SiriException("What is your deadline task?");
                }
                String by = words[1].trim();
                try {
                    Task task = new Deadline(description, by);
                    tasks.add(task);
                    ui.sayTaskAdded(task, tasks.size());
                } catch (java.time.format.DateTimeParseException e) {
                    throw new SiriException("Invalid date/time format. Please use yyyy-MM-dd HHmm, e.g., 2025-12-29 1800");
                }
            } else if (command.startsWith("delete")) {
                int index = Integer.parseInt(command.split(" ")[1]) - 1;
                Task removedTask = tasks.remove(index);
                ui.sayTaskDeleted(removedTask, tasks.size());
            } else {
                throw new SiriException("Sorry :((( I don't know what that means");
            }
    }

    public static void main(String[] args) {
        new Siri(FILE_PATH).run();
    }
}
