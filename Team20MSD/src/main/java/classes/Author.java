package main.java.classes;

import java.util.ArrayList;

public class Author {
	private String name;
	private ArrayList<Publication> blgphy; 
	
	// Constructor
	public Author(String n, ArrayList<Publication> b){
		name = n;
		blgphy = b;
	}
	
	// titleAsc: -> void
	// EFFECT: Sort this author's publications by title in ascending order
	public void titleAsc(){
		
	}
	
	public void titleDesc(){
		
	}

	public void yearAsc(){
		
	}

	public void yearDesc(){
		
	}

	public void publsAsc(){
		
	}

	public void publsDesc(){
		
	}
	
	// getName: -> String
	// RETURNS: the name of this author
	public String getName(){
		return name;
	}
}
