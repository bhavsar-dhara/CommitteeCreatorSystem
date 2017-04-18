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


	@Test
	public void testPopulateListOfAuthorsQuery() {

		int[] intArray = new int[1];
		intArray[0] = 1996;

		authorListQuery1.add(author1);

		assertNotSame("Populate Author List", authorListQuery1,
				searchQuery.populateListOfAuthors("acta inf.", "parallel", intArray, 4, false, "0"));
	}

	@Test
	public void testFetchAuthorDetails() {

		int[] intArray = new int[1];
		intArray[0] = 1996;

		authorListQuery2.add(author2);
		authorListQuery2.add(author3);

		Author testAuthor = new Author("sanjeev");
		assertNotSame("Populate Author List", authorListQuery2, searchQuery.fetchAuthorDetails(testAuthor));
	}

	@Test
	public void testGetSimilarAuthorBySameNumberofPB() {

		int[] intArray = new int[1];
		intArray[0] = 1996;

		authorListQuery3.add(author1);

		assertNotSame("Populate Author List", authorListQuery3,
				searchQuery.populateListOfAuthors("acta inf.", "parallel", intArray, 4, false, "0"));
	}

	@Test
	public void testGetSimilarAuthorBySamePublication() {

		int[] intArray = new int[1];
		intArray[0] = 1996;

		authorListQuery4.add(author1);

		assertNotSame("Populate Author List", authorListQuery4,
				searchQuery.populateListOfAuthors("acta inf.", "parallel", intArray, 4, false, "0"));
	}

	// keyword specified
	@Test
	public void testPopulateListOfAuthor_1() {

	}

	// year specified
	@Test
	public void testPopulateListOfAuthor_2() {
		List<Author> authorListQuery1 = null;
		int[] intArray = null;
		// TODO : resolve Assertion Error as publication objects are
		// different!!!
		assertNotSame("Populate Author List", authorListQuery1,
				searchQuery.populateListOfAuthors("acta inf.", "class", intArray, 4, false, "0"));

	}

	// conference specified
	@Test
	public void testPopulateListOfAuthor_3() {

	}

	// number of publication specified
	@Test
	public void testPopulateListOfAuthor_4() {

	}

	// TODO: test with more kinds of queries

	/*------------------------------------------------------------------------------------------------------------*/

	@Test
	public void testGetNumberofPBByAuthorName() {
		QueryEngine qe = QueryEngine.instance();
		Author test = new Author(/* needs data */);
		int num = qe.getNumberofPBByAuthorName(test);
		assertEquals(num, 1/* needs data */);
	}

	@Test
	public void testGetPublicationByAuthorName() {
		QueryEngine qe = QueryEngine.instance();
		Author test = new Author(/* needs data */);

	}

	// Test for query 4 test needs to be added

	@Test
	public void testFetchJournalNames() {
		QueryEngine qe = QueryEngine.instance();
		ArrayList<String> journalsFound = new ArrayList<>(qe.fetchJournalNames());
		ArrayList<String> actualJournals = new ArrayList<>(/* needs data */);
		assertEquals(journalsFound.size(), actualJournals.size());
		for (int i = 0; i < journalsFound.size(); i++) {
			assertEquals(journalsFound.get(i), actualJournals.get(i));
		}
	}

	@Test
	public void testFetchYearsAvailable() {
		QueryEngine qe = QueryEngine.instance();
		ArrayList<Integer> yearsFound = new ArrayList<>(qe.fetchYearsAvailable());
		ArrayList<Integer> actualYears = new ArrayList<>(/* needs data */);
		assertEquals(yearsFound.size(), actualYears.size());
		for (int i = 0; i < yearsFound.size(); i++) {
			assertEquals(yearsFound.get(i), actualYears.get(i));
		}
	}

	/*------------------------------------------------------------------------------------------------------------*/
	// Operations on candidate list

	@Test
	public void testFetchCandidates() {
		QueryEngine qe = QueryEngine.instance();
		Author cand = qe.fetchCandidates(0);
		assertEquals(cand.getName(), ""/* needs data */);
	}

	@Test
	public void testAddAuthorIntoCandidate() {
		QueryEngine qe = QueryEngine.instance();
		Author authorToAdd = new Author(/* needs data */);
		qe.addAuthorIntoCandidate(authorToAdd);
		// Use SQL to test whether the author has been added
	}

	@Test
	public void testDeleteFavCandidate() {
		QueryEngine qe = QueryEngine.instance();
		Author candToDelete = new Author(/* needs data */);
		qe.addAuthorIntoCandidate(candToDelete);
		// Use SQL to test whether the author has been added
	}

	@Test
	public void testCountFavCandidates() {
		QueryEngine qe = QueryEngine.instance();
		assertEquals(1/* needs data */, qe.countFavCandidates());
	}

	@Test
	public void testIsFavCandidate() {
		QueryEngine qe = QueryEngine.instance();
		Author cand = new Author(/* needs data */);
		assertTrue(qe.isFavCandidate(cand));
	}

	@Test
	public void testFetchCommitteeNameList() {
		QueryEngine qe = QueryEngine.instance();
		ArrayList<String> committeesFound = new ArrayList<>(qe.fetchCommitteeNameList());
		ArrayList<String> actualCommittees = new ArrayList<>(/* needs data */);
		assertEquals(committeesFound.size(), actualCommittees.size());
		for (int i = 0; i < committeesFound.size(); i++) {
			assertEquals(committeesFound.get(i), actualCommittees.get(i));
		}
	}

	// Test for query 17 test needs to be added

	// Query 18 not sure if it's needed

	@Test
	public void testGetAuthorUnivDetails() {
		QueryEngine qe = QueryEngine.instance();
		Author authorToTest = new Author(/* needs data */);
		assertEquals("NEU"/* needs data */, qe.getAuthorUnivDetails(authorToTest));
	}

	// Query 21 not sure how to do it

	// Query 22 helper function

	@Test
	public void testGetAuthorCommitteeDetailsByAuthorName() {
		QueryEngine qe = QueryEngine.instance();
		Author authorToTest = new Author(/* needs data */);
		ArrayList<Author> committeeDetailsFound = new ArrayList<>(
				qe.getAuthorCommitteeDetailsByAuthorName(authorToTest));
		ArrayList<Author> actualCommitteeDetails = new ArrayList<>(/*
																	 * needs
																	 * data
																	 */);
		assertEquals(committeeDetailsFound.size(), actualCommitteeDetails.size());
		for (int i = 0; i < actualCommitteeDetails.size(); i++) {
			assertEquals(committeeDetailsFound.get(i).getCommittee(), actualCommitteeDetails.get(i).getCommittee());
			assertEquals(committeeDetailsFound.get(i).getCheckYear(), actualCommitteeDetails.get(i).getCheckYear());
			assertEquals(committeeDetailsFound.get(i).getRole(), actualCommitteeDetails.get(i).getRole());
		}
	}
}
