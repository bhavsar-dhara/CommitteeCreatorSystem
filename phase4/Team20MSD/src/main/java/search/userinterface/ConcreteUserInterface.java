package main.java.search.userinterface;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.search.model.Author;
import main.java.search.model.Publication;
import main.java.search.service.QueryEngine;
import main.java.serach.interfaces.CandidateListListener;
import main.java.serach.interfaces.UserInterface;

public class ConcreteUserInterface implements UserInterface {

	public ConcreteUserInterface(QueryEngine qe) {
		this.qe = qe;
	}

	public void displayNewWindow(Scene s) {
		Stage stage = new Stage();
		stage.setScene(s);
		stage.show();
	}

	public void displayNewWindow(Scene s, String title) {
		Stage stage = new Stage();
		stage.setScene(s);
		stage.setTitle(title);
		stage.show();
	}

	// operations on candidate list
	public void addCand(Author a) {
		if (!hasCand(a)) {
			qe.addAuthorIntoCandidate(a);
			CandListListeners.forEach((CandidateListListener l) -> {
				l.refresh();
			});
		}
	}

	public boolean hasCand(Author a) {
		return qe.isFavCandidate(a);
	}

	public void remCand(Author a) {
		if (hasCand(a)) {
			qe.deleteFavCandidate(a);
			CandListListeners.forEach((CandidateListListener l) -> {
				l.refresh();
			});
		}
	}

	public void remCand(List<Author> cands) {
		for (int i = 0; i < cands.size(); i++) {
			qe.deleteFavCandidate(cands.get(i));
		}
		CandListListeners.forEach((CandidateListListener l) -> {
			l.refresh();
		});
	}

	public int getCandListSize() {
		return qe.countFavCandidates();
	}

	public Author getCand(int i) {
		return qe.fetchCandidates(i);
	}

	public void addListenerToCandList(CandidateListListener p) {
		CandListListeners.add(p);
	}

	public ObservableList<Author> getSearchResult(Author a) {
		return (ObservableList<Author>) qe.getSimilarAuthorBySamePublication(a);
	}

	public ObservableList<Publication> getAuthorPubs(Author atr) {
		return (ObservableList<Publication>) qe.getPublicationByAuthorName(atr);
	}
	
	@Override
	public Author getSearchResult(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	private QueryEngine qe;
	private ArrayList<CandidateListListener> CandListListeners = new ArrayList<>();
	
}
