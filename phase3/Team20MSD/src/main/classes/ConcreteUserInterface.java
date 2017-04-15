package main.classes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.interfaces.UserInterface;
import userInterface.interfaces.CandidateListListener;

public class ConcreteUserInterface implements UserInterface {
	
	public ConcreteUserInterface(QueryEngine qe){
		this.qe = qe;
	}
	
	public void displayNewWindow(Scene s){
		Stage stage = new Stage();
		stage.setScene(s);
		stage.show();
	}
	
	public void displayNewWindow(Scene s,String title){
		Stage stage = new Stage();
		stage.setScene(s);
		stage.setTitle(title);
		stage.show();
	}
	
	// operations on candidate list
	public void addCand(Author a){
		qe.addAuthorIntoCandidate(a);
		CandListListeners.forEach((CandidateListListener l) -> {
			l.refresh();
		});
	}
	public boolean hasCand(Author a){
		return qe.isFavCandidate(a);
	}
	
	public void remCand(Author a){
		qe.deleteFavCandidate(a);
		CandListListeners.forEach((CandidateListListener l) -> {
			l.refresh();
		});
	}
	
	public void remCand(List<Author> cands){
		for (int i=0;i<cands.size();i++){
			qe.deleteFavCandidate(cands.get(i));
		}
		CandListListeners.forEach((CandidateListListener l) -> {
			l.refresh();
		});
	}
	public int getCandListSize(){
		return qe.countFavCandidates();
	}
	public Author getCand(int i){
		return qe.fetchCandidate(i);
	}
	
	public void addListenerToCandList(CandidateListListener p){
		CandListListeners.add(p);
	}
		
	public ObservableList<Publication> getAuthorPubs(Author atr){
		return (ObservableList<Publication>) qe.getPublicationByAuthorName(atr);
	}
	
	private QueryEngine qe;
	private ArrayList<CandidateListListener> CandListListeners = new ArrayList<>(); 
}
