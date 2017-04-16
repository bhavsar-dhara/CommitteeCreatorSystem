package main.search;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Progress {

	public Progress(String msg) {
		message.setText(msg);
		message.setFont(new Font("Arial", 30));
		pin.setProgress(-1.0f);
		setHBox();
		setStage();
	}

	private void setHBox() {
		hbox.setAlignment(Pos.CENTER);
		hbox.setPadding(new Insets(10, 10, 10, 10));
	}

	private void setStage() {
		stage.setAlwaysOnTop(true);
		stage.setScene(new Scene(hbox));
		stage.initStyle(StageStyle.UTILITY);
		stage.setWidth(300);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setOnCloseRequest((WindowEvent e) -> {
			if (pin.getProgress() < 1) {
				e.consume();
			}
		});
	}

	public void display() {
		stage.show();
	}

	public void selfDestruct() {
		pin.setProgress(1.0f);
		stage.close();
	}

	private Stage stage = new Stage();
	private Label message = new Label();
	private ProgressIndicator pin = new ProgressIndicator();
	private HBox hbox = new HBox(20, message, pin);
}