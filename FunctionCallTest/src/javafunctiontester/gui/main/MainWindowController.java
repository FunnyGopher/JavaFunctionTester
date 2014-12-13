package javafunctiontester.gui.main;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;

public class MainWindowController implements Initializable {
	
	Parent root;
	
	List<File> javaFiles = new ArrayList<File>();
	
	@FXML
	ListView<File> javaFilesView = new ListView<>();
	ObservableList<File> observableJavaFiles = FXCollections.observableArrayList(javaFiles);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		javaFilesView.setCellFactory(new Callback<ListView<File>, ListCell<File>>() {

			@Override
			public ListCell<File> call(ListView<File> param) {
				ListCell<File> cell = new ListCell<File>() {
					
					@Override
                    protected void updateItem(File t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getName().substring(0, t.getName().length() - 5));
                        }
                    }
				};
				
				return cell;
			}
		});
	}
	
	@FXML
	public void openJavaFileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open J File");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Java File", "*.java"));
		List<File> files = fileChooser.showOpenMultipleDialog(root.getScene().getWindow());
		
		if(files.isEmpty()) {
			return;
		}
		
		for(File file : files) {
			addJavaFile(file);
		}
	}
	
	private void addJavaFile(File file) {
		if(!observableJavaFiles.contains(file)) {
			observableJavaFiles.add(file);
		}
		
		javaFilesView.setItems(observableJavaFiles);
	}
	
	@FXML
	private void removeJavaFile() {
		observableJavaFiles.remove(javaFilesView.getSelectionModel().getSelectedIndex());
	}
	
	private File getSelectedJavaFile() {
		return javaFilesView.getSelectionModel().getSelectedItem();
	}
	
	@FXML
	public void openAnswerKeyChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Answer Key File");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Answer Keys", "*.anskey"));
		List<File> files = fileChooser.showOpenMultipleDialog(root.getScene().getWindow());
	}
	
	public void setParent(Parent parent) {
		root = parent;
	}
	
	private void updateJavaFilesView() {
	    javaFilesView.setItems(observableJavaFiles);
	}
}
