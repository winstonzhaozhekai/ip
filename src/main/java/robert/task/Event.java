package robert.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }

    // Constructor for loading from storage
    public Event(String description, String from, String to) {
        super(description, TaskType.EVENT);
        this.from = LocalDateTime.parse(from, DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a"));
        this.to = LocalDateTime.parse(to, DateTimeFormatter.ofPattern("h:mm a"));
        // Adjust the 'to' time to be on the same day as 'from'
        this.to = this.from.withHour(this.to.getHour()).withMinute(this.to.getMinute());
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a")) +
                " to: " + to.format(DateTimeFormatter.ofPattern("h:mm a")) + ")";
    }
}
