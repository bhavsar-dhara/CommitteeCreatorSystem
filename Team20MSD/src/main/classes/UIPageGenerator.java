package main.classes;
import javafx.application.Application;
import javafx.stage.Stage;
import scndPartOfUI.*;
import main.interfaces.UserInterface;
import scndPartOfUI.UIForTest;

public class UIPageGenerator extends Application {
	
	@Override
    public void start(Stage stage) {
		SearchQuery sq = new SearchQuery();
		UserInterface ui = new UIForTest(sq);
		ui.showAuthorProfile(new Author("Sanjeev Saxena"));
	}
	
	public static void main(String[] args){
		launch(args);
	}
}
