package king.curtis.gui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import king.curtis.models.Account;
import king.curtis.models.AuthenticatedUser;
import king.curtis.models.Transfer;
import king.curtis.models.User;
import king.curtis.services.AccountServices;
import king.curtis.services.TransferService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class RequestBucks {
	Label instructions;
	Label getId;
	Label getAmount;
	TextField user;
	TextField amount;
	Button submit;
	Button cancel;
	Scene sceneOne;

	TableView<User> generatedUserTable;

	AccountServices accountServices = new AccountServices();
	TransferService transferService = new TransferService();

	public Scene display(Stage stage, List<User> userList, AuthenticatedUser currentAuthenticatedUser, Account currentAccount){

		instructions = new Label("Please Complete the Transaction Form Below");
		user = new TextField();
		user.setMaxWidth(200);
		amount = new TextField();
		amount.setMaxWidth(200);
		submit = new Button("SEND REQUEST");
		getId = new Label("Enter ID of user you wish to request TEbucks from.");
		getAmount = new Label("Enter the amount of TEbucks you wish to request.");
		cancel = new Button("CANCEL");

		generatedUserTable = getTable(userList, currentAuthenticatedUser);

		submit.setOnAction(e -> {

			if ((amount.getText() == null || Objects.equals(amount.getText(), "")) || (user.getText() == null || Objects.equals(user.getText(), ""))){
				AlertBox blankFields = new AlertBox();
				blankFields.display(stage, "Request Failed", "Request Failed: Blank Fields");
				stage.setScene(new MainLoggedInScene().getMainLoggedInScene(stage, currentAuthenticatedUser, currentAccount));
			}
			double requestedAmount = Double.parseDouble(amount.getText());
			int sendingUserId = Integer.parseInt(user.getText());

			if (requestedAmount <= 0) {
				AlertBox transactionFailed = new AlertBox();
				transactionFailed.display(stage, "Request Failed", "Request Failed: Amount Too Low");
				stage.setScene(new MainLoggedInScene().getMainLoggedInScene(stage, currentAuthenticatedUser, currentAccount));
			} else if (sendingUserId == currentAccount.getUserId()) {
				AlertBox transactionFailed = new AlertBox();
				transactionFailed.display(stage, "Request Failed", "Request Failed: Self Request");
				stage.setScene(new MainLoggedInScene().getMainLoggedInScene(stage, currentAuthenticatedUser, currentAccount));
			} else {
				Account sendingAccount = accountServices.getAccount(currentAuthenticatedUser, sendingUserId);

				Transfer transfer = new Transfer(1, 1, sendingAccount.getAccountId(), currentAccount.getAccountId(), BigDecimal.valueOf(requestedAmount));

				transferService.saveTransfer(transfer, currentAuthenticatedUser);
				AlertBox transactionComplete = new AlertBox();
				transactionComplete.display(stage, "Transfer Complete", "Request Sent");
				stage.setScene(new MainLoggedInScene().getMainLoggedInScene(stage, currentAuthenticatedUser, currentAccount));
			}
		});

		cancel.setOnAction(e -> {
			stage.setScene(new MainLoggedInScene().getMainLoggedInScene(stage, currentAuthenticatedUser, currentAccount));
		});

		VBox layout = new VBox(10);
		layout.getChildren().addAll(generatedUserTable, instructions, getId, user, getAmount, amount, submit, cancel);
		layout.setAlignment(Pos.CENTER);
		sceneOne = new Scene(layout, 337,407);

		return sceneOne;
	}

	public ObservableList<User> getUsers(List<User> userList, User currentUser){
		ObservableList<User> users = FXCollections.observableArrayList();
		for(User user : userList){
			if (user.getId() != currentUser.getId()){
				users.add(user);
			}
		}
		return users;
	}

	public TableView<User> getTable(List<User> users, AuthenticatedUser currentAuthenticatedUser){
		TableView<User> userTable;

		TableColumn<User, Integer> userIdColumn = new TableColumn<>("User ID");
		userIdColumn.setMinWidth(15);
		userIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

		TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
		usernameColumn.setMinWidth(25);
		usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		usernameColumn.setMinWidth(268);



		userTable = new TableView<>();
		userTable.setItems(getUsers(users, currentAuthenticatedUser.getUser()));
		userTable.getColumns().addAll(userIdColumn, usernameColumn);

		return userTable;
	}
}
