
package main.search;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
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

    //Pie chart Data
    private ObservableList<PieChart.Data> data;
    //Tableview data
//    private ObservableList<ExportHistory> histData;
    @FXML
    private PieChart myPieChart;
    @FXML
    private Button btnViewChart;

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
        buildPieChartData();
    }



    private void buildPieChartData() {

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Grapefruit", 13),
                        new PieChart.Data("Oranges", 25),
                        new PieChart.Data("Plums", 10),
                        new PieChart.Data("Pears", 22),
                        new PieChart.Data("Apples", 30));

//        final PieChart chart = new PieChart(pieChartData);
        myPieChart.setTitle("Exportation Year and Amount(Kgs) Exported");
        myPieChart.setData(pieChartData);


    }

//    @FXML
//    private void viewChart(ActionEvent event) {
//        buildPieChartData();
//    }
//
//    //Set Up line chart details
//    private void MakeLineGraph() {
////        xAxis.setLowerBound(1990);
////        xAxis.setUpperBound(2030);
////        xAxis.setTickUnit(1);
//        xAxis.setLabel("Export Year(Year)");
//        yAxis.setLabel("Amount Exported(Kgs)");
//        lineChart.setTitle("Chart Visualization");
//
//        ObservableList<XYChart.Series< Integer, Integer>> chartData = FXCollections.observableArrayList();
//
//        Series<Integer, Integer> series = new Series<>();
//        try {
//
//            conn = functions.connectDB();
//
//            String getData = "SELECT export_year,export_amount FROM mg_exports";
//            ResultSet rs = conn.createStatement().executeQuery(getData);
//            while (rs.next()) {
//                int eYear = rs.getInt(1);
//                int Amount = (int) rs.getDouble(2);
//
//                series.getData().add(new XYChart.Data<>(eYear, Amount));
//            }
//            chartData.add(series);
//            lineChart.getData().addAll(chartData);
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//    }
//
//    //Make History TableView
//    private void HistoryTableView() {
//        try {
//            conn = functions.connectDB();
//            histData = FXCollections.observableArrayList();
//            Statement stmt = conn.createStatement();
//            ResultSet set = stmt.executeQuery("SELECT * FROM mg_exports ORDER BY export_year asc");
//            while (set.next()) {
//                int year = set.getInt(4);
//                double amount = set.getDouble(2);
//                double price = set.getDouble(3);
//                String country = set.getString(5).toUpperCase();
//                String manager = set.getString(6).toUpperCase();
//
//                histData.add(new ExportHistory(year, amount, price, country, manager));
//
//            }
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
//        colAmnt.setCellValueFactory(new PropertyValueFactory<>("amount"));
//        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
//        colCtry.setCellValueFactory(new PropertyValueFactory<>("country"));
//        colManager.setCellValueFactory(new PropertyValueFactory<>("manager"));
//
//        tableHistory.setItems(histData);
//    }
//
//    @FXML
//    private void clerkView(ActionEvent event) {
//    }
//
//    @FXML
//    private void quitOption(ActionEvent event) {
//        Platform.exit();
//    }
//
//    @FXML
//    private void ActionNewClerk(ActionEvent event) {
//    }
//
//    @FXML
//    private void ActionChangePass(ActionEvent event) {
//    }
//
//    @FXML
//    private void FarmersView(ActionEvent event) {
//        try {
//            Stage currentStage = (Stage) btnRecordEx.getScene().getWindow();
//            functions.NewStage(Functions.FARMERS, "FARMERS MANAGEMENT", true);
//            currentStage.hide();
//        } catch (IOException ex) {
//            Logger.getLogger(FXemployeesController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    @FXML
//    private void EmployeeView(ActionEvent event) {
//        try {
//            Stage currentStage = (Stage) btnRecordEx.getScene().getWindow();
//            functions.NewStage(Functions.EMPLOYEES, "EXPLOYEES MANAGEMENT", true);
//            currentStage.hide();
//        } catch (IOException ex) {
//            Logger.getLogger(FXemployeesController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    @FXML
//    private void AttendanceView(ActionEvent event) {
//        try {
//            Stage currentStage = (Stage) btnRecordEx.getScene().getWindow();
//            functions.NewStage(Functions.ATTENDANCE, "ATTENDANCE MANAGEMENT", true);
//            currentStage.hide();
//        } catch (IOException ex) {
//            Logger.getLogger(FXemployeesController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    @FXML
//    private void ExportationView(ActionEvent event) {
//        try {
//            Stage currentStage = (Stage) btnRecordEx.getScene().getWindow();
//            functions.NewStage(Functions.EXPORT, "EXPORTATION MANAGEMENT", true);
//            currentStage.hide();
//        } catch (IOException ex) {
//            Logger.getLogger(FXemployeesController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    @FXML
//    private void ChemicalsView(ActionEvent event) {
//        try {
//            Stage currentStage = (Stage) btnRecordEx.getScene().getWindow();
//            functions.NewStage(Functions.CHEMICALS, "CHEMICALS MANAGEMENT", true);
//            currentStage.hide();
//        } catch (IOException ex) {
//            Logger.getLogger(FXemployeesController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    @FXML
//    private void logOut(ActionEvent event) throws IOException {
//        Stage current = (Stage) btnRecordEx.getScene().getWindow();
//        Stage stage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource(Functions.LOGIN));
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.setResizable(false);
//        current.hide();
//        stage.show();
//    }
//
//    @FXML
//    private void EmployeePaymentsView(ActionEvent event) {
//    }
//
//    @FXML
//    private void FarmerPaymentsView(ActionEvent event) {
//    }
}
