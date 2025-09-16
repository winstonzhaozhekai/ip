package robert.exception;

/**
 * Represents an exception thrown when a duplicate task is detected.
 */
public class DuplicateTaskException extends RobertException {
    /**
     * Constructs a new DuplicateTaskException with the specified detail message.
     *
     * @param message The detail message.
     */
    public DuplicateTaskException(String message) {
        super(message);
    }
}