package userInterface.helperClasses;

import javafx.scene.layout.Region;

public class UIElementFixer {
	
	public static void fixElementWidth(Region r,double width) {
		r.setMinWidth(width);
		r.setPrefWidth(width);
		r.setMaxWidth(width);
	}
	
	public static void fixElementHeight(Region r,double height) {
		r.setMinHeight(height);
		r.setPrefHeight(height);
		r.setMaxHeight(height);
	}
	
}
