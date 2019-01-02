package pt.iscte.pidesco.codegenerator.internal;

import java.util.List;

import pt.iscte.pidesco.codegenerator.extensability.CodeStringGeneratorService;
import pt.iscte.pidesco.codegenerator.wrappers.Field;
import pt.iscte.pidesco.codegenerator.wrappers.Regex;

/**
 * This class is the implementation of this component given service. This class is responsible to transform the input that the 
 * view gives into what the view wants. This is the class that generates the code based on inputs from the view by giving 
 * the necessary string to be inserted
 * @author D01
 *
 */
public class CodeGeneratorController implements CodeStringGeneratorService{

	@Override public String generateSetter(String variableType, String variableName) {
		if(!variableType.equals("") && !variableName.equals("")) {
			char c[] = variableName.toCharArray();
			c[0] = Character.toUpperCase(c[0]);
			String upperCasedVariableName = new String(c);
			String methodName = "set"+ upperCasedVariableName;
			return generateSetterString(variableType, variableName, methodName);
		}
		return "";
	}

	@Override public String generateSetter(String variableType, String variableName, String methodName) {
		if(!variableType.equals("") && !variableName.equals("")) {
			return generateSetterString(variableType, variableName, methodName);
		}
		return "";
	}


	@Override public String generateGetter(String variableType, String variableName) {
		if(!variableType.equals("") && !variableName.equals("")) {
			char c[] = variableName.toCharArray();
			c[0] = Character.toUpperCase(c[0]);
			String upperCasedVariableName = new String(c);
			String methodName = "get" + upperCasedVariableName;
			return generateGetterString(variableType, variableName, methodName);
		}
		return "";
	}

	@Override public String generateGetter(String variableType, String variableName, String methodName) {
		if(!variableType.equals("") && !variableName.equals("")) {
			return generateGetterString(variableType, variableName, methodName);
		}
		return "";
	}

	@Override
	public String generateVariableName(String convertFrom, String languageType, boolean isStatic) {
		if(convertFrom != null && !convertFrom.equals("")) {
			if(languageType == JAVA) {
				return generateJavaVariableName(convertFrom, isStatic);
			}
			else if(languageType == PYTHON) {
				return generatePythonVariableName(convertFrom);
			}
		}
		return "";
	}

	@Override
	public String generateVariableName(String convertFrom, Regex regex) {
		if(convertFrom != null && !convertFrom.equals("")) {
			return convertFrom.replaceAll(regex.getReplaceFrom(), regex.getReplaceTo());
		}
		return "";
	}

	@Override
	public String generateIfCondition(String selectedText, IfType ifType) {
		return generateIfString(selectedText, "", ifType);
	}

	@Override
	public String generateIfCondition(String selectedText, String body, IfType ifType) {
		return generateIfString(selectedText, body, ifType);
	}

	@Override
	public String generateBindedVariable(List<Field> fields) {
		String text = "";
		for(int i = 0; i < fields.size(); i++) {
			Field field = fields.get(i);
			text += "\t" + "this." + field.getName() + " = " + field.getName() + ";";
			if(i < fields.size() - 1) { //if last field
				text += "\n";
			}
		}
		return text;
	}

	@Override
	public String generateField(AcessLevel acessLevel, boolean isStatic, boolean isFinal, List<Field> fields) {
		String fieldCreator = "";
		for(Field field : fields) {
			String staticString = isStatic ? "static " : "";
			String finalString = isFinal ? "final " : "";
			String acessLevelString = acessLevel == AcessLevel.PACKAGE_PRIVATE ? "" : acessLevel.toString().toLowerCase() + " ";
			fieldCreator += acessLevelString + staticString + finalString + field.getType() + " " + field.getName() + ";\n";
		}
		return fieldCreator;
	}

	@Override
	public String generateConstructor(String className, List<Field> arguments) {
		return generateSimpleConstructorString(className, arguments, "");
	}

	@Override
	public String generateConstructor(String className, List<Field> arguments, String input) {
		return generateSimpleConstructorString(className, arguments, input);
	}


	@Override
	public String generateConstructorWithBinding(String className, List<Field> arguments) {
		return generateConstructorWithBindString(className, arguments, "");
	}

	@Override
	public String generateConstructorWithBinding(String className, List<Field> arguments, String input) {
		return generateConstructorWithBindString(className, arguments, input);
	}


	@Override
	public String generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName, List<Field> arguments) {
		return generateMethodString(acessLevel, isStatic, returnType, methodName, arguments, "", "");
	}

	@Override
	public String generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName, List<Field> arguments, String returnValue) {
		return generateMethodString(acessLevel, isStatic, returnType, methodName, arguments, returnValue, "");
	}

	@Override
	public String generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName, List<Field> arguments, String returnValue, String body) {
		return generateMethodString(acessLevel, isStatic, returnType, methodName, arguments, returnValue, body);
	}

	private String generateJavaVariableName(String convertFrom, boolean isStatic) {
		if(!isStatic) {
			char c[] = convertFrom.toCharArray();
			c[0] = Character.toLowerCase(c[0]);
			String variableName = new String(c);
			return " " + variableName;
		}
		else {
			convertFrom = convertFrom.replaceAll("[A-Z]", "_$1");
			return convertFrom.toUpperCase();
		}
	}

	private String generatePythonVariableName(String convertFrom) {
		convertFrom = convertFrom.replaceAll("[A-Z]", "_$1");
		return convertFrom.toLowerCase();
	}

	private String generateMethodString(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName,
			List<Field> arguments, String returnValue, String body) {
		if(!methodName.equals("")) {
			String staticString = isStatic ? "static " : "";
			String acessLevelString = acessLevel == AcessLevel.PACKAGE_PRIVATE ? "" : acessLevel.toString().toLowerCase() + " ";
			String returnTypeString = returnType == null ? "void" : returnType;
			if(body != null && !body.equals("")) {
				body += "\n"; 
			}
			String returnString = generateReturnString(returnValue, returnType);
			String signature = "\t" + acessLevelString + staticString + returnTypeString + " " + methodName + "(";
			signature = generateArgumentsString(arguments, signature);
			String methodEnd = ") { \n\t\t" + body + returnString + "\n" + "\t}\n";
			return signature + methodEnd;
		}
		return "";
	}

	private String generateReturnString(String returnValue, String returnType) {
		String returnString = "return null;";
		if(returnValue != null && !returnValue.equals("")) {
			returnString = "return " + returnValue + ";";
		}else if(returnType != null && returnType.equals("int")) {
			returnString = "return -1;";
		}
		else if(returnType != null && returnType.equals("void")) {
			returnString = "";
		}
		return returnString;
	}

	private String generateArgumentsString(List<Field> arguments, String signature) {
		for(Field argument : arguments) {
			signature +=  argument.getType() + " " + argument.getName() + ", ";
		}
		if(!arguments.isEmpty()) {
			signature = signature.substring(0, signature.length()-2);
		}
		return signature;
	}


	private String generateSetterString(String variableType, String variableName, String methodName) {
		String setter = "\n\tpublic " + "void " + methodName + "(" + variableType + " " + 
				variableName+ "){\n\t\t" + "this." + variableName + " = " + variableName + ";\n" + "\t}\n";
		return setter;
	}

	private String generateGetterString(String variableType, String variableName, String methodName) {
		String getter = "\n\tpublic " + variableType + " " + methodName + "(){\n\t\t" + "return " + variableName + ";\n" + "\t}\n";
		return getter;
	}

	private String generateConstructorString(String className, List<Field> arguments) {
		String constructor = "\n\tpublic " + className + "(";
		for(Field field : arguments) {
			constructor += field.getType() + " " + field.getName() + ", ";
		}
		if(!arguments.isEmpty()) {
			constructor = constructor.substring(0, constructor.length()-2);
		}
		return constructor;
	}

	private String generateIfString(String selectedText, String body, IfType ifType) {
		String ifCondition = "";
		String endIf = " { \n\t" + body + "\n} \n";
		String variable = "variable";
		if(selectedText != null && !selectedText.equals("")) {
			variable = selectedText;
		}
		if(ifType == IfType.NOT_NULL) {
			ifCondition = "if(" + variable + " != null)";
		}
		else if(ifType == IfType.NULL) {
			ifCondition = "if(" + variable + " == null)";
		}
		else {
			ifCondition = "if(" + variable + ")";
		}
		return ifCondition + endIf;
	}

	private String generateSimpleConstructorString(String className, List<Field> arguments, String input) {
		if(className != null) {
			String constructor = generateConstructorString(className, arguments);
			String constructorEnd = ") { \n\t\t" + input + "\n\t}\n";
			return constructor + constructorEnd;
		}
		return "";
	}

	private String generateConstructorWithBindString(String className, List<Field> arguments, String input) {
		if(className != null) {
			String constructor = generateConstructorString(className, arguments);
			constructor += ") { \n\t";
			constructor += generateBindedVariable(arguments);
			String constructorEnd = input + "\n\t}\n";
			return constructor + constructorEnd;
		}
		return "";
	}
}



