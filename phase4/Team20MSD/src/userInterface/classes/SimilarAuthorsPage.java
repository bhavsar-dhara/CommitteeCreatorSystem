package userInterface.classes;

import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import main.classes.Author;
import main.interfaces.UserInterface;
import userInterface.helperClasses.TableColumnAdder;

public class SimilarAuthorsPage {
	
	public SimilarAuthorsPage(Author a,UserInterface ui){
		this.ui = ui;
		setResultTable(a);
		setButton();
		setCanvas();
	}
	
	
	private void setResultTable(Author a) {
		SortedList<Author> origList = new SortedList<>(ui.getSearchResult(a));
		resultTable = new TableView<Author>(origList);
		origList.comparatorProperty().bind(resultTable.comparatorProperty());
		setResultTableColumns();
		resultTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}


	private void setResultTableColumns() {
		TableColumnAdder<Author> tca = new TableColumnAdder<>(resultTable);
		tca.addStringColumnToTable("Name", "name");
		tca.addStringColumnToTable("Co-authored Publication", "title");
		tca.addIntegerColumnToTable("Total Number of Publication", "noOfPublication");
	}


	private void setButton() {
		viewProfileBut = new Button("View Profile");
		viewProfileBut.setOnAction((ActionEvent e) -> {
			if (!resultTable.getSelectionModel().isEmpty()){
				AuthorProfilePage app = new AuthorProfilePage(resultTable.getSelectionModel().getSelectedItems().get(0),ui);
				ui.displayNewWindow(app.getScene());
			}
		});
	}


	private void setCanvas() {
		canvas = new VBox(20,resultTable,viewProfileBut);
		canvas.setPadding(new Insets(10,10,10,10));
	}
	
	public Scene getScene(){
		return new Scene(canvas);
	}


	private TableView<Author> resultTable;
	private Button viewProfileBut;
	private VBox canvas;
	private UserInterface ui;
}
