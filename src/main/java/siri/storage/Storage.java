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
 * Saves the tasks in the hard disk automatically whenever the task list changes and
 * loads the data from the hard disk when the chatbot starts up.
 */

public class Storage {
    private Path filePath;

    public Storage(String path) {
        this.filePath = Paths.get(path);
    }

    public List<Task> load() throws IOException {
        List<Task> tasks = new ArrayList<>();
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
            return tasks;
        }

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) {
                Task task = Task.fromFileString(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }

    public void save(List<Task> tasks) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            for (Task task : tasks) {
                bw.write(task.toFileString());
                bw.newLine();
            }
        }
    }
}