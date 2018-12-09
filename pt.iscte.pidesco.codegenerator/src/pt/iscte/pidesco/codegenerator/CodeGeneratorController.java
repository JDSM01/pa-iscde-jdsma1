package pt.iscte.pidesco.codegenerator;

import java.util.List;

import pt.iscte.pidesco.codegenerator.service.CodeGeneratorService;

public class CodeGeneratorController implements CodeGeneratorService{

	@Override public String generateSetter(String variableType, String variableName) {
		if(variableType != null && variableName != null) {
			char c[] = variableName.toCharArray();
			c[0] = Character.toUpperCase(c[0]);
			String upperCasedVariableName = new String(c);
			String methodName = "set"+ upperCasedVariableName;
			return generateSetterString(variableType, variableName, methodName);
		}
		return "";
	}

	@Override public String generateSetter(String variableType, String variableName, String methodName) {
		if(variableType != null && variableName != null) {
			return generateSetterString(variableType, variableName, methodName);
		}
		return "";
	}


	@Override public String generateGetter(String variableType, String variableName) {
		if(variableType != null && variableName != null) {
			char c[] = variableName.toCharArray();
			c[0] = Character.toUpperCase(c[0]);
			String upperCasedVariableName = new String(c);
			String methodName = "get" + upperCasedVariableName;
			return generateGetterString(variableType, variableName, methodName);
		}
		return "";
	}

	@Override public String generateGetter(String variableType, String variableName, String methodName) {
		if(variableType != null && variableName != null) {
			return generateGetterString(variableType, variableName, methodName);
		}
		return "";
	}

	@Override
	public String generateVariableName(String convertFrom, LanguageVariableType languageType, boolean isStatic) {
		if(convertFrom != null && !convertFrom.equals("")) {
			if(languageType == LanguageVariableType.JAVA) {
				return generateJavaVariableName(convertFrom, isStatic);
			}
			else if(languageType == LanguageVariableType.PYTHON) {
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
		String ifCondition = "";
		String endIf = " { \n\n } \n";
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

	@Override
	public String generateBindedVariable(List<Field> fields) {
		String text = "";
		for(int i = 0; i < fields.size(); i++) {
			Field field = fields.get(i);
			text += "\t\t" + "this." + field.getName() + " = " + field.getName() + ";";
			if(i < fields.size() - 1) {
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
		if(className != null) {
			String constructor = generateConstructorString(className, arguments);
			String constructorEnd = ") { \n\n" + "}\n";
			return constructor + constructorEnd;
		}
		return "";
	}

	@Override
	public String generateConstructorWithBinding(String className, List<Field> arguments) {
		if(className != null) {
			String constructor = generateConstructorString(className, arguments);
			constructor += ") { \n";
			constructor += generateBindedVariable(arguments);
			String constructorEnd = "\n}\n";
			return constructor + constructorEnd;
		}
		return "";
	}

	@Override
	public String generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName, List<Field> arguments) {
		return generateMethodString(acessLevel, isStatic, returnType, methodName, arguments, "");
	}

	@Override
	public String generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName, List<Field> arguments, String returnValue) {
		return generateMethodString(acessLevel, isStatic, returnType, methodName, arguments, returnValue);
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
			List<Field> arguments, String returnValue) {
		String staticString = isStatic ? "static " : "";
		String acessLevelString = acessLevel == AcessLevel.PACKAGE_PRIVATE ? "" : acessLevel.toString().toLowerCase() + " ";
		String returnTypeString = returnType == null ? "void" : returnType;
		String returnString = "return null;";
		if(returnValue != null && !returnValue.equals("")) {
			returnString = "return " + returnValue + ";";
		}
		if(returnType.equals("int")) {
			returnString = "return -1;";
		}
		else if(returnType.equals("void")) {
			returnString = "";
		}
		String method = acessLevelString + staticString + returnTypeString + " " + methodName + "(";
		for(Field argument : arguments) {
			method +=  argument.getType() + " " + argument.getName() + ", ";
		}

		method = method.substring(0, method.length()-3);
		String methodEnd = ") { \n\t" + returnString + "\n" + "}\n";
		return method + methodEnd;
	}

	private String generateSetterString(String variableType, String variableName, String methodName) {
		String setter = "\npublic " + "void " + methodName + "(" + variableType + " " + 
				variableName+ "){\n\t" + "this." + variableName + " = " + variableName + ";\n" + "}\n";
		return setter;
	}

	private String generateGetterString(String variableType, String variableName, String methodName) {
		String getter = "\npublic " + variableType + " " + methodName + "(){\n\t" + "return " + variableName + ";\n" + "}\n";
		return getter;
	}

	private String generateConstructorString(String className, List<Field> arguments) {
		String constructor = "\npublic " + className.substring(0, className.lastIndexOf(".")) + "(";
		for(Field field : arguments) {
			constructor += field.getType() + " " + field.getName() + ", ";
		}
		return constructor = constructor.substring(0, constructor.length()-2);
	}
}



