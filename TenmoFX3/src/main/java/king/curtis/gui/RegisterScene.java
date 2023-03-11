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
	Button cancel;
	private Label instructions;
	private Label username;
	private Label password;
	private TextField usernameField;
	private PasswordField passwordField;
	private final String API_BASE_URL = "http://localhost:8080/";

	private UserCredentials credentials;
	private AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);



	public Scene getRegisterScene(Stage stage, Scene welcomeScene){
		instructions = new Label("PLEASE ENTER A NEW USERNAME AND PASSWORD");
		username = new Label("USERNAME");
		password = new Label("PASSWORD");
		usernameField = new TextField();
		usernameField.setMaxWidth(150);
		passwordField = new PasswordField();
		passwordField.setMaxWidth(150);
		submit = new Button("SUBMIT");
		cancel= new Button("CANCEL");

		submit.setOnAction(e -> {
			if(usernameField == null || passwordField == null ){
				AlertBox nullFields = new AlertBox();
				nullFields.display(stage, "Blank Fields", "No Fields May Be Left Blank");
				stage.setScene(welcomeScene);
			}
			String usernameString = usernameField.getText();
			String passwordString = passwordField.getText();
			credentials = new UserCredentials(usernameString, passwordString);
			if (authenticationService.register(credentials)){
				AlertBox alertBox = new AlertBox();
				alertBox.display(stage, "Registration Success" ,"You may now Login.");
			} else {
				AlertBox alertBox = new AlertBox();
				alertBox.display(stage, "Registration Error" ,"Registration Failed");

			}
			stage.setScene(welcomeScene);
		});

		cancel.setOnAction(e -> {
			stage.setScene(new WelcomeScene().getWelcomeScene(stage));
		});


		VBox registerLayout = new VBox(20);
		registerLayout.setAlignment(Pos.CENTER);
		registerLayout.getChildren().addAll(instructions, username, usernameField, password, passwordField, submit, cancel);
		sceneOne = new Scene(registerLayout, 290,375);

		return sceneOne;
	}
}
