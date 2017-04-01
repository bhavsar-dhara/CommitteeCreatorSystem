package main.classes;
import javafx.application.Application;
import javafx.stage.Stage;
import scndPartOfUI.*;

public class UIPageGenerator extends Application {
	
	@Override
    public void start(Stage stage) {
		AuthorProfilePage aupp = new AuthorProfilePage(new Author("dslkj"));
		CandidateListPage calp = new CandidateListPage();
		SearchHistoryPage shp = new SearchHistoryPage();
		stage.setScene(shp.getScene());
		stage.show();
	}
	
	public static void main(String[] args){
		launch(args);
	}
}
