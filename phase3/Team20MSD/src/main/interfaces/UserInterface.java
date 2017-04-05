package main.interfaces;

import main.classes.Author;
import main.classes.Publication;
import scndPartOfUI.SearchQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;

public interface UserInterface {
	
	public void displayNewWindow(Scene scene);
	public void displayNewWindow(Scene scene,String title);
	
	// operations on candidate list
	public void addCand(Author a);
	public boolean hasCand(Author a);
	public void remCand(Author a);
	public void remCand(List<Author> cands);
	public int getCandListSize();
	public Author getCand(int i);
	public void addListenerToCandList(CandidateListListener p);
	
	
	// operations on search result
//	public int getSearchResultSize();
//	public Author getSearchResult(int i);
	
	// operations on search history
//	public void addSearchToHistory(SearchQuery sq);
//	public int getSearchHistorySize();
//	public SearchQuery getSearchHistoryItem(int i);
//	public void clearSearchHistory();
//	public void addListenerToSearchHistory(SearchHistoryListener p);
	
	public ObservableList<Publication> getAuthorPubs(Author atr);
}
