import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import robert.Robert;

/**
 * A GUI for Robert using FXML.
 */
public class Main extends Application {

    private Robert robert = new Robert("./data/duke.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Robert");
            fxmlLoader.<MainWindow>getController().setRobert(robert);  // inject the Robert instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}