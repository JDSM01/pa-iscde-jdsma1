package pt.iscte.pidesco.codegenerator.internal;

import java.io.File;
import java.util.List;

import pt.iscte.pidesco.codegenerator.extensability.CodeStringGeneratorService;
import pt.iscte.pidesco.codegenerator.extensability.CodeStringGeneratorService.AcessLevel;
import pt.iscte.pidesco.codegenerator.extensability.CodeStringGeneratorService.IfType;
import pt.iscte.pidesco.codegenerator.service.CodeGeneratorService;
import pt.iscte.pidesco.codegenerator.wrappers.Field;
import pt.iscte.pidesco.codegenerator.wrappers.Regex;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class CodeGeneratorServiceImpl implements CodeGeneratorService{
	private final JavaEditorServices javaService;
	private final CodeStringGeneratorService codeStringGeneratorService;
	private final CodeGeneratorModel codeGeneratorModel;

	public CodeGeneratorServiceImpl(JavaEditorServices javaService, CodeStringGeneratorService codeStringGeneratorService,
			CodeGeneratorModel codeGeneratorModel) {
		this.javaService = javaService;
		this.codeStringGeneratorService = codeStringGeneratorService;
		this.codeGeneratorModel = codeGeneratorModel;
	}

	@Override
	public void generateVariableName(String convertFrom, String languageType, boolean isStatic, int line) {
		File file = codeGeneratorModel.getFile();
		codeGeneratorModel.parse(file, null, convertFrom, line);
		String variableName = codeStringGeneratorService.generateVariableName(convertFrom, languageType, isStatic);
		javaService.insertText(file, variableName, codeGeneratorModel.getVariableOffset(), 0);
	}

	@Override
	public void generateVariableName(String convertFrom, Regex regex, int line) {
		File file = codeGeneratorModel.getFile();
		codeGeneratorModel.parse(file, null, convertFrom, line);
		String variableName = codeStringGeneratorService.generateVariableName(convertFrom, regex);
		javaService.insertText(file, variableName, codeGeneratorModel.getVariableOffset(), 0);
	}

	@Override
	public void generateIfConditionInLine(String condition, IfType ifType, int line) {
		File file = codeGeneratorModel.getFile();
		String ifString = codeStringGeneratorService.generateIfCondition(condition, ifType);
		javaService.insertLine(file, ifString, line);
	}

	@Override
	public void generateIfConditionInLine(String condition, String body, IfType ifType, int line) {
		File file = codeGeneratorModel.getFile();
		String ifString = codeStringGeneratorService.generateIfCondition(condition, body, ifType);
		javaService.insertLine(file, ifString, line);
	}

	@Override
	public void generateIfConditionInOffset(String condition, IfType ifType, int offset) {
		File file = codeGeneratorModel.getFile();
		String ifString = codeStringGeneratorService.generateIfCondition(condition, ifType);
		javaService.insertLine(file, ifString, offset);
	}

	@Override
	public void generateIfConditionInOffset(String condition, String body, IfType ifType, int offset) {
		File file = codeGeneratorModel.getFile();
		String ifString = codeStringGeneratorService.generateIfCondition(condition, body, ifType);
		javaService.insertLine(file, ifString, offset);
	}

	@Override
	public void generateIfConditionInOffset(String condition, IfType ifType) {
		String ifString = codeStringGeneratorService.generateIfCondition(condition, ifType);
		javaService.insertTextAtCursor(ifString);
	}

	@Override
	public void generateIfConditionInOffset(String condition, String body, IfType ifType) {
		String ifString = codeStringGeneratorService.generateIfCondition(condition, body, ifType);
		javaService.insertTextAtCursor(ifString);
	}

	@Override
	public void generateBindedVariable(List<Field> fields) {
		File file = codeGeneratorModel.getFile();
		codeGeneratorModel.parse(file, codeGeneratorModel.getFileNameWithoutExtension(), null);
		String bindedVariable = codeStringGeneratorService.generateBindedVariable(fields);
		int endLine = codeGeneratorModel.getMethodEndLine();
		if(endLine != 0) {
			javaService.insertText(file, bindedVariable, endLine, 0);
		}
	}

	@Override
	public void generateConstructor(String className, List<Field> arguments) {
		File file = codeGeneratorModel.getFile();
		codeGeneratorModel.parse(file);
		String constructor = codeStringGeneratorService.generateConstructor(className, arguments);
		insertAfterField(file, constructor);
	}

	@Override
	public void generateConstructor(String className, List<Field> arguments, String input) {
		File file = codeGeneratorModel.getFile();
		codeGeneratorModel.parse(file);
		String constructor = codeStringGeneratorService.generateConstructor(className, arguments, input);
		insertAfterField(file, constructor);
	}

	@Override
	public void generateConstructor(String className, List<Field> arguments, int line) {
		File file = codeGeneratorModel.getFile();
		codeGeneratorModel.parse(file);
		String constructor = codeStringGeneratorService.generateConstructor(className, arguments);
		javaService.insertLine(file, constructor, line);
	}

	@Override
	public void generateConstructor(String className, List<Field> arguments, String input, int line) {
		File file = codeGeneratorModel.getFile();
		codeGeneratorModel.parse(file);
		String constructor = codeStringGeneratorService.generateConstructor(className, arguments, input);
		javaService.insertLine(file, constructor, line);
	}

	@Override
	public void generateConstructorWithBinding(String className, List<Field> arguments) {
		File file = codeGeneratorModel.getFile();
		codeGeneratorModel.parse(file);
		String constructor = codeStringGeneratorService.generateConstructorWithBinding(className, arguments);
		insertAfterField(file, constructor);
	}

	@Override
	public void generateConstructorWithBinding(String className, List<Field> arguments, String input) {
		File file = codeGeneratorModel.getFile();
		codeGeneratorModel.parse(file);
		String constructor = codeStringGeneratorService.generateConstructorWithBinding(className, arguments, input);
		insertAfterField(file, constructor);
	}

	@Override
	public void generateConstructorWithBinding(String className, List<Field> arguments, int line) {
		File file = codeGeneratorModel.getFile();
		codeGeneratorModel.parse(file);
		String constructor = codeStringGeneratorService.generateConstructorWithBinding(className, arguments);
		javaService.insertLine(file, constructor, line);
	}

	@Override
	public void generateConstructorWithBinding(String className, List<Field> arguments, String input, int line) {
		File file = codeGeneratorModel.getFile();
		codeGeneratorModel.parse(file);
		String constructor = codeStringGeneratorService.generateConstructorWithBinding(className, arguments, input);
		javaService.insertLine(file, constructor, line);
	}

	@Override
	public void generateSetter(String variableType, String variableName) {
		File file = codeGeneratorModel.getFile();
		codeGeneratorModel.parse(file, codeGeneratorModel.getFileNameWithoutExtension(), null);
		String setter = codeStringGeneratorService.generateSetter(variableType, variableName);
		int endLine = getCorrectOffset(codeGeneratorModel.getMethodEndLine());
		javaService.insertLine(file, setter, endLine);
	}

	@Override
	public void generateSetter(String variableType, String variableName, int line) {
		File file = codeGeneratorModel.getFile();
		String setter = codeStringGeneratorService.generateSetter(variableType, variableName);
		javaService.insertLine(file, setter, line);
	}

	@Override
	public void generateSetter(String variableType, String variableName, String methodName) {
		File file = codeGeneratorModel.getFile();
		codeGeneratorModel.parse(file, codeGeneratorModel.getFileNameWithoutExtension(), null);
		String setter = codeStringGeneratorService.generateSetter(variableType, variableName, methodName);
		int endLine = getCorrectOffset(codeGeneratorModel.getMethodEndLine());
		javaService.insertLine(file, setter, endLine);
	}

	@Override
	public void generateSetter(String variableType, String variableName, String methodName, int line) {
		File file = codeGeneratorModel.getFile();
		String setter = codeStringGeneratorService.generateSetter(variableType, variableName, methodName);
		javaService.insertLine(file, setter, line);
	}

	@Override
	public void generateGetter(String variableType, String variableName) {
		File file = codeGeneratorModel.getFile();
		codeGeneratorModel.parse(file, codeGeneratorModel.getFileNameWithoutExtension(), null);
		String getter = codeStringGeneratorService.generateGetter(variableType, variableName);
		int endLine = getCorrectOffset(codeGeneratorModel.getMethodEndLine());
		javaService.insertLine(file, getter, endLine);
	}

	@Override
	public void generateGetter(String variableType, String variableName, int line) {
		File file = codeGeneratorModel.getFile();
		String getter = codeStringGeneratorService.generateGetter(variableType, variableName);
		javaService.insertLine(file, getter, line);
	}

	@Override
	public void generateGetter(String variableType, String variableName, String methodName) {
		File file = codeGeneratorModel.getFile();
		codeGeneratorModel.parse(file, codeGeneratorModel.getFileNameWithoutExtension(), null);
		String getter = codeStringGeneratorService.generateGetter(variableType, variableName, methodName);
		int endLine = getCorrectOffset(codeGeneratorModel.getMethodEndLine());
		javaService.insertLine(file, getter, endLine);
	}

	@Override
	public void generateGetter(String variableType, String variableName, String methodName, int line) {
		File file = codeGeneratorModel.getFile();
		String getter = codeStringGeneratorService.generateGetter(variableType, variableName, methodName);
		javaService.insertLine(file, getter, line);
	}

	@Override
	public void generateField(AcessLevel acessLevel, boolean isStatic, boolean isFinal, List<Field> fields) {
		File file = codeGeneratorModel.getFile();
		codeGeneratorModel.parse(file);
		String field = codeStringGeneratorService.generateField(acessLevel, isStatic, isFinal, fields);
		insertAfterField(file, field);
	}

	@Override
	public void generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName,
			List<Field> arguments) {
		File file = codeGeneratorModel.getFile();
		codeGeneratorModel.parse(file, codeGeneratorModel.getFileNameWithoutExtension(), null);
		String method = codeStringGeneratorService.generateMethod(acessLevel, isStatic, returnType, methodName, arguments);
		int endLine = getCorrectOffset(codeGeneratorModel.getMethodEndLine());
		javaService.insertLine(file, method, endLine);
	}

	@Override
	public void generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName,
			List<Field> arguments, String returnValue) {
		File file = codeGeneratorModel.getFile();
		codeGeneratorModel.parse(file, codeGeneratorModel.getFileNameWithoutExtension(), null);
		String method = codeStringGeneratorService.generateMethod(acessLevel, isStatic, returnType, methodName, arguments, returnValue);
		int endLine = getCorrectOffset(codeGeneratorModel.getMethodEndLine());
		javaService.insertLine(file, method, endLine);
	}

	@Override
	public void generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName,
			List<Field> arguments, String returnValue, String body) {
		File file = codeGeneratorModel.getFile();
		codeGeneratorModel.parse(file, codeGeneratorModel.getFileNameWithoutExtension(), null);
		String method = codeStringGeneratorService.generateMethod(acessLevel, isStatic, returnType, methodName, arguments, returnValue, body);
		int endLine = getCorrectOffset(codeGeneratorModel.getMethodEndLine());
		javaService.insertLine(file, method, endLine);
	}

	@Override
	public void generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName,
			List<Field> arguments, int line) {
		File file = codeGeneratorModel.getFile();
		String method = codeStringGeneratorService.generateMethod(acessLevel, isStatic, returnType, methodName, arguments);
		javaService.insertLine(file, method, line);
	}

	@Override
	public void generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName,
			List<Field> arguments, String returnValue, int line) {
		File file = codeGeneratorModel.getFile();
		String method = codeStringGeneratorService.generateMethod(acessLevel, isStatic, returnType, methodName, arguments, returnValue);
		javaService.insertLine(file, method, line);
	}

	@Override
	public void generateMethod(AcessLevel acessLevel, boolean isStatic, String returnType, String methodName,
			List<Field> arguments, String returnValue, String body, int line) {
		File file = codeGeneratorModel.getFile();
		String method = codeStringGeneratorService.generateMethod(acessLevel, isStatic, returnType, methodName, arguments, returnValue, body);
		javaService.insertLine(file, method, line);
	}

	//Gets end of file offset if there's no constructor
	private int getCorrectOffset(int constructorEndOffset) {
		int offset = constructorEndOffset == 0 ? codeGeneratorModel.getEndOfFileOffset() - 1 : constructorEndOffset + 2;
		return offset;
	}

	//Inserts a string after the last field or after the class initial line if there's no fields
	private void insertAfterField(File file, String constructor) {
		int fieldEndOffset = codeGeneratorModel.getFieldEndLine();
		int offset = fieldEndOffset == 0 ? codeGeneratorModel.getClassInitLine() : fieldEndOffset;
		if(offset != 0) {
			javaService.insertLine(file, constructor, offset);	
		}
	}
}
