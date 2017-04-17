package main.java.search.service;

import javafx.collections.FXCollections;
import main.java.search.model.Author;
import main.java.search.model.Publication;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dhara
 *
 */
/**
 * @author Dhara
 *
 */
/**
 * @author Dhara
 *
 */
/**
 * @author Dhara
 *
 */
/**
 * @author Dhara
 *
 */
/**
 * @author Dhara
 *
 */
/**
 * @author Dhara
 *
 */
public class QueryEngine {

	private static Connection conn = connectToDatabaseOrDisconnect();

	static String SQLEXCEPTION = "Threw a SQLException while ";
	static String CLASSNOTFOUNDEXECPTION = "Threw a ClassNotFoundException while connecting to the database.";

	static String GET_NOPB_BY_AUTHORNAME = "select distinct numberOfPb from tb_NumberOfPb where authorname=?";
	static String GET_AUTHORNAME_BY_NOPB = "select distinct authorname from tb_NumberOfPb where numberOfPb=?";
	static String GET_TITLE_BY_AUTHORNAME = "select distinct tp.* from tb_authorProfile ta, tb_publication tp where ta.title=tp.title and ta.authorname=?";
	static String GET_AUTHORNAME_BY_TITLE = "select distinct authorname from tb_authorProfile where title=?";

	/**
	 * a static jdbc connection class
	 * @return java.sql.Connection
	 */
	private static Connection connectToDatabaseOrDisconnect() {
		if (getConn() == null) {
			try {

				Class.forName("org.postgresql.Driver");
				setConn(DriverManager.getConnection(
						"jdbc:postgresql://mypostgresqlaws.cxeexamnifqk.us-west-2.rds.amazonaws.com:5432/msddblp",
						"luliuAWS", "1991715ll"));
				System.out.println("\nSuccessful connect with database! ");

				// Class.forName("org.postgresql.Driver");
				//// Connection to local database
				// String url = "jdbc:postgresql://localhost:5432/msddblp";
				// conn = DriverManager.getConnection(url, "postgres",
				// "1991715");

				System.out.println("Connection made successfully...");
			} catch (ClassNotFoundException e) {
				System.err.println(CLASSNOTFOUNDEXECPTION + " class not found exception");
				System.err.println(e.getMessage());
				System.exit(1);
			} catch (SQLException se) {
				System.err.println(SQLEXCEPTION + " connecting to the database");
				System.err.println(se.getMessage());
				System.exit(2);
			}
		}
		return getConn();
	}

	/**
	 * Query 1 : Search for author and his every publication list
	 * @param confJournal: based on a particular conference/journal selected
	 * @param keywords: based on keywords present in the title of the publication entered
	 * @param years: selecting publication year(s)
	 * @param noOfPublication: minimum # of publications made
	 * @param isServedAsCommittee: has been a previous committee member or not
	 * @return list of Author with all his publications
	 * @exception SQLException
	 */

	public List<Author> populateListOfAuthors(String confJournal, String keywords, int[] years, int noOfPublication,
			boolean isServedAsCommittee) {

		List<Author> listOfAuthors = new ArrayList<Author>();
		try {
			Statement st = getConn().createStatement();
			StringBuilder query = new StringBuilder();
			query.append("SELECT distinct ta.authorname, ta.title, tn.numberofpb, "
					/* + "tc.role, tc.checkyear, tc.committee, " */
					+ "tp.type, tp.pbyear, tp.pages, tp.journal, tp.ee, tp.url, tp.volume, "
					+ "tp.booktitle, tp.isbn, tp.publisher, tp.editor, tp.school, tp.number "
					+ "FROM tb_publication tp, " + "tb_authorprofile ta, "
					+ "tb_numberofpb tn " /* + ", tb_committeecheck tc " */);
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
				query.append("and tn.numberofpb >= " + noOfPublication + " ");
			}

			// if (isServedAsCommittee) {
			// query.append("and tc.authorname = ta.authorname" + " ");
			// }

			query.append("GROUP BY ta.authorname, ta.title, tn.numberofpb, "
					/* + "tc.role, tc.checkyear, tc.committee, " */
					+ "tp.type, tp.pbyear, tp.pages, tp.journal, tp.ee, tp.url, tp.volume, "
					+ "tp.booktitle, tp.isbn, tp.publisher, tp.editor, tp.school, tp.number ");
			query.append("ORDER BY ta.authorname, ta.title");

			System.out.println("............" + query.toString());

			ResultSet rs = st.executeQuery(query.toString());
			System.out.println(rs.getFetchSize() + "......");

			int count = 0;
			while (rs.next()) {
				count++;
				Author author = new Author();
				author.setName(rs.getString("authorname"));
				author.setTitle(rs.getString("title"));
				author.setNoOfPublication(rs.getString("numberofpb"));
				// author.setRole(rs.getString("role"));
				// author.setCheckYear(rs.getInt("checkyear"));
				// author.setCommittee(rs.getString("committee"));
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
			System.out.println(count + "......");

			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + " querying author list.");
			System.err.println(se.getMessage());
		}
		return listOfAuthors;
	}

	/**
	 * Query 2 : Search for similar authors
	 * @param author
	 * @return list of Authors with similar profile as the given author 
	 * @exception SQLException
	 */
	public List<Author> getSimilarAuthorList(Author author) {
		List<Author> similarAuthors = new ArrayList<Author>();
		List<Author> similarAuthorofSameNopb;
		List<Author> similarAuthorofSamePublication;

		similarAuthorofSameNopb = getSimilarAuthorBySameNumberofPB(author);
		similarAuthorofSamePublication = getSimilarAuthorBySamePublication(author);

		similarAuthors.addAll(similarAuthorofSameNopb);
		similarAuthors.addAll(similarAuthorofSamePublication);

		return similarAuthors;
	}

	/**
	 * Sub-Query 3a : Method to fetch number of publication based on author name
	 * @param author
	 * @return number of publications 
	 * @exception SQLException
	 */
	public int getNumberofPBByAuthorName(Author author) {
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
			System.err.println(SQLEXCEPTION + " querying number of publication based on authors.");
			System.err.println(se.getMessage());
		}
		return -1;
	}

	/**
	 * Sub-Query 3b : Method to fetch similar authors having same number on published data
	 * @param author
	 * @return list of authors with the same number of publications
	 * @exception SQLException
	 */
	public List<Author> getSimilarAuthorBySameNumberofPB(Author author) {
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
			System.err.println(SQLEXCEPTION + " querying similar author by same number of publications.");
			System.err.println(se.getMessage());
		}
		return null;
	}

	/**
	 * Sub-Query 3c : Method to fetch published papers based on author name
	 * @param author
	 * @return
	 * @exception SQLException
	 */
	public List<Publication> getPublicationByAuthorName(Author author) {
		try {
			PreparedStatement ps = getConn().prepareStatement(GET_TITLE_BY_AUTHORNAME);
			ps.setString(1, author.getName());
			ResultSet rs = ps.executeQuery();
			List<Publication> listofpublicationbyauthorname = FXCollections.observableArrayList();
			while (rs.next()) {
				Publication publicationRS = new Publication();
				publicationRS.setTitle(rs.getString("title"));
				publicationRS.setPublisher(rs.getString("publisher") != null ? rs.getString("publisher") : "N/A");
				publicationRS
						.setPbyear(rs.getInt("pbyear") > 0 && rs.getInt("pbyear") < 2018 ? rs.getInt("pbyear") : 0);
				publicationRS.setType(rs.getString("type"));
				publicationRS.setPages(rs.getString("pages") != null ? rs.getString("pages") : "N/A");
				publicationRS.setJournal(rs.getString("journal") != null ? rs.getString("journal") : "N/A");
				publicationRS.setEe(rs.getString("ee") != null ? rs.getString("ee") : "N/A");
				publicationRS.setUrl(rs.getString("url") != null ? rs.getString("url") : "N/A");
				publicationRS.setVolume(rs.getString("volume") != null ? rs.getString("volume") : "N/A");
				publicationRS.setBooktitle(rs.getString("booktitle") != null ? rs.getString("booktitle") : "N/A");
				publicationRS.setIsbn(rs.getString("isbn") != null ? rs.getString("isbn") : "N/A");
				publicationRS.setEditor(rs.getString("editor") != null ? rs.getString("editor") : "N/A");
				publicationRS.setSchool(rs.getString("school") != null ? rs.getString("school") : "N/A");
				publicationRS.setNumber(rs.getString("number") != null ? rs.getString("number") : "N/A");
				listofpublicationbyauthorname.add(publicationRS);
			}
			return listofpublicationbyauthorname;
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + " querying publications based on authors.");
			System.err.println(se.getMessage());
		}
		return null;
	}

	/**
	 * Sub-Query 3d : Method to fetch similar authors having co-authored paper
	 * @param author
	 * @return
	 * @exception SQLException
	 */
	public List<Author> getSimilarAuthorBySamePublication(Author author) {
		List<Publication> listofpublication = getPublicationByAuthorName(author);
		List<Author> listofAuthorBySamePB = FXCollections.observableArrayList();
		for (int i = 0; i < listofpublication.size() - 1; i++) {
			String titleName = listofpublication.get(i).getTitle();
			System.out.print(titleName);
			try {
				PreparedStatement ps = getConn().prepareStatement(GET_AUTHORNAME_BY_TITLE);
				ps.setString(1, titleName);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					Author authorRS = new Author();
					authorRS.setName(rs.getString("authorname"));
					authorRS.setTitle(titleName);
					authorRS.setNoOfPublication(rs.getString("numberofpb"));
					// String authornameBySamePB = rs.getString(1);
					listofAuthorBySamePB.add(authorRS);
					System.out.print(listofAuthorBySamePB);
				}
			} catch (SQLException se) {
				System.err.println(SQLEXCEPTION + " querying similar author who have co-authored a paper.");
				System.err.println(se.getMessage());
			}
		}
		return listofAuthorBySamePB;
	}

	/**
	 * Query 4 - Search authors details
	 * @param author
	 * @return list of author with individual publication object to it
	 * @exception SQLException
	 */
	public List<Author> fetchAuthorDetails(Author author) {
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
				// System.out.println(authorRS.toString());
				authorList.add(authorRS);
			}

			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + " querying author details.");
			System.err.println(se.getMessage());
		}
		return authorList;
	}

	/**
	 * Query 5 : Method to fetch distinct Journal names present in the Database
	 * @return
	 * @exception SQLException
	 */
	public List<String> fetchJournalNames() {
		List<String> journalList = new ArrayList<>();
		try {
			Statement st = getConn().createStatement();
			StringBuilder query = new StringBuilder();
			query.append("Select distinct journal from tb_publication order by journal");

			ResultSet rs = st.executeQuery(query.toString());
			while (rs.next()) {
				journalList.add(rs.getString("journal"));
			}

			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + " querying journal list.");
			System.err.println(se.getMessage());
		}
		return journalList;
	}

	/**
	 * Query 6 : Method to fetch distinct Journal names present in the Database
	 * @return
	 * @exception SQLException
	 */
	public List<Integer> fetchYearsAvailable() {
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
			System.err.println(SQLEXCEPTION + " querying publication year list.");
			System.err.println(se.getMessage());
		}
		return yearList;
	}

	/*
	 * Queries 7 - 10 Four Methods to carry out CRUD operations on the Favorite
	 * candidates
	 * 
	 */
	
	/**
	 * @param i
	 * @return
	 * @exception SQLException
	 */
	public Author fetchCandidates(int i) {
		List<Author> listofCandidate = new ArrayList<Author>();
		try {
			String sql = "select * from tb_candidate";
			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				listofCandidate.add(new Author(rs.getString(1)));
			}

			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Failed");
			e.printStackTrace();
		}
		return listofCandidate.get(i);
	}

	/**
	 * @param author
	 * @exception SQLException
	 */
	public void addAuthorIntoCandidate(Author author) {
		System.out.println(".. .. .. " + author.getName());
		try {
			String sql = "insert into tb_candidate values(?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, author.getName());

			ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			System.out.println("Failed!");
			e.printStackTrace();
		}
	}

	/**
	 * @param author
	 * @exception SQLException
	 */
	public void deleteFavCandidate(Author author) {
		try {
			PreparedStatement pstmt = getConn().prepareStatement("DELETE from tb_candidate where authorname=?");
			pstmt.setString(1, author.getName());

			pstmt.executeUpdate();

			pstmt.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + " deleting fav candidate.");
			System.err.println(se.getMessage());
		}
	}

	/**
	 * @return
	 * @exception SQLException
	 */
	public int countFavCandidates() {
		int numberOfRows = 0;
		try {
			PreparedStatement pstmt = getConn().prepareStatement("SELECT COUNT(*) from tb_candidate");

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				numberOfRows = rs.getInt(1);
//				System.out.println("numberOfRows= " + numberOfRows);
			} else {
				System.err.println("error: could not get the record counts");
			}

			pstmt.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + " counting fav candidate.");
			System.err.println(se.getMessage());
		}
		return numberOfRows;
	}

	/*
	 * Queries 11 - 14 Four Methods to carry out CRUD operations on the Saved
	 * Queries
	 * 
	 */
	
	/**
	 * Query 11 : to read saved queries
	 * @return list of string of saved queries
	 * @exception SQLException
	 */
	public List<String> fetchSavedQueries() {
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
			System.err.println(SQLEXCEPTION + " querying saved queries list.");
			System.err.println(se.getMessage());
		}
		return queryList;
	}

	/**
	 * @param saveQuery
	 * @return
	 * @exception SQLException
	 */
	public int addSavedQuery(String saveQuery) {
		int affectedRows = 0;
		try {
			Statement st = getConn().createStatement();
			StringBuilder query = new StringBuilder();
			query.append("INSERT INTO tb_savedqueries(query) VALUES (" + saveQuery + ")");

			affectedRows = st.executeUpdate(query.toString());

			st.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + " adding saved queries.");
			System.err.println(se.getMessage());
		}
		return affectedRows;
	}

	/**
	 * @param saveQuery
	 * @return
	 * @exception SQLException
	 */
	public int deleteSavedQuery(String saveQuery) {
		int affectedRows = 0;
		try {
			PreparedStatement pstmt = getConn().prepareStatement("DELETE from tb_savedqueries where query=?");
			pstmt.setString(1, saveQuery);

			affectedRows = pstmt.executeUpdate();

			pstmt.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + " deleting saved queries.");
			System.err.println(se.getMessage());
		}
		return affectedRows;
	}

	/**
	 * @return
	 * @exception SQLException
	 */
	public int countSavedQueries() {
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
			System.err.println(SQLEXCEPTION + " counting saved queries.");
			System.err.println(se.getMessage());
		}
		return numberOfRows;
	}

	/**
	 * Query 15 : to find a particular author in Favorite Author List
	 * @param author
	 * @return
	 * @exception SQLException
	 */
	public boolean isFavCandidate(Author author) {
		Author resultAuthor = new Author();
		boolean isPresent = false;
		try {
			Statement st = getConn().createStatement();
			StringBuilder query = new StringBuilder();
			query.append("Select distinct authorname from tb_candidate " + "where lower(authorname) = lower('"
					+ author.getName() + "') ");

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
			System.err.println(SQLEXCEPTION + " checking is a fav candidate.");
			se.printStackTrace();
			System.err.println(se.getMessage());
		}
		// return resultAuthor;
		return isPresent;
	}

	/**
	 * static connection getter
	 * @return
	 */
	public static Connection getConn() {
		return conn;
	}

	/**
	 * static connection setter
	 * @param conn
	 */
	public static void setConn(Connection conn) {
		QueryEngine.conn = conn;
	}

	/**
	 * Query 16 : to fetch distinct committee name list
	 * @return
	 * @exception SQLException
	 */
	public List<String> fetchCommitteeNameList() {
		List<String> committeeList = new ArrayList<>();
		try {
			Statement st = getConn().createStatement();
			StringBuilder query = new StringBuilder();
			query.append("Select distinct committee from tb_committeecheck");

			ResultSet rs = st.executeQuery(query.toString());
			while (rs.next()) {
				committeeList.add(rs.getString("committee"));
			}

			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + " querying committee name list.");
			System.err.println(se.getMessage());
		}
		return committeeList;
	}

	/**
	 * Query 17 : to fetch author list based on the committee and no of years
	 * served as a member
	 * @param committeeName
	 * @param noOfYears
	 * @return
	 * @exception SQLException
	 */
	public List<Author> fetchAuthorList(String committeeName, int noOfYears) {
		List<Author> authorList = new ArrayList<>();
		try {
			Statement st = getConn().createStatement();
			StringBuilder query = new StringBuilder();
			query.append("Select university from tb_university " + "where lower(authorname) = lower('" + committeeName
					+ "') and  = " + noOfYears);

			ResultSet rs = st.executeQuery(query.toString());
			while (rs.next()) {
				// authorList = rs.getString("committee");
			}

			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + " querying author university detail.");
			System.err.println(se.getMessage());
		}
		return authorList;
	}

	/**
	 * Query 18 : to fetch no of publication year list based on author name
	 * @param author
	 * @return HashMap<Year, No of publications>
	 * @exception SQLException
	 */
	public Map<Integer, Integer> fetchNoOfPublicationPerYear(Author author) {
		Map<Integer, Integer> listPerYear = new HashMap<>();
		try {
			Statement st = getConn().createStatement();
			StringBuilder query = new StringBuilder();
			query.append("Select distinct p.pbyear, count(*) from tb_publication p, tb_authorprofile a "
					+ "where lower(a.authorname) = lower('" + author.getName()
					+ "') and a.title = p.title group by p.pbyear");

			ResultSet rs = st.executeQuery(query.toString());
			System.out.println("### " + rs.getFetchSize());
			while (rs.next()) {
				listPerYear.put(rs.getInt(0), rs.getInt(1));
				System.out.println(".... " + rs.getInt(0) + " .... " + rs.getInt(1));
			}

			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + " querying NoOfPublication Per Year.");
			System.err.println(se.getMessage());
		}
		return listPerYear;
	}

	/**
	 * Query 19 : to fetch university based on author name
	 * @param author
	 * @return
	 * @exception SQLException
	 */
	public String getAuthorUnivDetails(Author author) {
		String committee = "";
		try {
			Statement st = getConn().createStatement();
			StringBuilder query = new StringBuilder();
			query.append("Select university from tb_university " + "where lower(authorname) = lower('"
					+ author.getName() + "') ");

			System.out.println(query + " .....  ....");
			ResultSet rs = st.executeQuery(query.toString());
			while (rs.next()) {
				committee = rs.getString("committee");
			}

			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + " querying author university detail.");
			System.err.println(se.getMessage());
		}
		return committee;
	}

	/**
	 * Query 20 : to fetch author list based on the committee and no of years
	 * served as a member Return type: List<list, list>
	 * 
	 * @exception SQLException
	 */
	public void convertXYToList() {

	}

	/**
	 * Query 21 Candidate Details include candidate names, their role in
	 * committee(maybe more than one roles) and their number of publication.
	 * Candidate Review page mainly aim at comparing different between authors
	 * basic contribute and performance.
	 * @return List of Authors with name, committee role list, and numberofPb
	 *         setted.
	 */
	public List<Author> fetchCandidateDetails() {
		List<Author> candidatesListWithDetails = new ArrayList<>();
		try {
			Statement st = getConn().createStatement();
			StringBuilder query = new StringBuilder();
			query.append("Select distinct authorname From tb_candidate");
			ResultSet rs = st.executeQuery(query.toString());
			while (rs.next()) {
				Author CandidateAuthor = new Author();
				String authorName = rs.getString("authorname");
				CandidateAuthor.setName(authorName);
				String rolestring = getCommitteeRoleByAuthorName(authorName);
				CandidateAuthor.setRole(rolestring);
				int numberofpb = getNumberofPBByAuthorName(CandidateAuthor);
				CandidateAuthor.setNoOfPublication(Integer.toString(numberofpb));
				candidatesListWithDetails.add(CandidateAuthor);
			}
			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + " querying fav candidate list.");
			System.err.println(se.getMessage());
		}
		return candidatesListWithDetails;
	}

	/**
	 * Query 22 This is a help method for fetchCandidateDetails method. Get
	 * their role of every committee they ever attened by their name.
	 * 
	 * @param authorname
	 * @return a string, the role of an author. The role may more than one.
	 */
	private static String getCommitteeRoleByAuthorName(String authorname) {
		String roleString = "";
		try {
			String sql = "select role from tb_committeecheck where authorname =?";
			PreparedStatement ps = getConn().prepareStatement(sql);
			ps.setString(1, authorname);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				roleString = roleString.concat(rs.getString(1)+", ");
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(SQLEXCEPTION);
			e.printStackTrace();
		}
		return roleString;
	}

	/**
	 * Query 23 This method is get an author's all committee information such as
	 * his name, in which year was checked, and what committee name, and what is
	 * his role. One author may not just only checked one time. Some author was
	 * checked more than one time in different years. So the input is an author
	 * name, but return type is a list.
	 * 
	 * @param author
	 * @return a list of authors with their committee information.
	 */
	public List<Author> getAuthorCommitteeDetailsByAuthorName(Author author) {
		List<Author> authorsCommittee = new ArrayList<>();
		String authorname = author.getName();
		try {
			Statement st = getConn().createStatement();
			String sql = "Select * From tb_candidate where authorname =?";
			PreparedStatement ps = getConn().prepareStatement(sql);
			ps.setString(1, authorname);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Author CommitteeAuthor = new Author();
				CommitteeAuthor.setName(authorname);
				CommitteeAuthor.setCheckYear(rs.getInt("checkyear"));
				CommitteeAuthor.setRole(rs.getString("role"));
				CommitteeAuthor.setCommittee(rs.getString("committee"));
				authorsCommittee.add(CommitteeAuthor);
			}
			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + " querying fav candidate list.");
			System.err.println(se.getMessage());
		}
		return authorsCommittee;
	}
}
