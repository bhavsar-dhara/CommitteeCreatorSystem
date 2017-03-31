package main.search;

import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.classes.Author;
import main.classes.SearchQuery;
import main.interfaces.UserInterface;
import scndPartOfUI.UIForTest;

public class Result extends Application {

	private TableView table = new TableView();
	Stage window;

	SearchQuery searchQuery = new SearchQuery();
    ObservableList<Author> products = FXCollections.observableArrayList();

	public void setCurrentInfo(String key, String conf, String date, String numofpub, String commit) {
		
		// TODO : if multiple year selection enabled - change code
		 int[] intArray = new int[1];
		 intArray[0] = Integer.parseInt(date);
		 
		 List<Author> authorList = searchQuery.populateListOfAuthors(conf.trim(), key.trim(), intArray, Integer.parseInt(numofpub));
		 
		 for (Author a : authorList) {
			 products.add(new Author(a.getTitle(), a.getName(), a.getNoOfPublication()));
		 }
		 
		start(new Stage());
	}

	@Override
	public void start(Stage primaryStage) {

		window = primaryStage;
		window.setTitle("Result Page");

		// Name column
		TableColumn<Author, String> nameColumn = new TableColumn<>("Author_Name");
		nameColumn.setMinWidth(100);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		// publication column
		TableColumn<Author, String> pubColumn = new TableColumn<>("Publications");
		pubColumn.setMinWidth(100);
		pubColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

		// publication number column
		TableColumn<Author, String> pubnoColumn = new TableColumn<>("PubNumber");
		pubnoColumn.setMinWidth(100);
		pubnoColumn.setCellValueFactory(new PropertyValueFactory<>("noOfPublication"));

		Button ViewButton = new Button("View Profile");
		ViewButton.setOnAction(e -> ButtonClicked());

		HBox hBox = new HBox();
		hBox.setPadding(new Insets(10, 10, 10, 10));
		hBox.setSpacing(10);
		hBox.getChildren().addAll(ViewButton);

		table = new TableView<>();
		table.setItems(products);
		table.getColumns().addAll(nameColumn, pubColumn, pubnoColumn);

		VBox vBox = new VBox();
		vBox.getChildren().addAll(table, hBox);

		Scene scene = new Scene(vBox);
		window.setScene(scene);
		window.show();
	}

//	public ObservableList<Author> getProduct() {
//		ObservableList<Author> products = FXCollections.observableArrayList();
//		products.add(new Author("Laptop", "Laptop", "Laptop"));
//		products.add(new Author("Laptop1", "Laptop1", "Laptop1"));
//		products.add(new Author("Laptop2", "Sanjeev Saxena", "Laptop2"));
//		products.add(new Author("Laptop3", "Laptop3", "Laptop3"));
//		return products;
//	}

	// Delete button clicked
	public void ButtonClicked() {
		ObservableList<Author> AuthorSelected;
		AuthorSelected = table.getSelectionModel().getSelectedItems();
		System.out.println(AuthorSelected.size());
		ui.showAuthorProfile(AuthorSelected.get(0));
	}
	
	private UserInterface ui = new UIForTest(searchQuery);
}
