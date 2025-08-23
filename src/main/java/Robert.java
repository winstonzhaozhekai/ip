import java.util.Scanner;

public class Robert {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int count = 0;

        System.out.println("____________________________________________________________");
        System.out.println(" Wassup chat! I'm Robert");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");

        while (true) {
            String input = sc.nextLine().trim();

            System.out.println("____________________________________________________________");
            try {
                if (input.equals("bye")) {
                    System.out.println(" Bye. Hope to see you again soon!");
                    System.out.println("____________________________________________________________");
                    break;
                } else if (input.equals("list")) {
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < count; i++) {
                        System.out.println(" " + (i + 1) + ". " + tasks[i]);
                    }
                    System.out.println("____________________________________________________________");
                } else if (input.startsWith("mark ")) {
                    Integer idx = parseIndex(input);
                    if (idx == null) {
                        throw new RobertException("Please provide a valid task number to mark, e.g., 'mark 2'.");
                    } else if (idx < 1 || idx > count) {
                        throw new RobertException("Task number out of range. You have " + count + " task(s).");
                    } else {
                        Task t = tasks[idx - 1];
                        t.markAsDone();
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + t);
                    }
                    System.out.println("____________________________________________________________");
                } else if (input.startsWith("unmark ")) {
                    Integer idx = parseIndex(input);
                    if (idx == null) {
                        throw new RobertException("Please provide a valid task number to unmark, e.g., 'unmark 2'.");
                    } else if (idx < 1 || idx > count) {
                        throw new RobertException("Task number out of range. You have " + count + " task(s).");
                    } else {
                        Task t = tasks[idx - 1];
                        t.markAsNotDone();
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + t);
                    }
                    System.out.println("____________________________________________________________");
                } else if (input.startsWith("todo ")) {
                    String description = input.substring(5).trim();
                    if (description.isEmpty()) {
                        throw new RobertException("The description of a todo cannot be empty.");
                    } else {
                        tasks[count++] = new Todo(description);
                        System.out.println(" Got it. I've added this task:");
                        System.out.println("   " + tasks[count - 1]);
                        System.out.println(" Now you have " + count + " task(s) in the list.");
                    }
                    System.out.println("____________________________________________________________");
                } else if (input.startsWith("deadline ")) {
                    String[] parts = input.substring(9).split(" /by ", 2);
                    if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                        throw new RobertException("Please provide a valid description and deadline, e.g., 'deadline return book /by Sunday'.");
                    } else {
                        tasks[count++] = new Deadline(parts[0].trim(), parts[1].trim());
                        System.out.println(" Got it. I've added this task:");
                        System.out.println("   " + tasks[count - 1]);
                        System.out.println(" Now you have " + count + " task(s) in the list.");
                    }
                    System.out.println("____________________________________________________________");
                } else if (input.startsWith("event ")) {
                    String[] parts = input.substring(6).split(" /from ", 2);
                    if (parts.length < 2 || parts[0].trim().isEmpty()) {
                        throw new RobertException("Please provide a valid description and event time, e.g., 'event project meeting /from Mon 2pm /to 4pm'.");
                    } else {
                        String[] timeParts = parts[1].split(" /to ", 2);
                        if (timeParts.length < 2 || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
                            throw new RobertException("Please provide both start and end times, e.g., 'event project meeting /from Mon 2pm /to 4pm'.");
                        } else {
                            tasks[count++] = new Event(parts[0].trim(), timeParts[0].trim(), timeParts[1].trim());
                            System.out.println(" Got it. I've added this task:");
                            System.out.println("   " + tasks[count - 1]);
                            System.out.println(" Now you have " + count + " task(s) in the list.");
                        }
                    }
                    System.out.println("____________________________________________________________");
                } else {
                    throw new RobertException("Only 'list', 'mark <num>', 'unmark <num>', 'todo <desc>', 'deadline <desc> /by <time>', 'event <desc> /from <start> /to <end>', and 'bye' commands are supported.");
                }
            } catch (RobertException e) {
                System.out.println(" " + e.getMessage());
                System.out.println("____________________________________________________________");
            }
        }

        sc.close();
    }

    private static Integer parseIndex(String input) {
        String[] parts = input.split("\\s+");
        if (parts.length != 2) return null;
        try {
            return Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
