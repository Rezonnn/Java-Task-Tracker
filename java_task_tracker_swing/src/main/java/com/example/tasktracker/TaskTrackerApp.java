package com.example.tasktracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskTrackerApp extends JFrame {

    private final DefaultListModel<Task> listModel = new DefaultListModel<>();
    private final JList<Task> taskList = new JList<>(listModel);
    private final JTextField inputField = new JTextField();
    private final JLabel statusLabel = new JLabel("Welcome!");
    private final TaskRepository repository = new TaskRepository("tasks.txt");

    public TaskTrackerApp() {
        super("Task Tracker – Java Swing");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 420);
        setLocationRelativeTo(null);

        initUi();
        loadTasks();
    }

    private void initUi() {
        // Layout: top input, center list, bottom buttons + status
        setLayout(new BorderLayout(8, 8));

        JPanel topPanel = new JPanel(new BorderLayout(6, 6));
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 0, 8));
        topPanel.add(new JLabel("New task:"), BorderLayout.WEST);
        topPanel.add(inputField, BorderLayout.CENTER);
        JButton addButton = new JButton("Add");
        topPanel.add(addButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setCellRenderer(new TaskCellRenderer());
        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Tasks"));
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton toggleDoneButton = new JButton("Toggle Done");
        JButton deleteButton = new JButton("Delete");
        JButton clearDoneButton = new JButton("Clear Completed");
        buttonsPanel.add(toggleDoneButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(clearDoneButton);

        bottomPanel.add(buttonsPanel, BorderLayout.NORTH);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(4, 2, 0, 2));
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        // Actions
        addButton.addActionListener(this::onAdd);
        inputField.addActionListener(this::onAdd);
        toggleDoneButton.addActionListener(e -> onToggleDone());
        deleteButton.addActionListener(e -> onDelete());
        clearDoneButton.addActionListener(e -> onClearCompleted());
    }

    private void loadTasks() {
        List<Task> tasks = repository.load();
        tasks.sort(Comparator.comparing(Task::isDone).thenComparing(Task::getId));
        for (Task t : tasks) {
            listModel.addElement(t);
        }
        updateStatus();
    }

    private void saveTasks() {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < listModel.getSize(); i++) {
            tasks.add(listModel.get(i));
        }
        repository.save(tasks);
        updateStatus();
    }

    private int nextId() {
        int max = 0;
        for (int i = 0; i < listModel.getSize(); i++) {
            max = Math.max(max, listModel.get(i).getId());
        }
        return max + 1;
    }

    private void onAdd(ActionEvent e) {
        String text = inputField.getText().trim();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Task title cannot be empty.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Task t = new Task(nextId(), text, false);
        listModel.addElement(t);
        inputField.setText("");
        saveTasks();
    }

    private void onToggleDone() {
        Task selected = taskList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a task first.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        selected.setDone(!selected.isDone());
        taskList.repaint();
        saveTasks();
    }

    private void onDelete() {
        int idx = taskList.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "Select a task to delete.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int result = JOptionPane.showConfirmDialog(this, "Delete selected task?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            listModel.remove(idx);
            saveTasks();
        }
    }

    private void onClearCompleted() {
        List<Task> toKeep = new ArrayList<>();
        for (int i = 0; i < listModel.getSize(); i++) {
            Task t = listModel.get(i);
            if (!t.isDone()) {
                toKeep.add(t);
            }
        }
        listModel.clear();
        for (Task t : toKeep) {
            listModel.addElement(t);
        }
        saveTasks();
    }

    private void updateStatus() {
        int total = listModel.getSize();
        int done = 0;
        for (int i = 0; i < total; i++) {
            if (listModel.get(i).isDone()) {
                done++;
            }
        }
        statusLabel.setText("Total: " + total + "  |  Completed: " + done + "  |  Pending: " + (total - done));
    }

    // Custom renderer to gray out completed tasks
    private static class TaskCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Task task) {
                if (task.isDone()) {
                    c.setForeground(Color.GRAY);
                } else {
                    c.setForeground(new Color(0, 102, 204));
                }
            }
            return c;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            TaskTrackerApp app = new TaskTrackerApp();
            app.setVisible(true);
        });
    }
}
