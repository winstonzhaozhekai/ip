import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import robert.Robert;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Robert robert;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/Robert.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        showWelcomeMessage();
    }

    /** Injects the Robert instance */
    public void setRobert(Robert r) {
        robert = r;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Robert's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = robert.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();
    }

    /**
     * Shows the welcome message when the application starts.
     */
    private void showWelcomeMessage() {
        String welcomeText = "Wassup chat! I'm Robert\nWhat can I do for you?";
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(welcomeText, dukeImage)
        );
    }
}