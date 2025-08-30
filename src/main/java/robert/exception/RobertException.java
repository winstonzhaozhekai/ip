package robert.exception;

/**
 * Represents exceptions specific to the Robert chatbot application.
 */
public class RobertException extends Exception {
    /**
     * Constructs a new RobertException with the specified detail message.
     *
     * @param message The detail message.
     */
    public RobertException(String message) {
        super(message);
    }
}