package main.java.interfaces;

import java.util.ArrayList;

import main.java.classes.Author;
import main.java.classes.SearchQuery;
import main.java.classes.SortCriterion;

public interface QueryEngine {
	
	public ArrayList<Author> answerQuery(SearchQuery query);

	public void sortSearchResult(SortCriterion c);
}
