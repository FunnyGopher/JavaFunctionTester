package javafunctiontester;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	
	public static void runProgram(String[] args) {
		List<AnswerKey> answerKeys = new ArrayList<AnswerKey>();
		
		String filepath;
		int param1, param2, expected;
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Calculator class file path >> ");
		filepath = scanner.nextLine();
		System.out.println();
		
		System.out.println("Testing the 'add' function");
		System.out.println("'add' takes two parameters");
		
		System.out.print("Parameter 1 >> ");
		param1 = scanner.nextInt();
		
		System.out.print("Parameter 2 >> ");
		param2 = scanner.nextInt();
		
		System.out.print("Expected Outcome >> ");
		expected = scanner.nextInt();
		
		AnswerKey answerKey = new AnswerKey(
				"Add Function Test",
				"add",
				new Data<Integer>(int.class, expected),
				new Data<?>[] {
					new Data<Integer>(int.class, param1),
					new Data<Integer>(int.class, param2)
				}
				
		);
		
		answerKeys.add(answerKey);
		
		File javaFile = new File(filepath);
		List<File> javaFiles = new ArrayList<File>();
		javaFiles.add(javaFile);
		
		Grader grader = new Grader(javaFiles, answerKeys);
		grader.grade();
		List<Result> results = grader.getResults();
		
		for(Result result : results)
			System.out.println("[ Pass: " + result.didPass() + ", Test Name: " + result.getAnswerKeyName() + ", Reason: " + result.getReason() + "]");
		
		
		scanner.nextLine();
	}
}
