package userInterface.classes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class MainPageSingleton {

	private MainPageSingleton() throws Exception{
//      Parent root = FXMLLoader.load(getClass().getResource("/search/Search.fxml"));
		root = FXMLLoader.load(getClass().getResource("/resources/Front.fxml"));
	}
    public Scene getScene() {
    	return new Scene(root, VBox.USE_COMPUTED_SIZE, VBox.USE_COMPUTED_SIZE);
    }
    
    private Parent root;
    
    public static MainPageSingleton instance() throws Exception{
    	if (_theMainPage == null){
    		return new MainPageSingleton();
    	}
    	return _theMainPage;
    }
    private static MainPageSingleton _theMainPage;

}
