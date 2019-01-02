package pt.iscte.pidesco.codegenerator.service;

import java.util.List;

import pt.iscte.pidesco.codegenerator.extensability.CodeStringGeneratorService.AcessLevel;
import pt.iscte.pidesco.codegenerator.extensability.CodeStringGeneratorService.IfType;
import pt.iscte.pidesco.codegenerator.wrappers.Field;
import pt.iscte.pidesco.codegenerator.wrappers.Regex;

/**
 * This service is responsible for generating and inserting them 
 * by using the JavaEditorServices
 * @author D01
 *
 */
public interface CodeGeneratorService {

	/**
	 * String used to specify the language to generate a variable name
	 */
	public static final String JAVA = "JAVA"; 

	/**
	 * String used to specify the language to generate a variable name
	 */
	public static final String PYTHON = "PYTHON";

	/**
	 * Generates and inserts the appropriate variable name based on a given String, language type and if it's static or not.
	 * The string is inserted in front of the variable type that matches the convertFrom and the given line
	 * @param convertFrom the string to convert to a variable name
	 * @param languageType a string to specify to which type of programming language to generate the variable name
	 * @param isStatic is the variable static?
	 * @param line line in the editor where the variable will be inserted
	 */
	void generateVariableName(String convertFrom, String languageType, boolean isStatic, int line);

	/**
	 * Generates and inserts the appropriate variable name based on a given String, language type and if it's static or not.
	 * The string is inserted in front of the variable type that matches the convertFrom and the given line
	 * @param convertFrom the string to convert to a variable name
	 * @param regex the values that the method will find (replaceFrom) and then replace(replaceTo) on the given String
	 * @param line line in the editor where the variable will be inserted
	 */
	void generateVariableName(String convertFrom, Regex regex, int line);

	/**
	 * Generates an if condition based on the string and type given. In case the ifType is not recognized it will return the condition If.
	 * e.g. If type is null it will return a condition of the type if(selection == null) in a given line
	 * @param condition what will be used as condition. 
	 * @param ifType the type of if to be generated 
	 * @param line line in the editor where the if will be inserted
	 */
	void generateIfConditionInLine(String condition, IfType ifType, int line);

	/**
	 * Generates an if condition based on the string, body and type given. In case the ifType is not recognized it will return the condition If.
	 * e.g. If type is null it will return a condition of the type if(selection == null) in a given line
	 * @param selectedText what will be used as condition
	 * @param body what will be used inside of the if (break lines and tabs need to be specified)
	 * @param ifType the type of if to be generated
	 * @param line line in the editor where the if will be inserted
	 */
	void generateIfConditionInLine(String selectedText, String body, IfType ifType, int line);

	/**
	 * Generates an if condition based on the string and type given. In case the ifType is not recognized it will return the condition If.
	 * e.g. If type is null it will return a condition of the type if(selection == null) in a given offset
	 * @param condition what will be used as condition. 
	 * @param ifType the type of if to be generated 
	 * @param offset offset in the editor where the if will be inserted
	 */
	void generateIfConditionInOffset(String condition, IfType ifType, int offset);

	/**
	 * Generates an if condition based on the string, body and type given. In case the ifType is not recognized it will return the condition If.
	 * e.g. If type is null it will return a condition of the type if(selection == null) in a given offset
	 * @param condition what will be used as condition
	 * @param body what will be used inside of the if (break lines and tabs need to be specified)
	 * @param ifType the type of if to be generated
	 * @param offset offset in the editor where the if will be inserted
	 */
	void generateIfConditionInOffset(String condition, String body, IfType ifType, int offset);

	/**
	 * Generates an if condition based on the string and type given. In case the ifType is not recognized it will return the condition If.
	 * e.g. If type is null it will return a condition of the type if(selection == null) in the cursor position
	 * @param condition what will be used as condition. 
	 * @param ifType the type of if to be generated 
	 * @param offset offset in the editor where the if will be inserted
	 */
	void generateIfConditionInOffset(String condition, IfType ifType);

	/**
	 * Generates an if condition based on the string, body and type given. In case the ifType is not recognized it will return the condition If.
	 * e.g. If type is null it will return a condition of the type if(selection == null) in the cursor position
	 * @param condition what will be used as condition
	 * @param body what will be used inside of the if (break lines and tabs need to be specified)
	 * @param ifType the type of if to be generated
	 * @param offset offset in the editor where the if will be inserted
	 */
	void generateIfConditionInOffset(String condition, String body, IfType ifType);

	/**
	 * Generates and inserts a string of a binded variable in the constructor with the given fields.
	 * @param fields list of fields(type and name of a variables) to be binded
	 */
	void generateBindedVariable(List<Field> fields);

	/**
	 * Generates and inserts a constructor in the current class
	 * This will be inserted below the last field or if there are no fields below the class statement.
	 * Does nothing if there's no class statement
	 * @param className name of the class to generate the constructor
	 * @param arguments list of fields (type and name of a variable) to be used in the constructor
	 */
	void generateConstructor(String className, List<Field> arguments);
	
	/**
	 * Generates and inserts a constructor in the current class in a given line
	 * @param className name of the class to generate the constructor
	 * @param arguments list of fields (type and name of a variable) to be used in the constructor
	 */
	void generateConstructor(String className, List<Field> arguments, int line);

	/**
	 * Generates and inserts a constructor in the current class with an input inside of it.
	 * This will be inserted below the last field or if there are no fields below the class statement.
	 * Does nothing if there's no class statement
	 * @param className name of the class to generate the constructor
	 * @param arguments list of fields (type and name of a variable) to be used in the constructor
	 * @param input what will be inside the constructor
	 */
	void generateConstructor(String className, List<Field> arguments, String input);

	/**
	 * Generates and inserts a constructor in the current class with an input inside of it in a given line
	 * @param className name of the class to generate the constructor
	 * @param arguments list of fields (type and name of a variable) to be used in the constructor
	 * @param input what will be inside the constructor
	 */
	void generateConstructor(String className, List<Field> arguments, String input, int line);
	
	/**
	 * Generates and inserts a constructor of the class and binds the variables
	 * This will be inserted below the last field or if there are no fields below the class statement.
	 * Does nothing if there's no class statement
	 * @param className name of the class to generate the constructor
	 * @param arguments list of fields (type and name of a variable) to be used in the constructor and to bind
	 */
	void generateConstructorWithBinding(String className, List<Field> arguments);
	
	/**
	 * Generates and inserts a constructor of the class and binds the variables in a given line
	 * @param className name of the class to generate the constructor
	 * @param arguments list of fields (type and name of a variable) to be used in the constructor and to bind
	 */
	void generateConstructorWithBinding(String className, List<Field> arguments, int line);

	/**
	 * Generates and inserts a constructor of the class, binds the variables and adds the input after it
	 * This will be inserted below the last field or if there are no fields below the class statement.
	 * Does nothing if there's no class statement
	 * @param className name of the class to generate the constructor
	 * @param arguments list of fields (type and name of a variable) to be used in the constructor and to bind
	 * @param input what will be inside the constructor after the binding
	 */
	void generateConstructorWithBinding(String className, List<Field> arguments, String input);

	/**
	 * Generates and inserts a constructor of the class, binds the variables and adds the input after it in a given line
	 * @param className name of the class to generate the constructor
	 * @param arguments list of fields (type and name of a variable) to be used in the constructor and to bind
	 * @param input what will be inside the constructor after the binding
	 */
	void generateConstructorWithBinding(String className, List<Field> arguments, String input, int line);
	
	/**
	 * Generates and inserts a setter for a given type and variableName below the last constructor or 
	 * at the end of the file if there's not constructor
	 * @param variableType the type of the variable that you want to create the setter
	 * @param variableName the name of the variable that you want to create the setter
	 */
	void generateSetter(String variableType, String variableName);

	/**
	 * Generates and inserts a setter for a given type and variableName at a given line
	 * @param variableType the type of the variable that you want to create the setter
	 * @param variableName the name of the variable that you want to create the setter
	 * @param line line in the editor where the setter will be inserted
	 */
	void generateSetter(String variableType, String variableName, int line);

	/**
	 * Generates and inserts a setter for a given type and variableName below the last constructor or 
	 * at the end of the file if there's not constructor
	 * @param variableType the type of the variable that you want to create the setter
	 * @param variableName the name of the variable that you want to create the setter
	 * @param methodName the name of the method that you want to give to the setter
	 */
	void generateSetter(String variableType, String variableName, String methodName);

	/**
	 * Generates and inserts a setter for a given type and variableName at a given line
	 * @param variableType the type of the variable that you want to create the setter
	 * @param variableName the name of the variable that you want to create the setter
	 * @param methodName the name of the method that you want to give to the setter
	 * @param line line in the editor where the setter will be inserted
	 */
	void generateSetter(String variableType, String variableName, String methodName, int line);

	/**
	 * Generates and inserts a getter for a given type and variableName below the last constructor or 
	 * at the end of the file if there's not constructor
	 * @param variableType the type of the variable that you want to create the getter
	 * @param variableName the name of the variable that you want to create the getter
	 */
	void generateGetter(String variableType, String variableName);

	/**
	 * Generates and inserts a getter for a given type and variableName at a given line
	 * @param variableType the type of the variable that you want to create the getter
	 * @param variableName the name of the variable that you want to create the getter
	 * @param line line in the editor where the getter will be inserted
	 */
	void generateGetter(String variableType, String variableName, int line);

	/**
	 * Generates and inserts a getter for a given type and variableName below the last constructor or 
	 * at the end of the file if there's not constructor
	 * @param variableType the type of the variable that you want to create the getter
	 * @param variableName the name of the variable that you want to create the getter
	 * @param methodName the name of the method that you want to give to the getter
	 */
	void generateGetter(String variableType, String variableName, String methodName);

	/**
	 * Generates and inserts a getter for a given type and variableName at a given line
	 * @param variableType the type of the variable that you want to create the getter
	 * @param variableName the name of the variable that you want to create the getter
	 * @param methodName the name of the method that you want to give to the getter
	 */
	void generateGetter(String variableType, String variableName, String methodName, int line);

	/**
	 * Generates and inserts a field given the arguments (e.g. public static final type name) below the last field of the current class
	 * or below the class statement if there are no fields
	 * @param acessLevel enum of the different acessLevels that the field can have (public, private, protected, package-private)
	 * @param isStatic is the field static?
	 * @param isFinal is the field final?
	 * @param fields list of fields (type and name of a variable) to be used in the fields
	 */
	void generateField(AcessLevel acessLevel, boolean isStatic, boolean isFinal, List<Field> fields);

	/**
	 * Generates and inserts a method given the arguments below the last constructor or at the end of the file if there's not a constructor
	 * @param acessLevel enum of the different acessLevels that the field can have (public, private, protected, package-private)
	 * @param isStatic is the field static?
	 * @param returnType type of what the method will return
	 * @param methodName name of the method that will be generated
	 * @param arguments list of fields (type and name of a variable) to be used in the method as arguments
	 */
	void generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName, List<Field> arguments);

	/**
	 * Generates and inserts a method given the arguments below the last constructor or at the end of the file if there's not a constructor
	 * @param acessLevel enum of the different acessLevels that the field can have (public, private, protected, package-private)
	 * @param isStatic is the field static?
	 * @param returnType type of what the method will return
	 * @param methodName name of the method that will be generated
	 * @param arguments list of fields (type and name of a variable) to be used in the method as arguments
	 * @param returnValue value to be used as default in the return statement (e.g. return returnValue).
	 * no return will be used if value is null or an empty string
	 */
	void generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName,
			List<Field> arguments, String returnValue);

	/**
	 * Generates and inserts a method given the arguments below the last constructor or at the end of the file if there's not a constructor
	 * @param acessLevel enum of the different acessLevels that the field can have (public, private, protected, package-private)
	 * @param isStatic is the field static?
	 * @param returnType type of what the method will return
	 * @param methodName name of the method that will be generated
	 * @param arguments list of fields (type and name of a variable) to be used in the method as arguments
	 * @param returnValue value to be used as default in the return statement (e.g. return returnValue).
	 * no return will be used if value is null or an empty string
	 * @param body a string with the code to be used inside the method created (break lines and tabs need to be specified)
	 */
	void generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName,
			List<Field> arguments, String returnValue, String body);

	/**
	 * Generates and inserts a method given the arguments at a given line
	 * @param acessLevel enum of the different acessLevels that the field can have (public, private, protected, package-private)
	 * @param isStatic is the field static?
	 * @param returnType type of what the method will return
	 * @param methodName name of the method that will be generated
	 * @param arguments list of fields (type and name of a variable) to be used in the method as arguments
	 * @param line line in the editor where the method will be inserted
	 */
	void generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName, List<Field> arguments, 
			int line);

	/**
	 * Generates and inserts a method given the arguments at a given line
	 * @param acessLevel enum of the different acessLevels that the field can have (public, private, protected, package-private)
	 * @param isStatic is the field static?
	 * @param returnType type of what the method will return
	 * @param methodName name of the method that will be generated
	 * @param arguments list of fields (type and name of a variable) to be used in the method as arguments
	 * @param returnValue value to be used as default in the return statement (e.g. return returnValue).
	 * no return will be used if value is null or an empty string
	 * @param line line in the editor where the method will be inserted
	 */
	void generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName,
			List<Field> arguments, String returnValue, int line);

	/**
	 * Generates and inserts a method given the arguments at a given line
	 * @param acessLevel enum of the different acessLevels that the field can have (public, private, protected, package-private)
	 * @param isStatic is the field static?
	 * @param returnType type of what the method will return
	 * @param methodName name of the method that will be generated
	 * @param arguments list of fields (type and name of a variable) to be used in the method as arguments
	 * @param returnValue value to be used as default in the return statement (e.g. return returnValue).
	 * no return will be used if value is null or an empty string
	 * @param body a string with the code to be used inside the method created (break lines and tabs need to be specified)
	 * @param line line in the editor where the method will be inserted
	 */
	void generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName,
			List<Field> arguments, String returnValue, String body, int line);
}
