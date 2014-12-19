package javafunctiontester;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class Grader {
	
	private List<File> filesToGrade;
	private List<AnswerKey> answerKeys;
	private List<FileResult> fileResults;
	
	public Grader(List<File> filesToGrade, List<AnswerKey> answerKeys) {
		this.filesToGrade = filesToGrade;
		this.answerKeys = answerKeys;
		fileResults = new ArrayList<FileResult>();
	}
	
	public void grade() {		
		for(File file : filesToGrade) {
			List<Result> results = new ArrayList<Result>();
			
			for(AnswerKey key : answerKeys) {
				Result result = new Result(false, key.getName());
				
				try {
					compileAFile(file);
					Class<?> clazz = loadClass(file);
					Object obj = clazz.newInstance();
					Method method = clazz.getDeclaredMethod(key.getMethodName(), key.getArgumentClassTypes());
					method.setAccessible(true);
					Object output = method.invoke(obj, key.getArguementValues());
					
					if(key.getExpectedOutput().getValue().equals(output)) {
						result.setPass(true);
					} else {
						result.setReason("The expected outcome did not match the actual result, Expected Outcome: " + key.getExpectedOutput().getValue() + ", Actual Result: " + output);
					}
					
				} catch (MalformedURLException | ClassNotFoundException e) {
					result.setPass(false);
					result.setReason(e.getMessage());
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					result.setPass(false);
					result.setReason(e.getMessage());
					e.printStackTrace();
				} catch (SecurityException e) {
					result.setPass(false);
					result.setReason(e.getMessage());
					e.printStackTrace();
				} catch (InstantiationException e) {
					result.setPass(false);
					result.setReason(e.getMessage());
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					result.setPass(false);
					result.setReason(e.getMessage());
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					result.setPass(false);
					result.setReason(e.getMessage());
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					result.setPass(false);
					result.setReason(e.getMessage());
					e.printStackTrace();
				}
				results.add(result);
			}
			fileResults.add(new FileResult(file, results));
		}
	}
	
	private Class<?> loadClass(File file) throws MalformedURLException, ClassNotFoundException {
		Class<?> clazz = null;
		URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{file.getParentFile().toURI().toURL()});
		String fileName = file.getName().substring(0, file.getName().length() - 5);
		clazz = Class.forName(fileName, true, classLoader);
		
		return clazz;
	}
	
	private static int compileAFile(File file) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();		
		int success = compiler.run(System.in, System.out, System.err, file.getAbsolutePath());
		return success;
	}
	
	public List<FileResult> getFileResults() {
		return fileResults;
	}
}
