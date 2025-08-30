package robert.storage;

import robert.task.Task;
import robert.task.TaskList;
import robert.task.Todo;
import robert.task.Deadline;
import robert.task.Event;
import java.io.*;
import java.util.ArrayList;

public class Storage {
    private final File file;

    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!file.exists()) {
            file.getParentFile().mkdirs(); // Create directories if they don't exist
            file.createNewFile(); // Create the file if it doesn't exist
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(" \\| ");
                    String type = parts[0];
                    boolean isDone = parts[1].equals("1");
                    String description = parts[2];
                    Task task = null;
                    switch (type) {
                        case "T":
                            task = new Todo(description);
                            break;
                        case "D":
                            task = new Deadline(description, parts[3]);
                            break;
                        case "E":
                            task = new Event(description, parts[3], parts[4]);
                            break;
                    }
                    if (task != null) {
                        if (isDone) {
                            task.markAsDone();
                        }
                        tasks.add(task);
                    }
                }
            } catch (Exception e) {
                System.out.println("Warning: Data file is corrupted. Starting with an empty task list.");
            }
        }
        return tasks;
    }

    public void save(TaskList taskList) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < taskList.size(); i++) {
                Task task = taskList.get(i);
                StringBuilder sb = new StringBuilder();
                if (task instanceof Todo) {
                    sb.append("T | ");
                    sb.append(task.getStatusIcon().equals("X") ? "1" : "0").append(" | ");
                    sb.append(task.toString().substring(7));
                } else if (task instanceof Deadline) {
                    sb.append("D | ");
                    sb.append(task.getStatusIcon().equals("X") ? "1" : "0").append(" | ");
                    String fullString = task.toString();
                    String description = fullString.substring(7, fullString.indexOf(" (by: "));
                    String deadline = fullString.substring(fullString.indexOf("(by: ") + 5, fullString.length() - 1);
                    sb.append(description).append(" | ").append(deadline);
                } else if (task instanceof Event) {
                    sb.append("E | ");
                    sb.append(task.getStatusIcon().equals("X") ? "1" : "0").append(" | ");
                    String fullString = task.toString();
                    String description = fullString.substring(7, fullString.indexOf(" (from: "));
                    String[] eventParts = fullString.split(" \\(from: | to: |\\)");
                    sb.append(description).append(" | ").append(eventParts[1]).append(" | ").append(eventParts[2]);
                }
                bw.write(sb.toString());
                bw.newLine();
            }
        }
    }
}
