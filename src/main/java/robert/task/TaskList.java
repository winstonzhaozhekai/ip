package robert.task;

import java.util.ArrayList;

/**
 * Represents a list of tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList from an existing list of tasks.
     *
     * @param tasks List of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task Task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Checks if a task already exists in the list.
     * Two tasks are considered duplicates if they have the same type and description.
     * For Deadline and Event tasks, they must also have the same date/time.
     *
     * @param task Task to check for duplicates.
     * @return true if a duplicate exists, false otherwise.
     */
    public boolean isDuplicate(Task task) {
        for (Task existingTask : tasks) {
            if (areTasksEqual(existingTask, task)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds and returns the existing duplicate task if it exists.
     *
     * @param task Task to check for duplicates.
     * @return The existing duplicate task, or null if no duplicate exists.
     */
    public Task findDuplicate(Task task) {
        for (Task existingTask : tasks) {
            if (areTasksEqual(existingTask, task)) {
                return existingTask;
            }
        }
        return null;
    }

    /**
     * Checks if two tasks are equal based on their type, description, and date/time (if applicable).
     *
     * @param task1 First task to compare.
     * @param task2 Second task to compare.
     * @return true if tasks are equal, false otherwise.
     */
    private boolean areTasksEqual(Task task1, Task task2) {
        // Check if types are different
        if (task1.getType() != task2.getType()) {
            return false;
        }

        // Check if descriptions are different (case-insensitive)
        if (!task1.getDescription().equalsIgnoreCase(task2.getDescription())) {
            return false;
        }

        // For Deadline tasks, also compare the deadline
        if (task1 instanceof Deadline && task2 instanceof Deadline) {
            Deadline deadline1 = (Deadline) task1;
            Deadline deadline2 = (Deadline) task2;
            return deadline1.getBy().equals(deadline2.getBy());
        }

        // For Event tasks, also compare start and end times
        if (task1 instanceof Event && task2 instanceof Event) {
            Event event1 = (Event) task1;
            Event event2 = (Event) task2;
            return event1.getFrom().equals(event2.getFrom()) && 
                   event1.getTo().equals(event2.getTo());
        }

        // For Todo tasks, only description matters (already checked above)
        return true;
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param index Index of the task to remove.
     * @return The removed task.
     */
    public Task remove(int index) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds in remove";
        return tasks.remove(index);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index Index of the task.
     * @return The task at the index.
     */
    public Task get(int index) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds in get";
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index Index of the task.
     */
    public void markTask(int index) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds in markTask";
        tasks.get(index).markAsDone();
    }

    /**
     * Marks the task at the specified index as not done.
     *
     * @param index Index of the task.
     */
    public void unmarkTask(int index) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds in unmarkTask";
        tasks.get(index).markAsNotDone();
    }

    /**
     * Returns the list of tasks.
     *
     * @return ArrayList of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Finds tasks that contain the specified keyword in their description.
     *
     * @param keyword The keyword to search for.
     * @return A new TaskList containing matching tasks.
     */
    public TaskList findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.toString().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        return new TaskList(matchingTasks);
    }
}
