package main.java.search.userinterface;

import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.java.search.model.Author;
import main.java.search.model.Publication;
import main.java.search.userinterface.helperclasses.Experience;
import main.java.search.userinterface.helperclasses.TableColumnAdder;
import main.java.search.userinterface.helperclasses.UIElementFixer;
import main.java.serach.interfaces.CandidateListListener;
import main.java.serach.interfaces.UserInterface;

public class AuthorProfilePage implements CandidateListListener {

	public AuthorProfilePage(Author a, UserInterface ui) {
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
		dropLabel = new Label("Drag the Author's name & Drop here to add him to candidate list");
		dropLabel.setWrapText(true);
		dropArea = new HBox(dropLabel);
		UIElementFixer.fixElementWidth(dropArea, 100);
		UIElementFixer.fixElementHeight(dropArea, 100);
		VBox.setMargin(dropArea, new Insets(0, 0, -20, 0));
	}

	private void setNameArea() {
		name = new Label(atr.getName());
		name.setFont(new Font("Arial", 30));
		associatedSchool = new Label("Associated School:");
		associatedSchool.setFont(new Font("Black", 15));
		nameArea = new VBox(name, associatedSchool);
		nameArea.setAlignment(Pos.CENTER);
		downArrow = new Image("Down Arrow.gif", 100, 100, true, true);
	}

	private void setButtonArea() {
		int buttonAreaSpacing = 50;
		setAddBut();
		setRemBut();
		setSimAuthBut();
		buttonArea = new HBox(buttonAreaSpacing, ui.hasCand(atr) ? remBut : addBut, simAuthBut);
		buttonArea.setAlignment(Pos.CENTER);
	}

	private int sizeOfAddRemBut = 200;
	private Font butTextFont = new Font("Black", 12);
	private String addButText = "Add to candidate list";
	private String remButText = "Remove from candidate list";

	private void setAddBut() {
		addBut = new Button(addButText);
		UIElementFixer.fixElementWidth(addBut, sizeOfAddRemBut);
		addBut.setFont(butTextFont);
		addBut.setOnAction((ActionEvent ae) -> {
			ui.addCand(atr);
		});
	}

	public void refresh() {
		buttonArea.getChildren().set(0, ui.hasCand(atr) ? remBut : addBut);
	}

	private void setRemBut() {
		remBut = new Button(remButText);
		UIElementFixer.fixElementWidth(remBut, sizeOfAddRemBut);
		remBut.setFont(butTextFont);
		remBut.setOnAction((ActionEvent ae) -> {
			ui.remCand(atr);
		});
	}

	private void setSimAuthBut() {
		simAuthBut = new Button("Find similar authors");
		simAuthBut.setFont(butTextFont);
		simAuthBut.setOnAction((ActionEvent ae) -> {
			SimilarAuthorsPage page = new SimilarAuthorsPage(atr, ui);
			ui.displayNewWindow(page.getScene(), "Authors with similar Profile to" + atr.getName());
		});
	}

	private void setAuthorInformationPane() {
		setExpArea();
		setBlgrphyArea();
		authorInformation = new VBox(20, expArea, blgrphyArea);
		authorInformationPane = new ScrollPane(authorInformation);
		authorInformationPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
	}

	private void setExpArea() {
		setExpLabel();
		setExpTable();
		expArea = new VBox(10, experience, expTable);
	}

	private void setExpTable() {
		expTable = new TableView<Experience>();
		expTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		setExpTableColumns();
		UIElementFixer.fixElementWidth(expTable, 400);
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
		experience.setFont(new Font("Black", 20));
	}

	private void setBlgrphyArea() {
		setBlgrphyLabel();
		setBlgrphyTable();
		blgrphyArea = new VBox(10, blgrphy, blgrphyTable);
	}

	private void setBlgrphyLabel() {
		blgrphy = new Label("Bibliography");
		blgrphy.setFont(new Font("Black", 20));
	}

	private void setBlgrphyTable() {
		SortedList<Publication> origList = new SortedList<>(ui.getAuthorPubs(atr));
		blgrphyTable = new TableView<Publication>(origList);
		origList.comparatorProperty().bind(blgrphyTable.comparatorProperty());
		blgrphyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		setBlgrphyTableColumns();
	}

	private void setBlgrphyTableColumns() {
		TableColumnAdder<Publication> tca = new TableColumnAdder<>(blgrphyTable);
		tca.addStringColumnToTable("Type", "type");
		tca.addStringColumnToTable("Title", "title");
		tca.addIntegerColumnToTable("Year", "pbyear");
		tca.addStringColumnToTable("Publisher", "publisher");
		tca.addStringColumnToTable("Book Title", "booktitle");
		tca.addStringColumnToTable("Journal", "journal");
		tca.addStringColumnToTable("Volume", "volume");
		tca.addStringColumnToTable("Number", "number");
		tca.addStringColumnToTable("School", "school");
		tca.addStringColumnToTable("Pages", "pages");
	}

	private void setCanvas() {
		canvas = new VBox(20, dropArea, nameArea, buttonArea, authorInformationPane);
		canvas.setPadding(new Insets(20, 40, 20, 40));
		canvas.setAlignment(Pos.CENTER_RIGHT);
		canvas.setPrefSize(VBox.USE_COMPUTED_SIZE, VBox.USE_COMPUTED_SIZE);
	}

	// getters for tests
	public Scene getScene() {
		return new Scene(canvas);
	}

	Button getAddBut() {
		return addBut;
	}

	Button getRemBut() {
		return remBut;
	}

	// EFFECT: allows the user to drag the name and drop it over "drop here"
	// label
	// to add this author to candidate list
	private void setDragAndDropFeature() {
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
			if (e.getDragboard().hasString()) {
				e.acceptTransferModes(TransferMode.MOVE);
			}
			e.consume();
		});
	}

	private void setDragEnteredEvent() {
		dropLabel.setOnDragEntered((DragEvent e) -> {
			if (e.getDragboard().hasString()) {
				dropLabel.setText("");
				dropLabel.setGraphic(new ImageView(downArrow));
			}
			e.consume();
		});
	}

	private void setDragExitedEvent() {
		dropLabel.setOnDragExited((DragEvent e) -> {
			dropLabel.setText("Drag the Author's name & Drop here to add him to candidate list");
			dropLabel.setGraphic(null);
			e.consume();
		});
	}

	private void setDragDroppedEvent() {
		dropLabel.setOnDragDropped((DragEvent e) -> {
			Dragboard db = e.getDragboard();
			boolean success = false;
			if (db.hasString()) {
				ui.addCand(new Author(db.getString()));
				success = true;
			}
			e.setDropCompleted(success);
			e.consume();
		});
	}

	HBox getButtonArea() {
		return buttonArea;
	}

	Button getSimAuthBut() {
		return simAuthBut;
	}

	Label getName() {
		return name;
	}

	Label getdropLabel() {
		return dropLabel;
	}

}