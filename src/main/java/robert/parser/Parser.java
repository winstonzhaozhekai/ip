package robert.parser;

import robert.task.Todo;
import robert.task.Deadline;
import robert.task.Event;
import robert.exception.RobertException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Handles parsing of user input commands for the Robert chatbot.
 */
public class Parser {
    /**
     * Parses the command word from the user input.
     *
     * @param input The full user input string.
     * @return The command word.
     */
    public static String parseCommand(String input) {
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            return "";
        }
        return trimmed.split("\\s+")[0];
    }

    /**
     * Parses the task index from the user input.
     *
     * @param input The full user input string.
     * @return The zero-based task index.
     * @throws RobertException If the input format is invalid.
     */
    public static int parseTaskIndex(String input) throws RobertException {
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 2) {
            throw new RobertException("Please provide a valid task number.");
        }
        try {
            int index = Integer.parseInt(parts[1]);
            return index - 1; // Convert to 0-based index
        } catch (NumberFormatException e) {
            throw new RobertException("Please provide a valid task number.");
        }
    }

    /**
     * Parses a Todo task from the user input.
     *
     * @param input The full user input string.
     * @return The Todo task.
     * @throws RobertException If the description is empty or invalid.
     */
    public static Todo parseTodo(String input) throws RobertException {
        String trimmed = input.trim();
        if (trimmed.length() <= 4 || !trimmed.startsWith("todo")) {
            throw new RobertException("The description of a todo cannot be empty.");
        }
        String description = trimmed.substring(4).trim();
        if (description.isEmpty()) {
            throw new RobertException("The description of a todo cannot be empty.");
        }
        return new Todo(description);
    }

    /**
     * Parses a Deadline task from the user input.
     *
     * @param input The full user input string.
     * @return The Deadline task.
     * @throws RobertException If the input format or date/time is invalid.
     */
    public static Deadline parseDeadline(String input) throws RobertException {
        String[] parts = input.substring(9).split(" /by ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new RobertException("Please provide a valid description and deadline, e.g., 'deadline return book /by 2019-12-02 1800'.");
        }
        
        try {
            LocalDateTime by = LocalDateTime.parse(parts[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            return new Deadline(parts[0].trim(), by);
        } catch (DateTimeParseException e) {
            throw new RobertException("Invalid date/time format. Please use 'yyyy-MM-dd HHmm', e.g., '2019-12-02 1800'.");
        }
    }

    /**
     * Parses an Event task from the user input.
     *
     * @param input The full user input string.
     * @return The Event task.
     * @throws RobertException If the input format or date/time is invalid.
     */
    public static Event parseEvent(String input) throws RobertException {
        String[] parts = input.substring(6).split(" /from ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty()) {
            throw new RobertException("Please provide a valid description and event time, e.g., 'event project meeting /from 2019-12-02 1400 /to 1600'.");
        }
        
        String[] timeParts = parts[1].split(" /to ", 2);
        if (timeParts.length < 2 || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
            throw new RobertException("Please provide both start and end times, e.g., 'event project meeting /from 2019-12-02 1400 /to 1600'.");
        }
        
        try {
            LocalDateTime from = LocalDateTime.parse(timeParts[0].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            LocalDateTime to;
            if (timeParts[1].trim().contains("-")) {
                // Full date and time provided for /to
                to = LocalDateTime.parse(timeParts[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            } else {
                // Only time provided for /to, assume same day as /from
                to = from.withHour(Integer.parseInt(timeParts[1].trim().substring(0, 2)))
                         .withMinute(Integer.parseInt(timeParts[1].trim().substring(2)));
            }
            return new Event(parts[0].trim(), from, to);
        } catch (DateTimeParseException | NumberFormatException e) {
            throw new RobertException("Invalid date/time format. Please use 'yyyy-MM-dd HHmm', e.g., '2019-12-02 1400'.");
        }
    }
}
