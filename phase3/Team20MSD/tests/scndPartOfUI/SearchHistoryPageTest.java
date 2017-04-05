package scndPartOfUI;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Hyperlink;
import main.interfaces.UserInterface;
import main.classes.QueryEngine;
import main.classes.Author;
import java.util.ArrayList;

public class SearchHistoryPageTest {

	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Test
	public void testClearSearchBut() {
		UITesting uiForTest = new UIForTest();
		uiForTest.setTestData();
		SearchHistoryPage page = new SearchHistoryPage(uiForTest);
		page.getClearBut().fire();
		assertEquals(uiForTest.getSearchHistorySize(),0);
	}
	
	@Test
	public void testSearchQueryHyperLink(){
		UITesting uiForTest = new UIForTest();
		uiForTest.setTestData();
		SearchHistoryPage page = new SearchHistoryPage(uiForTest);
		VBox vbox = (VBox) page.getPagination().getPageFactory().call(0);
		((Hyperlink) vbox.getChildren().get(0)).fire();
		for (int i=0;i<uiForTest.getSearchResultSize();i++){
			assertEquals(uiForTest.getSearchResult(i),AuthorProfilePageTest.rFTSimAuth.get(i));
		}
	}
}
