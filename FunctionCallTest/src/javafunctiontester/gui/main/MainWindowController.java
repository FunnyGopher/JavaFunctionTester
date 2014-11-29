package javafunctiontester.gui.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainWindowController {
	
	Parent root;
	
	List<File> javaFiles = new ArrayList<File>(); 
	ObservableList<SimpleStringProperty> javaNames = FXCollections.observableArrayList();
	ListView<SimpleStringProperty> javaFilesView = new ListView<SimpleStringProperty>();
	
	@FXML
	public void openAnswerKeyChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Answer Keys", "*.anskey"));
		List<File> files = fileChooser.showOpenMultipleDialog(root.getScene().getWindow());
	}
	
	@FXML
	public void openJavaFileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Java File", "*.java"));
		List<File> files = fileChooser.showOpenMultipleDialog(root.getScene().getWindow());
		
		if(files.isEmpty()) {
			return;
		}
		
		javaNames.clear();
		for(File file : files) {
			if(!javaFiles.contains(file)) {
				javaFiles.add(file);
				javaNames.add(new SimpleStringProperty(file.getName()));
			}
		}
		
		javaFilesView.setItems(javaNames);
	}
	
	private void addJavaFile(File file) {
		if(!javaFiles.contains(file)) {
			javaFiles.add(file);
			javaNames.add(new SimpleStringProperty(file.getName()));
		}
		
		javaFilesView.setItems(javaNames);
	}
	
	
	
	public void setParent(Parent parent) {
		root = parent;
	}
}
