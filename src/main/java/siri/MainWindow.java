package siri;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import siri.util.Ui;

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

    private Siri siri;
    private Ui ui = new Ui();

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/userImage.jpg"));
    private Image siriImage = new Image(this.getClass().getResourceAsStream("/images/siriImage.jpeg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(
                DialogBox.getSiriDialog(ui.getWelcomeMessage(), siriImage)
        );
    }

    /** Injects the Siri instance */
    public void setSiri(Siri s) {
        siri = s;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = siri.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getSiriDialog(response, siriImage)
        );
        userInput.clear();
    }
}
