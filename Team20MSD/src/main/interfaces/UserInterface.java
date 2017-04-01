package main.interfaces;

import main.classes.Author;
import main.classes.SearchQuery;

public interface UserInterface {
	
	public void viewMainPage();

	public void setName(String n);

	public void setTitle(String t);

	public void setPubNum(String n);

	public void ConJour(String n);

	public void viewSearchHistory();

	public void repeatSearch(SearchQuery query);

	public void clearSearch();

	public void viewFavAuthros();

	public void addFavAuthor(Author a);

	public void removeFavAuthor(Author a);

	public void viewSearchResult(int pagenumber);

	public void viewAuthorProfile(Author a);
}
