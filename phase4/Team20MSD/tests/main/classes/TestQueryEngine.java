package main.classes;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;

public class TestQueryEngine extends TestCase {

	QueryEngine searchQuery = QueryEngine.instance();

	Publication publication1 = new Publication("Parallel Approximation Schemes for Problems on Planar Graphs.",
			"articles", 1996, "387-408", "Acta Inf.", "http://dx.doi.org/10.1007/s002360050049",
			"db/journals/acta/acta33.html#DiazST96", "33", "", "", "", "", "", "4");
	List<Author> authorListQuery1 = new ArrayList<Author>();
	Author author1 = new Author("Parallel Approximation Schemes for Problems on Planar Graphs.", "Jacobo Torán", "4",
			publication1);

	Publication publication2 = new Publication("Parallel Integer Sorting and Simulation Amongst CRCW Models.",
			"articles", 1996, "607-619", "Acta Inf.", "http://dx.doi.org/10.1007/s002360050049",
			"db/journals/acta/acta33.html#DiazST96", "7", "", "", "", "", "", "2");
	Publication publication3 = new Publication(
			"Effective and efficient similarity search in scientific workflow repositories.", "articles", 2016,
			"584-594", "Future Generation Comp. Syst.", "http://dx.doi.org/10.1007/s002360050049",
			"db/journals/acta/acta33.html#DiazST96", "7", "", "", "", "", "", "2");
	List<Author> authorListQuery2 = new ArrayList<Author>();
	Author author2 = new Author("Parallel Integer Sorting and Simulation Amongst CRCW Models.", "Sanjeev Saxena", "2",
			publication2);
	Author author3 = new Author("Effective and efficient similarity search in scientific workflow repositories.",
			"Sanjeev Saxena", "2", publication3);

	List<Author> authorListQuery3 = new ArrayList<Author>();

	List<Author> authorListQuery4 = new ArrayList<Author>();

	Publication publication4 = new Publication("Class Hierarchy Specialization.", "inproceedings", 2002, "927-982",
			"Acta Inf.", "http://link.springer.de/link/service/journals/00236/bibs/0036012/00360927.htm",
			"db/journals/acta/acta36.html#TipS00", "36", "", "", "", "", "", "12");
	Author author4 = new Author("Class Hierarchy Specialization.", "Frank Tip", "14", 2007, "Member", "issta",
			publication4);

	Publication publication5 = new Publication("The Implementation of Retention in a Coroutine Environment.",
			"articles", 2000, "221-233", "Acta Inf.", "http://dx.doi.org/10.1007/BF00265556",
			"db/journals/acta/acta19.html#KearnsS83", "19", "", "", "", "", "", "3");
	Author author5 = new Author("The Implementation of Retention in a Coroutine Environment.", "Mary Lou Soffa", "49",
			publication5);

	Publication publication6 = new Publication("safeDpi: a language for controlling mobile code.", "inproceedings",
			2012, "227-290", "Acta Inf.", "http://dx.doi.org/10.1007/s00236-005-0178-y",
			"db/journals/acta/acta42.html#HennessyRY05", "42", "", "", "", "", "", "4-5");
	Author author6 = new Author("safeDpi: a language for controlling mobile code.", "Nobuko Yoshida", "63",
			publication6);

	@Test
	public void testPopulateListOfAuthorsQuery() {

		int[] intArray = new int[1];
		intArray[0] = 2002;

		authorListQuery4.add(author4);

		assertNotSame("Populate Author List", authorListQuery4,
				searchQuery.populateListOfAuthors("acta inf.", "class", intArray, 14, true, "10"));
	}

	@Test
	public void testFetchAuthorDetails() {

		int[] intArray = new int[1];
		intArray[0] = 1996;

		authorListQuery2.add(author2);
		authorListQuery2.add(author3);

		Author testAuthor = new Author("sanjeev");
		assertNotSame("Get Author Details", authorListQuery2, searchQuery.fetchAuthorDetails(testAuthor));
	}

	@Test
	public void testPopulateListOfAuthorsQuery_2() {

		int[] intArray = new int[1];
		intArray[0] = 1996;

		authorListQuery3.add(author1);

		assertNotSame("Populate Author List", authorListQuery3,
				searchQuery.populateListOfAuthors("acta inf.", "parallel", intArray, 4, false, "0"));
	}

	@Test
	public void testtGetSimilarAuthorBySamePublication() {

		int[] intArray = new int[1];
		intArray[0] = 1996;

		authorListQuery4.add(author1);

		assertNotSame("Same No of Publication -> Author List", authorListQuery4,
				searchQuery.populateListOfAuthors("acta inf.", "parallel", intArray, 4, false, "0"));
	}

	@Test
	public void testPopulateListOfAuthor_1() {
		List<Author> authorListQuery1 = null;
		int[] intArray = null;

		assertNotSame("Populate Author List", authorListQuery1,
				searchQuery.populateListOfAuthors("acta inf.", "class", intArray, 4, true, "2"));

	}

	@Test
	public void testGetNumberofPBByAuthorName() {

		int num = searchQuery.getNumberofPBByAuthorName(author5);
		assertEquals(num, 49);
	}

	@Test
	public void testGetPublicationByAuthorName() {

		int num = searchQuery.getPublicationByAuthorName(author5).size();
		assertEquals(num, 49);
	}

	// Test for query 4 test needs to be added

	@Test
	public void testFetchJournalNames() {

		ArrayList<String> journalsFound = new ArrayList<>(searchQuery.fetchJournalNames());
		assertEquals(journalsFound.size(), 60);
	}

	@Test
	public void testFetchYearsAvailable() {

		ArrayList<Integer> yearsFound = new ArrayList<>(searchQuery.fetchYearsAvailable());
		assertEquals(yearsFound.size(), 41);
	}

	/*------------------------------------------------------------------------------------------------------------*/
	// Operations on candidate list

	@Test
	public void testFetchCandidates() {

		Author cand = searchQuery.fetchCandidates(0);
		assertNotSame(cand.getName(), author5.getName());
	}

	@Test
	public void testAddAuthorIntoCandidate() {

		searchQuery.addAuthorIntoCandidate(author4);
	}

	@Test
	public void testDeleteFavCandidate() {

		searchQuery.addAuthorIntoCandidate(author5);
	}

	@Test
	public void testCountFavCandidates() {

		assertNotSame(11, searchQuery.countFavCandidates());
	}

	@Test
	public void testIsFavCandidate() {

		assertFalse(searchQuery.isFavCandidate(author6));
	}

	@Test
	public void testFetchCommitteeNameList() {

		ArrayList<String> committeesFound = new ArrayList<>(searchQuery.fetchCommitteeNameList());
		assertEquals(committeesFound.size(), 12);
	}

	// Test for query 17 test needs to be added

	// Query 18 not sure if it's needed

	@Test
	public void testGetAuthorUnivDetails() {

		assertEquals("Northeastern University", searchQuery.getAuthorUnivDetails(author4));
	}

	// Query 21 not sure how to do it

	// Query 22 helper function

	@Test
	public void testGetAuthorCommitteeDetailsByAuthorName() {

		authorListQuery4.add(author4);
		ArrayList<Author> committeeDetailsFound = new ArrayList<>(
				searchQuery.getAuthorCommitteeDetailsByAuthorName(author4));
		ArrayList<Author> actualCommitteeDetails = new ArrayList<>(authorListQuery4);
		assertEquals(committeeDetailsFound.size(), actualCommitteeDetails.size());
		for (int i = 0; i < actualCommitteeDetails.size(); i++) {
			assertEquals(committeeDetailsFound.get(i).getCommittee(), actualCommitteeDetails.get(i).getCommittee());
			assertEquals(committeeDetailsFound.get(i).getCheckYear(), actualCommitteeDetails.get(i).getCheckYear());
			assertEquals(committeeDetailsFound.get(i).getRole(), actualCommitteeDetails.get(i).getRole());
		}
	}
	
	
}
