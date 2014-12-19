package javafunctiontester;

import java.io.IOException;

import javafunctiontester.gui.main.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Program extends Application {
	
	public static void main(String[] args) {
		runProgramGUI(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(MainWindowController.class.getResource("MainWindow.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
	        Scene scene = new Scene(page);
	        
	        MainWindowController controller = loader.getController();
	        controller.setParent(page);
	        
	        primaryStage.setScene(scene);
	        primaryStage.setTitle("Java Function Tester v1.0");
	        primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void runProgramGUI(String[] args) {
		launch(args);
	}
}
