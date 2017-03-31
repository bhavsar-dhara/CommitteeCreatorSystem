package main.java.classes;

import java.sql.*;
import java.util.*;

import main.java.interfaces.QueryEngine;

public class SearchQuery implements QueryEngine {

	static Connection conn;

	static String SQLEXCEPTION = "Threw a SQLException while ";
	static String CLASSNOTFOUNDEXECPTION = "Threw a ClassNotFoundException while connecting to the database.";

	static String GET_NOPB_BY_AUTHORNAME = "select distinct numberOfPb from tb_NumberOfPb where authorname=?";
	static String GET_AUTHORNAME_BY_NOPB = "select distinct authorname from tb_NumberOfPb where numberOfPb=?";
	static String GET_TITLE_BY_AUTHORNAME = "select distinct title from tb_authorProfile where authorname=?";
	static String GET_AUTHORNAME_BY_TITLE = "select distinct authorname from tb_authorProfile where title=?";

//	Main method to test database queries from code
//	public static void main(String[] args) {
//		List<Author> listOfAuthors = new ArrayList<Author>();
//		
////		Statement st = null;
////		try {
//			conn = connectToDatabaseOrDisconnect();
////			st = conn.createStatement();
//			
//			int[] intArray = new int[1];
//			intArray[0] = 1996;
//			populateListOfAuthors("acta inf.", "parallel", intArray, 2);
//			
////			fetchAuthorDetails("sanjeev");
//			
//			// QUERY for getting author list
////			String query = "SELECT distinct tp.*, tn.* "
////					+ "FROM tb_publication tp, tb_authorprofile ta, tb_numberofpb tn " + "where tp.title = ta.title "
////					+ "and ta.authorname = tn.authorname " + "and lower(tp.journal) = 'acta inf.' "
////					+ "and lower(tp.title) like '%parallel%' " + "and tp.pbyear = 1996 " + "and tn.numberofpb = 2";
////
////			System.out.println(query.toString());
//			
//			// QUERY for getting author details
////			String query2 = "SELECT distinct tp.*, tn.* "
////					+ " FROM tb_publication tp, tb_authorprofile ta, tb_numberofpb tn " + " where tp.title = ta.title "
////					+ " and ta.authorname = tn.authorname" + " and lower(ta.authorname) like '%sanjeev%'";
////			
////			System.out.println(query2.toString());
//
////			ResultSet rs = null;
//
////			rs = st.executeQuery(query.toString());
////			rs = st.executeQuery(query2.toString());
//
//			
////			while (rs.next()) {
////				Author author = new Author();
////				author.setName(rs.getString("authorname"));
////				author.setTitle(rs.getString("title"));
////				System.out.println(author.toString());
////				listOfAuthors.add(author);
////			}
//
////			rs.close();
////			st.close();
////		} catch (SQLException e) {
////			e.printStackTrace();
////			System.err.println(e.getMessage());
////		}
//
//	}

	public SearchQuery() {
		// connect to POSTGRESQL Database
		conn = connectToDatabaseOrDisconnect();
	}

	@Override
	public ArrayList<Author> answerQuery(SearchQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sortSearchResult(SortCriterion c) {
		// TODO Auto-generated method stub

	}

	// a singleton jdbc connection class
	private static Connection connectToDatabaseOrDisconnect() {
		Connection conn = null;
		try {
			Class.forName("org.postgresql.Driver");
//			Connection to local database
			String url = "jdbc:postgresql://localhost:5432/msddblp";
			conn = DriverManager.getConnection(url, "postgres", "1991715");
//			Connection to AWS PostgreSQL Database
//			ERROR ::: on trying to connect to remote
//			Connection refused. Check that the hostname and port are correct and that the postmaster is accepting TCP/IP connections.
//			String url = "jdbc:postgresql://mypostgresqlaws.cxeexamnifqk.us-west-2.rds.amazonaws.com:5432/msddblp";
//			url = "jdbc:postgresql://mypostgresqlaws.cxeexamnifqk.us-west-2.rds.amazonaws.com:5432/msddblp";
//			conn = DriverManager.getConnection(url, "luliuAWS", "1991715ll");
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
		return conn;
	}

	// Query 1 Search for author list
	// input: 1. from a particular conference/journal
	// 2. keywords present in the title of the publication
	// 3. selecting publication year(s)
	// 4. minimum # of publications made
	// 5. has been a previous committee member or not
	// 6. saving the search criteria??
	// output: list of authors

	//TODO : multiple conferences selected?
	public static List<Author> populateListOfAuthors(String confJournal, String keywords, int[] years,
			int noOfPublication) {
		List<Author> listOfAuthors = new ArrayList<Author>();
		try {
			Statement st = conn.createStatement();
			StringBuilder query = new StringBuilder();
			query.append("SELECT distinct * FROM tb_publication tp, tb_authorprofile ta, tb_numberofpb tn ");
			query.append("where tp.title = ta.title and ta.authorname = tn.authorname ");

			if (confJournal != null && !confJournal.equals("") && keywords != null && !keywords.equals("")
					&& years != null && noOfPublication > 0) {
				query.append(allQueryParameters(confJournal, keywords, years, noOfPublication));
			} else if (confJournal != null && !confJournal.equals("") && keywords != null && !keywords.equals("")
					&& years != null) {
				query.append(queryParameters3a(confJournal, keywords, years));
			} else if (confJournal != null && !confJournal.equals("") && keywords != null && !keywords.equals("")
					&& noOfPublication < 0) {
				query.append(queryParameters3b(confJournal, keywords, noOfPublication));
			} else if (confJournal != null && !confJournal.equals("") && years != null && noOfPublication < 0) {
				query.append(queryParameters3c(confJournal, years, noOfPublication));
			} else if (keywords != null && !keywords.equals("") && years != null && noOfPublication < 0) {
				query.append(queryParameters3d(keywords, years, noOfPublication));
			} else if (confJournal != null && !confJournal.equals("") && keywords != null && !keywords.equals("")) {
				query.append(queryParameters2a(confJournal, keywords));
			} else if (keywords != null && !keywords.equals("") && years != null) {
				query.append(queryParameters2b(keywords, years));
			} else if (years != null && noOfPublication < 0) {
				query.append(queryParameters2c(years, noOfPublication));
			} else if (confJournal != null && !confJournal.equals("") && noOfPublication < 0) {
				query.append(queryParameters2d(confJournal, noOfPublication));
			} else if (confJournal != null && !confJournal.equals("")) {
				query.append(queryParameters1a(confJournal));
			} else if (keywords != null && !keywords.equals("")) {
				query.append(queryParameters1b(keywords));
			} else if (years != null) {
				query.append(queryParameters1c(years));
			} else if (noOfPublication < 0) {
				query.append(queryParameters1d(noOfPublication));
			}

			ResultSet rs = st.executeQuery(query.toString());
			// System.out.println(rs.getFetchSize() + "......");
			while (rs.next()) {
				Author author = new Author();
				author.setName(rs.getString("authorname"));
				author.setTitle(rs.getString("title"));
				author.setNoOfPublication(rs.getString("numberofpb"));
				Publication publication = new Publication();
				publication.setTitle(rs.getString("title"));
				publication.setPbyear(rs.getInt("pbYear"));
				publication.setPages(rs.getString("pages"));
				publication.setJournal(rs.getString("journal"));
				publication.setVolume(rs.getString("volume"));
				publication.setUrl(rs.getString("url"));
				publication.setEe(rs.getString("ee"));
				publication.setNumber(rs.getString("number"));
				author.setPublication(publication);
				System.out.println(author.toString());
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

	private static String allQueryParameters(String confJournal, String keywords, int[] years, int noOfPublication) {
		StringBuilder query = new StringBuilder();
		query.append("and lower(tp.journal) = '" + confJournal + "' ");
		query.append("and lower(tp.title) like '%" + keywords + "%' ");
		for(int year : years) {
			query.append("and tp.pbyear = " + year + " ");
		}
		query.append("and tn.numberofpb = " + noOfPublication + " ");
		return query.toString();
	}

	private static String queryParameters3a(String confJournal, String keywords, int[] years) {
		StringBuilder query = new StringBuilder();
		query.append("and lower(tp.journal) = '" + confJournal + "' ");
		query.append("and lower(tp.title) like '%" + keywords + "%' ");
		for(int year : years) {
			query.append("and tp.pbyear = " + year + " ");
		}
		return query.toString();
	}

	private static String queryParameters3b(String confJournal, String keywords, int noOfPublication) {
		StringBuilder query = new StringBuilder();
		query.append("and lower(tp.journal) = '" + confJournal + "' ");
		query.append("and lower(tp.title) like '%" + keywords + "%' ");
		query.append("and tn.numberofpb = " + noOfPublication + " ");
		return query.toString();
	}

	private static String queryParameters3c(String confJournal, int[] years, int noOfPublication) {
		StringBuilder query = new StringBuilder();
		query.append("and lower(tp.journal) = '" + confJournal + "' ");
		for(int year : years) {
			query.append("and tp.pbyear = " + year + " ");
		}
		query.append("and tn.numberofpb = " + noOfPublication + " ");
		return query.toString();
	}

	private static String queryParameters3d(String keywords, int[] years, int noOfPublication) {
		StringBuilder query = new StringBuilder();
		query.append("and lower(tp.title) like '%" + keywords + "%' ");
		for(int year : years) {
			query.append("and tp.pbyear = " + year + " ");
		}
		query.append("and tn.numberofpb = " + noOfPublication + " ");
		return query.toString();
	}

	private static String queryParameters2a(String confJournal, String keywords) {
		StringBuilder query = new StringBuilder();
		query.append("and lower(tp.journal) = '" + confJournal + "' ");
		query.append("and lower(tp.title) like '%" + keywords + "%' ");
		return query.toString();
	}

	private static String queryParameters2b(String keywords, int[] years) {
		StringBuilder query = new StringBuilder();
		query.append("and lower(tp.title) like '%" + keywords + "%' ");
		for(int year : years) {
			query.append("and tp.pbyear = " + year + " ");
		}
		return query.toString();
	}

	private static String queryParameters2c(int[] years, int noOfPublication) {
		StringBuilder query = new StringBuilder();
		for(int year : years) {
			query.append("and tp.pbyear = " + year + " ");
		}
		query.append("and tn.numberofpb = " + noOfPublication + " ");
		return query.toString();
	}

	private static String queryParameters2d(String confJournal, int noOfPublication) {
		StringBuilder query = new StringBuilder();
		query.append("and lower(tp.journal) = '" + confJournal + "' ");
		query.append("and tn.numberofpb = " + noOfPublication + " ");
		return query.toString();
	}

	private static String queryParameters1a(String confJournal) {
		StringBuilder query = new StringBuilder();
		query.append("and lower(tp.journal) = '" + confJournal + "' ");
		return query.toString();
	}

	private static String queryParameters1b(String keywords) {
		StringBuilder query = new StringBuilder();
		query.append("and lower(tp.title) like '%" + keywords + "%' ");
		return query.toString();
	}

	private static String queryParameters1c(int[] years) {
		StringBuilder query = new StringBuilder();
		for(int year : years) {
			query.append("and tp.pbyear = " + year + " ");
		}
		return query.toString();
	}

	private static String queryParameters1d(int noOfPublication) {
		StringBuilder query = new StringBuilder();
		query.append("and tn.numberofpb = " + noOfPublication + " ");
		return query.toString();
	}

	// Query 2 Search for similar authors
	// result based on: 1. were members of same committee
	// 2. have papers published in similar conference or journal
	// output: list of authors

	static int getNumberofPBByAuthorName(String authorName) {
		try {
			PreparedStatement ps = conn.prepareStatement(GET_NOPB_BY_AUTHORNAME);
			ps.setString(1, authorName);
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

	public static List<String> getSimilarAuthorBySameNumberofPB(String authorName) {
		int inputAuthorNumberofPB = getNumberofPBByAuthorName(authorName);
		try {
			PreparedStatement ps = conn.prepareStatement(GET_AUTHORNAME_BY_NOPB);
			ps.setInt(1, inputAuthorNumberofPB);
			ResultSet rs = ps.executeQuery();
			List<String> authorsBySameNOPB = new ArrayList<String>();
			while (rs.next()) {
				authorsBySameNOPB.add(rs.getString(1));
			}
			return authorsBySameNOPB;
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + "querying similar author by same number of publications.");
			System.err.println(se.getMessage());
		}
		return null;
	}

	static List<String> getPublicationByAuthorName(String authorName) {
		try {
			PreparedStatement ps = conn.prepareStatement(GET_TITLE_BY_AUTHORNAME);
			ps.setString(1, authorName);
			ResultSet rs = ps.executeQuery();
			List<String> listofpublicationbyauthorname = new ArrayList<String>();
			while (rs.next()) {
				String publication = rs.getString(1);
				listofpublicationbyauthorname.add(publication);
			}
			return listofpublicationbyauthorname;
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + "querying publications based on authors.");
			System.err.println(se.getMessage());
		}
		return null;
	}

	public static List<String> getSimilarAuthorBySamePublication(String authorName) {
		List<String> listofpublication = getPublicationByAuthorName(authorName);
		List<String> listofAuthorBySamePB = new ArrayList<String>();
		for (int i = 0; i < listofpublication.size() - 1; i++) {
			String titleName = listofpublication.get(i);
			try {
				PreparedStatement ps = conn.prepareStatement(GET_AUTHORNAME_BY_TITLE);
				ps.setString(1, titleName);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					String authornameBySamePB = rs.getString(1);
					listofAuthorBySamePB.add(authornameBySamePB);
				}
			} catch (SQLException se) {
				System.err.println(SQLEXCEPTION + "querying similar author who have co-authored a paper.");
				System.err.println(se.getMessage());
			}
		}
		return listofAuthorBySamePB;
	}

	// Query 3 Search authors details
	// input: author name
	// output: list of author's publication list

	public static List<Author> fetchAuthorDetails(String authorName) {
		List<Author> authorList = new ArrayList<>();
		try {
			Statement st = conn.createStatement();
			StringBuilder query = new StringBuilder();
			query.append("SELECT distinct * FROM tb_publication tp, tb_authorprofile ta, tb_numberofpb tn ");
			query.append("where tp.title = ta.title and ta.authorname = tn.authorname ");
			query.append("and lower(ta.authorname) like '%" + authorName + "%'");

			ResultSet rs = st.executeQuery(query.toString());
			while (rs.next()) {
				Author author = new Author();
				author.setName(rs.getString("authorname"));
				author.setTitle(rs.getString("title"));
				author.setNoOfPublication(rs.getString("numberofpb"));
				Publication publication = new Publication();
				publication.setTitle(rs.getString("title"));
				publication.setPbyear(rs.getInt("pbYear"));
				publication.setPages(rs.getString("pages"));
				publication.setJournal(rs.getString("journal"));
				publication.setVolume(rs.getString("volume"));
				publication.setUrl(rs.getString("url"));
				publication.setEe(rs.getString("ee"));
				publication.setNumber(rs.getString("number"));
				author.setPublication(publication);
				System.out.println(author.toString());
				authorList.add(author);
			}

			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + "querying author details.");
			System.err.println(se.getMessage());
		}
		return authorList;
	}

}
