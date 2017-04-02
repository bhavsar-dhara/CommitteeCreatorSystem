package main.java.search.controller;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.search.model.Author;
import main.java.search.ui.AuthorProfilePage;
import main.java.search.ui.CandidateListPage;
import main.java.search.ui.SearchHistoryPage;

public class UIPageGenerator extends Application {

	@Override
	public void start(Stage stage) {
		AuthorProfilePage aupp = new AuthorProfilePage(new Author("dslkj"));
		CandidateListPage calp = new CandidateListPage();
		SearchHistoryPage shp = new SearchHistoryPage();
		stage.setScene(shp.getScene());
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
