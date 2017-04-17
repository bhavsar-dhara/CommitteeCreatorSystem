package main.java.search.userinterface;

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
import main.java.search.model.Author;
import main.java.search.service.QueryEngine;
import main.java.serach.interfaces.UserInterface;

import java.util.List;

public class Result {

	private TableView table = new TableView();

	ObservableList<Author> products = FXCollections.observableArrayList();

	public void setCurrentInfo(String key, String conf, String date, String numofpub, String commit,
			boolean isSearvedAsCommittee, UserInterface ui, QueryEngine qe) {

		int[] intArray = null;
		if (date != null) {
			// TODO : if multiple year selection enabled - change code
			intArray = new int[1];
			intArray[0] = Integer.parseInt(date);
		}

		List<Author> authorList = qe.populateListOfAuthors(conf, key, intArray, Integer.parseInt(numofpub),
				isSearvedAsCommittee);

		for (Author a : authorList) {
			products.add(new Author(a.getTitle(), a.getName(), a.getNoOfPublication()));
		}

		this.ui = ui;
		this.qe = qe;
	}

	public Scene getScene() {

		// window.setTitle("Result Page");

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

		return new Scene(vBox);
	}

	// public ObservableList<Author> getProduct() {
	// ObservableList<Author> products = FXCollections.observableArrayList();
	// products.add(new Author("Laptop", "Laptop", "Laptop"));
	// products.add(new Author("Laptop1", "Laptop1", "Laptop1"));
	// products.add(new Author("Laptop2", "Sanjeev Saxena", "Laptop2"));
	// products.add(new Author("Laptop3", "Laptop3", "Laptop3"));
	// return products;
	// }

	// Delete button clicked
	public void ButtonClicked() {
		ObservableList<Author> AuthorSelected;
		AuthorSelected = table.getSelectionModel().getSelectedItems();
		AuthorProfilePage page = new AuthorProfilePage(AuthorSelected.get(0), ui);
		ui.displayNewWindow(page.getScene());
	}

	private UserInterface ui;
	private QueryEngine qe;
}
