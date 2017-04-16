package scndPartOfUI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;

import javafx.collections.FXCollections;
import main.classes.Author;

public class AuthorProfilePageTest {

	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Test
	public void testAddButInitialization(){
		Author reikoForTest = new Author("Reiko Heckle",951321);
		UITesting uiForTest = new UIForTest();
		AuthorProfilePage page = new AuthorProfilePage(reikoForTest,uiForTest);
		assertEquals("page should display addBut when Initialized",page.getButtonArea().getChildren().get(0),page.getAddBut());
	}
	
	@Test
	public void testAddBut() {
		Author reikoForTest = new Author("Reiko Heckle",951321);
		UITesting uiForTest = new UIForTest();
		AuthorProfilePage page = new AuthorProfilePage(reikoForTest,uiForTest);
		page.getAddBut().fire();
		assertTrue("Reiko should be added to the candidate list",uiForTest.hasCand(reikoForTest));
		assertEquals("page should now display remBut",page.getButtonArea().getChildren().get(0),page.getRemBut());
	}

	@Test
	public void testRemButInitialization(){
		Author reikoForTest = new Author("Reiko Heckle",951321);
		UITesting uiForTest = new UIForTest();
		uiForTest.addCand(reikoForTest);
		AuthorProfilePage page = new AuthorProfilePage(reikoForTest,uiForTest);
		assertEquals("page should display remBut when Initialized",page.getButtonArea().getChildren().get(0),page.getRemBut());
	}
	
	@Test
	public void testRemBut(){
		Author reikoForTest = new Author("Reiko Heckle",951321);
		UITesting uiForTest = new UIForTest();
		uiForTest.addCand(reikoForTest);
		AuthorProfilePage page = new AuthorProfilePage(reikoForTest,uiForTest);
		page.getRemBut().fire();
		assertFalse("Reiko should be removed from the candidate list",uiForTest.hasCand(reikoForTest));
		assertEquals("page should now display addBut",page.getButtonArea().getChildren().get(0),page.getAddBut());
	}
	
	public static ArrayList<Author> rFTSimAuth = new ArrayList<Author>(FXCollections.observableArrayList(
				new Author("Yadaiah N.",7891),
				new Author("Vikrant Dabas",285290),
				new Author("R. Iacono ",47994802)));
	
	@Test
	public void testSimAuthBut(){
		Author reikoForTest = new Author("Reiko Heckle",951321);
		UITesting uiForTest = new UIForTest();
		AuthorProfilePage page = new AuthorProfilePage(reikoForTest,uiForTest);
		page.getSimAuthBut().fire();
		for(int i=0;i<uiForTest.getSearchResultSize();i++){
			assertEquals("The result of search for reikoForTest's similar authors is incorrect",
					uiForTest.getSearchResult(i),rFTSimAuth.get(i));
		}
	}
	
	
	
}
