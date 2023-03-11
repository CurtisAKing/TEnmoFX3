package king.curtis.gui;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import king.curtis.models.Account;
import king.curtis.models.AuthenticatedUser;
import king.curtis.models.Transfer;
import king.curtis.services.AccountServices;
import king.curtis.services.TransferService;


public class PendingRequests {

	TransferService transferService = new TransferService();
	AccountServices accountServices = new AccountServices();

	public void display(Stage stage, Transfer transfer, AuthenticatedUser currentAuthenticatedUser, Account currentAccount, Account accountSending, Account accountReceiving){


		Stage pendingBoxWindow = new Stage();
		pendingBoxWindow.initModality(Modality.APPLICATION_MODAL);
		pendingBoxWindow.initOwner(stage);
		pendingBoxWindow.setTitle("Pending Transactions");
		pendingBoxWindow.setMinWidth(250);

		Button approveButton = new Button("Approve");
		Button rejectButton = new Button("Reject");
		Button skipButton = new Button("Skip");
		Button confirmButton = new Button("Confirm");

		Label incomingRequest = new Label("Incoming Request For Transfer");
		Label outgoingRequest = new Label("Request for Bucks");
		incomingRequest.setFont(new Font("ariel", 15));
		Label labelId = new Label("Transfer ID: " + transfer.getTransferId());
		Label requestingAccount = new Label("Requesting Account: " + transfer.getAccountTo());
		Label amount = new Label(String.valueOf(transfer.getAmount()));

		approveButton.setOnAction(e -> {

			if (accountSending.getBalance().compareTo(transfer.getAmount()) < 0) {
				AlertBox insufficientFunds = new AlertBox();
				insufficientFunds.display(stage, "Insufficient Funds", "You do not have enough to approve this transaction");
				pendingBoxWindow.close();
			} else {
				accountServices.processTransactionsSent(accountSending, transfer.getAmount().doubleValue());
				accountServices.processTransactionReceived(accountReceiving, transfer.getAmount().doubleValue());
				transfer.setTransferStatusId(2);
				transfer.setTransferStatusString("Approved");
				transferService.updateTransfer(transfer, currentAuthenticatedUser);
				accountServices.updateAccount(accountSending, currentAuthenticatedUser);
				accountServices.updateAccount(accountReceiving, currentAuthenticatedUser);
				accountServices.transactionComplete(accountSending);
				pendingBoxWindow.close();
			}
		});

		rejectButton.setOnAction(e -> {
			transfer.setTransferStatusId(3);
			transferService.updateTransfer(transfer, currentAuthenticatedUser);
			pendingBoxWindow.close();
		});

		skipButton.setOnAction(e -> {
			pendingBoxWindow.close();
		});

		confirmButton.setOnAction(e -> {
			pendingBoxWindow.close();
		});

		if(transfer.getAccountFrom() == currentAccount.getAccountId()){
			VBox layout = new VBox(10);
			layout.getChildren().addAll(incomingRequest, labelId,requestingAccount, amount, approveButton, rejectButton, skipButton);
			layout.setAlignment(Pos.CENTER);

			Scene pendingScene = new Scene(layout, 368,290);
			pendingBoxWindow.setScene(pendingScene);
			pendingBoxWindow.showAndWait();
		} else if (transfer.getAccountTo() == currentAccount.getAccountId()){
			VBox layout = new VBox(10);
			layout.getChildren().addAll(outgoingRequest, labelId,requestingAccount, amount, confirmButton);
			layout.setAlignment(Pos.CENTER);

			Scene pendingScene = new Scene(layout, 368,290);
			pendingBoxWindow.setScene(pendingScene);
			pendingBoxWindow.showAndWait();
		}



	}
}
