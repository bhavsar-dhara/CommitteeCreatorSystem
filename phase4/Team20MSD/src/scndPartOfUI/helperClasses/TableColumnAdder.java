package scndPartOfUI.helperClasses;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableColumnAdder<T> {
	public TableColumnAdder(TableView<T> table){
		this.table = table;
	}
	
	public void addStringColumnToTable(String header,String propertyName){
		TableColumn<T,String> newColumn = new TableColumn<>(header);
		newColumn.setCellValueFactory(new PropertyValueFactory<T,String>(propertyName));
		table.getColumns().add(newColumn);
	}
	
	public void addIntegerColumnToTable(String header,String propertyName){
		TableColumn<T,Integer> newColumn = new TableColumn<>(header);
		newColumn.setCellValueFactory(new PropertyValueFactory<T,Integer>(propertyName));
		table.getColumns().add(newColumn);
	}
	 
	private TableView<T> table;
}
