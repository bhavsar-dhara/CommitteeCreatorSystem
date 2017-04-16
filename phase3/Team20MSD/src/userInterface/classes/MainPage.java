package userInterface.classes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainPage {

	public MainPage() throws Exception{
//      Parent root = FXMLLoader.load(getClass().getResource("/search/Search.fxml"));
		root = FXMLLoader.load(getClass().getResource("/resources/fxmlFiles/MainPage.fxml"));
	}
    public Scene getScene() {
    	return new Scene(root, 600, 650);
    }
    
    private Parent root;
}
