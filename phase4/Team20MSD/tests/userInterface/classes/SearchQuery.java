package userInterface.classes;

public class SearchQuery {
	public SearchQuery(String query){
		content = query;
	}
	
	public String toString(){
		return content;
	}
	
	private String content;
}
