package main.java.search.controller;

import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import main.java.search.model.Author;
import main.java.search.model.Publication;
import main.java.search.ui.AuthorProfilePage;
import main.java.search.ui.CandidateListPage;
import main.java.search.ui.SearchHistoryPage;

public abstract class UserInterface {

	public static void showCandidateList() {
		CandidateListPage page = new CandidateListPage();
		Stage stage = new Stage();
		stage.setScene(page.getScene());
		stage.show();
	}

	public static void showSearchHistory() {
		SearchHistoryPage page = new SearchHistoryPage();
		Stage stage = new Stage();
		stage.setScene(page.getScene());
		stage.show();
	}

	public static void showAuthorProfile(Author a) {
		AuthorProfilePage page = new AuthorProfilePage(a);
		Stage stage = new Stage();
		stage.setScene(page.getScene());
		stage.show();
	}

	public static void showSearchResult(Author a) {
		SearchQuery.getSimilarAuthorList(a);
	}

	public static void showSearchResult(SearchQuery sq) {

	}

	// operations on candidate list
	public static void addCand(Author a) {
		candidateList.add(a);
	}

	public static void remCand(Author a) {
		candidateList.remove(a);
	}

	public static boolean hasCand(Author a) {
		return candidateList.contains(a);
	}

	public static void remCand(Collection<Author> cands) {
		candidateList.removeAll(cands);
	}

	public static int getCandListSize() {
		return candidateList.size();
	}

	public static Author getCand(int i) {
		return candidateList.get(i);
	}

	public static void addListenerToCandList(ListChangeListener<Author> listner) {
		candidateList.addListener(listner);
	}

	// operations on search history
	public static void addSearchToHistory(SearchQuery sq) {
		searchHistory.add(sq);
	}

	public static void addListenerToSearchHistory(ListChangeListener<SearchQuery> listner) {
		searchHistory.addListener(listner);
	}

	public static int getSearchHistorySize() {
		return searchHistory.size();
	}

	public static SearchQuery getSearchHistoryItem(int i) {
		return searchHistory.get(i);
	}

	public static void clearSearchHistory() {
		searchHistory.clear();
	}

	public static ObservableList<Publication> getAuthorPubs(Author atr) {
		return (ObservableList<Publication>) SearchQuery.getPublicationByAuthorName(atr);
	}

	private static ObservableList<Author> candidateList = FXCollections.observableArrayList();
	private static ObservableList<SearchQuery> searchHistory = FXCollections.observableArrayList();

}
