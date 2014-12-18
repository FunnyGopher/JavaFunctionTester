package javafunctiontester;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class AnswerKey {
	private String name;
	private int numberOfInputs;
	private String methodName;
	private Data<?> expectedOutput;
	private Data<?>[] arguments;
	
	public AnswerKey(String name, String methodName, Data<?> expectedOutput, Data<?>[] arguments) {
		this.name = name;
		this.methodName = methodName;
		this.expectedOutput = expectedOutput;
		this.arguments = arguments;
		numberOfInputs = this.arguments.length - 1;
	}

	public Data<?>[] getArguments() {
		return arguments;
	}

	public Data<?> getExpectedOutput() {
		return expectedOutput;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getName() {
		return name;
	}

	public int getNumberOfInputs() {
		return numberOfInputs;
	}
	
	public Class<?>[] getArgumentClassTypes() {
		Class<?>[] types = new Class<?>[numberOfInputs];
		for(int i = 0; i < numberOfInputs; i++) {
			types[i] = arguments[i].getType();
		}
		return types;
	}
	
	public Object[] getArguementValues() {
		Object[] values = new Object[numberOfInputs];
		for(int i = 0; i < numberOfInputs; i++) {
			values[i] = arguments[i].getValue();
		}
		return values;
	}
	
	public static AnswerKey createFromFile(File file) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			
			String name;
			String methodName;
			Data<?> expectedOutput = null;
			Data<?>[] arguments;
			
			Node rootNode = doc.getElementsByTagName("answerkey").item(0);
			Element rootElement = (Element) rootNode;
			
			name = rootElement.getAttribute("name");
			methodName = rootElement.getAttribute("methodname");
			
			NodeList nList = doc.getElementsByTagName("data");
			arguments = new Data<?>[nList.getLength()];
			
			int argumentCount = 0;
			for(int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				Element element = (Element) node;
				
				if(element.getAttribute("id").equals("expected output")) {
					expectedOutput = Data.create(element);
				}
				
				if(element.getAttribute("id").equals("argument")) {
					arguments[argumentCount] = Data.create(element);
					argumentCount++;
				}
			}
			
			return new AnswerKey(name, methodName, expectedOutput, arguments);
		} catch(Exception e) {}
		
		return null;
	}
	
	public static boolean isValid(File file) {
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			
			System.out.println("Root Element: " + doc.getDocumentElement().getNodeName());
			
			NodeList nList = doc.getElementsByTagName("answerkey");
			System.out.println("---------------------------------");
			
			if(nList.getLength() > 1)
				return false;
			
			for(int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				System.out.println("\nCurrent Element: " + node.getNodeName());
				
				Element element = (Element) node;
				System.out.println(element.getAttribute("name"));
			}
			
		} catch(Exception e) {}
		
		return true;
	}
}
