package main.java.search.userinterface;

import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.java.search.model.Author;
import main.java.search.userinterface.helperclasses.UIElementFixer;
import main.java.serach.interfaces.CandidateListListener;
import main.java.serach.interfaces.UserInterface;

public class CandidateListPage implements CandidateListListener {

	public CandidateListPage(UserInterface ui) {
		this.ui = ui;
		ui.addListenerToCandList(this);
		setPageHead();
		setRemBut();
		setPaginationControl();
		setCanvas();
	}

	private UserInterface ui;
	private static int itemNbrPerPage = 15;
	private Label pageHead = new Label("Candidate List");
	private Button remBut = new Button("Remove");
	private HBox buttonArea = new HBox(remBut);
	private Pagination candListPagination = new Pagination();
	private VBox canvas = new VBox(10, pageHead, buttonArea, candListPagination);
	private ArrayList<Author> selectedAuthors = new ArrayList<>();

	private int getPageCount() {
		if (ui.getCandListSize() == 0) {
			return 1;
		} else {
			int quotient = (int) ui.getCandListSize() / itemNbrPerPage;
			int remainder = ui.getCandListSize() % itemNbrPerPage;
			return remainder == 0 ? quotient : quotient + 1;
		}
	}

	private void setPageHead() {
		pageHead.setFont(new Font("Arial", 40));
	}

	private void setRemBut() {
		remBut.setFont(new Font("Black", 12));
		remBut.setOnAction((ActionEvent ae) -> {
			ui.remCand(selectedAuthors);
			selectedAuthors.clear();
		});
	}

	private void setPaginationControl() {
		candListPagination.setPageCount(getPageCount());
		candListPagination.setPageFactory((Integer pageIndex) -> createPage(pageIndex));
	}

	private VBox createPage(int pageIndex) {
		VBox pageContent = new VBox();
		int itemIndex = pageIndex * itemNbrPerPage;
		selectedAuthors.clear();
		for (int i = itemIndex; i < itemIndex + itemNbrPerPage && i < ui.getCandListSize(); i++) {
			Author atr = ui.getCand(i);
			CheckBox checkBox = new CheckBox();
			setCheckBox(checkBox, atr);
			Hyperlink hyperLink = new Hyperlink(atr.getName());
			setHyperLink(hyperLink, atr);
			HBox hbox = new HBox(5, checkBox, hyperLink);
			hbox.setAlignment(Pos.CENTER_LEFT);
			pageContent.getChildren().add(hbox);
		}
		UIElementFixer.fixElementHeight(pageContent, 500);
		UIElementFixer.fixElementWidth(pageContent, 500);
		return pageContent;
	}

	public void refresh() {
		int cpi = candListPagination.getCurrentPageIndex();
		setPaginationControl();
		candListPagination.setCurrentPageIndex(
				cpi < candListPagination.getPageCount() ? cpi : candListPagination.getPageCount() - 1);
	}

	private void setCheckBox(CheckBox cb, Author a) {
		cb.setAllowIndeterminate(false);
		cb.selectedProperty()
				.addListener((ObservableValue<? extends Boolean> ov, Boolean old_value, Boolean new_value) -> {
					if (new_value) {
						selectedAuthors.add(a);
					} else {
						selectedAuthors.remove(a);
					}
				});

		// Visual Effect
		DropShadow ds = new DropShadow();
		ds.setColor(Color.GRAY);
		cb.setOnMouseEntered((MouseEvent e) -> {
			cb.setEffect(ds);
		});

		cb.setOnMouseExited((MouseEvent e) -> {
			cb.setEffect(null);
		});
	}

	private void setHyperLink(Hyperlink hl, Author a) {
		hl.setOnAction((ActionEvent e) -> {
			AuthorProfilePage page = new AuthorProfilePage(a, ui);
			ui.displayNewWindow(page.getScene());
		});
		hl.setFont(new Font("Arial", 20));

		// Visual Effect
		DropShadow ds = new DropShadow();
		ds.setColor(Color.GOLD);
		hl.setOnMouseEntered((MouseEvent e) -> {
			hl.setEffect(ds);
		});

		hl.setOnMouseExited((MouseEvent e) -> {
			hl.setEffect(null);
		});
	}

	private void setCanvas() {
		canvas.setAlignment(Pos.CENTER);
		canvas.setPadding(new Insets(20, 40, 20, 40));
	}

	public Scene getScene() {
		return new Scene(canvas);
	}

	Button getRemCandBut() {
		return remBut;
	}

	public static int getItemNbrPerPage() {
		return itemNbrPerPage;
	}

	Pagination getPagination() {
		return candListPagination;
	}

	ArrayList<Author> getSelectedAuthors() {
		return selectedAuthors;
	}
}
