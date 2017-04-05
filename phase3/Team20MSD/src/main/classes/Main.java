package main.classes;

import javafx.application.Application;
import javafx.stage.Stage;
import main.search.Candidate;

public class Main extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		Candidate mainPage = new Candidate();
		stage.setScene(mainPage.getScene());
		stage.show();
	}
	
	public static void main(String[] args){
		launch(args);
	}
}
