package king.curtis.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import king.curtis.TEnmoApplication;
import king.curtis.models.Account;
import king.curtis.models.AuthenticatedUser;
import king.curtis.models.Transfer;
import king.curtis.models.User;
import king.curtis.services.AccountServices;
import king.curtis.services.TransferService;
import king.curtis.services.UserService;

import java.util.List;


public class MainLoggedInScene
{
	Label instructions;
	Button viewCurrentBalance;
	Button viewTransferHistory;
	Button viewPendingRequests;
	Button searchForTransfer;
	Button sendBucks;
	Button requestBucks;
	Button exit;
	Scene sceneOne;

	private TransferService transferService = new TransferService();
	private AccountServices accountServices = new AccountServices();
	private UserService userService = new UserService();
	PendingRequests pendingRequests = new PendingRequests();

	public Scene getMainLoggedInScene(Stage stage, AuthenticatedUser currentAuthenticatedUser, Account currentAccount){
		instructions = new Label("PLEASE MAKE A SELECTION");
		viewCurrentBalance = new Button("View Balance");
		viewTransferHistory = new Button("Transfer History");
		viewPendingRequests = new Button("Veiw Pending Requests");
		searchForTransfer = new Button("Lookup Transfer");
		sendBucks = new Button("Send TEnmo");
		requestBucks = new Button("Request TEnmo");
		exit = new Button("Logout");

		viewCurrentBalance.setOnAction(e -> {
			if (currentAccount.getBalance() != null) {
				AlertBox balanceBox = new AlertBox();
				balanceBox.display(stage,"Current Balance" , "Current Balance: $" + currentAccount.getBalance());
			} else {
				AlertBox balanceBox = new AlertBox();
				balanceBox.display(stage, "Balance Error" , "Current Balance Cannot Be Found");
			}
		});

		viewTransferHistory.setOnAction(e -> {
			List<Transfer> transfers = transferService.viewTransferHistory(currentAuthenticatedUser, currentAccount);

			if(transfers != null && !transfers.isEmpty()) {
				new TransferHistory().display(stage,"Transfer History", transferService.viewTransferHistory(currentAuthenticatedUser, currentAccount));
			}else {
				AlertBox noHistory = new AlertBox();
				noHistory.display(stage, "Transaction History", "No Pending Transactions");
				stage.setScene(new MainLoggedInScene().getMainLoggedInScene(stage, currentAuthenticatedUser, currentAccount));
			}
		});

		viewPendingRequests.setOnAction(e -> {

			List<Transfer> incoming = transferService.getPendingIncoming(currentAuthenticatedUser, currentAccount);
			//Outgoing Requests to be approved by logged in user
			List<Transfer> outgoing = transferService.getPendingOutgoing(currentAuthenticatedUser, currentAccount);

			if(outgoing.isEmpty() || outgoing == null){
				AlertBox noPending = new AlertBox();
				noPending.display(stage, "Pending Transactions", "No Pending Transactions");
				stage.setScene(new MainLoggedInScene().getMainLoggedInScene(stage, currentAuthenticatedUser, currentAccount));
			}

			for(Transfer transfer: outgoing){
				if(transfer.getAccountFrom() == currentAccount.getAccountId()){
					Account accountSending = accountServices.getAccountByAccountId(currentAuthenticatedUser, transfer.getAccountFrom());
					Account accountReceiving = accountServices.getAccountByAccountId(currentAuthenticatedUser, transfer.getAccountTo());
					pendingRequests.display(stage, transfer, currentAuthenticatedUser, currentAccount, accountSending, accountReceiving);
				}
			}

			for(Transfer transfer: incoming){
				if(transfer.getAccountTo() == currentAccount.getAccountId()){
					Account accountSending = accountServices.getAccountByAccountId(currentAuthenticatedUser, transfer.getAccountFrom());
					Account accountReceiving = accountServices.getAccountByAccountId(currentAuthenticatedUser, transfer.getAccountTo());
					pendingRequests.display(stage, transfer, currentAuthenticatedUser, currentAccount, accountSending, accountReceiving);
				}
			}
		});

		searchForTransfer.setOnAction(e -> {
			TransferSearch transferSearch = new TransferSearch();
			stage.setScene(transferSearch.display(stage, currentAuthenticatedUser, currentAccount));
		});

		sendBucks.setOnAction(e -> {
			List<User> userList = userService.listOfUsers(currentAuthenticatedUser);
			stage.setScene(new SendBucks().display(stage, userList, currentAuthenticatedUser, currentAccount));
		});

		requestBucks.setOnAction(e -> {
			List<User> userList = userService.listOfUsers(currentAuthenticatedUser);
			stage.setScene(new RequestBucks().display(stage, userList, currentAuthenticatedUser, currentAccount));
		});

		exit.setOnAction(e -> {
			TEnmoApplication tEnmoApplication = new TEnmoApplication();
			try {
				tEnmoApplication.start(stage);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		});

		VBox mainLayout = new VBox(20);

		mainLayout.setAlignment(Pos.CENTER);
		mainLayout.getChildren().addAll(instructions, viewCurrentBalance, viewTransferHistory, viewPendingRequests,
				searchForTransfer, sendBucks, requestBucks, exit);
		sceneOne = new Scene(mainLayout, 337,407);

		return sceneOne;
	}




}
