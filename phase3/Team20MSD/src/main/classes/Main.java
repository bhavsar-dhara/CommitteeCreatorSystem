package main.classes;

import javafx.application.Application;
import javafx.stage.Stage;
import userInterface.classes.MainPage;

public class Main extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		MainPage mainPage = new MainPage();
		stage.setScene(mainPage.getScene());
		stage.show();
	}
	
	public static void main(String[] args){
		launch(args);
	}
} 
