package king.curtis.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WelcomeScene {


	Button registerButton;
	Button goBack;
	Button loginButton;
	Button exit;
	Label welcomeLabel;
	Label tenmoLabel;
	Label lowerLabel;
	Scene sceneOne;

	public Scene getWelcomeScene(Stage stage) {
		//Register
		registerButton = new Button("Register");
		registerButton.setOnAction(e -> {
			try {
				stage.setScene(new RegisterScene().getRegisterScene(stage, sceneOne));
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		});
		//Login
		loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
			try{
				stage.setScene(new LoginScene().getLoginScene(stage));
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		});

		exit = new Button("Exit");
		exit.setOnAction(e -> stage.close());
		//Welcome Scene setup
		tenmoLabel = new Label("TEnmo");
		tenmoLabel.setFont(new Font("Arial", 30));
		welcomeLabel = new Label("Connecting you with your friends...");
		welcomeLabel.setFont(new Font("Arial", 15));
		lowerLabel = new Label("and their cash!");
		lowerLabel.setFont(new Font("Arial", 10));

		VBox welcomeLayout = new VBox(20);
		welcomeLayout.setAlignment(Pos.CENTER);
		welcomeLayout.getChildren().addAll(tenmoLabel, welcomeLabel, lowerLabel, loginButton, registerButton, exit);

		sceneOne = new Scene(welcomeLayout, 325, 254);

		return sceneOne;
	}
}
