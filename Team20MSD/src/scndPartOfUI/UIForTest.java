package scndPartOfUI;

import java.util.Collection;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import main.classes.Author;
import main.classes.Publication;
import main.classes.SearchQuery;
import main.interfaces.UserInterface;

public class UIForTest implements UserInterface {
	
	public UIForTest(SearchQuery sq){
		searchQuery = sq;
	}
	public void showMainPage(){
		
	}
	public void showSearchResult(SearchQuery q){
		
	}
	public void showSearchResult(Author a){
		
	}
	public void showCandidateList(){
		
	}
	public void showSearchHistory(){
		
	}
	public void showAuthorProfile(Author a){
		Stage stage = new Stage();
		AuthorProfilePage page = new AuthorProfilePage(a,this);
		stage.setScene(page.getScene());
		stage.show();
	}
	
	// operations on candidate list
	public void addCand(Author a){
		
	}
	public void remCand(Author a){
		
	}
	public boolean hasCand(Author a){
		return true;
	}
	public void remCand(Collection<Author> cands){
		
	}
	public int getCandListSize(){
		return 0;
	}
	public Author getCand(int i){
		return new Author();
	}
	public void addListenerToCandList(ListChangeListener<Author> listner){
		
	}
	
	// operations on search result
	public int getSearchResultSize(){
		return 0;
	}
	public Author getSearchResult(int i){
		return new Author();
	}
	
	// operations on search history
	public void addSearchToHistory(SearchQuery sq){
		
	}
	public void addListenerToSearchHistory(ListChangeListener<SearchQuery> listner){
		
	}
	public int getSearchHistorySize(){
		return 0;
	}
	public SearchQuery getSearchHistoryItem(int i){
		return new SearchQuery();
	}
	public void clearSearchHistory(){
		
	}
	
	public ObservableList<Publication> getAuthorPubs(Author atr){
		return (ObservableList<Publication>) searchQuery.getPublicationByAuthorName(atr);
	}
	
	private SearchQuery searchQuery;
}
