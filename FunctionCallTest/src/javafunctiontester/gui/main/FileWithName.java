package javafunctiontester.gui.main;

import java.io.File;

import javafx.beans.property.SimpleStringProperty;

public class FileWithName {
	
	public SimpleStringProperty name;
	public File file;
	
	public FileWithName(File file) {
		this.file = file;
		name = new SimpleStringProperty(file.getName());
	}
}
