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
import java.io.IOException;

public class Robert {
    private static final String FILE_PATH = "./data/duke.txt";
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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
                    handleMark(input);
                } else if (command.equals("unmark")) {
                    handleUnmark(input);
                } else if (command.equals("todo")) {
                    handleTodo(input);
                } else if (command.equals("deadline")) {
                    handleDeadline(input);
                } else if (command.equals("event")) {
                    handleEvent(input);
                } else if (command.equals("delete")) {
                    handleDelete(input);
                } else {
                    throw new RobertException("Only 'list', 'mark <num>', 'unmark <num>', 'todo <desc>', 'deadline <desc> /by <time>', 'event <desc> /from <start> /to <end>', 'delete <num>', and 'bye' commands are supported.");
                }
            } catch (RobertException | IOException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
        
        ui.close();
    }

    private void handleMark(String input) throws RobertException, IOException {
        int index = Parser.parseTaskIndex(input);
        if (index < 0 || index >= tasks.size()) {
            throw new RobertException("Task number out of range. You have " + tasks.size() + " task(s).");
        }
        tasks.markTask(index);
        ui.showTaskMarked(tasks.get(index));
        storage.save(tasks);
    }

    private void handleUnmark(String input) throws RobertException, IOException {
        int index = Parser.parseTaskIndex(input);
        if (index < 0 || index >= tasks.size()) {
            throw new RobertException("Task number out of range. You have " + tasks.size() + " task(s).");
        }
        tasks.unmarkTask(index);
        ui.showTaskUnmarked(tasks.get(index));
        storage.save(tasks);
    }

    private void handleTodo(String input) throws RobertException, IOException {
        Todo todo = Parser.parseTodo(input);
        tasks.add(todo);
        ui.showTaskAdded(todo, tasks.size());
        storage.save(tasks);
    }

    private void handleDeadline(String input) throws RobertException, IOException {
        Deadline deadline = Parser.parseDeadline(input);
        tasks.add(deadline);
        ui.showTaskAdded(deadline, tasks.size());
        storage.save(tasks);
    }

    private void handleEvent(String input) throws RobertException, IOException {
        Event event = Parser.parseEvent(input);
        tasks.add(event);
        ui.showTaskAdded(event, tasks.size());
        storage.save(tasks);
    }

    private void handleDelete(String input) throws RobertException, IOException {
        int index = Parser.parseTaskIndex(input);
        if (index < 0 || index >= tasks.size()) {
            throw new RobertException("Task number out of range. You have " + tasks.size() + " task(s).");
        }
        Task removedTask = tasks.remove(index);
        ui.showTaskDeleted(removedTask, tasks.size());
        storage.save(tasks);
    }

    public static void main(String[] args) {
        new Robert(FILE_PATH).run();
    }
}
