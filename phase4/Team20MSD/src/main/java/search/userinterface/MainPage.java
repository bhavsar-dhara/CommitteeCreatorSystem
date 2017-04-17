package main.java.search.userinterface;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class MainPage {

	public MainPage() throws Exception{
		root = FXMLLoader.load(getClass().getClassLoader().getResource("Front.fxml"));
	}
    public Scene getScene() {
    	return new Scene(root, VBox.USE_COMPUTED_SIZE, 650);
    }
    
    private Parent root;
}


