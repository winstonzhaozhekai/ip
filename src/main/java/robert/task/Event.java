package robert.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task with a start and end time.
 */
public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Constructs an Event with a description, start, and end time.
     *
     * @param description Description of the event.
     * @param from Start date and time.
     * @param to End date and time.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description, TaskType.EVENT);
        assert from != null : "Event start time should not be null";
        assert to != null : "Event end time should not be null";
        this.from = from;
        this.to = to;
    }

    /**
     * Constructs an Event from a description and formatted date strings.
     *
     * @param description Description of the event.
     * @param from Start date/time string in "MMM d yyyy, h:mm a" format.
     * @param to End time string in "h:mm a" format.
     */
    public Event(String description, String from, String to) {
        super(description, TaskType.EVENT);
        this.from = LocalDateTime.parse(from, DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a"));
        this.to = LocalDateTime.parse(to, DateTimeFormatter.ofPattern("h:mm a"));
        // Adjust the 'to' time to be on the same day as 'from'
        this.to = this.from.withHour(this.to.getHour()).withMinute(this.to.getMinute());
    }

    /**
     * Gets the start date and time.
     *
     * @return The start LocalDateTime.
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Gets the end date and time.
     *
     * @return The end LocalDateTime.
     */
    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Returns the string representation of the event.
     *
     * @return String representation.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a")) +
                " to: " + to.format(DateTimeFormatter.ofPattern("h:mm a")) + ")";
    }
}
