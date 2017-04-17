package userInterface.classes;

import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.stage.Stage;
import main.classes.Author;
import userInterface.classes.AuthorProfilePage;
import userInterface.classes.CandidateListPage;
import userInterface.classes.SearchHistoryPage;
import userInterface.messageBoxes.Progress;
import userInterface.messageBoxes.Warning;

public class Test extends Application {
	public void start(Stage stage){
		UITesting ui = new UIForTest();
		ui.setTestData();
		CandidateListPage clp = new CandidateListPage(ui);
		Author reckle = new Author("Reckle R");
		AuthorProfilePage app = new AuthorProfilePage(reckle,ui);
		SearchHistoryPage shp = new SearchHistoryPage(ui);
		stage.setScene(app.getScene());
		stage.show();
	}
	
	public static void main(String[] args){
		launch(args);
	}
}
