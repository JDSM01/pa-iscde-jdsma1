package pt.iscte.pidesco.codegenerator.service;

import java.util.List;

import pt.iscte.pidesco.codegenerator.Field;
import pt.iscte.pidesco.codegenerator.Regex;

public interface CodeGeneratorService {

	/**
	 * Generates the appropriate variable name based on a given String, language type and if it's static or not.
	 * @param convertFrom the string to convert to a variable name
	 * @param languageType an enum to specify to which type of programming language to generate the variable name
	 * @param isStatic is the variable static?
	 * @return string based on convertFrom, language type and if it's static or not. Returns an empty string if convertFrom is null.
	 */
	String generateVariableName(String convertFrom, LanguageVariableType languageType, boolean isStatic);

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
	 * @param returnValue value to be used as default in the return statement (e.g. return returnValue)
	 * @return a string with the method
	 */
	String generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName,
			List<Field> arguments, String returnValue);
	
	public enum LanguageVariableType{
		JAVA, PYTHON
	}

	public enum IfType{
		CONDITION, NULL, NOT_NULL
	}

	public enum AcessLevel{
		PUBLIC, PROTECTED, PACKAGE_PRIVATE, PRIVATE
	}
}
