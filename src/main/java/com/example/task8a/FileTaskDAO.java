package com.example.task8a;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileTaskDAO implements TaskDAO {
    private String fileName;

    public FileTaskDAO(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Преобразование строки в объект Task и добавление его в список tasks
                Task task = parseTaskFromLine(line);
                tasks.add(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public Task getTaskById(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = parseTaskFromLine(line);
                if (task.getId() == id) {
                    return task;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addTask(Task task) {
        List<Task> tasks = getAllTasks();
        int maxId = tasks.stream().mapToInt(Task::getId).max().orElse(0);
        task.setId(maxId + 1);
        tasks.add(task);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Task t : tasks) {
                String line = taskToString(t);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTask(Task task) {
        List<Task> tasks = getAllTasks();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Task t : tasks) {
                if (t.getId() == task.getId()) {
                    // Обновляем задачу
                    t.setName(task.getName());
                    t.setTime(task.getTime());
                    t.setStatus(task.getStatus());
                }
                String line = taskToString(t);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTask(int id) {
        List<Task> tasks = getAllTasks();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Task t : tasks) {
                if (t.getId() != id) {
                    String line = taskToString(t);
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Task parseTaskFromLine(String line) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        String time = parts[2];
        String status = parts[3];
        return new Task(id, name, time, status);
    }

    private String taskToString(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(task.getId()).append(",");
        sb.append(task.getName()).append(",");
        sb.append(task.getTime()).append(",");
        sb.append(task.getStatus());
        return sb.toString();
    }
}
