package main.search;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Warning {

	// public Warning(String msg){
	// setMessage(msg);
	// setConfBut();
	// setVBox();
	// setStage();
	//
	// }
	//
	// private void setStage() {
	// stage.setScene(new Scene(vbox));
	// stage.setTitle("Warning");
	// stage.setWidth(300);
	// }
	//
	// private void setVBox() {
	// vbox.setAlignment(Pos.CENTER);
	// vbox.setPadding(new Insets(10,10,10,10));
	// }
	//
	// private void setConfBut() {
	// confirmBut.setOnAction((ActionEvent ae) -> {
	// stage.close();
	// });
	// }
	//
	// private void setMessage(String msg) {
	// message.setText(msg);
	// message.setFont(new Font("Arial",20));
	// message.set
	// }
	//
	// public void display(){
	// stage.show();
	// }
	//
	// private Label message = new Label();
	// private Button confirmBut = new Button("OK");
	// private VBox vbox = new VBox(10,message,confirmBut);
	// private Stage stage = new Stage();
	//

	public Warning() {
		alert.setTitle("Warning!");
		alert.setContentText("hello");
	}

	public void display() {
		alert.showAndWait();
	}

	private Alert alert = new Alert(AlertType.WARNING);
}
