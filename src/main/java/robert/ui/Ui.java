package robert.ui;

import robert.task.Task;
import robert.task.TaskList;
import java.util.Scanner;

/**
 * Handles user interaction and input/output for the Robert chatbot.
 */
public class Ui {
    private Scanner scanner;

    /**
     * Constructs a Ui instance for user interaction.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message.
     */
    public void showWelcome() {
        showLine();
        System.out.println(" Wassup chat! I'm Robert");
        System.out.println(" What can I do for you?");
        showLine();
    }

    /**
     * Displays the goodbye message.
     */
    public void showGoodbye() {
        System.out.println(" Bye. Hope to see you again soon!");
    }

    /**
     * Displays a horizontal line for formatting.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println(" " + message);
    }

    /**
     * Displays a loading error message.
     */
    public void showLoadingError() {
        System.out.println("Error loading tasks. Starting with an empty task list.");
    }

    /**
     * Displays a message when a task is added.
     *
     * @param task The task added.
     * @param taskCount The current number of tasks.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " task(s) in the list.");
    }

    /**
     * Displays a message when a task is marked as done.
     *
     * @param task The task marked as done.
     */
    public void showTaskMarked(Task task) {
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task);
    }

    /**
     * Displays a message when a task is marked as not done.
     *
     * @param task The task marked as not done.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
    }

    /**
     * Displays a message when a task is deleted.
     *
     * @param task The task deleted.
     * @param taskCount The current number of tasks.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " task(s) in the list.");
    }

    /**
     * Displays the list of tasks.
     *
     * @param tasks The TaskList to display.
     */
    public void showTaskList(TaskList tasks) {
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + tasks.get(i));
        }
    }

    /**
     * Displays the list of matching tasks from a search.
     *
     * @param tasks The TaskList containing matching tasks.
     */
    public void showMatchingTasks(TaskList tasks) {
        if (tasks.size() == 0) {
            System.out.println(" No matching tasks found.");
        } else {
            System.out.println(" Here are the matching tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i));
            }
        }
    }

    /**
     * Reads a command from the user.
     *
     * @return The user's input command.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Closes the scanner and releases resources.
     */
    public void close() {
        scanner.close();
    }
}
