package javafunctiontester;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Data<T> {
	private Class<T> classType;
	private T value;
	
	public Data(Class<T> classType) {
		this.classType = classType;
	}
	
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
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public static Data<?> create(Element element) {
		String className = element.getAttribute("type");
		String value = element.getTextContent();
		
		switch(className) {
		case "byte":
			Data<Byte> byteData = new Data<Byte>(byte.class, Byte.parseByte(value));
			return byteData;
		case "short":
			Data<Short> shortData = new Data<Short>(short.class, Short.parseShort(value));
			return shortData;
		case "int":
			Data<Integer> intData = new Data<Integer>(int.class, Integer.parseInt(value));
			return intData;
		case "long":
			Data<Long> longData = new Data<Long>(long.class, Long.parseLong(value));
			return longData;
			
		case "float":
			Data<Float> floatData = new Data<Float>(float.class, Float.parseFloat(value));
			return floatData;
		case "double":
			Data<Double> doubleData = new Data<Double>(double.class, Double.parseDouble(value));
			return doubleData;
			
		case "boolean":
			Data<Boolean> data = new Data<Boolean>(boolean.class, Boolean.parseBoolean(value));
			return data;
		

		case "string":
		default:
			Data<String> stringData = new Data<String>(String.class, value);
			return stringData;
		}
	}
	
	public static boolean isValid(Element element) {
		
		return true;
	}
}
