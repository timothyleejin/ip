package siri.storage;

import siri.tasktypes.Task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Saves the tasks in the hard disk automatically whenever the tasks are created
 * and the task list changes. It loads the data from the hard disk when the chatbot starts up.
 * The storage system ensures that task data persists between application sessions,
 * providing a seamless user experience across multiple runs of the application.
 *
 * @see Task
 * @see #load()
 * @see #save(List)
 */
public class Storage {
    private Path filePath;

    /**
     * Constructs a new Storage instance with the specified file path.
     * The path should point to the location where task data will be stored and loaded from.
     *
     * @param path the file system path where task data will be persisted.
     *             This can be a relative or absolute path to the data file.
     */
    public Storage(String path) {
        this.filePath = Paths.get(path);
    }

    /**
     * Loads tasks from the storage file.
     * If the file does not exist, creates the necessary directories and file first.
     *
     * @return a List of Task objects loaded from the storage file.
     *         Returns an empty list if the file is empty or does not exist.
     * @throws IOException if an I/O error occurs during file reading or creation,
     *         such as disk errors.
     * @see Task#fromFileString(String)
     */
    public List<Task> load() throws IOException {
        List<Task> tasks = new ArrayList<>();
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
            return tasks;
        }

        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Task task = Task.fromFileString(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }

    /**
     * Saves the current list of tasks to the storage file.
     * Each task is converted to a different representation using {@link Task#toFileString()}.
     * It is then written to the file, with each task on a separate line.
     *
     * @param tasks the list of Task objects to be persisted to disk.
     * @throws IOException if an I/O error occurs during file writing,
     *         such as disk or storage issues.
     * @see Task#toFileString()
     */
    public void save(List<Task> tasks) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            for (Task task : tasks) {
                bufferedWriter.write(task.toFileString());
                bufferedWriter.newLine();
            }
        }
    }
}