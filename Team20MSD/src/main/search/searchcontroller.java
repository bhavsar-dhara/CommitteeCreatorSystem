package main.search;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
//import main.java.classes.Author;
//import main.java.classes.SearchQuery;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.classes.SearchQuery;
import main.classes.UserInterface;

public class searchcontroller implements Initializable {

	// declaration

	@FXML
	private TextField keyword;
	@FXML
	private ChoiceBox conferencename;
	@FXML
	private ChoiceBox pubdate;
	@FXML
	private TextField numofpub;
	@FXML
	private ChoiceBox numofcom;

	@FXML
	private Label label123;

	@FXML
	private Button searchforresult;

	@FXML
	private Button myfavouriteauthor;

	@FXML
	private void setSearch(Event event) throws Exception {
		// get the criteria parameters from the search page
		String conferencename1 = conferencename.getSelectionModel().getSelectedItem().toString().equals("") 
				? "" : conferencename.getSelectionModel().getSelectedItem().toString();
		String pubdate1 = pubdate.getSelectionModel().getSelectedItem().toString().equals("") 
				? "0" : pubdate.getSelectionModel().getSelectedItem().toString();
		String numofcom1 = numofcom.getSelectionModel().getSelectedItem().toString();
		String keyword1 = keyword.getText().equals("") ? "" : keyword.getText();
		String numofpub1 = numofpub.getText().equals("") ? "0" : numofpub.getText();

		// int[] intArray = new int[1];
		// intArray[0] = (Integer.parseInt(pubdate1));
		// List<Author> authorList =
		// SearchQuery.populateListOfAuthors(conferencename1, keyword1,
		// intArray, Integer.parseInt(numofpub1));
		// TableViewSample tableViewSample = new TableViewSample();

		Result tableViewSample = new Result();

		// pass the criteria variables to query engine
		tableViewSample.setCurrentInfo(keyword1, conferencename1, pubdate1, numofpub1, numofcom1);
	}

	@FXML
	private void candidateson(Event event) throws Exception {
		// UserInterface.showCandidateListPage();
		UserInterface.showCandidateList();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<String> list = SearchQuery.fetchJournalNames();
		List<Integer> plist = SearchQuery.fetchYearsAvailable();
		ObservableList confList = (ObservableList) FXCollections.observableList(list);
		ObservableList pubdateList = (ObservableList) FXCollections.observableList(plist);

		conferencename.getItems().clear();
		conferencename.setItems(confList);

		pubdate.getItems().clear();
		pubdate.setItems(pubdateList);

		System.out.println("Dynamic Loading successful..");
	}

}
