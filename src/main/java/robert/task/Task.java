package robert.task;

/**
 * Represents a generic task with a description, completion status, and type.
 */
public class Task {
    private String description;
    private boolean isDone;
    private TaskType type;

    /**
     * Constructs a Task with the given description and type.
     *
     * @param description Description of the task.
     * @param type Type of the task (TODO, DEADLINE, EVENT).
     */
    public Task(String description, TaskType type) {
        this.description = description;
        this.isDone = false;
        this.type = type;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the status icon representing whether the task is done.
     *
     * @return "X" if done, " " otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns the type of this task.
     *
     * @return TaskType of the task.
     */
    public TaskType getType() {
        return type;
    }

    /**
     * Gets the description of the task.
     *
     * @return The task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the string representation of the task.
     *
     * @return String representation.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
