package king.curtis.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WelcomeScene {


	Button registerButton;
	Button goBack;
	Button loginButton;
	Label welcomeLabel;
	Label tenmoLabel;
	Scene sceneOne;

	public Scene getWelcomeScene(Stage stage) {
		registerButton = new Button("Register");
		registerButton.setOnAction(e -> {
			try {
				stage.setScene(new RegisterScene().getRegisterScene(stage, sceneOne));
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		});
		loginButton = new Button("Login");
//        loginButton.setOnAction();
		tenmoLabel = new Label("TEnmo");
		welcomeLabel = new Label("Connecting you with your friends and their cash!");

		VBox welcomeLayout = new VBox(20);
		welcomeLayout.setAlignment(Pos.CENTER);
		welcomeLayout.getChildren().addAll(tenmoLabel, welcomeLabel, registerButton, loginButton);

		sceneOne = new Scene(welcomeLayout, 325, 254);

		return sceneOne;
	}
}
