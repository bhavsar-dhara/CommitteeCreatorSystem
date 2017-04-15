package userInterface.classes;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.classes.Author;
import main.classes.Publication;
import java.util.ArrayList;
import main.interfaces.UserInterface;
import userInterface.classes.CandidateListPage;

public class CandidateListPageTest {

	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Test
	public void testCheckBoxesInitialization(){
		UITesting uiForTest = new UIForTest();
		uiForTest.setTestData();
		CandidateListPage page = new CandidateListPage(uiForTest);
		VBox vbox = (VBox) page.getPagination().getPageFactory().call(0);
		for (int i=0; i<CandidateListPage.getItemNbrPerPage();i++){
			assertFalse("The check boxes should be deselected when initialized",
					((CheckBox) ((HBox) vbox.getChildren().get(i)).getChildren().get(0)).isSelected());
			assertFalse("The check boxes should be two-state checkboxes",
					((CheckBox) ((HBox) vbox.getChildren().get(i)).getChildren().get(0)).isAllowIndeterminate());
		}
	}
	
	@Test
	public void testCheckBox(){
		UITesting uiForTest = new UIForTest();
		uiForTest.setTestData();
		CandidateListPage page = new CandidateListPage(uiForTest);
		VBox vbox = (VBox) page.getPagination().getPageFactory().call(0);
		CheckBox cb = (CheckBox) ((HBox) vbox.getChildren().get(0)).getChildren().get(0);
		cb.fire();
		assertEquals("After a check box is selected, its corresponding author should be selected.",
				page.getSelectedAuthors().size(),1);
		cb.fire();
		assertEquals("After a check box is deselected, its corresponding author should be deselected.",
				page.getSelectedAuthors().size(),0);
	}
	
	@Test
	public void testPaginationRefresh_LastPageRemoved(){
		UITesting uiForTest = new UIForTest();
		uiForTest.setTestData();
		CandidateListPage page = new CandidateListPage(uiForTest);
		int originalPageCount = page.getPagination().getPageCount();
		VBox vbox = (VBox) page.getPagination().getPageFactory().call(page.getPagination().getPageCount()-1);
		page.getPagination().setCurrentPageIndex(originalPageCount-1);
		for (int i=0; i<CandidateListPage.getItemNbrPerPage();i++){
			CheckBox cb = (CheckBox) ((HBox) vbox.getChildren().get(i)).getChildren().get(0);
			cb.fire();
		}
		
		page.getRemCandBut().fire();
		assertEquals(page.getPagination().getPageCount(),originalPageCount-1);
		assertEquals(page.getPagination().getCurrentPageIndex(),originalPageCount-2);
	}
	
	@Test
	public void testPaginationRefresh_MidPageRemoved(){
		UITesting uiForTest = new UIForTest();
		uiForTest.setTestData();
		CandidateListPage page = new CandidateListPage(uiForTest);
		int originalPageCount = page.getPagination().getPageCount();
		VBox vbox = (VBox) page.getPagination().getPageFactory().call((int) originalPageCount/2);
		page.getPagination().setCurrentPageIndex((int) originalPageCount/2);
		for (int i=0; i<CandidateListPage.getItemNbrPerPage();i++){
			CheckBox cb = (CheckBox) ((HBox) vbox.getChildren().get(i)).getChildren().get(0);
			cb.fire();
		}
		page.getRemCandBut().fire();
		assertEquals(page.getPagination().getPageCount(),originalPageCount-1);
		assertEquals(page.getPagination().getCurrentPageIndex(),(int) originalPageCount/2);
	}
	
	@Test
	public void testPaginationRefresh_PartOfPageRemoved(){
		UITesting uiForTest = new UIForTest();
		uiForTest.setTestData();
		CandidateListPage page = new CandidateListPage(uiForTest);
		int originalPageCount = page.getPagination().getPageCount();
		VBox vbox = (VBox) page.getPagination().getPageFactory().call((int) originalPageCount/2);
		page.getPagination().setCurrentPageIndex((int) originalPageCount/2);
		for (int i=0; i<(int) CandidateListPage.getItemNbrPerPage()/2;i++){
			CheckBox cb = (CheckBox) ((HBox) vbox.getChildren().get(i)).getChildren().get(0);
			cb.fire();
		}
		page.getRemCandBut().fire();
		assertEquals(page.getPagination().getPageCount(),originalPageCount);
		assertEquals(page.getPagination().getCurrentPageIndex(),(int) originalPageCount/2);
	}
	
	@Test
	public void testSelectedAuthorsRefresh(){
		UITesting uiForTest = new UIForTest();
		uiForTest.setTestData();
		CandidateListPage page = new CandidateListPage(uiForTest);
		VBox vbox = (VBox) page.getPagination().getPageFactory().call(0);
		for (int i=0; i<(int) CandidateListPage.getItemNbrPerPage()/2;i++){
			CheckBox cb = (CheckBox) ((HBox) vbox.getChildren().get(i)).getChildren().get(0);
			cb.fire();
		}
		vbox = (VBox) page.getPagination().getPageFactory().call(1);
		assertEquals(page.getSelectedAuthors().size(),0);
	}
	
	@Test
	public void testRemCanBut() {
		UITesting uiForTest = new UIForTest();
		uiForTest.setTestData();
		CandidateListPage page = new CandidateListPage(uiForTest);
		VBox vbox = (VBox) page.getPagination().getPageFactory().call(0);
		for (int i=0; i<(int) CandidateListPage.getItemNbrPerPage()/2;i++){
			CheckBox cb = (CheckBox) ((HBox) vbox.getChildren().get(i)).getChildren().get(0);
			cb.fire();
		}
		ArrayList<Author> removedAuthors = page.getSelectedAuthors();
		page.getRemCandBut().fire();
		for (int i=0;i<removedAuthors.size();i++){
			assertFalse(uiForTest.hasCand(removedAuthors.get(i)));
		}
	}
}
