import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EnlighterMain extends Application
{
    private Stage mainStage;

    public static void main(String[] args)
    {
        Application.launch(EnlighterMain.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.mainStage = primaryStage;
        this.mainStage.setTitle("Enlighter - Enlight Configuration Tool");

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EnlighterMain.class.getResource("fxml/Home.fxml"));

            mainStage.setScene(new Scene(loader.load()));
            mainStage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
