package userInterface.classes;
import main.classes.Author;
import main.classes.Experience;
import main.classes.Publication;
import main.interfaces.*;
import userInterface.helperClasses.TableColumnAdder;
import userInterface.helperClasses.UIElementFixer;
import userInterface.interfaces.CandidateListListener;

import java.util.ArrayList;

import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AuthorProfilePage implements CandidateListListener {
	
	public AuthorProfilePage(Author a, UserInterface ui){
		atr = a;
		this.ui = ui;
		ui.addListenerToCandList(this);
		setDropArea();
		setNameArea();
		setButtonArea();
		setAuthorInformationPane();
		setCanvas();
	}

	private Author atr;
	private UserInterface ui;
	
	private Label dropHere;
	private HBox dropArea;
	
	private Label name;
	private Label associatedSchool;
	private VBox nameArea;
	
	private Button addBut;
	private Button remBut;
	private Button simAuthBut;
	private HBox buttonArea;
	
	private Label experience;
	private TableView<Experience> expTable;
	private VBox expArea;
	
	private Label blgrphy;
	private TableView<Publication> blgrphyTable;
	private VBox blgrphyArea;
	
	private VBox authorInformation;
	private ScrollPane authorInformationPane;

	private VBox canvas;

	private void setDropArea() {
		dropHere = new Label("drop here");
		dropArea = new HBox(dropHere);
		dropArea.setAlignment(Pos.CENTER_RIGHT);
		VBox.setMargin(dropArea,new Insets(0,0,-20,0));
	}
	
	private void setNameArea(){
		name = new Label(atr.getName());
		name.setFont(new Font("Arial",30));
		associatedSchool = new Label("Associated School:");
		associatedSchool.setFont(new Font("Black",15));
		nameArea = new VBox(name,associatedSchool);
		nameArea.setAlignment(Pos.CENTER);
	}
	
	private void setButtonArea(){
		int buttonAreaSpacing = 50;
		setAddBut();
		setRemBut();
		setSimAuthBut();
		buttonArea = new HBox(buttonAreaSpacing,ui.hasCand(atr) ? remBut:addBut,simAuthBut);
		buttonArea.setAlignment(Pos.CENTER);
	}
	
	private int sizeOfAddRemBut = 200;
	private Font butTextFont = new Font("Black",12);
	private String addButText = "Add to candidate list";
	private String remButText = "Remove from candidate list";
	
	private void setAddBut(){
		addBut = new Button(addButText);
		UIElementFixer.fixElementWidth(addBut,sizeOfAddRemBut);
		addBut.setFont(butTextFont);
		addBut.setOnAction((ActionEvent ae) -> {
			ui.addCand(atr);
		});
	}

	public void refresh(){
		buttonArea.getChildren().set(0,ui.hasCand(atr) ? remBut : addBut);
	}
	
	private void setRemBut(){
		remBut = new Button(remButText);
		UIElementFixer.fixElementWidth(remBut,sizeOfAddRemBut);
		remBut.setFont(butTextFont);
		remBut.setOnAction((ActionEvent ae) -> {
			ui.remCand(atr);
		});
	}

	private void setSimAuthBut(){
		simAuthBut = new Button("Find similar authors");
		simAuthBut.setFont(butTextFont);
		simAuthBut.setOnAction((ActionEvent ae) -> {
			SimilarAuthorsPage page = new SimilarAuthorsPage(atr,ui);
			ui.displayNewWindow(page.getScene(),"Authors with similar Profile to"+atr.getName());
		});
	}
	
	private void setAuthorInformationPane() {
		setExpArea();
		setBlgrphyArea();
		authorInformation = new VBox(20,expArea,blgrphyArea);
		authorInformationPane = new ScrollPane(authorInformation);
		authorInformationPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
	}
	
	private void setExpArea() {
		setExpLabel();
		setExpTable();
		expArea = new VBox(10,experience,expTable);
	}

	private void setExpTable() {
		expTable = new TableView<Experience>();
		expTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		setExpTableColumns();
		UIElementFixer.fixElementWidth(expTable,400);
		UIElementFixer.fixElementHeight(expTable, 300);
	}

	private void setExpTableColumns() {
		TableColumnAdder<Experience> tca = new TableColumnAdder<>(expTable);
		tca.addStringColumnToTable("Committee", "committee");
		tca.addStringColumnToTable("Role", "role");
		tca.addIntegerColumnToTable("Year", "year");
	}

	private void setExpLabel() {
		experience = new Label("Experience");
		experience.setFont(new Font("Black",20));
	}
	
	private void setBlgrphyArea() {
		setBlgrphyLabel();
		setBlgrphyTable();
		blgrphyArea = new VBox(10,blgrphy,blgrphyTable);
	}

	private void setBlgrphyLabel() {
		blgrphy = new Label("Bibliography");
		blgrphy.setFont(new Font("Black",20));
	}

	private void setBlgrphyTable(){
		SortedList<Publication> origList = new SortedList<>(ui.getAuthorPubs(atr));
		blgrphyTable = new TableView<Publication>(origList);
		origList.comparatorProperty().bind(blgrphyTable.comparatorProperty());
		blgrphyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		setBlgrphyTableColumns();
	}
	
	private void setBlgrphyTableColumns(){
		TableColumnAdder<Publication> tca = new TableColumnAdder<>(blgrphyTable);
		tca.addStringColumnToTable("Type","type");
		tca.addStringColumnToTable("Title","title");
		tca.addIntegerColumnToTable("Year","pbyear");
		tca.addStringColumnToTable("Publisher","publisher");
		tca.addStringColumnToTable("Book Title","booktitle");
		tca.addStringColumnToTable("Journal","journal");
		tca.addStringColumnToTable("Volume","volume");
		tca.addStringColumnToTable("Number","number");
		tca.addStringColumnToTable("School","school");
		tca.addStringColumnToTable("Pages","pages");
	}
	
	private void setCanvas(){
		canvas = new VBox(20,dropArea,nameArea,buttonArea,authorInformationPane);
		canvas.setAlignment(Pos.CENTER);
		canvas.setPadding(new Insets(20,40,20,40));
	}
	public Scene getScene(){
		return new Scene(canvas);
	}
	
	// getters for tests
	Button getAddBut(){
		return addBut;
	}
	
	Button getRemBut(){
		return remBut;
	}
	
	HBox getButtonArea(){
		return buttonArea;
	}
	
	Button getSimAuthBut(){
		return simAuthBut;
	}
	
	Label getName(){
		return name;
	}
	
	Label getDropHere(){
		return dropHere;
	}
	
}