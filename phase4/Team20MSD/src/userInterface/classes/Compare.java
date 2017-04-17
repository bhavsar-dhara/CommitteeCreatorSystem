
package userInterface.classes;
import java.util.*;
import java.net.URL;
import java.util.List;

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

import main.classes.*;
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

    private ObservableList<PieChart.Data> data3;


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
        List<Integer> list1  =  qe.getPBYearListByAuthorname(name);
        System.out.println(list1);
        Map<Integer, Integer> map2 = sort(list1);
        ArrayList<Integer> newList1 = new ArrayList<Integer>(map2.keySet());
        ArrayList<Integer> newList = new ArrayList<Integer>(map2.values());
        System.out.print(newList);

        data3 = FXCollections.observableArrayList();

        for(int x = 0; x < newList.size()-1; x = x + 1) {
            data3.add(new PieChart.Data(newList1.get(x).toString() , newList.get(x)));
        }


//        ObservableList<PieChart.Data> pieChartData2 =
//                FXCollections.observableArrayList(
//                        new PieChart.Data("Grapefruit", 13),
//                        new PieChart.Data("Oranges", 25),
//                        new PieChart.Data("Plums", 10),
//                        new PieChart.Data("Pears", 22),
//                        new PieChart.Data("Apples", 30));

        if (newList1.isEmpty())
        {        pie1.setTitle("No data for this Author");}
        else{
            pie1.setTitle(name.getName() +"'s Yearly Publication");
        pie1.setData(data3);}


    }


    public static Map<Integer, Integer> sort(List<Integer> list) {

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        for(Integer obj: list){

            if(map.containsKey(obj)){
                map.put(obj, map.get(obj).intValue() + 1);
            }else{
                map.put(obj, 1);
            }
        }
        Map<Integer, Integer> map1 = new TreeMap<Integer, Integer>(map);


       return map1;

    }
    private UserInterface ui;

    private QueryEngine qe;
//    }
}
