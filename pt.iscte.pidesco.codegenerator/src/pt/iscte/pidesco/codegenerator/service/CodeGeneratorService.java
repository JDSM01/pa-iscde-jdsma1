package pt.iscte.pidesco.codegenerator.service;

import java.util.List;

import pt.iscte.pidesco.codegenerator.internal.Field;
import pt.iscte.pidesco.codegenerator.internal.Regex;

/**
 * This is the service to be used by other components.
 * @author D01
 *
 */
public interface CodeGeneratorService {

	public static final String JAVA = "JAVA"; 
	public static final String PYTHON = "PYTHON";
	/**
	 * Generates the appropriate variable name based on a given String, language type and if it's static or not.
	 * @param convertFrom the string to convert to a variable name
	 * @param languageType a string to specify to which type of programming language to generate the variable name
	 * @param isStatic is the variable static?
	 * @return string based on convertFrom, language type and if it's static or not. Returns an empty string if convertFrom is null.
	 */
	String generateVariableName(String convertFrom, String languageType, boolean isStatic);

	/**
	 * Generates a name for a variable name based on a given String and a given regex
	 * @param convertFrom the string to convert to a variable name
	 * @param regex the values that the method will find (replaceFrom) and then replace(replaceTo) on the given String
	 * @return string based on convertFrom and regex. Returns an empty string if convertFrom is null.
	 */
	String generateVariableName(String convertFrom, Regex regex);
	/**
	 * Generates an if condition.
	 * @param selection what will be used as condition. 
	 * @param ifType the type of if to be generated 
	 * @return an if condition based on the string and type given. In case the ifType is not recognized it will return the condition If.
	 * e.g. If type is null it will return a condition of the type if(selection == null)
	 */
	String generateIfCondition(String selection, IfType ifType);

	/**
	 * Generates an if condition with an input on what to do inside of it
	 * @param selectedText what will be used as condition
	 * @param body what will be used inside of the if (break lines and tabs need to be specified)
	 * @param ifType the type of if to be generated
	 * @return an if condition based on the string and type given. In case the ifType is not recognized it will return the condition If.
	 * e.g. If type is null it will return a condition of the type if(selection == null)
	 */
	String generateIfCondition(String selectedText, String body, IfType ifType);

	/**
	 * Generates a string of a binded variable.
	 * @param fields list of fields(type and name of a variables) to be binded
	 * @return a string of the type this.variableName = variableName. Returns empty string if list is empty
	 */
	String generateBindedVariable(List<Field> fields);

	/**
	 * Generates the  constructor of the class
	 * @param className name of the class to generate the constructor
	 * @param arguments list of fields (type and name of a variable) to be used in the constructor
	 * @return a string of a constructor with given className and a list of arguments. Returns an empty string if className is null
	 */
	String generateConstructor(String className, List<Field> arguments);

	/**
	 * Generates the constructor of the class with an input inside of it
	 * @param className name of the class to generate the constructor
	 * @param arguments list of fields (type and name of a variable) to be used in the constructor
	 * @param input what will be inside the constructor
	 * @return a string of a constructor with given className and a list of arguments. Returns an empty string if className is null
	 */
	String generateConstructor(String className, List<Field> arguments, String input);

	/**
	 * Generates the  constructor of the class and binds the variables
	 * @param className name of the class to generate the constructor
	 * @param arguments list of fields (type and name of a variable) to be used in the constructor and to bind
	 * @return a string of a constructor and bindings with given className and a list of arguments. Returns an empty string if className is null
	 */
	String generateConstructorWithBinding(String className, List<Field> arguments);

	/**
	 * Generates the  constructor of the class, binds the variables and adds the input after it
	 * @param className name of the class to generate the constructor
	 * @param arguments list of fields (type and name of a variable) to be used in the constructor and to bind
	 * @param input input what will be inside the constructor after the binding
	 * @return a string of a constructor and bindings with given className and a list of arguments. Returns an empty string if className is null
	 */
	String generateConstructorWithBinding(String className, List<Field> arguments, String input);

	/**
	 * Generates a setter for a given type and variableName
	 * @param variableType the type of the variable that you want to create the setter
	 * @param variableName the name of the variable that you want to create the setter
	 * @return a string of the setter method
	 */
	String generateSetter(String variableType, String variableName);

	/**
	 * Generates a setter for a given type and variableName
	 * @param variableType the type of the variable that you want to create the setter
	 * @param variableName the name of the variable that you want to create the setter
	 * @param methodName the name of the method that you want to give to the setter
	 * @return a string of the setter method
	 */
	String generateSetter(String variableType, String variableName, String methodName);

	/**
	 * Generates a getter for a given type and variableName
	 * @param variableType the type of the variable that you want to create the getter
	 * @param variableName the name of the variable that you want to create the getter
	 * @return a string of the getter method
	 */
	String generateGetter(String variableType, String variableName);

	/**
	 * Generates a getter for a given type and variableName
	 * @param variableType the type of the variable that you want to create the getter
	 * @param variableName the name of the variable that you want to create the getter
	 * @param methodName the name of the method that you want to give to the getter
	 * @return a string of the getter method
	 */
	String generateGetter(String variableType, String variableName, String methodName);

	/**
	 * Generates a field given the arguments (e.g. public static final type name)
	 * @param acessLevel enum of the different acessLevels that the field can have (public, private, protected, package-private)
	 * @param isStatic is the field static?
	 * @param isFinal is the field final?
	 * @param fields list of fields (type and name of a variable) to be used in the fields
	 * @return a string with the field or list of fields
	 */
	String generateField(AcessLevel acessLevel, boolean isStatic, boolean isFinal, List<Field> fields);

	/**
	 * Generates a field given the arguments (e.g. public static final type name)
	 * @param acessLevel enum of the different acessLevels that the field can have (public, private, protected, package-private)
	 * @param isStatic is the field static?
	 * @param returnType type of what the method will return
	 * @param methodName name of the method that will be generated
	 * @param arguments list of fields (type and name of a variable) to be used in the method as arguments
	 * @return a string with the method
	 */
	String generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName, List<Field> arguments);

	/**
	 * Generates a field given the arguments (e.g. public static final type name)
	 * @param acessLevel enum of the different acessLevels that the field can have (public, private, protected, package-private)
	 * @param isStatic is the field static?
	 * @param returnType type of what the method will return
	 * @param methodName name of the method that will be generated
	 * @param arguments list of fields (type and name of a variable) to be used in the method as arguments
	 * @param returnValue value to be used as default in the return statement (e.g. return returnValue).
	 * no return will be used if value is null or an empty string
	 * @return a string with the method
	 */
	String generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName,
			List<Field> arguments, String returnValue);

	/**
	 * Generates a field given the arguments (e.g. public static final type name)
	 * @param acessLevel enum of the different acessLevels that the field can have (public, private, protected, package-private)
	 * @param isStatic is the field static?
	 * @param returnType type of what the method will return
	 * @param methodName name of the method that will be generated
	 * @param arguments list of fields (type and name of a variable) to be used in the method as arguments
	 * @param returnValue value to be used as default in the return statement (e.g. return returnValue).
	 * no return will be used if value is null or an empty string
	 * @param body a string with the code to be used inside the method created (break lines and tabs need to be specified)
	 * @return a string with the method
	 */
	String generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName,
			List<Field> arguments, String returnValue, String body);

	public enum IfType{
		CONDITION, NULL, NOT_NULL
	}

	public enum AcessLevel{
		PUBLIC, PROTECTED, PACKAGE_PRIVATE, PRIVATE
	}
}
