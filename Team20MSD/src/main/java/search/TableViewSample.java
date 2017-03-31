package main.java.search;


import java.util.List;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.java.classes.Author;
import main.java.classes.SearchQuery;
import javafx.scene.control.TableRow;

public class TableViewSample extends Application {
 
    private TableView table = new TableView();
    
//	int[] intArray = new int[1];
//	intArray[0] = 1996;
//	populateListOfAuthors("acta inf.", "parallel", intArray, 2);
    
    private final ObservableList data =
        FXCollections.observableArrayList(
//            new Person("Jacob", "Smith", "jacob.smith@example.com"),
//            new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
//            new Person("Ethan", "Williams", "ethan.williams@example.com"),
//            new Person("Emma", "Jones", "emma.jones@example.com"),
//            new Person("Michael", "Brown", "michael.brown@example.com")
        );
    
    public void setCurrentInfo(String key, String conf, String date, String numofpub, String commit ) {
    	   int[] intArray = new int[1];
           intArray[0] = 1996;
           List<Author> authorList = SearchQuery.populateListOfAuthors("acta inf.", "parallel", intArray, 2);
        for (Author a : authorList) {
        	data.add(new Person(a.getName(), a.getTitle(), a.getNoOfPublication()));
        }
    	System.out.print("sssssssssssss");
    	start(new Stage());
    }   
    
    
    
//    public static void main(String[] args) {
//        launch(args);
//    }
 
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(700);
        stage.setHeight(500);
 
        final Label label = new Label("Author List");
        label.setFont(new Font("Arial", 20));
 
        table.setEditable(true);
 
        TableColumn firstNameCol = new TableColumn("Author Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory("firstName"));
 
        TableColumn lastNameCol = new TableColumn("Publication Title");
        lastNameCol.setMinWidth(550);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory("lastName"));
 
        TableColumn emailCol = new TableColumn("Publication#");
        emailCol.setMinWidth(50);
        emailCol.setCellValueFactory(
                new PropertyValueFactory("email"));
 
        
        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
        
//        table.setRowFactory( tv -> {
//            TableRow<MyType> row = new TableRow<>();
//            row.setOnMouseClicked(event -> {
//                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
//                    MyType rowData = row.getItem();
//                    System.out.println(rowData);
//                }
//            });
//            return row ;
//        });
        

//      table.setRowFactory(tv -> {
//      TableRow<MyDataType> row = new TableRow<>();
//      row.setOnMouseClicked(event -> {
//        if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
//             && event.getClickCount() == 2) {
//
//            MyDataType clickedRow = row.getItem();
//           System.out.print("success");
//}
//    });
//    return row ;
//});

// ...

//private void printRow(MyDataType item) {
//    // ...
//}
//        Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("Hello World!");
//            }
//        });
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }
 
    public static class Person {
 
        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;
 
        private Person(String fName, String lName, String email) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
        }
 
        public String getFirstName() {
            return firstName.get();
        }
 
        public void setFirstName(String fName) {
            firstName.set(fName);
        }
 
        public String getLastName() {
            return lastName.get();
        }
 
        public void setLastName(String fName) {
            lastName.set(fName);
        }
 
        public String getEmail() {
            return email.get();
        }
 
        public void setEmail(String fName) {
            email.set(fName);
        }
    }
} 
