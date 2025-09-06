package robert.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task with a due date and time.
 */
public class Deadline extends Task {
    private LocalDateTime by;

    /**
     * Constructs a Deadline with a description and due date/time.
     *
     * @param description Description of the deadline.
     * @param by Due date and time.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description, TaskType.DEADLINE);
        assert by != null : "Deadline date/time should not be null";
        this.by = by;
    }

    /**
     * Constructs a Deadline from a description and a formatted date string.
     *
     * @param description Description of the deadline.
     * @param by Date/time string in "MMM d yyyy, h:mm a" format.
     */
    public Deadline(String description, String by) {
        super(description, TaskType.DEADLINE);
        this.by = LocalDateTime.parse(by, DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a"));
    }

    /**
     * Gets the deadline date and time.
     *
     * @return The deadline LocalDateTime.
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Returns the string representation of the deadline.
     *
     * @return String representation.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a")) + ")";
    }
}
