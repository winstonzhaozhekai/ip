package robert;

import robert.storage.Storage;
import robert.task.TaskList;
import robert.task.Task;
import robert.task.Todo;
import robert.task.Deadline;
import robert.task.Event;
import robert.ui.Ui;
import robert.parser.Parser;
import robert.exception.RobertException;
import robert.exception.DuplicateTaskException;
import java.io.IOException;

/**
 * Main class for the Robert chatbot application.
 * Handles the main program flow and user command processing.
 */
public class Robert {
    private static final String FILE_PATH = "./data/duke.txt";
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a new Robert instance with the specified file path for storage.
     *
     * @param filePath Path to the file used for saving and loading tasks.
     */
    public Robert(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            String command = Parser.parseCommand(input);
            
            if (command.equals("bye")) {
                return "Bye. Hope to see you again soon!";
            } else if (command.equals("list")) {
                return getTaskListString();
            } else if (command.equals("mark")) {
                return handleMark(input);
            } else if (command.equals("unmark")) {
                return handleUnmark(input);
            } else if (command.equals("todo")) {
                return handleTodo(input);
            } else if (command.equals("deadline")) {
                return handleDeadline(input);
            } else if (command.equals("event")) {
                return handleEvent(input);
            } else if (command.equals("delete")) {
                return handleDelete(input);
            } else if (command.equals("find")) {
                return handleFind(input);
            } else {
                return "Only 'list', 'mark <num>', 'unmark <num>', 'todo <desc>', 'deadline <desc> /by <time>', 'event <desc> /from <start> /to <end>', 'delete <num>', 'find <keyword>', and 'bye' commands are supported.";
            }
        } catch (DuplicateTaskException e) {
            return "This task already exists in your list:\n  " + e.getMessage();
        } catch (RobertException | IOException e) {
            return e.getMessage();
        }
    }

    private String getTaskListString() {
        if (tasks.size() == 0) {
            return "You have no tasks in your list.";
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Handles the "mark" command to mark a task as done.
     *
     * @param input The full user input string.
     * @return Response message.
     * @throws RobertException If the task index is invalid.
     * @throws IOException If saving tasks fails.
     */
    private String handleMark(String input) throws RobertException, IOException {
        int index = Parser.parseTaskIndex(input);
        if (index < 0 || index >= tasks.size()) {
            throw new RobertException("Task number out of range. You have " + tasks.size() + " task(s).");
        }
        tasks.markTask(index);
        storage.save(tasks);
        return "Nice! I've marked this task as done:\n  " + tasks.get(index);
    }

    /**
     * Handles the "unmark" command to unmark a task as not done.
     *
     * @param input The full user input string.
     * @return Response message.
     * @throws RobertException If the task index is invalid.
     * @throws IOException If saving tasks fails.
     */
    private String handleUnmark(String input) throws RobertException, IOException {
        int index = Parser.parseTaskIndex(input);
        if (index < 0 || index >= tasks.size()) {
            throw new RobertException("Task number out of range. You have " + tasks.size() + " task(s).");
        }
        tasks.unmarkTask(index);
        storage.save(tasks);
        return "OK, I've marked this task as not done yet:\n  " + tasks.get(index);
    }

    /**
     * Handles the "todo" command to add a new Todo task.
     *
     * @param input The full user input string.
     * @return Response message.
     * @throws RobertException If the input is invalid.
     * @throws IOException If saving tasks fails.
     */
    private String handleTodo(String input) throws RobertException, IOException {
        Todo todo = Parser.parseTodo(input);
        
        // Check for duplicates
        if (tasks.isDuplicate(todo)) {
            Task existingTask = tasks.findDuplicate(todo);
            throw new DuplicateTaskException(existingTask.toString());
        }
        
        tasks.add(todo);
        storage.save(tasks);
        return "Got it. I've added this task:\n  " + todo + "\nNow you have " + tasks.size() + " task(s) in the list.";
    }

    /**
     * Handles the "deadline" command to add a new Deadline task.
     *
     * @param input The full user input string.
     * @return Response message.
     * @throws RobertException If the input is invalid.
     * @throws IOException If saving tasks fails.
     */
    private String handleDeadline(String input) throws RobertException, IOException {
        Deadline deadline = Parser.parseDeadline(input);
        
        // Check for duplicates
        if (tasks.isDuplicate(deadline)) {
            Task existingTask = tasks.findDuplicate(deadline);
            throw new DuplicateTaskException(existingTask.toString());
        }
        
        tasks.add(deadline);
        storage.save(tasks);
        return "Got it. I've added this task:\n  " + deadline + "\nNow you have " + tasks.size() + " task(s) in the list.";
    }

    /**
     * Handles the "event" command to add a new Event task.
     *
     * @param input The full user input string.
     * @return Response message.
     * @throws RobertException If the input is invalid.
     * @throws IOException If saving tasks fails.
     */
    private String handleEvent(String input) throws RobertException, IOException {
        Event event = Parser.parseEvent(input);
        
        // Check for duplicates
        if (tasks.isDuplicate(event)) {
            Task existingTask = tasks.findDuplicate(event);
            throw new DuplicateTaskException(existingTask.toString());
        }
        
        tasks.add(event);
        storage.save(tasks);
        return "Got it. I've added this task:\n  " + event + "\nNow you have " + tasks.size() + " task(s) in the list.";
    }

    /**
     * Handles the "delete" command to remove a task.
     *
     * @param input The full user input string.
     * @return Response message.
     * @throws RobertException If the task index is invalid.
     * @throws IOException If saving tasks fails.
     */
    private String handleDelete(String input) throws RobertException, IOException {
        int index = Parser.parseTaskIndex(input);
        if (index < 0 || index >= tasks.size()) {
            throw new RobertException("Task number out of range. You have " + tasks.size() + " task(s).");
        }
        Task removedTask = tasks.remove(index);
        storage.save(tasks);
        return "Noted. I've removed this task:\n  " + removedTask + "\nNow you have " + tasks.size() + " task(s) in the list.";
    }

    /**
     * Handles the "find" command to search for tasks containing a keyword.
     *
     * @param input The full user input string.
     * @return Response message.
     * @throws RobertException If the keyword is invalid.
     */
    private String handleFind(String input) throws RobertException {
        String keyword = Parser.parseFind(input);
        TaskList matchingTasks = tasks.findTasks(keyword);
        if (matchingTasks.size() == 0) {
            return "No matching tasks found.";
        } else {
            StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
            for (int i = 0; i < matchingTasks.size(); i++) {
                sb.append((i + 1)).append(".").append(matchingTasks.get(i)).append("\n");
            }
            return sb.toString().trim();
        }
    }

    /**
     * Runs the command-line interface version of Robert.
     * This method is for backward compatibility with the CLI version.
     */
    public void run() {
        ui.showWelcome();
        
        while (true) {
            String input = ui.readCommand();
            ui.showLine();
            
            try {
                String command = Parser.parseCommand(input);
                
                if (command.equals("bye")) {
                    ui.showGoodbye();
                    ui.showLine();
                    break;
                } else if (command.equals("list")) {
                    ui.showTaskList(tasks);
                } else if (command.equals("mark")) {
                    int index = Parser.parseTaskIndex(input);
                    if (index < 0 || index >= tasks.size()) {
                        throw new RobertException("Task number out of range. You have " + tasks.size() + " task(s).");
                    }
                    tasks.markTask(index);
                    storage.save(tasks);
                    ui.showTaskMarked(tasks.get(index));
                } else if (command.equals("unmark")) {
                    int index = Parser.parseTaskIndex(input);
                    if (index < 0 || index >= tasks.size()) {
                        throw new RobertException("Task number out of range. You have " + tasks.size() + " task(s).");
                    }
                    tasks.unmarkTask(index);
                    storage.save(tasks);
                    ui.showTaskUnmarked(tasks.get(index));
                } else if (command.equals("todo")) {
                    Todo todo = Parser.parseTodo(input);
                    if (tasks.isDuplicate(todo)) {
                        Task existingTask = tasks.findDuplicate(todo);
                        throw new DuplicateTaskException("This task already exists in your list: " + existingTask.toString());
                    }
                    tasks.add(todo);
                    storage.save(tasks);
                    ui.showTaskAdded(todo, tasks.size());
                } else if (command.equals("deadline")) {
                    Deadline deadline = Parser.parseDeadline(input);
                    if (tasks.isDuplicate(deadline)) {
                        Task existingTask = tasks.findDuplicate(deadline);
                        throw new DuplicateTaskException("This task already exists in your list: " + existingTask.toString());
                    }
                    tasks.add(deadline);
                    storage.save(tasks);
                    ui.showTaskAdded(deadline, tasks.size());
                } else if (command.equals("event")) {
                    Event event = Parser.parseEvent(input);
                    if (tasks.isDuplicate(event)) {
                        Task existingTask = tasks.findDuplicate(event);
                        throw new DuplicateTaskException("This task already exists in your list: " + existingTask.toString());
                    }
                    tasks.add(event);
                    storage.save(tasks);
                    ui.showTaskAdded(event, tasks.size());
                } else if (command.equals("delete")) {
                    int index = Parser.parseTaskIndex(input);
                    if (index < 0 || index >= tasks.size()) {
                        throw new RobertException("Task number out of range. You have " + tasks.size() + " task(s).");
                    }
                    Task removedTask = tasks.remove(index);
                    storage.save(tasks);
                    ui.showTaskDeleted(removedTask, tasks.size());
                } else if (command.equals("find")) {
                    String keyword = Parser.parseFind(input);
                    TaskList matchingTasks = tasks.findTasks(keyword);
                    ui.showMatchingTasks(matchingTasks);
                } else {
                    throw new RobertException("Only 'list', 'mark <num>', 'unmark <num>', 'todo <desc>', 'deadline <desc> /by <time>', 'event <desc> /from <start> /to <end>', 'delete <num>', 'find <keyword>', and 'bye' commands are supported.");
                }
            } catch (DuplicateTaskException e) {
                ui.showError(e.getMessage());
            } catch (RobertException | IOException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
        
        ui.close();
    }

    /**
     * Main entry point for the Robert chatbot application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new Robert(FILE_PATH).run();
    }
}
