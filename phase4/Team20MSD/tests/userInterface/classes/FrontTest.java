package userInterface.classes;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class FrontTest {

	@Test
	public void testClearBut() throws IOException {
		Front f = new Front();
		f.getClearBut().fire();
		assertNull(f.GetKeyWord().getText());
	}

	@Test
	public void testNormalSearch(){
		Front f = new Front();
		f.getSearchBut().fire();
	}
	
	@Test
	public void testInvalidSearch(){
		Front f = new Front();
		f.getSearchBut().fire();
		assertNull(f.getEmpTable().getItems());
	}
}
