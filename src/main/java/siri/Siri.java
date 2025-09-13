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
        assert tasks != null : "TaskList should be initialised";
        assert ui != null : "UI should be initialised";
        assert storage != null : "Storage should be initialised";
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
        assert input != null : "Input command should not be null";
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
        assert command != null : "Command should not be null";
        String[] parsedCommand = Parser.parseCommand(command);
        assert parsedCommand != null && parsedCommand.length > 0 : "Parsed command should not be null or empty";
        return dispatchCommand(parsedCommand);
    }

    private String dispatchCommand(String[] parsedCommand) throws SiriException {
        String argument = parsedCommand[0].toLowerCase();
        String userAction = parsedCommand.length > 1 ? parsedCommand[1] : "";

        switch (argument) {
        case "bye":
            return performExitAction();
        case "list":
            return performListAction();
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

    private String performExitAction() {
        isExit = true;
        return ui.getGoodbyeMessage();
    }

    private String performListAction() {
        return ui.getTaskListMessage(tasks.getAll());
    }

    protected String performMarkAction(String description, boolean isMark) throws SiriException {
        assert description != null : "Task description should not be null";
        assert tasks != null : "TaskList should be initialised";

        if (description.isEmpty()) {
            throw new InvalidCommandException("Hi! Please specify a task number to " + (isMark ? "mark" : "unmark"));
        }
        try {
            int index = Integer.parseInt(description) - 1;
            assert index >= 0 : "Task index should be non-negative";
            Task task = tasks.get(index);
            assert task != null : "Task at index should exist";
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
        assert description != null : "Todo description should not be null";
        if (description.isEmpty()) {
            throw new TaskNotFoundException("What is your todo task?");
        }
        Task task = new ToDo(description);
        tasks.add(task);
        assert tasks.get(tasks.size() - 1) == task : "Task should be added to the task list";
        return ui.getTaskAddedMessage(task, tasks.size());
    }

    protected String performEventAction(String arguments) throws SiriException {
        assert arguments != null : "Event arguments passed to parseEvent should not be null";
        if (arguments.isEmpty()) {
            throw new InvalidCommandException("What is your event?");
        }
        String[] parts = Parser.parseEvent(arguments);
        try {
            Task task = new Event(parts[0], parts[1], parts[2]);
            tasks.add(task);
            return ui.getTaskAddedMessage(task, tasks.size());
        } catch (java.time.format.DateTimeParseException e) {
            throw new InvalidCommandException(
                    "Please enter valid date & time formats (yyyy-MM-dd HHmm). Example: /from 2025-12-29 1800 /to 2025-12-29 2000"
            );
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException(e.getMessage());
        }
    }

    protected String performDeadlineAction(String arguments) throws SiriException {
        assert arguments != null : "Deadline arguments passed to parseDeadline should not be null";
        if (arguments.isEmpty()) {
            throw new InvalidCommandException("What is your deadline task?");
        }
        String[] parts = Parser.parseDeadline(arguments);
        try {
            Task task = new Deadline(parts[0], parts[1]);
            tasks.add(task);
            return ui.getTaskAddedMessage(task, tasks.size());
        } catch (java.time.format.DateTimeParseException e) {
            throw new InvalidCommandException(
                    "Please enter a valid date & time format (yyyy-MM-dd HHmm). Example: /by 2025-12-29 1800");
        }
    }

    protected String performDeleteAction(String arguments) throws SiriException {
        assert arguments != null : "Delete argument should not be null";
        if (arguments.isEmpty()) {
            throw new InvalidCommandException("Please specify a task number to delete");
        }

        try {
            int index = Integer.parseInt(arguments) - 1;
            assert index >= 0 : "Task index should be non-negative";
            Task removedTask = tasks.remove(index);
            assert removedTask != null : "Removed task should exist";
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
        assert keyword != null : "Search keyword should not be null";
        if (keyword.isEmpty()) {
            throw new InvalidCommandException("Please specify a keyword to search for. Example: find book");
        }

        List<Task> matchingTasks = tasks.findTasks(keyword);
        assert matchingTasks != null : "Matching tasks list should not be null";
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