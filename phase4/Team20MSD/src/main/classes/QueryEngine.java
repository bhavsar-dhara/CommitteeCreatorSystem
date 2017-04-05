package main.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;

public class QueryEngine {

	private static Connection conn = connectToDatabaseOrDisconnect();

	static String SQLEXCEPTION = "Threw a SQLException while ";
	static String CLASSNOTFOUNDEXECPTION = "Threw a ClassNotFoundException while connecting to the database.";

	static String GET_NOPB_BY_AUTHORNAME = "select distinct numberOfPb from tb_NumberOfPb where authorname=?";
	static String GET_AUTHORNAME_BY_NOPB = "select distinct authorname from tb_NumberOfPb where numberOfPb=?";
	static String GET_TITLE_BY_AUTHORNAME = "select distinct tp.title, tp.publisher, tp.pbyear from tb_authorProfile ta, tb_publication tp where ta.title=tp.title and ta.authorname=?";
	static String GET_AUTHORNAME_BY_TITLE = "select distinct authorname from tb_authorProfile where title=?";

	// a singleton jdbc connection class
	private static Connection connectToDatabaseOrDisconnect() {
		if (getConn() == null) {
			try {
				
//				Class.forName("org.postgresql.Driver");
//		        setConn(DriverManager.getConnection("jdbc:postgresql://mypostgresqlaws.cxeexamnifqk.us-west-2.rds.amazonaws.com:5432/msddblp","luliuAWS", "1991715ll"));
//		        System.out.println("\nSuccessful connect with database! ");
		        
				Class.forName("org.postgresql.Driver");
//				Connection to local database
				String url = "jdbc:postgresql://localhost:5432/msddblp";
				conn = DriverManager.getConnection(url, "postgres", "1991715");
		        
				// Connection to AWS PostgreSQL Database
				// ERROR ::: on trying to connect to remote
				// Connection refused. Check that the hostname and port are
				// correct and that the postmaster is accepting TCP/IP
				// connections.
//				 String url = "jdbc:postgresql://mypostgresqlaws.cxeexamnifqk.us-west-2.rds.amazonaws.com:5432/msddblp";
////				 url = "jdbc:postgresql://mypostgresqlaws.cxeexamnifqk.us-west-2.rds.amazonaws.com:5432/msddblp";
//				 conn = DriverManager.getConnection(url, "luliuAWS", "1991715ll");
				
				System.out.println("Connection made successfully...");
			} catch (ClassNotFoundException e) {
				System.err.println(CLASSNOTFOUNDEXECPTION);
				System.err.println(e.getMessage());
				System.exit(1);
			} catch (SQLException se) {
				System.err.println(SQLEXCEPTION + "connecting to the database");
				System.err.println(se.getMessage());
				System.exit(2);
			}
		}
		return getConn();
	}

	// Query 1 Search for author list
	// input: 1. from a particular conference/journal
	// 2. keywords present in the title of the publication
	// 3. selecting publication year(s)
	// 4. minimum # of publications made
	// 5. has been a previous committee member or not
	// 6. saving the search criteria??
	// output: list of authors

	// TODO : multiple conferences selected?
	public List<Author> populateListOfAuthors(String confJournal, String keywords, int[] years,
			int noOfPublication) {

		// System.err.println(confJournal);
		// System.err.println(keywords);
		// System.err.println(years[0]);
		// System.err.println(noOfPublication);

		List<Author> listOfAuthors = new ArrayList<Author>();
		try {
			Statement st = getConn().createStatement();
			StringBuilder query = new StringBuilder();
			query.append("SELECT distinct * FROM tb_publication tp, tb_authorprofile ta, tb_numberofpb tn ");
			query.append("where tp.title = ta.title and ta.authorname = tn.authorname ");

			if (confJournal != null && !confJournal.equals("")) {
				query.append("and lower(tp.journal) = lower('" + confJournal + "') ");
			}

			if (keywords != null && !keywords.equals("")) {
				query.append("and lower(tp.title) like lower('%" + keywords + "%') ");
			}

			if (years != null && years.length > 0 && years[0] != 0) {
				for (int year : years) {
					query.append("and tp.pbyear = " + year + " ");
				}
			}

			if (noOfPublication > 0) {
				query.append("and tn.numberofpb = " + noOfPublication + " ");
			}

			System.out.println("............" + query.toString());

			ResultSet rs = st.executeQuery(query.toString());
			 System.out.println(rs.getFetchSize() + "......");
			while (rs.next()) {
				Author author = new Author();
				author.setName(rs.getString("authorname"));
				author.setTitle(rs.getString("title"));
				author.setNoOfPublication(rs.getString("numberofpb"));
				Publication publication = new Publication();
				publication.setTitle(rs.getString("title"));
				publication.setType(rs.getString("type"));
				publication.setPbyear(rs.getInt("pbYear"));
				publication.setPages(rs.getString("pages"));
				publication.setJournal(rs.getString("journal"));
				publication.setEe(rs.getString("ee"));
				publication.setUrl(rs.getString("url"));
				publication.setVolume(rs.getString("volume"));
				publication.setBooktitle(rs.getString("booktitle"));
				publication.setIsbn(rs.getString("isbn"));
				publication.setPublisher(rs.getString("publisher"));
				publication.setEditor(rs.getString("editor"));
				publication.setSchool(rs.getString("school"));
				publication.setNumber(rs.getString("number"));
				author.setPublication(publication);
				// System.out.println(author.toString());
				listOfAuthors.add(author);
			}
			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + "querying author list.");
			System.err.println(se.getMessage());
		}
		return listOfAuthors;
	}

	// Query 2 Search for similar authors
	// result based on: 1. were members of same committee
	// 2. have papers published in similar conference or journal
	// output: list of authors
	public  List<Author> getSimilarAuthorList(Author author) {
		List<Author> similarAuthors = new ArrayList<Author>();
		List<Author> similarAuthorofSameNopb;
		List<Author> similarAuthorofSamePublication;

		similarAuthorofSameNopb = getSimilarAuthorBySameNumberofPB(author);
		similarAuthorofSamePublication = getSimilarAuthorBySamePublication(author);

		similarAuthors.addAll(similarAuthorofSameNopb);
		similarAuthors.addAll(similarAuthorofSamePublication);

		return similarAuthors;
	}

	/*
	 * Sub-Query 3a 
	 * Method to fetch number of publication based on author name
	 * 
	 */
	public  int getNumberofPBByAuthorName(Author author) {
		try {
			PreparedStatement ps = getConn().prepareStatement(GET_NOPB_BY_AUTHORNAME);
			ps.setString(1, author.getName());
			ResultSet rs = ps.executeQuery();
			int numberofPB = 0;
			while (rs.next()) {
				numberofPB = rs.getInt(1);
			}
			return numberofPB;
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + "querying number of publication based on authors.");
			System.err.println(se.getMessage());
		}
		return -1;
	}

	/*
	 * Sub-Query 3b 
	 * Method to fetch similar authors having same number on published data
	 * 
	 */
	public  List<Author> getSimilarAuthorBySameNumberofPB(Author author) {
		int inputAuthorNumberofPB = getNumberofPBByAuthorName(author);
		try {
			PreparedStatement ps = getConn().prepareStatement(GET_AUTHORNAME_BY_NOPB);
			ps.setInt(1, inputAuthorNumberofPB);
			ResultSet rs = ps.executeQuery();
			List<Author> authorsBySameNOPB = new ArrayList<Author>();
			while (rs.next()) {
				Author authorRS = new Author();
				authorRS.setName(rs.getString("authorname"));
				// authorsBySameNOPB.add(rs.getString(1));
				authorsBySameNOPB.add(authorRS);
			}
			return authorsBySameNOPB;
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + "querying similar author by same number of publications.");
			System.err.println(se.getMessage());
		}
		return null;
	}

	/*
	 * Sub-Query 3c 
	 * Method to fetch published papers based on author name
	 * 
	 */
	public  List<Publication> getPublicationByAuthorName(Author author) {
		try {
			PreparedStatement ps = getConn().prepareStatement(GET_TITLE_BY_AUTHORNAME);
			ps.setString(1, author.getName());
			ResultSet rs = ps.executeQuery();
			List<Publication> listofpublicationbyauthorname = FXCollections.observableArrayList();
			while (rs.next()) {
				Publication publicationRS = new Publication();
				publicationRS.setTitle(rs.getString("title"));
				publicationRS.setPublisher(rs.getString("publisher") != null ? rs.getString("publisher") : "");
				publicationRS.setPbyear(rs.getInt("pbyear"));
				// String publication = rs.getString(1);
				listofpublicationbyauthorname.add(publicationRS);
			}
			return listofpublicationbyauthorname;
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + "querying publications based on authors.");
			System.err.println(se.getMessage());
		}
		return null;
	}

	/*
	 * Sub-Query 3d 
	 * Method to fetch similar authors having co-authored paper
	 * 
	 */
	public  List<Author> getSimilarAuthorBySamePublication(Author author) {
		List<Publication> listofpublication = getPublicationByAuthorName(author);
		List<Author> listofAuthorBySamePB = new ArrayList<Author>();
		for (int i = 0; i < listofpublication.size() - 1; i++) {
			String titleName = listofpublication.get(i).getTitle();
			try {
				PreparedStatement ps = getConn().prepareStatement(GET_AUTHORNAME_BY_TITLE);
				ps.setString(1, titleName);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					Author authorRS = new Author();
					authorRS.setName(rs.getString("authorname"));
					// String authornameBySamePB = rs.getString(1);
					listofAuthorBySamePB.add(authorRS);
				}
			} catch (SQLException se) {
				System.err.println(SQLEXCEPTION + "querying similar author who have co-authored a paper.");
				System.err.println(se.getMessage());
			}
		}
		return listofAuthorBySamePB;
	}

	// Query 4 Search authors details
	// input: author name
	// output: list of author's publication list
	public  List<Author> fetchAuthorDetails(Author author) {
		List<Author> authorList = new ArrayList<>();
		try {
			Statement st = getConn().createStatement();
			StringBuilder query = new StringBuilder();
			query.append("SELECT distinct * FROM tb_publication tp, tb_authorprofile ta, tb_numberofpb tn ");
			query.append("where tp.title = ta.title and ta.authorname = tn.authorname ");
			query.append("and lower(ta.authorname) like '%" + author.getName() + "%'");

			ResultSet rs = st.executeQuery(query.toString());
			while (rs.next()) {
				Author authorRS = new Author();
				authorRS.setName(rs.getString("authorname"));
				authorRS.setTitle(rs.getString("title"));
				authorRS.setNoOfPublication(rs.getString("numberofpb"));
				Publication publication = new Publication();
				publication.setTitle(rs.getString("title"));
				publication.setPbyear(rs.getInt("pbYear"));
				publication.setPages(rs.getString("pages"));
				publication.setJournal(rs.getString("journal"));
				publication.setVolume(rs.getString("volume"));
				publication.setUrl(rs.getString("url"));
				publication.setEe(rs.getString("ee"));
				publication.setNumber(rs.getString("number"));
				authorRS.setPublication(publication);
//				System.out.println(authorRS.toString());
				authorList.add(authorRS);
			}

			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + "querying author details.");
			System.err.println(se.getMessage());
		}
		return authorList;
	}

	/*
	 * Query 5 Method to fetch distinct Journal names present in the Database
	 * 
	 */
	public  List<String> fetchJournalNames() {
		List<String> journalList = new ArrayList<>();
		try {
			Statement st = getConn().createStatement();
			StringBuilder query = new StringBuilder();
			query.append("Select distinct journal from tb_publication ");

			ResultSet rs = st.executeQuery(query.toString());
			while (rs.next()) {
				journalList.add(rs.getString("journal"));
			}

			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + "querying journal list.");
			System.err.println(se.getMessage());
		}
		return journalList;
	}

	/*
	 * Query 6 Method to fetch distinct Journal names present in the Database
	 * 
	 */
	public  List<Integer> fetchYearsAvailable() {
		List<Integer> yearList = new ArrayList<>();
		try {
			Statement st = getConn().createStatement();
			StringBuilder query = new StringBuilder();
			query.append("Select distinct pbyear from tb_publication order by pbyear desc; ");

			ResultSet rs = st.executeQuery(query.toString());
			while (rs.next()) {
				yearList.add(rs.getInt("pbyear"));
			}

			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + "querying publication year list.");
			System.err.println(se.getMessage());
		}
		return yearList;
	}

	/*
	 * Queries 7 - 10 Four Methods to carry out CRUD operations on the Favorite
	 * candidates
	 * 
	 */
	public  Author fetchFavCandidate(int i) {
		List<Author> candidatesList = new ArrayList<>();
		try {
			Statement st = getConn().createStatement();
			StringBuilder query = new StringBuilder();
			query.append("Select distinct authorname from tb_candidate ");

			ResultSet rs = st.executeQuery(query.toString());
			while (rs.next()) {
				candidatesList.add(new Author(rs.getString("authorname")));
			}

			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + "querying fav candidate list.");
			System.err.println(se.getMessage());
		}
		return candidatesList.get(i);
	}

	public  int addFavCandidate(Author author) {
		int affectedRows = 0;
		try {
			Statement st = getConn().createStatement();
			StringBuilder query = new StringBuilder();
			query.append("INSERT INTO tb_candidate(authorname) VALUES (" + author.getName() + ")");

			affectedRows = st.executeUpdate(query.toString());

			st.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + "adding fav candidate.");
			System.err.println(se.getMessage());
		}
		return affectedRows;
	}

	public  int deleteFavCandidate(Author author) {
		int affectedRows = 0;
		try {
			PreparedStatement pstmt = getConn().prepareStatement("DELETE from tb_candidate where authorname=?");
			pstmt.setString(1, author.getName());

			affectedRows = pstmt.executeUpdate();

			pstmt.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + "deleting fav candidate.");
			System.err.println(se.getMessage());
		}
		return affectedRows;
	}

	public  int countFavCandidates() {
		int numberOfRows = 0;
		try {
			PreparedStatement pstmt = getConn().prepareStatement("SELECT COUNT(*) from tb_candidate");

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				numberOfRows = rs.getInt(1);
				System.out.println("numberOfRows= " + numberOfRows);
			} else {
				System.err.println("error: could not get the record counts");
			}

			pstmt.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + "counting fav candidate.");
			System.err.println(se.getMessage());
		}
		return numberOfRows;
	}

	/*
	 * Queries 11 - 14 Four Methods to carry out CRUD operations on the Saved
	 * Queries
	 * 
	 */
	public  List<String> fetchSavedQueries() {
		List<String> queryList = new ArrayList<>();
		try {
			Statement st = getConn().createStatement();
			StringBuilder query = new StringBuilder();
			query.append("Select distinct query from tb_savedqueries ");

			ResultSet rs = st.executeQuery(query.toString());
			while (rs.next()) {
				queryList.add(rs.getString("query"));
			}

			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + "querying saved queries list.");
			System.err.println(se.getMessage());
		}
		return queryList;
	}

	public  int addSavedQuery(String saveQuery) {
		int affectedRows = 0;
		try {
			Statement st = getConn().createStatement();
			StringBuilder query = new StringBuilder();
			query.append("INSERT INTO tb_savedqueries(query) VALUES (" + saveQuery + ")");

			affectedRows = st.executeUpdate(query.toString());

			st.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + "adding saved queries.");
			System.err.println(se.getMessage());
		}
		return affectedRows;
	}

	public  int deleteSavedQuery(String saveQuery) {
		int affectedRows = 0;
		try {
			PreparedStatement pstmt = getConn().prepareStatement("DELETE from tb_savedqueries where query=?");
			pstmt.setString(1, saveQuery);

			affectedRows = pstmt.executeUpdate();

			pstmt.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + "deleting saved queries.");
			System.err.println(se.getMessage());
		}
		return affectedRows;
	}

	public  int countSavedQueries() {
		int numberOfRows = 0;
		try {
			PreparedStatement pstmt = getConn().prepareStatement("SELECT COUNT(*) from tb_savedqueries");

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				numberOfRows = rs.getInt(1);
				System.out.println("numberOfRows= " + numberOfRows);
			} else {
				System.err.println("error: could not get the record counts");
			}

			pstmt.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + "counting saved queries.");
			System.err.println(se.getMessage());
		}
		return numberOfRows;
	}
	
	/*
	 * 
	 * Query 15 : to find a particular author in Favorite Author List
	 * 
	 */
	public boolean isFavCandidate(Author author) {
		Author resultAuthor = new Author();
		boolean isPresent = false;
		try {
			Statement st = getConn().createStatement();
			StringBuilder query = new StringBuilder();
			query.append("Select distinct authorname from tb_candidate "
					+ "where lower(authorname) = lower('" + author.getName() + "') ");

			ResultSet rs = st.executeQuery(query.toString());
			while (rs.next()) {
				resultAuthor.setName(rs.getString("authorname"));
			}
			
			if (resultAuthor.getName() != null) {
				isPresent = true;
			}

			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + "deleting fav candidate.");
			System.err.println(se.getMessage());
		}
//		return resultAuthor;
		return isPresent;
	}

	public static Connection getConn() {
		return conn;
	}

	public static void setConn(Connection conn) {
		QueryEngine.conn = conn;
	}
}
