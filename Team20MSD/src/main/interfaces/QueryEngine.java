package main.interfaces;

import java.util.List;

import main.classes.Author;
import main.classes.Publication;

public interface QueryEngine {
	
	public List<Author> populateListOfAuthors(String confJournal, String keywords, 
			int[] years, int noOfPublication);
	
	public List<Author> getSimilarAuthorList(Author author);
	
	public int getNumberofPBByAuthorName(Author author);
	
	public List<Author> getSimilarAuthorBySameNumberofPB(Author author);
	
	public List<Publication> getPublicationByAuthorName(Author author);
	
	public List<Author> getSimilarAuthorBySamePublication(Author author);
	
	public List<Author> fetchAuthorDetails(Author author);
}
