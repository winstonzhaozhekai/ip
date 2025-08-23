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
                    switch (type) {
                        case "T":
                            tasks.add(new Todo(description));
                            break;
                        case "D":
                            tasks.add(new Deadline(description, parts[3]));
                            break;
                        case "E":
                            tasks.add(new Event(description, parts[3], parts[4]));
                            break;
                    }
                    if (isDone) {
                        tasks.get(tasks.size() - 1).markAsDone();
                    }
                }
            } catch (Exception e) {
                System.out.println("Warning: Data file is corrupted. Starting with an empty task list.");
            }
        }
        return tasks;
    }

    public void save(ArrayList<Task> tasks) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Task task : tasks) {
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