package userInterface.classes;

import javafx.application.Application;
import javafx.stage.Stage;
import main.classes.Author;
import userInterface.classes.AuthorProfilePage;
import userInterface.classes.CandidateListPage;
import userInterface.classes.SearchHistoryPage;

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
		Stage stage2 = new Stage();
		stage2.setScene(clp.getScene());
		stage2.show();
	}
	
	public static void main(String[] args){
		launch(args);
	}
}
