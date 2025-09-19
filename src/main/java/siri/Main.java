package siri;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The entry point of the JavaFX application.
 * <p>
 * {@code Main} initializes the JavaFX GUI for the Siri chatbot by
 * loading the FXML layout, setting up the primary stage (window),
 * and injecting the {@link Siri} instance into the {@link MainWindow} controller.
 * </p>
 */
public class Main extends Application {

    private Siri siri = new Siri();

    /**
     * Called when the JavaFX application is started.
     * <p>
     * This method loads the {@code MainWindow.fxml} layout, creates the scene,
     * sets the stage title, and injects the {@link Siri} instance into
     * the {@link MainWindow} controller before displaying the stage.
     * </p>
     *
     * @param stage the primary stage provided by the JavaFX runtime
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Siri chatbot :)");
            fxmlLoader.<MainWindow>getController().setSiri(siri);  // inject the Siri instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
