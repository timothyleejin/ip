package siri;

import java.util.List;

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
import siri.util.Parser;

import java.io.IOException;
import java.util.Scanner;

public class Siri {
    private static final String FILE_PATH = "./data/siri.txt";

    protected TaskList tasks;
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
        String[] parsedCommand = Parser.parse(command);
        String argument = parsedCommand[0].toLowerCase();
        String userAction = parsedCommand.length > 1 ? parsedCommand[1] : "";

        switch (argument) {

            case "bye":
                ui.sayGoodbye();
                System.exit(0);
                break;

            case "list":
                ui.sayTaskList(tasks.getAll());
                break;

            case "mark":
                performMarkAction(userAction, true);
                break;

            case "unmark":
                performMarkAction(userAction, false);
                break;

            case "todo":
                performTodoAction(userAction);
                break;

            case "event":
                performEventAction(userAction);
                break;

            case "deadline":
                performDeadlineAction(userAction);
                break;

            case "delete":
                performDeleteAction(userAction);
                break;

            case "find":
                performFindAction(userAction);
                break;

            default:
                throw new InvalidCommandException("Sorry :((( I don't know what that means");
        }
    }

    protected void performMarkAction(String description, boolean isMark) throws SiriException {
        if (description.isEmpty()) {
            throw new InvalidCommandException("Hi! Please specify a task number to " + (isMark ? "mark" : "unmark"));
        }
        try {
            int index = Integer.parseInt(description) - 1;
            Task task = tasks.get(index);
            if (isMark) {
                task.markDone();
            } else {
                task.markUndone();
            }
            ui.sayTaskMarked(task, isMark);
        } catch (IndexOutOfBoundsException e) {
            throw new TaskNotFoundException("Oops!! The task number provided does not exist :(");
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("Sorry, please key in a valid task number!");
        }
    }

    protected void performTodoAction(String description) throws SiriException {
        if (description.isEmpty()) {
            throw new TaskNotFoundException("What is your todo task?");
        }
        Task task = new ToDo(description);
        tasks.add(task);
        ui.sayTaskAdded(task, tasks.size());
    }

    protected void performEventAction(String arguments) throws SiriException {
        if (arguments.isEmpty()) {
            throw new InvalidCommandException("What is your event?");
        }

        String[] parts = arguments.split("/from|/to");
        if (parts.length < 3) {
            throw new InvalidCommandException("Please specify the event duration using /from and /to.");
        }

        String description = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();

        Task task = new Event(description, from, to);
        tasks.add(task);
        ui.sayTaskAdded(task, tasks.size());
    }

    protected void performDeadlineAction(String arguments) throws SiriException {
        if (arguments.isEmpty()) {
            throw new InvalidCommandException("What is your deadline task?");
        }

        String[] words = arguments.split("/by", 2);
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
    }

    protected void performDeleteAction(String arguments) throws SiriException {
        if (arguments.isEmpty()) {
            throw new InvalidCommandException("Please specify a task number to delete");
        }

        try {
            int index = Integer.parseInt(arguments) - 1;
            Task removedTask = tasks.remove(index);
            ui.sayTaskDeleted(removedTask, tasks.size());
        } catch (IndexOutOfBoundsException e) {
            throw new TaskNotFoundException("Oops!! The task number provided does not exist :(");
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("Sorry, please key in a valid task number!");
        }
    }

    /**
     * Finds and displays tasks that contain the given keyword in their description.
     *
     * @param keyword the search term to look for in task list.
     * @throws InvalidCommandException if the keyword is empty.
     */
    protected void performFindAction(String keyword) throws SiriException {
        if (keyword.isEmpty()) {
            throw new InvalidCommandException("Please specify a keyword to search for. Example: find book");
        }

        List<Task> matchingTasks = tasks.findTasks(keyword);
        ui.sayMatchingTasks(matchingTasks, keyword);
    }

    public static void main(String[] args) {
        new Siri(FILE_PATH).run();
    }
}