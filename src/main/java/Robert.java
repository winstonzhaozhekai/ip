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
                    System.out.println(" Please provide a valid task number to mark, e.g., 'mark 2'.");
                } else if (idx < 1 || idx > count) {
                    System.out.println(" Task number out of range. You have " + count + " task(s).");
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
                    System.out.println(" Please provide a valid task number to unmark, e.g., 'unmark 2'.");
                } else if (idx < 1 || idx > count) {
                    System.out.println(" Task number out of range. You have " + count + " task(s).");
                } else {
                    Task t = tasks[idx - 1];
                    t.markAsNotDone();
                    System.out.println(" OK, I've marked this task as not done yet:");
                    System.out.println("   " + t);
                }
                System.out.println("____________________________________________________________");
            } else {
                if (count >= tasks.length) {
                    System.out.println(" Sorry, the list has hit limit of 100 items.");
                } else {
                    tasks[count++] = new Task(input);
                    System.out.println(" added: " + input);
                }
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
