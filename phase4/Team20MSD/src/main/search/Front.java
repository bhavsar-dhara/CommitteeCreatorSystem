package main.search;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
//import main.java.classes.Author;
//import main.java.classes.SearchQuery;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.classes.Author;
import main.classes.ConcreteUserInterface;
import main.classes.QueryEngine;
import main.interfaces.UserInterface;
import scndPartOfUI.AuthorProfilePage;



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
    private TableColumn<Author, Integer> noofpub;
    @FXML
    private TableView<Author> empTable;

    @FXML
    private Button btnClear1;
    @FXML
    private CheckBox  checkBox;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnClear;

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
        ObservableList confList = (ObservableList) FXCollections.observableList(list);
        ObservableList pubdateList = (ObservableList) FXCollections.observableList(plist);
        ObservableList pubno1 = (ObservableList) FXCollections.observableList(pubno);
        ObservableList comittno1 = (ObservableList) FXCollections.observableList(comittno);

        conferencename.getItems().clear();
        conferencename.setItems(confList);

        pubdate.getItems().clear();
        pubdate.setItems(pubdateList);

        numofpub.getItems().clear();
        numofpub.setItems(pubno1);

        year.getItems().clear();
        year.setItems(comittno1);



        data = FXCollections.observableArrayList();
        data.add(new Author("sdsdsd","sdsdsdsd","2"));
        data.add(new Author("sdsdsd","sdsdsdsd","2"));
        data.add(new Author("frank","sdsdsdsd","2"));
        data.add(new Author("dd","frank","2"));
        data.add(new Author("sdsdsd","tome","2"));
        data.add(new Author("sdsdsd","zhi","2"));
        data.add(new Author("sdsdsd","zhu","2"));
        data.add(new Author("sdsdsd","he","2"));
        data.add(new Author("sdsdsd","sdsdsdsd","2"));
        data.add(new Author("sdsdsd","li","2"));

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        university.setCellValueFactory(new PropertyValueFactory<>("title"));
        noofpub.setCellValueFactory(new PropertyValueFactory<>("noOfPublication"));
        commit.setCellValueFactory(new PropertyValueFactory<>("noOfPublication"));
//        empTable.setItems(null);
        empTable.setItems(data);

        System.out.println("Dynamic Loading successful..");

        String selection;


    }

    @FXML
    private void setSearch(Event event) throws Exception {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please Enter proper information " +  " ?", ButtonType.YES);
        alert.show();

        String conferencename1 = null;

        if ( conferencename.getSelectionModel().isEmpty() ) {
            conferencename1 = conferencename.getSelectionModel().getSelectedItem().toString().equals("")
                    ? "" : conferencename.getSelectionModel().getSelectedItem().toString();
        }

        String pubdate1 = null;
        if ( pubdate.getSelectionModel().isEmpty() ) {
            pubdate1 =  pubdate.getSelectionModel().getSelectedItem().toString().equals("")
                    ? "0" : pubdate.getSelectionModel().getSelectedItem().toString();
        }


        if(check.isSelected()){
            String numofcom1 = null;
            if (year.getSelectionModel().isEmpty()) {
                numofcom1 = year.getSelectionModel().getSelectedItem().toString().equals("")
                        ? "0" : year.getSelectionModel().getSelectedItem().toString();
            }
        }else{String numofcom1 = null;}


        String numofpub1 = null;
        if ( numofpub.getSelectionModel().isEmpty() ) {
            numofpub1 =  numofpub.getSelectionModel().getSelectedItem().toString().equals("")
                    ? "0" : numofpub.getSelectionModel().getSelectedItem().toString();
        }

        String keyword1 = keyword.getText().equals("") ? "" : keyword.getText();



//
//        String numofpub1 = numofpub.getText().equals("") ? "0" : numofpub.getText();


//        System.out.println(checkbox);

    }

    @FXML
    private void viewauthor(ActionEvent event) throws IOException {
        Author selectedauthor = empTable.getSelectionModel().getSelectedItem();

        if(selectedauthor != null)
        {        System.out.println(selectedauthor.getName());

        AuthorProfilePage page = new AuthorProfilePage(selectedauthor,ui);
        ui.displayNewWindow(page.getScene());}

    }


    @FXML
    private void clearInputs(ActionEvent event) {
        keyword.setText(null);
        pubdate.getSelectionModel().clearSelection();
        numofpub.getSelectionModel().clearSelection();
        year.getSelectionModel().clearSelection();
        conferencename.getSelectionModel().selectFirst();

    }



    private boolean isYearInputOkey() {
        String key1 = keyword.getText().trim();
        return key1.matches("^[a-zA-Z0-9]*$");
    }

    private UserInterface ui;
    private QueryEngine qe;
}
