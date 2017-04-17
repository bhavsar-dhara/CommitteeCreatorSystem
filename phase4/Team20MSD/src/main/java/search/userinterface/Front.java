package main.java.search.userinterface;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.java.search.model.Author;
import main.java.search.service.QueryEngine;
import main.java.serach.interfaces.UserInterface;

public class Front implements Initializable {

	@FXML
	private TextField keyword;
	@FXML
	private ChoiceBox conferencename;
	@FXML
	private ChoiceBox pubdate;
	@FXML
	private ChoiceBox numofpub;
	@FXML
	private ChoiceBox year;

	private ObservableList<Author> data;

	@FXML
	private CheckBox check;

	@FXML
	private RadioButton checkcomm;
	@FXML
	private TableColumn<Author, String> name;
	@FXML
	private TableColumn<Author, String> university;
	@FXML
	private TableColumn<Author, String> commit;
	@FXML
	private TableColumn<Author, String> noofpub;
	@FXML
	private TableView<Author> empTable;

	@FXML
	private Button btnClear1;
	@FXML
	private CheckBox checkBox;
	@FXML
	private Button btnSearch;
	@FXML
	private Button btnClear;

	@FXML
	private Button searchforresult;

	@FXML
	private Button myfavouriteauthor;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		qe = new QueryEngine();
		ui = new ConcreteUserInterface(qe);
		List<Integer> pubno = new ArrayList<Integer>();
		pubno.add(1);
		pubno.add(2);
		pubno.add(3);
		pubno.add(4);
		pubno.add(5);
		pubno.add(6);
		pubno.add(7);
		pubno.add(8);
		pubno.add(9);
		pubno.add(10);
		pubno.add(11);
		pubno.add(12);
		pubno.add(13);

		List<Integer> comittno = new ArrayList<Integer>();
		comittno.add(1);
		comittno.add(2);
		comittno.add(3);
		comittno.add(4);
		comittno.add(5);

		List<String> list = qe.fetchJournalNames();
		List<Integer> plist = qe.fetchYearsAvailable();
		ObservableList<String> confList = FXCollections.observableList(list);
		ObservableList<Integer> pubdateList = FXCollections.observableList(plist);
		ObservableList<Integer> pubno1 = FXCollections.observableList(pubno);
		ObservableList<Integer> comittno1 = FXCollections.observableList(comittno);

		conferencename.getItems().clear();
		conferencename.setItems(confList);

		pubdate.getItems().clear();
		pubdate.setItems(pubdateList);

		numofpub.getItems().clear();
		numofpub.setItems(pubno1);

		year.getItems().clear();
		year.setItems(comittno1);

		// empTable.getItems().clear();

		// data = FXCollections.observableArrayList();
		//// data.clear();
		// // data.add(new Author("sdsdsd", "sdsdsdsd", "2"));
		// // data.add(new Author("sdsdsd", "sdsdsdsd", "2"));
		// // data.add(new Author("frank", "sdsdsdsd", "2"));
		// // data.add(new Author("dd", "frank", "2"));
		// // data.add(new Author("sdsdsd", "tome", "2"));
		// // data.add(new Author("sdsdsd", "zhi", "2"));
		// // data.add(new Author("sdsdsd", "zhu", "2"));
		// // data.add(new Author("sdsdsd", "he", "2"));
		// // data.add(new Author("sdsdsd", "sdsdsdsd", "2"));
		// // data.add(new Author("sdsdsd", "li", "2"));
		//
		// name.setCellValueFactory(new PropertyValueFactory<>("name"));
		// university.setCellValueFactory(new PropertyValueFactory<>("title"));
		// noofpub.setCellValueFactory(new
		// PropertyValueFactory<>("noOfPublication"));
		// commit.setCellValueFactory(new
		// PropertyValueFactory<>("noOfPublication"));
		//// empTable.setItems(null);
		// empTable.setItems(data);

		System.out.println("Dynamic Loading successful..");
	}

	@FXML
	private void setSearch(Event event) throws Exception {

		System.out.println("onButtonCLick.........");

		// Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please Enter
		// proper information " + " ?",
		// ButtonType.YES);
		// alert.show();'
		data = FXCollections.observableArrayList();

		String conferencename1 = null;
		if (!conferencename.getSelectionModel().isSelected(0) && !conferencename.getSelectionModel().isSelected(-1)) {
			System.out.println("conference name item... " + conferencename.getSelectionModel().getSelectedItem());
			conferencename1 = (conferencename.getSelectionModel().getSelectedItem().toString().equals("")
					|| conferencename.getSelectionModel().getSelectedItem().toString().equals(" ")) ? ""
							: conferencename.getSelectionModel().getSelectedItem().toString().trim();
		}

		String pubdate1 = null;
		if (!pubdate.getSelectionModel().isEmpty()) {
			System.out.println("pubdate item... " + pubdate.getSelectionModel().getSelectedItem().toString());
			pubdate1 = (pubdate.getSelectionModel().getSelectedItem().toString().equals("")
					|| pubdate.getSelectionModel().getSelectedItem().toString().equals(" ")) ? ""
							: pubdate.getSelectionModel().getSelectedItem().toString().trim();
		}

		String numofcom1 = null;
		if (check.isSelected()) {
			if (!year.getSelectionModel().isEmpty()) {
				System.out.println("year... " + year.getSelectionModel().getSelectedItem().toString());
				numofcom1 = year.getSelectionModel().getSelectedItem().toString().equals("") ? "0"
						: year.getSelectionModel().getSelectedItem().toString().trim();
			}
		}

		String numofpub1 = "0";
		if (!numofpub.getSelectionModel().isEmpty()) {
			System.out.println("numofpub... " + numofpub.getSelectionModel().getSelectedItem().toString());
			numofpub1 = numofpub.getSelectionModel().getSelectedItem().toString().equals("") ? "0"
					: numofpub.getSelectionModel().getSelectedItem().toString();
		}

		String keyword1 = "";
		if (!numofpub.getSelectionModel().isEmpty()) {
			keyword1 = keyword.getText().equals("") ? "" : keyword.getText().trim();
			System.out.println("keyword... " + keyword1);
		}

		// String numofpub1 = numofpub.getText().equals("") ? "0" :
		// numofpub.getText();

		int[] intArray = null;
		if (pubdate1 != null) {
			// TODO : if multiple year selection enabled - change code
			intArray = new int[1];
			intArray[0] = Integer.parseInt(pubdate1);
		}

		List<Author> authorList = qe.populateListOfAuthors(conferencename1, keyword1, intArray,
				Integer.parseInt(numofpub1), false);

		for (Author a : authorList) {
			data.add(new Author(a.getTitle(), a.getName(), a.getNoOfPublication(), a.getPublication()));
		}

		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		university.setCellValueFactory(new PropertyValueFactory<>("title"));
		noofpub.setCellValueFactory(new Callback<CellDataFeatures<Author, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Author, String> param) {
				String value = param.getValue().getPublication().getStrPbyear();
				if (value == null) {
					value = "N/A";
				}
				return new ReadOnlyStringWrapper(value);
			}
		});
		commit.setCellValueFactory(new Callback<CellDataFeatures<Author, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Author, String> param) {
				String value = param.getValue().getPublication().getPages();
				if (value == null) {
					value = "N/A";
				}
				return new ReadOnlyStringWrapper(value);
			}
		});

		// empTable.refresh();'
		empTable.setItems(null);
		empTable.setItems(data);

	}

	@FXML
	private void viewauthor(ActionEvent event) throws IOException {
		Author selectedauthor = empTable.getSelectionModel().getSelectedItem();

		if (selectedauthor != null) {
			System.out.println(selectedauthor.getName());

			AuthorProfilePage page = new AuthorProfilePage(selectedauthor, ui);
			ui.displayNewWindow(page.getScene());
		}

	}

	@FXML
	private void clearInputs(ActionEvent event) {
		keyword.setText(null);
		pubdate.getSelectionModel().clearSelection();
		numofpub.getSelectionModel().clearSelection();
		year.getSelectionModel().clearSelection();
		conferencename.getSelectionModel().selectFirst();

	}

	@FXML
	private void candidateson(Event event) throws Exception {
		CandidateListPage page = new CandidateListPage(ui);
		ui.displayNewWindow(page.getScene());
	}

	private boolean isYearInputOkey() {
		String key1 = keyword.getText().trim();
		return key1.matches("^[a-zA-Z0-9]*$");
	}

	private UserInterface ui;
	private QueryEngine qe;
}
