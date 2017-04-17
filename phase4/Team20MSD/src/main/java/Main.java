package main.java;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.search.userinterface.MainPage;

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
