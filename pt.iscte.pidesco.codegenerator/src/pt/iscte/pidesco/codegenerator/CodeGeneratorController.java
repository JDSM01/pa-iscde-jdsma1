package pt.iscte.pidesco.codegenerator;

import pt.iscte.pidesco.codegenerator.service.CodeGeneratorService;

public class CodeGeneratorController implements CodeGeneratorService{

	public CodeGeneratorController() {
	}

	@Override
	public String generateVariableName(String convertFrom, LanguageVariableType languageType) {
		if(convertFrom != null && !convertFrom.equals("")) {
			char c[] = convertFrom.toCharArray();
			c[0] = Character.toLowerCase(c[0]);
			String variableName = new String(c);
			return " " + variableName;
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
	public String generateBindedVariable(String variableName) {
		if(variableName != null && !variableName.equals("")) {
			String text = "\t\t" + "this." + variableName + " = " + variableName + ";";
			return text;
		}
		return "";
	}

	@Override
	public String generateConstructor(String className) {
		if(className != null) {
			String constructor = "public " + className.split(".")[0] + "() { \n\n\t" +"}";
			return constructor;
		}
		return "";
	}
}
