package test;

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
	private List<Boolean> results;
	
	public Grader(List<File> filesToGrade, List<AnswerKey> answerKeys) {
		this.filesToGrade = filesToGrade;
		this.answerKeys = answerKeys;
		results = new ArrayList<Boolean>();
	}
	
	public void grade() {
		for(File file : filesToGrade) {
			for(AnswerKey key : answerKeys) {
				boolean result = false;
				
				try {
					compileAFile(file);
					Class<?> clazz = loadClass(file);
					Object obj = clazz.newInstance();
					Method method = clazz.getDeclaredMethod(key.getMethodName(), key.getArgumentClassTypes());
					method.setAccessible(true);
					Object output = method.invoke(obj, key.getArguementValues());
					
					if(key.getExpectedOutput().getValue().equals(output)) {
						result = true;
					} else {
						result = false;
					}
					
				} catch (MalformedURLException | ClassNotFoundException e) {
					result = false;
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					result = false;
					e.printStackTrace();
				} catch (SecurityException e) {
					result = false;
					e.printStackTrace();
				} catch (InstantiationException e) {
					result = false;
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					result = false;
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					result = false;
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					result = false;
					e.printStackTrace();
				}
				
				results.add(result);
			}
		}
	}
	
	private Class<?> loadClass(File file) throws MalformedURLException, ClassNotFoundException {
		Class<?> clazz = null;
		URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{file.getParentFile().toURI().toURL()});
		clazz = Class.forName(file.getName().substring(0, file.getName().length() - 5), true, classLoader);
		
		return clazz;
	}
	
	private static int compileAFile(File file) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();		
		int success = compiler.run(System.in, System.out, System.err, file.getAbsolutePath());
		return success;
	}
	
	public List<Boolean> getResults() {
		return results;
	}
}
