package king.curtis.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import king.curtis.models.UserCredentials;
import king.curtis.services.AuthenticationService;

public class RegisterScene{
	private Scene sceneOne;
	private Button submit;
	private Label instructions;
	private Label username;
	private Label password;
	private TextField usernameField;
	private PasswordField passwordField;
	private final String API_BASE_URL = "http://localhost:8080/";

	private UserCredentials credentials;
	private AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);



	public Scene getRegisterScene(Stage stage, Scene welcomeScene){
		instructions = new Label("Please enter a new Username and Password");
		username = new Label("Username");
		password = new Label("Password");
		usernameField = new TextField();
		usernameField.setMaxWidth(150);
		passwordField = new PasswordField();
		passwordField.setMaxWidth(150);
		submit = new Button("Submit");

		submit.setOnAction(e -> {
			String usernameString = usernameField.getText();
			String passwordString = passwordField.getText();
			credentials = new UserCredentials(usernameString, passwordString);
			if (authenticationService.register(credentials)){
				AlertBox alertBox = new AlertBox();
				alertBox.display("Registration Success" ,"You may now Login.");
			} else {
				AlertBox alertBox = new AlertBox();
				alertBox.display("Registration Error" ,"Registration Failed");

			}
			stage.setScene(welcomeScene);
		});


		VBox registerLayout = new VBox(20);
		registerLayout.setAlignment(Pos.CENTER);
		registerLayout.getChildren().addAll(instructions, username, usernameField, password, passwordField, submit);
		sceneOne = new Scene(registerLayout, 325,254);

		return sceneOne;
	}
}
