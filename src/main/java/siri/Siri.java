package siri;

import java.util.List;
import java.util.Scanner;

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

public class Siri {
    private static final String FILE_PATH = "./data/siri.txt";
    protected TaskList tasks;
    private Storage storage;
    private Ui ui;
    private boolean isExit = false;

    public Siri(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (Exception e) {
            tasks = new TaskList();
        }
    }

    /**
     * Overloaded Siri constructor that takes in no arguments
     */
    public Siri() {
        this(FILE_PATH);
    }

    /**
     * This method is used by GUI and gets the response from Siri for the given input command.
     *
     * @param input the user input command
     * @return the response from Siri
     */
    public String getResponse(String input) {
        try {
            if (input.trim().equalsIgnoreCase("bye")) {
                isExit = true;
                return ui.getGoodbyeMessage();
            }

            String response = executeCommand(input);
            storage.save(tasks.getAll());
            return response;
        } catch (SiriException | IOException e) {
            return ui.getErrorMessage(e.getMessage());
        }
    }

    private String executeCommand(String command) throws SiriException {
        String[] parsedCommand = Parser.parse(command);
        String argument = parsedCommand[0].toLowerCase();
        String userAction = parsedCommand.length > 1 ? parsedCommand[1] : "";

        switch (argument) {
        case "bye":
            isExit = true;
            return ui.getGoodbyeMessage();

        case "list":
            return ui.getTaskListMessage(tasks.getAll());

        case "mark":
            return performMarkAction(userAction, true);

        case "unmark":
            return performMarkAction(userAction, false);

        case "todo":
            return performTodoAction(userAction);

        case "event":
            return performEventAction(userAction);

        case "deadline":
            return performDeadlineAction(userAction);

        case "delete":
            return performDeleteAction(userAction);

        case "find":
            return performFindAction(userAction);

        default:
            throw new InvalidCommandException("Sorry :((( I don't know what that means");
        }
    }

    protected String performMarkAction(String description, boolean isMark) throws SiriException {
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
            return ui.getTaskMarkedMessage(task, isMark);
        } catch (IndexOutOfBoundsException e) {
            throw new TaskNotFoundException("Oops!! The task number provided does not exist :(");
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("Sorry, please key in a valid task number!");
        }
    }

    protected String performTodoAction(String description) throws SiriException {
        if (description.isEmpty()) {
            throw new TaskNotFoundException("What is your todo task?");
        }
        Task task = new ToDo(description);
        tasks.add(task);
        return ui.getTaskAddedMessage(task, tasks.size());
    }

    protected String performEventAction(String arguments) throws SiriException {
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
        return ui.getTaskAddedMessage(task, tasks.size());
    }

    protected String performDeadlineAction(String arguments) throws SiriException {
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
            return ui.getTaskAddedMessage(task, tasks.size());
        } catch (java.time.format.DateTimeParseException e) {
            throw new InvalidCommandException("Please enter a valid date/time format (yyyy-MM-dd HHmm). Example: 2025-12-29 1800");
        }
    }

    protected String performDeleteAction(String arguments) throws SiriException {
        if (arguments.isEmpty()) {
            throw new InvalidCommandException("Please specify a task number to delete");
        }

        try {
            int index = Integer.parseInt(arguments) - 1;
            Task removedTask = tasks.remove(index);
            return ui.getTaskDeletedMessage(removedTask, tasks.size());
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
    protected String performFindAction(String keyword) throws SiriException {
        if (keyword.isEmpty()) {
            throw new InvalidCommandException("Please specify a keyword to search for. Example: find book");
        }

        List<Task> matchingTasks = tasks.findTasks(keyword);
        return ui.getMatchingTasksMessage(matchingTasks, keyword);
    }

    public static void main(String[] args) {
        new Siri(FILE_PATH).run();
    }

    private void run() {
        ui.sayWelcome();
        Scanner scanner = new Scanner(System.in);

        while (!isExit) {
            String command = scanner.nextLine();
            try {
                String response = executeCommand(command);
                System.out.println(response);
                storage.save(tasks.getAll());
            } catch (SiriException | IOException e) {
                System.out.println(ui.getErrorMessage(e.getMessage()));
            }
        }
    }
}