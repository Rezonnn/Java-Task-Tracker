package com.example.tasktracker;

public class Task {
    private final int id;
    private String title;
    private boolean done;

    public Task(int id, String title, boolean done) {
        this.id = id;
        this.title = title;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        String checkbox = done ? "[x]" : "[ ]";
        return String.format("%s (%d) %s", checkbox, id, title);
    }

    // Very simple serialization: id|done|title
    public String serialize() {
        String safeTitle = title.replace("|", " "); // avoid splitting issues
        return id + "|" + (done ? "1" : "0") + "|" + safeTitle;
    }

    public static Task deserialize(String line) {
        String[] parts = line.split("\\|", 3);
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid task line: " + line);
        }
        int id = Integer.parseInt(parts[0]);
        boolean done = "1".equals(parts[1]);
        String title = parts[2];
        return new Task(id, title, done);
    }
}
