package king.curtis.gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import king.curtis.models.Transfer;
import king.curtis.services.TransferService;

import java.math.BigDecimal;
import java.util.List;


public class TransferHistory {

	TableView<Transfer> generatedTransferTable;

	public void display(Stage stage, String title, List<Transfer> transfers){


		Stage alertBoxWindow = new Stage();
		alertBoxWindow.initModality(Modality.APPLICATION_MODAL);
		alertBoxWindow.initOwner(stage);
		alertBoxWindow.setTitle(title);
		alertBoxWindow.setMinWidth(250);


		Button closeButton = new Button("Close Window");
		closeButton.setOnAction(e -> alertBoxWindow.close());

		generatedTransferTable = getTable(transfers);

		VBox layout = new VBox(10);
		layout.getChildren().addAll(generatedTransferTable,closeButton);
		layout.setAlignment(Pos.CENTER);

		Scene sceneOne = new Scene(layout, 578,509);
		alertBoxWindow.setScene(sceneOne);
		alertBoxWindow.showAndWait();

	}

	public ObservableList<Transfer> getTransfers(List<Transfer> transferList){
		ObservableList<Transfer> transfers = FXCollections.observableArrayList();
		for(Transfer transfer: transferList){
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
			transfers.add(transfer);
		}
		return transfers;
	}

	public TableView<Transfer> getTable(List<Transfer> transfers){
		TableView<Transfer> transferTable;

		TableColumn<Transfer, Integer> transferIdColumn = new TableColumn<>("Transfer ID");
		transferIdColumn.setMinWidth(15);
		transferIdColumn.setCellValueFactory(new PropertyValueFactory<>("transferId"));

		TableColumn<Transfer, String> typeStringColumn = new TableColumn<>("Transfer Type");
		typeStringColumn.setMinWidth(25);
		typeStringColumn.setCellValueFactory(new PropertyValueFactory<>("transferTypeString"));

		TableColumn<Transfer, String> statusStringColumn = new TableColumn<>("Transfer Status");
		statusStringColumn.setMinWidth(25);
		statusStringColumn.setCellValueFactory(new PropertyValueFactory<>("transferStatusString"));

		TableColumn<Transfer, Integer> accountFromColumn = new TableColumn<>("Sending Account");
		accountFromColumn.setMinWidth(15);
		accountFromColumn.setCellValueFactory(new PropertyValueFactory<>("accountFrom"));

		TableColumn<Transfer, Integer> accountToColumn = new TableColumn<>("Receiving Account");
		accountToColumn.setMinWidth(15);
		accountToColumn.setCellValueFactory(new PropertyValueFactory<>("accountTo"));

		TableColumn<Transfer, BigDecimal> amountColumn = new TableColumn<>("Amount");
		amountColumn.setMinWidth(15);
		amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

		transferTable = new TableView<>();
		transferTable.setItems(getTransfers(transfers));
		transferTable.getColumns().addAll(transferIdColumn, typeStringColumn, statusStringColumn, accountFromColumn, accountToColumn, amountColumn);

		return transferTable;
	}

}
