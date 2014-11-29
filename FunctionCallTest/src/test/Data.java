package test;

public class Data<T> {
	private Class<T> classType;
	private T value;
	
	public Data(Class<T> classType, T value) {
		this.classType = classType;
		this.value = value;
	}
	
	public Class<T> getType() {
		return classType;
	}
	
	public T getValue() {
		return value;
	}
}
