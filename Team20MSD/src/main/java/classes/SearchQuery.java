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

	public static void main(String[] args) {
		List<Author> listOfAuthors = new ArrayList<Author>();
		Statement st = null;
		try {
			conn = connectToDatabaseOrDisconnect();
			st = conn.createStatement();

			String query = "SELECT distinct tp.*, tn.* "
					+ "FROM tb_publication tp, tb_authorprofile ta, tb_numberofpb tn " 
					+ "where tp.title = ta.title "
					+ "and ta.authorname = tn.authorname " 
					+ "and lower(tp.journal) = 'acta inf.' "
					+ "and lower(tp.title) like '%parallel%' " 
					+ "and tp.pbyear = 1996 " 
					+ "and tn.numberofpb = 2";

			ResultSet rs = null;

			rs = st.executeQuery(query.toString());

			while (rs.next()) {
				Author author = new Author();
				author.setName(rs.getString("authorname"));
				author.setTitle(rs.getString("title"));
				System.out.println(author.toString());
				listOfAuthors.add(author);
			}

			rs.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println(e.getMessage());
		}

	}

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
			String url = "jdbc:postgresql://localhost:5432/msddblp";
			conn = DriverManager.getConnection(url, "postgres", "1991715");
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

	// TODO: Query 1 Search for author
	// input: 1. from a particular conference/journal
	// 2. keywords present in the title of the publication
	// 3. selecting publication year(s)
	// 4. minimum # of publications made
	// 5. has been a previous committee member or not
	// 6. saving the search criteria??
	// output: list of authors

	private List<Author> populateListOfAuthors(Connection conn, String confJournal, String keywords, int[] years,
			int noOfPublication, int prevMembrYears) {
		List<Author> listOfAuthors = new ArrayList<Author>();
		try {
			Statement st = conn.createStatement();
			StringBuilder query = new StringBuilder();
			query.append("SELECT distinct * FROM tb_publication tp, tb_authorprofile ta, tb_numberofpb tn ");
			query.append("where tp.title = ta.title and ta.authorname = tn.authorname ");
			if (confJournal != null && confJournal.equals("") && keywords != null && keywords.equals("")
					&& years != null && noOfPublication < 0) {
				query.append(allQueryParameters(confJournal, keywords, years, noOfPublication));
			}
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
				author.setPublication(publication);
				listOfAuthors.add(author);
			}
			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println(SQLEXCEPTION + "querying author details.");
			System.err.println(se.getMessage());
		}
		return listOfAuthors;
	}

	private String allQueryParameters(String confJournal, String keywords, int[] years, int noOfPublication) {
		StringBuilder query = new StringBuilder();
		query.append("and lower(tp.journal) = '" + confJournal + "' ");
		query.append("and lower(tp.title) like '%" + keywords + "%' ");
		// TODO : handle multiple years
		query.append("and tp.pbyear = " + years + " ");
		query.append("and tp.numberofpb = " + noOfPublication + " ");
		return query.toString();
	}

	// TODO: Query 2 Search for similar authors
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

	static List<String> getSimilarAuthorBySameNumberofPB(String authorName) {
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

	static List<String> getSimilarAuthorBySamePublication(String authorName) {
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
}
