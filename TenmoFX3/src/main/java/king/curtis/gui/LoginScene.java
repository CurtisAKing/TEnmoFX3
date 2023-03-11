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
import king.curtis.models.Account;
import king.curtis.models.AuthenticatedUser;
import king.curtis.models.UserCredentials;
import king.curtis.services.AuthenticationService;
import king.curtis.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class LoginScene {
	private Scene sceneOne;
	private TextField usernameField;
	private PasswordField passwordField;
	private Label instructions;
	private Label username;
	private Label password;
	private Button submit;
	private Button cancel;
	private final String API_BASE_URL = "http://localhost:8080/";

	private UserCredentials credentials;
	private AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
	private AuthenticatedUser currentAuthenticatedUser;
	private Account currentAccount;
	private RestTemplate restTemplate = new RestTemplate();

	public Scene getLoginScene(Stage stage){
		instructions = new Label("PLEASE ENTER YOUR CREDENTIALS");
		username = new Label("USERNAME");
		password = new Label("PASSWORD");
		usernameField = new TextField();
		usernameField.setMaxWidth(150);
		passwordField = new PasswordField();
		passwordField.setMaxWidth(150);
		submit = new Button("SUBMIT");
		cancel = new Button("CANCEL");

		submit.setOnAction(e -> {
			String usernameString = usernameField.getText();
			String passwordString = passwordField.getText();
			credentials = new UserCredentials(usernameString, passwordString);
			currentAuthenticatedUser = authenticationService.login(credentials);
			if(currentAuthenticatedUser == null){
				AlertBox failedLogin = new AlertBox();
				failedLogin.display(stage, "Login Error", "Username and Password not found.");
				stage.setScene(getLoginScene(stage));
			} else{
				try {
					HttpEntity<Object> entity = authenticationService.getHeaders(currentAuthenticatedUser);
					currentAccount = restTemplate.exchange(API_BASE_URL + "account/getaccount/" +
							currentAuthenticatedUser.getUser().getId(), HttpMethod.GET, entity, Account.class).getBody();
					stage.setScene( new MainLoggedInScene().getMainLoggedInScene(stage, currentAuthenticatedUser, currentAccount));
				} catch (RestClientResponseException restClientResponseException) {
					BasicLogger.log(restClientResponseException.getRawStatusCode() + " : " +restClientResponseException.getStatusText());
				} catch (ResourceAccessException resourceAccessException) {
					BasicLogger.log(resourceAccessException.getMessage());
				}
			}
		});

		cancel.setOnAction(e -> {
			stage.setScene(new WelcomeScene().getWelcomeScene(stage));
		});

		VBox loginLayout = new VBox(20);
		loginLayout.setAlignment(Pos.CENTER);
		loginLayout.getChildren().addAll(instructions, username, usernameField, password, passwordField, submit, cancel);
		sceneOne = new Scene(loginLayout, 278, 354);

		return sceneOne;
	}
}
