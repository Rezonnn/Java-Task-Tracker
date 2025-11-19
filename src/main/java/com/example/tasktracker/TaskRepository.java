package com.example.tasktracker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private final Path filePath;

    public TaskRepository(String fileName) {
        this.filePath = Path.of(fileName);
    }

    public List<Task> load() {
        List<Task> result = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return result;
        }
        try {
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            for (String line : lines) {
                if (line.isBlank()) continue;
                try {
                    result.add(Task.deserialize(line));
                } catch (IllegalArgumentException e) {
                    // skip malformed line
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load tasks: " + e.getMessage());
        }
        return result;
    }

    public void save(List<Task> tasks) {
        List<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            lines.add(t.serialize());
        }
        try {
            Files.write(filePath, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Failed to save tasks: " + e.getMessage());
        }
    }
}
