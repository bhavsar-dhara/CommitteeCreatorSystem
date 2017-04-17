package userInterface.classes;
import main.classes.Author;
import main.classes.Publication;
import main.interfaces.*;
import userInterface.helperClasses.TableColumnAdder;
import userInterface.helperClasses.UIElementFixer;
import userInterface.interfaces.CandidateListListener;

import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.input.DragEvent;

public class AuthorProfilePage implements CandidateListListener {
	
	public AuthorProfilePage(Author a, UserInterface ui){
		atr = a;
		this.ui = ui;
		ui.addListenerToCandList(this);
		setDropArea();
		setNameArea();
		setButtonArea();
		setAuthorInformationPane();
		setDragAndDropFeature();
		setCanvas();
	}

	private Author atr;
	private UserInterface ui;
	
	private Label dropLabel;
	private HBox dropArea;
	private Image downArrow;
	
	private Label name;
	private Label associatedSchool;
	private VBox nameArea;
	
	private Button addBut;
	private Button remBut;
	private Button simAuthBut;
	private Button favAuthBut;
	private HBox buttonArea;
	
	private Label experience;
	private TableView<Author> expTable;
	private VBox expArea;
	
	private Label blgrphy;
	private TableView<Publication> blgrphyTable;
	private VBox blgrphyArea;
	
	private VBox authorInformation;
	private ScrollPane authorInformationPane;

	private VBox canvas;
	
	/* ----------------------------------------------------------------------------------------------------*/
	
	private void setDropArea() {
		dropLabel = new Label("Drag name here");
//		dropLabel.setWrapText(true);
		dropArea = new HBox(dropLabel);
		dropArea.setAlignment(Pos.CENTER_RIGHT);
//		UIElementFixer.fixElementWidth(dropArea, 75);
//		UIElementFixer.fixElementHeight(dropArea,50);
		VBox.setMargin(dropArea,new Insets(0,0,-20,0));
		downArrow = new Image("/Down Arrow.gif",10,10,true,true);
	}
	
	private void setNameArea(){
		name = new Label(atr.getName());
		name.setFont(new Font("Arial",30));
		associatedSchool = new Label("Associated School:"+"   " +ui.getAuthorSchool(atr));
		associatedSchool.setFont(new Font("Black",15));
		nameArea = new VBox(name,associatedSchool);
		nameArea.setAlignment(Pos.CENTER);
	}
	
	/* ----------------------------------------------------------------------------------------------------*/
	
	// EFFECT: allows the user to drag the name and drop it over "drop here" label
	//         to add this author to candidate list
	private void setDragAndDropFeature(){
		setDragDetectedEvent();
		setDragOverEvent();
		setDragEnteredEvent();
		setDragExitedEvent();
		setDragDroppedEvent();
	}

	private void setDragDetectedEvent() {
		name.setOnDragDetected((MouseEvent e) -> {
			Dragboard db = name.startDragAndDrop(TransferMode.ANY);
			ClipboardContent content = new ClipboardContent();
			content.putString(name.getText());
			db.setContent(content);
			
			e.consume();
		});
	}

	private void setDragOverEvent() {
		dropLabel.setOnDragOver((DragEvent e) -> {
			if (e.getDragboard().hasString()){
				e.acceptTransferModes(TransferMode.MOVE);
			}
			e.consume();
		});
	}

	private void setDragEnteredEvent() {
		dropLabel.setOnDragEntered((DragEvent e) -> {
			if (e.getDragboard().hasString()){
				dropLabel.setText("Add to Candidate List");
				dropLabel.setTextFill(Color.RED);
				dropLabel.setFont(Font.font(null, FontWeight.BOLD, 20));
				dropLabel.setGraphic(new ImageView(downArrow));
			}
			e.consume();
		});
	}

	private void setDragExitedEvent() {
		dropLabel.setOnDragExited((DragEvent e) -> {
			dropLabel.setText("Drag name here");
			dropLabel.setTextFill(Color.BLACK);
			dropLabel.setFont(Font.font(null, FontWeight.NORMAL, 10));
			dropLabel.setGraphic(null);
			e.consume();
		});
	}

	private void setDragDroppedEvent() {
		dropLabel.setOnDragDropped((DragEvent e) -> {
			Dragboard db = e.getDragboard();
			boolean success = false;
			if (db.hasString()){
				ui.addCand(new Author(db.getString()));
				success = true;
			}
			e.setDropCompleted(success);
			e.consume();
		});
	}
	
	/* ----------------------------------------------------------------------------------------------------*/
	
	private void setButtonArea(){
		int buttonAreaSpacing = 50;
		setAddBut();
		setRemBut();
		setSimAuthBut();
		setFavAuthBut();
		buttonArea = new HBox(buttonAreaSpacing,ui.hasCand(atr) ? remBut:addBut,simAuthBut,favAuthBut);
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
	
	private void setFavAuthBut() {
		favAuthBut = new Button("My Favorite Author");
		favAuthBut.setOnAction((ActionEvent e) -> {
			ui.displayCandidateList();
		});
	}
	
	/* ----------------------------------------------------------------------------------------------------*/
	
	private void setAuthorInformationPane() {
		setExpArea();
		setBlgrphyArea();
		authorInformation = new VBox(20,blgrphyArea,expArea);
		authorInformationPane = new ScrollPane(authorInformation);
		authorInformationPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
	}
	
	/* ----------------------------------------------------------------------------------------------------*/
	
	private void setExpArea() {
		setExpLabel();
		setExpTable();
		expArea = new VBox(10,experience,expTable);
	}

	private void setExpTable() {
		expTable = new TableView<Author>();
		expTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		SortedList<Author> sortedList = new SortedList<>(ui.getAuthorExperience(atr)); 
		expTable.setItems(sortedList);
		sortedList.comparatorProperty().bind(expTable.comparatorProperty());
		setExpTableColumns();
		UIElementFixer.fixElementHeight(expTable, 200);
	}

	private void setExpTableColumns() {
		TableColumnAdder<Author> tca = new TableColumnAdder<>(expTable);
		tca.addStringColumnToTable("Committee", "committee");
		tca.addStringColumnToTable("Role", "role");
		tca.addIntegerColumnToTable("Year", "checkYear");
	}

	private void setExpLabel() {
		experience = new Label("Experience");
		experience.setFont(new Font("Black",20));
	}
	
	/* ----------------------------------------------------------------------------------------------------*/
	
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
		UIElementFixer.fixElementHeight(blgrphyTable, 300);
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
	
	/* ----------------------------------------------------------------------------------------------------*/
	
	private void setCanvas(){
		canvas = new VBox(20,dropArea,nameArea,buttonArea,authorInformationPane);
		canvas.setPadding(new Insets(20,40,20,40));
	}
	public Scene getScene(){
		return new Scene(canvas);
	}
	
	/* ----------------------------------------------------------------------------------------------------*/
	
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
	
	Label getdropLabel(){
		return dropLabel;
	}
	
}