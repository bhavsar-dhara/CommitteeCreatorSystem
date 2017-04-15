package userInterface.classes;
import main.classes.Author;
import main.interfaces.UserInterface;
import userInterface.interfaces.SearchHistoryListener;

public interface UITesting extends UserInterface {
	public void setTestData();
	
	public int getSearchResultSize();
	public Author getSearchResult(int i);
	public void getSearchResult(SearchQuery sq);
	
	// operations on search history
	public void addSearchToHistory(SearchQuery sq);
	public int getSearchHistorySize();
	public SearchQuery getSearchHistoryItem(int i);
	public void clearSearchHistory();
	public void addListenerToSearchHistory(SearchHistoryListener p);
}
