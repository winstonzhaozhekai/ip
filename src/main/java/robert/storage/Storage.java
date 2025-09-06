package robert.storage;

import robert.task.Task;
import robert.task.TaskList;
import robert.task.Todo;
import robert.task.Deadline;
import robert.task.Event;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Handles loading and saving of tasks to a file for persistence.
 */
public class Storage {
    private final File file;
    private static final DateTimeFormatter STORAGE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Constructs a Storage object for the given file path.
     *
     * @param filePath Path to the file for storing tasks.
     */
    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    /**
     * Loads tasks from the file.
     *
     * @return ArrayList of loaded tasks.
     * @throws IOException If an I/O error occurs.
     */
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!file.exists()) {
            assert file.getParentFile() != null : "Parent directory should not be null";
            file.getParentFile().mkdirs(); // Create directories if they don't exist
            file.createNewFile(); // Create the file if it doesn't exist
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(" \\| ");
                    if (parts.length < 3) continue; // Skip malformed lines

                    assert parts[0] != null : "Task type should not be null";
                    assert parts[1] != null : "Task status should not be null";
                    assert parts[2] != null : "Task description should not be null";
                    
                    String type = parts[0];
                    boolean isDone = parts[1].equals("1");
                    String description = parts[2];
                    Task task = null;
                    
                    try {
                        switch (type) {
                            case "T":
                                task = new Todo(description);
                                break;
                            case "D":
                                if (parts.length >= 4) {
                                    LocalDateTime by = LocalDateTime.parse(parts[3], STORAGE_FORMAT);
                                    task = new Deadline(description, by);
                                }
                                break;
                            case "E":
                                if (parts.length >= 5) {
                                    LocalDateTime from = LocalDateTime.parse(parts[3], STORAGE_FORMAT);
                                    LocalDateTime to = LocalDateTime.parse(parts[4], STORAGE_FORMAT);
                                    task = new Event(description, from, to);
                                }
                                break;
                        }
                        if (task != null) {
                            if (isDone) {
                                task.markAsDone();
                            }
                            tasks.add(task);
                        }
                    } catch (Exception e) {
                        System.out.println("Warning: Skipping corrupted task: " + line);
                    }
                }
            } catch (Exception e) {
                System.out.println("Warning: Data file is corrupted. Starting with an empty task list.");
            }
        }
        return tasks;
    }

    /**
     * Saves the given TaskList to the file.
     *
     * @param taskList The TaskList to save.
     * @throws IOException If an I/O error occurs.
     */
    public void save(TaskList taskList) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < taskList.size(); i++) {
                Task task = taskList.get(i);
                StringBuilder sb = new StringBuilder();
                
                if (task instanceof Todo) {
                    sb.append("T | ");
                    sb.append(task.getStatusIcon().equals("X") ? "1" : "0").append(" | ");
                    sb.append(task.getDescription());
                } else if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;
                    sb.append("D | ");
                    sb.append(task.getStatusIcon().equals("X") ? "1" : "0").append(" | ");
                    sb.append(task.getDescription()).append(" | ");
                    sb.append(deadline.getBy().format(STORAGE_FORMAT));
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    sb.append("E | ");
                    sb.append(task.getStatusIcon().equals("X") ? "1" : "0").append(" | ");
                    sb.append(task.getDescription()).append(" | ");
                    sb.append(event.getFrom().format(STORAGE_FORMAT)).append(" | ");
                    sb.append(event.getTo().format(STORAGE_FORMAT));
                }
                bw.write(sb.toString());
                bw.newLine();
            }
        }
    }
}
