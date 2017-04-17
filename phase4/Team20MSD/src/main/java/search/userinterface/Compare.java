
package main.java.search.userinterface;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.java.search.model.Author;
import main.java.search.service.QueryEngine;
//import pkgcommon.Functions;
//import pkgmodels.ExportHistory;



public class Compare implements Initializable {

    @FXML
    private TextField txtProductionYear;
    @FXML
    private TextField txtAmountExported;
    @FXML
    private TextField txtPricePerKg;
    @FXML
    private TextField txtCountry;
    @FXML
    private TextField txtInchargeManager;
    private Connection conn;
    private PreparedStatement pst;
//    private Functions functions;
    @FXML
    private Button btnRecordEx;

    private ObservableList<Author> data1;

    //Pie chart Data
    private ObservableList<PieChart.Data> data;
    //Tableview data
//    private ObservableList<ExportHistory> histData;
    @FXML
    private PieChart myPieChart;
    @FXML
    private Button btnViewChart;
    @FXML
    private TableColumn<Author, String> colrole;
    @FXML
    private TableColumn<Author, String> aname;
    @FXML
    private TableView<Author> colAmnt;


//    @FXML
//    private TableView<ExportHistory> tableHistory;
//    @FXML
//    private TableColumn<ExportHistory, String> colManager;
//    @FXML
//
//    private TableColumn<ExportHistory, Integer> colYear;
//    @FXML
//    private TableColumn<ExportHistory, Double> colAmnt;
//    @FXML
//    private TableColumn<ExportHistory, Double> colPrice;
//    @FXML
//    private TableColumn<ExportHistory, String> colCtry;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        qe = new QueryEngine();
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


//        ObservableList<PieChart.Data> pieChartData =
//                FXCollections.observableArrayList(
//                        new PieChart.Data("Grapefruit", 13),
//                        new PieChart.Data("Oranges", 25),
//                        new PieChart.Data("Plums", 10),
//                        new PieChart.Data("Pears", 22),
//                        new PieChart.Data("Apples", 30));

//        final PieChart chart = new PieChart(pieChartData);
        myPieChart.setTitle("Publication Number of Different Authors ");
        myPieChart.setData(data);


    }
    private QueryEngine qe;
//    }
}
