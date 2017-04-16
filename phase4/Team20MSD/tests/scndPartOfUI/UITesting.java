package scndPartOfUI;

import main.classes.Author;
import main.interfaces.SearchHistoryListener;
import main.interfaces.UserInterface;
import main.search.Result;

public interface UITesting extends UserInterface {
	public void setTestData();

	public int getSearchResultSize();

	public Author getSearchResult(int i);

	public void getSearchResult(Result sq);

	// operations on search history
	public void addSearchToHistory(Result sq);

	public int getSearchHistorySize();

	public Result getSearchHistoryItem(int i);

	public void clearSearchHistory();

	public void addListenerToSearchHistory(SearchHistoryListener p);
}
