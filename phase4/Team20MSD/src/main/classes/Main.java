package main.classes;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import userInterface.classes.MainPageSingleton;

public class Main extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		MainPageSingleton mainPage = MainPageSingleton.instance();
		stage.setScene(mainPage.getScene());
		stage.show();
		stage.setOnCloseRequest((WindowEvent e) -> {
			Platform.exit();
		});
	}
	
	public static void main(String[] args){
		launch(args);
	}
} 
