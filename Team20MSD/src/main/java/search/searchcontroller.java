package main.java.search;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.java.classes.Author;
import main.java.classes.SearchQuery;
import javafx.scene.control.Label;  
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

public class searchcontroller  {


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
    private void setAdminTeacherSearchButtonClick(Event event) throws Exception{
    	

        String conferencename1 = conferencename.getSelectionModel().getSelectedItem().toString();
        String pubdate1 = pubdate.getSelectionModel().getSelectedItem().toString();
        String numofcom1 = numofcom.getSelectionModel().getSelectedItem().toString();
        String keyword1 = keyword.getText();
        String numofpub1 = numofpub.getText();
        String smart = "iloveyou";
//        int[] intArray = new int[1];
//        intArray[0] = (Integer.parseInt(pubdate1));
//        List<Author> authorList = SearchQuery.populateListOfAuthors(conferencename1, keyword1, intArray, Integer.parseInt(numofpub1));
//        TableViewSample tableViewSample = new TableViewSample();
        TableViewSample tableViewSample = new TableViewSample();
        tableViewSample.setCurrentInfo(keyword1,conferencename1,pubdate1,numofpub1,numofcom1);


    }


}
