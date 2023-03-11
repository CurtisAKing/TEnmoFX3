package king.curtis;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import king.curtis.gui.AlertBox;
import king.curtis.gui.RegisterScene;
import king.curtis.gui.WelcomeScene;

/**
 * JavaFX App
 */
public class TEnmoApplication extends Application{

   public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
       stage.setTitle("TEnmo KingPaulHallow CORP");
        stage.setScene(new WelcomeScene().getWelcomeScene(stage));
        stage.show();


    }

}