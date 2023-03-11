package king.curtis.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AlertBox {

	public void display(String title, String message){
		Stage alertBoxWindow = new Stage();

		alertBoxWindow.initModality(Modality.APPLICATION_MODAL);
		alertBoxWindow.setTitle(title);
		alertBoxWindow.setMinWidth(250);

		Label label = new Label();
		label.setText(message);

		Button closeButton = new Button("Close Window");
		closeButton.setOnAction(e -> alertBoxWindow.close());

		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);

		Scene alertScene = new Scene(layout, 367,146);
		alertBoxWindow.setScene(alertScene);
		alertBoxWindow.showAndWait();
	}
}
