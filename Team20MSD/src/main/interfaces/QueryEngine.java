package main.interfaces;

import java.util.ArrayList;

import main.classes.Author;
import main.classes.SearchQuery;
import main.classes.SortCriterion;

public interface QueryEngine {
	
	public ArrayList<Author> answerQuery(SearchQuery query);

	public void sortSearchResult(SortCriterion c);
}
