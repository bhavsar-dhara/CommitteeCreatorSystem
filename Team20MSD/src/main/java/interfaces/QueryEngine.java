package main.java.interfaces;

import java.util.List;

import main.java.classes.Author;

public interface QueryEngine {
	
	public List<Author> populateListOfAuthors(String confJournal, String keywords, 
			int[] years, int noOfPublication);
	
	public List<String> getSimilarAuthorBySameNumberofPB(String authorName);
	
	public List<String> getSimilarAuthorBySamePublication(String authorName);
	
	public List<Author> fetchAuthorDetails(String authorName);
}
