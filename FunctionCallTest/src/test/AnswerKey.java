package test;


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
		numberOfInputs = this.arguments.length;
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
		Class<?>[] types = new Class<?>[arguments.length];
		for(int i = 0; i < arguments.length; i++) {
			types[i] = arguments[i].getType();
		}
		return types;
	}
	
	public Object[] getArguementValues() {
		Object[] values = new Object[arguments.length];
		for(int i = 0; i < arguments.length; i++) {
			values[i] = arguments[i].getValue();
		}
		return values;
	}
}
