
package userInterface.classes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

//import main.java.search.model.Author;
//import main.java.search.service.QueryEngine;
//import main.java.serach.interfaces.UserInterface;
import main.classes.Author;
import main.classes.QueryEngine;
import main.interfaces.UserInterface;

//import pkgcommon.Functions;
//import pkgmodels.ExportHistory;



public class Compare implements Initializable {


//    private Functions functions;

    private ObservableList<PieChart.Data> data2;
    @FXML
    private Button btnRecordEx;
    @FXML
    private Button linechartclick;

    private ObservableList<Author> data1;

    //Pie chart Data
    private ObservableList<PieChart.Data> data;

    @FXML
    private PieChart myPieChart;
    @FXML
    private PieChart pie1;
    @FXML
    private Button btnViewChart;
    @FXML
    private TableColumn<Author, String> colrole;
    @FXML
    private TableColumn<Author, String> aname;
    @FXML
    private TableView<Author> colAmnt;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        qe = QueryEngine.instance();
        data = FXCollections.observableArrayList();
        data1 = FXCollections.observableArrayList();
        List<Author> authorslist  =qe.fetchCandidateDetails();

        System.out.println("...... .. " + authorslist.size());
        for (Author a : authorslist) {
            data1.add(new Author(a.getName(),1,a.getRole()));
            data.add(new PieChart.Data(a.getName(), Double.parseDouble(a.getNoOfPublication())));
        }

        aname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colrole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colAmnt.setItems(data1);
        buildPieChartData(data);
    }



    private void buildPieChartData( ObservableList<PieChart.Data> data) {


        myPieChart.setTitle("Number of Publication of Different Authors ");
        myPieChart.setData(data);


    }

    @FXML
    private void lineclick(Event event) throws Exception {
        Author selectedauthor = colAmnt.getSelectionModel().getSelectedItem();

        if (selectedauthor != null) {
            System.out.println(selectedauthor.getName());
            MakeLineGraph(selectedauthor);
        }


    }
    private void MakeLineGraph(Author name) {


        System.out.println("connection complete");

        ObservableList<PieChart.Data> pieChartData2 =
                FXCollections.observableArrayList(
                        new PieChart.Data("Grapefruit", 13),
                        new PieChart.Data("Oranges", 25),
                        new PieChart.Data("Plums", 10),
                        new PieChart.Data("Pears", 22),
                        new PieChart.Data("Apples", 30));

        pie1.setTitle(name.getName() +"'s Yearly Publication");
        pie1.setData(pieChartData2);


    }
    private UserInterface ui;

    private QueryEngine qe;
//    }
}
