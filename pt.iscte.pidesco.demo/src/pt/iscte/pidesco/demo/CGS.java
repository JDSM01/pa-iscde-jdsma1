package pt.iscte.pidesco.demo;

import java.util.List;

import pt.iscte.pidesco.codegenerator.extensability.CodeStringGeneratorService;
import pt.iscte.pidesco.codegenerator.wrappers.Field;
import pt.iscte.pidesco.codegenerator.wrappers.Regex;

public class CGS implements CodeStringGeneratorService{

	@Override
	public String generateVariableName(String convertFrom, String languageType, boolean isStatic) {
		return "IT WORKED";
	}

	@Override
	public String generateVariableName(String convertFrom, Regex regex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateIfCondition(String selection, IfType ifType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateBindedVariable(List<Field> fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateConstructor(String className, List<Field> arguments) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateConstructorWithBinding(String className, List<Field> arguments) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateSetter(String variableType, String variableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateSetter(String variableType, String variableName, String methodName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateGetter(String variableType, String variableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateGetter(String variableType, String variableName, String methodName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateField(AcessLevel acessLevel, boolean isStatic, boolean isFinal, List<Field> fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName,
			List<Field> arguments) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName,
			List<Field> arguments, String returnValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateIfCondition(String selectedText, String input, IfType ifType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateConstructor(String className, List<Field> arguments, String input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateConstructorWithBinding(String className, List<Field> arguments, String input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName,
			List<Field> arguments, String returnValue, String body) {
		// TODO Auto-generated method stub
		return null;
	}

}
