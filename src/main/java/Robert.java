import java.util.Scanner;

public class Robert {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] tasks = new String[100];
        int count = 0;

        System.out.println("____________________________________________________________");
        System.out.println(" Wassup chat! I'm Robert");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");

        while (true) {
            String input = sc.nextLine();

            System.out.println("____________________________________________________________");
            if (input.equals("bye")) {
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if (input.equals("list")) {
                for (int i = 0; i < count; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks[i]);
                }
                System.out.println("____________________________________________________________");
            } else {
                if (count >= tasks.length) {
                    System.out.println(" Sorry, the list has hit limit of 100 items.");
                } else {
                    tasks[count++] = input;
                    System.out.println(" added: " + input);
                }
                System.out.println("____________________________________________________________");
            }
        }

        sc.close();
    }
}
