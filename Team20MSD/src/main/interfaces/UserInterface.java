package main.interfaces;

import main.classes.Author;
import main.classes.Publication;
import main.classes.SearchQuery;

import java.util.ArrayList;
import java.util.Collection;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public interface UserInterface {
	public void showMainPage();
	public void showSearchResult(SearchQuery q);
	public void showSearchResult(Author a);
	public void showCandidateList();
	public void showSearchHistory();
	public void showAuthorProfile(Author a);
	
	// operations on candidate list
	public void addCand(Author a);
	public void remCand(Author a);
	public boolean hasCand(Author a);
	public void remCand(Collection<Author> cands);
	public int getCandListSize();
	public Author getCand(int i);
	public void addListenerToCandList(ListChangeListener<Author> listner);
	
	// operations on search result
	public int getSearchResultSize();
	public Author getSearchResult(int i);
	
	// operations on search history
	public void addSearchToHistory(SearchQuery sq);
	public void addListenerToSearchHistory(ListChangeListener<SearchQuery> listner);
	public int getSearchHistorySize();
	public SearchQuery getSearchHistoryItem(int i);
	public void clearSearchHistory();
	
	public ObservableList<Publication> getAuthorPubs(Author atr);
}
