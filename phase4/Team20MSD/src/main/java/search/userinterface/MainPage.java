package main.java.search.userinterface;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainPage {

	public MainPage() throws Exception{
//      Parent root = FXMLLoader.load(getClass().getResource("/search/Search.fxml"));
		//root = FXMLLoader.load(getClass().getResource("Search.fxml"));
		root = FXMLLoader.load(getClass().getClassLoader().getResource("Front.fxml"));
	}
    public Scene getScene() {
    	return new Scene(root, 1200, 650);
    }
    
    private Parent root;
}

