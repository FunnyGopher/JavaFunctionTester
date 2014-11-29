package test;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Program {

	public static void main(String[] args) {
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
				"Test",
				"add",
				new Data<Integer>(int.class, expected),
				new Data<?>[] {
					new Data<Integer>(int.class, param1),
					new Data<Integer>(int.class, param2)
				}
				
		);
		
		answerKeys.add(answerKey);
		/*
		for(Class<?> classType : answerKey.getArgumentClassTypes())
			System.out.println(classType);
		*/
		
		File javaFile = new File(filepath);
		List<File> javaFiles = new ArrayList<File>();
		javaFiles.add(javaFile);
		
		Grader grader = new Grader(javaFiles, answerKeys);
		grader.grade();
		List<Boolean> results = grader.getResults();
		
		for(Boolean bool : results)
			System.out.println("Result: " + bool);
		
		
		scanner.nextLine();
	}
	
	public static Class<?> loadClass(File file) {
		Class<?> clazz = null;
		try {
			URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{file.getParentFile().toURI().toURL()});
			clazz = Class.forName("MyProgram", true, classLoader);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return clazz;
	}
}
