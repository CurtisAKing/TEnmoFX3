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
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AlertBox {

	public void display(Stage stage, String title, String message){
		Stage alertBoxWindow = new Stage();
		alertBoxWindow.initModality(Modality.APPLICATION_MODAL);
		alertBoxWindow.initOwner(stage);
		alertBoxWindow.setTitle(title);
		alertBoxWindow.setMinWidth(250);

		Label label = new Label();
		label.setText(message);
		label.setFont(new Font("Arial", 15));

		Button closeButton = new Button("Close Window");
		closeButton.setOnAction(e -> alertBoxWindow.close());

		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);

		Scene alertScene = new Scene(layout, 286,215);
		alertBoxWindow.setScene(alertScene);
		alertBoxWindow.showAndWait();
	}
}
