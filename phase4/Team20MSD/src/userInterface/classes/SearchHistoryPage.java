package userInterface.classes;
import main.classes.Author;
import main.interfaces.*;
import userInterface.classes.SearchQuery;
import userInterface.classes.UITesting;
import userInterface.interfaces.SearchHistoryListener;

import java.util.ArrayList;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SearchHistoryPage implements SearchHistoryListener {
	
	public SearchHistoryPage(UITesting ui){
		this.ui = ui;
		ui.addListenerToSearchHistory(this);
		setPageHead();
		setClearBut();
		setPaginationControl();
		setCanvas();
	}

	private UITesting ui;
	private static int itemNbrPerPage = 15;
	private Label pageHead = new Label("Search History");
	private Button clearBut = new Button("Clear");
	private HBox buttonArea = new HBox(clearBut);
	private Pagination searchHistoryPagination = new Pagination();
	private VBox canvas = new VBox(10,pageHead,buttonArea,searchHistoryPagination);
	
	private int getPageCount(){
		if (ui.getSearchHistorySize()==0){
			return 1;
		}
		else {
			int quotient = (int) ui.getSearchHistorySize()/itemNbrPerPage;
			int remainder = ui.getSearchHistorySize() % itemNbrPerPage;
			return remainder == 0 ? quotient : quotient +1;
		}
	}
	
	private void setPageHead() {
		pageHead.setFont(new Font("Arial",40));
	}
	
	private void setClearBut(){
		clearBut.setFont(new Font("Black",12));
		clearBut.setOnAction((ActionEvent ae) -> {
			ui.clearSearchHistory();
		});
	}
	
	private void setPaginationControl(){
		searchHistoryPagination.setPageCount(getPageCount());
		searchHistoryPagination.setPageFactory((Integer pageIndex) -> createPage(pageIndex));
	}
	
	private VBox createPage(int pageIndex){
		VBox pageContent = new VBox();
		int itemIndex = pageIndex*itemNbrPerPage;
		for (int i=itemIndex;i<itemIndex+itemNbrPerPage && i<ui.getSearchHistorySize();i++){
			SearchQuery sq = ui.getSearchHistoryItem(i);
			Hyperlink hyperLink = new Hyperlink(sq.toString());
			setHyperLink(hyperLink,sq);
			pageContent.getChildren().add(hyperLink);
		}
		fixElementHeight(pageContent,500);
		fixElementWidth(pageContent,500);
		return pageContent;
	}
	
	private void fixElementHeight(Region r,double height) {
		r.setMinHeight(height);
		r.setPrefHeight(height);
		r.setMaxHeight(height);
	}
	
	private void fixElementWidth(Region r,double height) {
		r.setMinWidth(height);
		r.setPrefWidth(height);
		r.setMaxWidth(height);
	}
	
	public void refresh(){
		setPaginationControl();
	}
	
	private void setHyperLink(Hyperlink hl,SearchQuery sq){
		hl.setOnAction((ActionEvent e) -> {
			ui.getSearchResult(sq);
			SearchResultPage sr = new SearchResultPage();
			ui.displayNewWindow(sr.getScene());
		});
		hl.setFont(new Font("Arial",20));
		
		//Visual Effect
		DropShadow ds = new DropShadow();
		ds.setColor(Color.GOLD);
		hl.setOnMouseEntered((MouseEvent e) -> {
			hl.setEffect(ds);
		});
		
		hl.setOnMouseExited((MouseEvent e) -> {
			hl.setEffect(null);
		});
	}
	private void setCanvas(){
		canvas.setAlignment(Pos.CENTER);
		canvas.setPadding(new Insets(20,40,20,40));
	}
	
	public Scene getScene(){
		return new Scene(canvas);
	}
	
	Button getClearBut(){
		return clearBut;
	}
	
	Pagination getPagination(){
		return searchHistoryPagination;
	}
	public static int getItemNbrPerPage(){
		return itemNbrPerPage;
	}
}
