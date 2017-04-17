package main.java.search.userinterface;

import main.java.search.model.Author;
import main.java.serach.interfaces.SearchHistoryListener;
import main.java.serach.interfaces.UserInterface;

public interface UITesting extends UserInterface {
	public void setTestData();

	public int getSearchResultSize();

	public Author getSearchResult(int i);

//	public void getSearchResult(Result sq);
//
//	// operations on search history
//	public void addSearchToHistory(Result sq);

	public int getSearchHistorySize();

//	public Result getSearchHistoryItem(int i);

	public void clearSearchHistory();

	public void addListenerToSearchHistory(SearchHistoryListener p);
}
