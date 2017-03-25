package main.java.classes;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Entity;

@Entity
public class Author implements Serializable {

	public Author() {

	}

	// Constructor
	public Author(String n, ArrayList<Publication> b) {
		name = n;
		blgphy = b;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private ArrayList<Publication> blgphy;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArrayList<Publication> getBlgphy() {
		return blgphy;
	}

	public void setBlgphy(ArrayList<Publication> blgphy) {
		this.blgphy = blgphy;
	}

	// getName: -> String
	// RETURNS: the name of this author
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// titleAsc: -> void
	// EFFECT: Sort this author's publications by title in ascending order
	public void titleAsc() {

	}

	public void titleDesc() {

	}

	public void yearAsc() {

	}

	public void yearDesc() {

	}

	public void publsAsc() {

	}

	public void publsDesc() {

	}
}
