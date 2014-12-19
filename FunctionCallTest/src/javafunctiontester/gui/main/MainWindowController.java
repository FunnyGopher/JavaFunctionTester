package javafunctiontester.gui.main;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafunctiontester.AnswerKey;
import javafunctiontester.FileResult;
import javafunctiontester.Grader;
import javafunctiontester.Result;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;

public class MainWindowController implements Initializable {
	
	Parent root;
	
	@FXML
	ListView<File> javaFilesView = new ListView<>();
	ObservableList<File> javaFiles = FXCollections.observableArrayList();
	
	@FXML
	ListView<AnswerKey> answerKeysView = new ListView<>();
	ObservableList<AnswerKey> answerKeys = FXCollections.observableArrayList();
	
	@FXML
	ListView<FileResult> fileResultsView = new ListView<>();
	ObservableList<FileResult> fileResults = FXCollections.observableArrayList();
	
	@FXML
	ListView<Result> akResultsView = new ListView<>();
	ObservableList<Result> akResults = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initJavaFilesView();
		initAnswerKeysView();
		initFileResultsView();
		initAKResultsView();
	}
	
	@FXML public void openJavaFileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open a Java File");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Java File", "*.java"));
		List<File> files = fileChooser.showOpenMultipleDialog(root.getScene().getWindow());
		
		if(files.isEmpty()) {
			return;
		}
		
		for(File file : files) {
			addJavaFile(file);
		}
		
		updateView(javaFilesView, javaFiles);
	}
	
	@FXML public void openAnswerKeyChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open an Answer Key");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Answer Key", "*.xml"));
		List<File> files = fileChooser.showOpenMultipleDialog(root.getScene().getWindow());
		
		if(files.isEmpty()) {
			System.out.println("I'm empty! :D");
			return;
		}
		
		for(File file : files) {
			//if(AnswerKey.isValid(file)) {\
			AnswerKey aKey = AnswerKey.createFromFile(file);
			addAnswerKey(aKey);
				// Convert to answerkey object
				// Add to anskwerKeys
			//}
		}
		
		updateView(answerKeysView, answerKeys);
	}
	
	private void initJavaFilesView() {
		javaFilesView.setCellFactory(new Callback<ListView<File>, ListCell<File>>() {
			@Override
			public ListCell<File> call(ListView<File> param) {
				ListCell<File> cell = new ListCell<File>() {
					@Override
                    protected void updateItem(File t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getName().substring(0, t.getName().length() - 5));
                        } else {
                        	setText(null);
                        }
                    }
				};
				return cell;
			}
		});
	}
	
	private void initAnswerKeysView() {		
		answerKeysView.setCellFactory(new Callback<ListView<AnswerKey>, ListCell<AnswerKey>>() {
			@Override
			public ListCell<AnswerKey> call(ListView<AnswerKey> param) {
				ListCell<AnswerKey> cell = new ListCell<AnswerKey>() {
					@Override
                    protected void updateItem(AnswerKey t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getName());
                        } else {
                        	setText(null);
                        }
                    }
				};
				return cell;
			}
		});
	}
	
	private void initFileResultsView() {
		fileResultsView.setCellFactory(new Callback<ListView<FileResult>, ListCell<FileResult>>() {
			@Override
			public ListCell<FileResult> call(ListView<FileResult> param) {
				ListCell<FileResult> cell = new ListCell<FileResult>() {
					@Override
                    protected void updateItem(FileResult t, boolean bln) {
                        super.updateItem(t, bln);
                        Circle circ = new Circle(5);
                        if (t != null) {
                        	Color color;
                        	if(t.getPass()) {
                        		color = Color.GREEN;
                        	} else {
                        		color = Color.RED;
                        	}
                        	circ.setFill(color);
                            setGraphic(circ);
                            setText(t.getFile().getName().substring(0, t.getFile().getName().length() - 5));
                        } else {
                        	setText(null);
                        }
                    }
				};
				
				cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						populateAKResults(event);
					}
				});
				return cell;
			}
		});
	}
	
	private void initAKResultsView() {
		akResultsView.setCellFactory(new Callback<ListView<Result>, ListCell<Result>>() {
			@Override
			public ListCell<Result> call(ListView<Result> param) {
				ListCell<Result> cell = new ListCell<Result>() {
					@Override
                    protected void updateItem(Result t, boolean bln) {
                        super.updateItem(t, bln);
                        Circle circ = new Circle(5);
                        if (t != null) {
                        	Color color;
                        	if(t.didPass()) {
                        		color = Color.GREEN;
                        	} else {
                        		color = Color.RED;
                        	}
                        	circ.setFill(color);
                            setGraphic(circ);
                            setText(t.getAnswerKeyName());
                        } else {
                        	setText(null);
                        }
                    }
				};
				return cell;
			}
		});
	}

	@FXML private void populateAKResults(MouseEvent event) {
		FileResult fileResult = (FileResult) getSelection(fileResultsView).getSelectedItem();
		
		akResults.clear();
		for(Result result : fileResult.getResults()) {
			akResults.add(result);
		}
		
		updateView(akResultsView, akResults);
	}
	
	private void addJavaFile(File file) {
		if(!javaFiles.contains(file))
			javaFiles.add(file);
	}
	
	@FXML private void removeJavaFile() {
		javaFiles.remove(getSelection(javaFilesView).getSelectedItem());
	}	
	
	private void addAnswerKey(AnswerKey answerKey) {
		if(!answerKeys.contains(answerKey))
			answerKeys.add(answerKey);
	}
	
	@FXML private void removeAnswerKey() {
		answerKeys.remove(getSelection(answerKeysView).getSelectedItem());
	}
	
	public void setParent(Parent parent) {
		root = parent;
	}
	
	private SelectionModel<?> getSelection(ListView<?> view) {
		return view.getSelectionModel();
	}
	
	private void updateView(ListView<?> view, ObservableList list) {
		view.setItems(list);
	}
	
	@FXML private void run() {
		Grader grader = new Grader(javaFiles, answerKeys);
		grader.grade();
		List<FileResult> results = grader.getFileResults();
		
		fileResults.clear();
		akResults.clear();
		for(FileResult result : results) {
			fileResults.add(result);
		}
		
		updateView(fileResultsView, fileResults);
	}
}
