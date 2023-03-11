package king.curtis.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import king.curtis.models.Account;
import king.curtis.models.AuthenticatedUser;
import king.curtis.models.Transfer;
import king.curtis.services.TransferService;

public class TransferSearch {

	Label label;
	Label instructions;
	TextField transactionNumber;
	Button search;
	Button exitSearch;
	Scene sceneOne;
	String transferString;
	Transfer transfer;

	private TransferService transferService = new TransferService();

	public Scene display(Stage stage , AuthenticatedUser currentAuthenticatedUser, Account currentAccount){
		instructions = new Label("Please Enter Transaction ID");
		transactionNumber = new TextField();
		transactionNumber.setMaxWidth(100);
		search = new Button("SEARCH");
		exitSearch = new Button("Exit Search");

		search.setOnAction(e -> {
			AlertBox alertBox = new AlertBox();
			transfer = transferService.getTransfer(Integer.parseInt(transactionNumber.getText()), currentAuthenticatedUser, currentAccount);
			if (transfer.getTransferStatusId() == 1){
				transfer.setTransferStatusString("Pending");
			} else if (transfer.getTransferStatusId() == 2) {
				transfer.setTransferStatusString("Approved");
			} else {
				transfer.setTransferStatusString("Rejected");
			}
			if(transfer.getTransferTypeId() == 1){
				transfer.setTransferTypeString("Requested");
			} else {
				transfer.setTransferTypeString("Sent");
			}
			transferString = "Transfer ID: " + transfer.getTransferId()
					+ "\nTransfer Type: " + transfer.getTransferTypeString()
					+ "\nTransfer Status: " + transfer.getTransferStatusString()
					+ "\nAccount From: " + transfer.getAccountFrom()
					+ "\nAccount To: " + transfer.getAccountTo()
					+ "\nAmount: " + transfer.getAmount();
			alertBox.display(stage, "Transaction View", transferString);
		});

		exitSearch.setOnAction(e -> stage.setScene(new MainLoggedInScene().getMainLoggedInScene(stage, currentAuthenticatedUser,currentAccount)));

		VBox mainLayout = new VBox(20);

		mainLayout.setAlignment(Pos.CENTER);
		mainLayout.getChildren().addAll(instructions, transactionNumber, search, exitSearch);
		sceneOne = new Scene(mainLayout, 337,407);

		return sceneOne;

	}
}
