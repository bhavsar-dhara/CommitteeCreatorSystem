package main.java.classes;

import java.sql.*;
import java.util.*;

import main.java.interfaces.QueryEngine;

public class SearchQuery implements QueryEngine {

	static Connection conn;
	
	public static void main(String[] args) {
		List<Author> listOfAuthors = new ArrayList<Author>();
		Statement st = null;
		try {
			conn = connectToDatabaseOrDisconnect(); 
			st = conn.createStatement();
		
			String query = "SELECT distinct tp.*, tn.* " +
				"FROM tb_publication tp, tb_authorprofile ta, tb_numberofpb tn " + 
				"where tp.title = ta.title " +
				"and ta.authorname = tn.authorname " +
				"and lower(tp.journal) = 'acta inf.' " +
				"and lower(tp.title) like '%parallel%' " + 
				"and tp.pbyear = 1996 " +
				"and tn.numberofpb = 2";
		
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
	
// TODO: make a singleton connection class
	private static Connection connectToDatabaseOrDisconnect() {
		Connection conn = null;
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/msddblp";
			conn = DriverManager.getConnection(url, "postgres", "1991715");
			System.out.println("Connection made successfully...");
		} catch (ClassNotFoundException e) {
			System.err.println("Threw a ClassNotFoundException while connecting to the database.");
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

	private List<Author> populateListOfAuthors(Connection conn, String confJournal, String keywords, 
			int[] years, int noOfPublication, int prevMembrYears) {
		List<Author> listOfAuthors = new ArrayList<Author>();
		try {
			Statement st = conn.createStatement();
			StringBuilder query = new StringBuilder();
			query.append("SELECT distinct * FROM tb_publication tp, tb_authorprofile ta, tb_numberofpb tn");
			query.append("where tp.title = ta.title and ta.authorname = tn.authorname");
			if(confJournal != null && confJournal.equals("") &&
					keywords != null && keywords.equals("") &&
					years != null && noOfPublication < 0) {
				query.append(allQueryParameters(confJournal, keywords, years, noOfPublication));
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
	
	private String allQueryParameters(String confJournal, String keywords, 
			int[] years, int noOfPublication) {
		StringBuilder query = new StringBuilder();
		query.append("where ");
		query.append("tp.journal = '" + confJournal + "'");
		query.append(" and tp.title like '%" + keywords + "%'");
		// TODO : handle multiple years 
		query.append(" and tp.pbyear = '" + years + "'");
		query.append(" and tp.numberofpb = '" + noOfPublication + "'");
		return query.toString();
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
	
	// TODO: Query 2 Search for similar authors
	// result based on:	1. were members of same committee
	//					2. have papers published in similar conference or journal
	// output: list of authors
	
}
