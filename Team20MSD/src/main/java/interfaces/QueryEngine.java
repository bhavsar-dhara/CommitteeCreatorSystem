package main.java.interfaces;

import java.util.ArrayList;
import java.util.List;

import main.java.classes.Author;
import main.java.classes.SearchQuery;
import main.java.classes.SortCriterion;

public interface QueryEngine {
	
	// TODO : update with new methods
	
	public ArrayList<Author> answerQuery(SearchQuery query);

	public void sortSearchResult(SortCriterion c);
	
	public List<Author> populateListOfAuthors(String confJournal, String keywords, 
			int[] years, int noOfPublication);
	
	public List<String> getSimilarAuthorBySameNumberofPB(String authorName);
	
	public List<String> getSimilarAuthorBySamePublication(String authorName);
	
	public List<Author> fetchAuthorDetails(String authorName);
}
