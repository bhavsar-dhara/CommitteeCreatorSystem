package main.classes;

import java.io.Serializable;

public class Author implements Serializable {

	/**
	 * Default serial id
	 */
	private static final long serialVersionUID = 1L;

	public Author() { }
	
//	Constructor of Second part of UI
	public Author(String name,int id) {
		this.id =(long) id;
		this.name = name;
	}
	
//	Constructor for QueryEngine Tests
	public Author(String name) {
		this.name = name;
	}

//	Constructor of First part of UI
	public Author(String title, String name, String noOfPublication) {
		this.name = name;
		this.title = title;
		this.noOfPublication = noOfPublication;
	}
	
//	SearchQuery constructor
	public Author(String title, String name, String noOfPublication, Publication publication) {
		this.name = name;
		this.title = title;
		this.noOfPublication = noOfPublication;
		this.publication = publication;
	}

	private Long id;
	private String title;
	private String name;
	private String noOfPublication;
	private int checkYear;
	private String role;
	private boolean isCommittee;
	private Publication publication;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// getName: -> String
	// RETURNS: the name of this author
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNoOfPublication() {
		return noOfPublication;
	}

	public void setNoOfPublication(String noOfPublication) {
		this.noOfPublication = noOfPublication;
	}

	public int getCheckYear() {
		return checkYear;
	}

	public void setCheckYear(int checkYear) {
		this.checkYear = checkYear;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isCommittee() {
		return isCommittee;
	}

	public void setCommittee(boolean isCommittee) {
		this.isCommittee = isCommittee;
	}

	public Publication getPublication() {
		return publication;
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}

	@Override
	public String toString() {
		StringBuilder author = new StringBuilder();
		if(id != null)
			author.append("id = " + id.toString() + " \n ");
		if(title != null)
			author.append("title = " + title + " \n ");
		if(name != null)
			author.append("name = " + name + " \n ");
		if(noOfPublication != null)
			author.append("noOfPublication = " + noOfPublication + " \n ");
//		if(checkYear > 0)
//			author.append("checkYear = " + checkYear + " \n ");
		if(role != null)
			author.append("role = " + role + " \n ");
//		if(isCommittee == null)
//			author.append("isCommittee = " + isCommittee + " \n ");
		if(publication != null) 
			author.append("publication = " + publication.toString());
		return author.toString();
	}
}
