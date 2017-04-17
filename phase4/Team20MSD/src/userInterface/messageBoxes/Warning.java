package userInterface.messageBoxes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class Warning {

//	public Warning(String msg){
//		setMessage(msg);
//		setConfBut();
//		setVBox();
//		setStage();
//
//	}
//	
//	private void setStage() {
//		stage.setScene(new Scene(vbox));
//		stage.setTitle("Warning");
//		stage.setWidth(300);
//	}
//
//	private void setVBox() {
//		vbox.setAlignment(Pos.CENTER);
//		vbox.setPadding(new Insets(10,10,10,10));
//	}
//
//	private void setConfBut() {
//		confirmBut.setOnAction((ActionEvent ae) -> {
//			stage.close();
//		});
//	}
//
//	private void setMessage(String msg) {
//		message.setText(msg);
//		message.setFont(new Font("Arial",20));
//		message.set
//	}
//
//	public void display(){
//		stage.show();
//	}
//	
//	private Label message = new Label();
//	private Button confirmBut = new Button("OK");
//	private VBox vbox = new VBox(10,message,confirmBut);
//	private Stage stage = new Stage();
//
	
	public Warning(String msg){
		alert.setTitle("Warning!");
		alert.setContentText(msg);
	}
	
	public void display(){
		alert.showAndWait();
	}
	private Alert alert = new Alert(AlertType.WARNING);
}
