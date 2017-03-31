package test.java;

import java.util.List;

import org.junit.Test;

import main.java.classes.Author;
import main.java.classes.SearchQuery;

public class TestQueryEngine {
	
	@Test
	public void testPopulateListOfAuthorsQuery() {
		SearchQuery searchQuery = new SearchQuery();
		
		int[] intArray = new int[1];
		intArray[0] = 1996;
		List<Author> resultList = searchQuery.populateListOfAuthors("acta inf.", "parallel", intArray, 2);
	}

}
