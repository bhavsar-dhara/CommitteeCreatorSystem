package userInterface.classes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.classes.Author;
import main.classes.Publication;
import userInterface.classes.CandidateListPage;
import userInterface.interfaces.CandidateListListener;
import userInterface.interfaces.SearchHistoryListener;

public class UIForTest implements UITesting {

	public void displayNewWindow(Scene s){
		Stage stage = new Stage();
		stage.setScene(s);
		stage.show();
	}
	
	public void displayNewWindow(Scene s,String title){
		Stage stage = new Stage();
		stage.setScene(s);
		stage.setTitle(title);
		stage.show();
	}

	// operations on candidate list
	public void addCand(Author a){
		candidateList.add(a);
	}
	
	public boolean hasCand(Author a){
		return candidateList.contains(a);
	}
	
	public void remCand(Author a){
		candidateList.remove(a);
	}
	public void remCand(List<Author> cands){
		candidateList.removeAll(cands);
	}
	public int getCandListSize(){
		return candidateList.size();
	}
	public Author getCand(int i){
		return candidateList.get(i);
	}
	
	public void addListenerToCandList(CandidateListListener p){
		candidateList.addListener((ListChangeListener.Change<? extends Author> c) -> {
			p.refresh();
		});
	}
	
	// operations on search result
	public int getSearchResultSize(){
		return searchResultPool.size();
	}
	public Author getSearchResult(int i){
		return searchResultPool.get(i);
	}
	public void getSearchResult(SearchQuery sq){
		searchResultPool.clear();
		searchResultPool.addAll(AuthorProfilePageTest.rFTSimAuth);
	}
	
	// operations on search history
	public void addSearchToHistory(SearchQuery sq){
		searchHistory.add(sq);
	}
	
	public void addListenerToSearchHistory(SearchHistoryListener p){
		searchHistory.addListener((ListChangeListener.Change<? extends SearchQuery> c) -> {
			p.refresh();
		});
	}
	
	public int getSearchHistorySize(){
		return searchHistory.size();
	}
	
	
	public SearchQuery getSearchHistoryItem(int i){
		return searchHistory.get(i);
	}
	public void clearSearchHistory(){
		searchHistory.clear();
	}
	
	public ObservableList<Publication> getAuthorPubs(Author atr){
		return FXCollections.observableArrayList(
				new Publication("Battery","Encyclopedia Britannica",1990),
				new Publication("Cells and Batteries","The DK Science Encyclopedia",1993),
				new Publication("Learning Centre","Duracell. The Gillette Company",2006));
	}
	 
	private ObservableList<Author> candidateList = FXCollections.observableArrayList(
			new Author("Ha, Do Viet",329338),
			new Author("Amir K. C",1735),
			new Author("Sai Kumar A",11591164));
	private ObservableList<SearchQuery> searchHistory = 
			FXCollections.observableArrayList(
					new SearchQuery("Reiko Heckle"),
					new SearchQuery("pointer analysis"),
					new SearchQuery("1992"),
					new SearchQuery("ECOOP"));
	private ArrayList<Author> searchResultPool = new ArrayList<Author>();
	
	public void setTestData(){
		candidateList.clear();
		for (int i=0; i<5*CandidateListPage.getItemNbrPerPage();i++){
			candidateList.add(new Author(String.valueOf(i),i));
		}
		searchHistory.clear();
		for (int i=0; i<5*CandidateListPage.getItemNbrPerPage();i++){
			searchHistory.add(new SearchQuery(String.valueOf(i)));
		}
	}

	public ObservableList<Author> getSearchResult(Author a) {
		return null;
	}
}
