package pt.iscte.pidesco.codegenerator.wrappers;

import java.util.List;

//An object containing a method name and its arguments
public class SimpleMethod {

	private final String name;
	private final List<Field> arguments;

	public SimpleMethod(String name, List<Field> arguments) {
		this.name = name;
		this.arguments = arguments;
	}

	public String getName() {
		return name;
	}

	public List<Field> getArguments() {
		return arguments;
	}
}
