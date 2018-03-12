package controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutController
{
    @FXML
    private Button okayButton;

    public void handleOkayButton()
    {
        Stage stage = (Stage)okayButton.getScene().getWindow();
        stage.close();
    }
}
