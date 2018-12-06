package pt.iscte.pidesco.codegenerator.service;

public interface CodeGeneratorService {

	/**
	 * Generates the appropriate variable name based on a given String.
	 * @param convertFrom the string to convert to a variable name, given a specific
	 * @param languageType an enum to specify to which type of programming language to generate the variable name
	 * @return string based on convertFrom and language type. Returns an empty string if convertFrom is null.
	 */
	String generateVariableName(String convertFrom, LanguageVariableType languageType);

	/**
	 * Generates an if condition.
	 * @param selection what will be used as condition. 
	 * @param ifType the type of if to be generated 
	 * @return an if condition based on the string and type given. In case the ifType is not recognized it will return the condition If.
	 * e.g. If type is null it will return a condition of the type if(selection == null)
	 */
	String generateIfCondition(String selection, IfType ifType);

	/**
	 * Generates a string of a binded variable.
	 * @param variableName string of the variable to generate the binding
	 * @return a string of the type this.variableName = variableName;
	 */
	String generateBindedVariable(String variableName);

	/**
	 * Generates the  constructor of the class
	 * @param className name of the class to generate the constructor
	 * @return a string of a constructor with given className
	 */
	String generateConstructor(String className);
	
	public enum LanguageVariableType{
		JAVA, PYTHON, PHP, RUBY
	}

	public enum IfType{
		CONDITION, NULL, NOT_NULL
	}
}
