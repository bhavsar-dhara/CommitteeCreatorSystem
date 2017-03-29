package main.java.classes;

import java.sql.*;
import java.util.*;

public class SearchQuery {

	Connection conn;

	public SearchQuery() {
		// connect to POSTGRESQL Database
		conn = connectToDatabaseOrDisconnect();
	}
	
// TODO: make a singleton connection class
	private Connection connectToDatabaseOrDisconnect() {
		Connection conn = null;
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://THE_HOST/THE_DATABASE";
			conn = DriverManager.getConnection(url, "THE_USER", "THE_PASSWORD");
		} catch (ClassNotFoundException e) {
			System.err.println("Threw a SQLException while connecting to the database.");
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (SQLException e) {
			System.err.println("Threw a SQLException while connecting to the database.");
			System.err.println(e.getMessage());
			System.exit(2);
		}
		return conn;
	}
	
	// TODO: Query 1 Search for author
	// input: 	1. from a particular conference/journal
	//			2. keywords present in the title of the publication
	//			3. selecting publication year(s)
	//			4. minimum # of publications made
	//			5. has been a previous committee member or not
	//			6. saving the search criteria??
	// output: list of authors

	private List<Author> populateListOfAuthors(Connection conn, boolean isJournal, String[] keywords, 
			int[] years, int noOfPublication, boolean isPrevMembr) {
		List<Author> listOfAuthors = new ArrayList<Author>();
		try {
			Statement st = conn.createStatement();
			StringBuilder query = new StringBuilder();
			query.append("SELECT * FROM tb_publication tp, tb_authorprofile ta");
			if(keywords != null && years != null && noOfPublication != 0) {
				query.append("where ");
				query.append("tp.journal = '" + isJournal + "'");
				query.append("tp.title like '%" + keywords + "%'");
				query.append("tp.years like '" + years + "'");
				query.append("tp.noOfPublication = '" + noOfPublication + "'");
				query.append("tp.isPrevMembr = '" + isPrevMembr + "'");
			}
			ResultSet rs = st.executeQuery(query.toString());
			while (rs.next()) {
				Author author = new Author();
				author.setId(rs.getLong("id"));
				author.setName(rs.getString("authorname"));
				author.setTitle(rs.getString("title"));
				listOfAuthors.add(author);
			}
			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println("Threw a SQLException creating the list of blogs.");
			System.err.println(se.getMessage());
		}
		return listOfAuthors;
	}
	
	// TODO: Query 2 Search for similar authors
	// result based on:	1. were members of same committee
	//					2. have papers published in similar conference or journal
	// output: list of authors
	
}
