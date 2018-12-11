package pt.iscte.pidesco.codegenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.text.ITextSelection;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class CodeGeneratorModel {
	private final JavaEditorServices javaService;
	private int constructorEndOffset;
	private int fieldEndOffset;
	private String expressionType;
	private int endOfFile;

	public CodeGeneratorModel(JavaEditorServices javaService) {
		this.javaService = javaService;
	}

	public File getFile() {
		return javaService.getOpenedFile();
	}

	public ITextSelection getSelection() {
		File file = getFile();
		if(getFile() != null) {
			javaService.getTextSelected(file);
		}
		return null;
	}

	public int getOffset() {
		ITextSelection selection = getSelection();
		if(selection != null) {
			return selection.getOffset();
		}
		return -1;
	}

	public String getTextSelected() {
		ITextSelection selection = getSelection();
		if(selection != null) {
			return selection.getText();
		}
		return "";
	}

	public String getFileName() {
		File file = getFile();
		if(file != null) {
			return file.getName();
		}
		return "";
	}

	public String getFileNameWithoutExtension() {
		String fileName = getFileName();
		return fileName.substring(0, fileName.lastIndexOf("."));
	}

	public String getFileNameWithoutExtension(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf("."));
	}

	public CodeGeneratorResponse getCodeGeneratorResponseWithOffset() {
		File file = getFile();
		String selectionText = "";
		int offset = 0;
		if(file != null) {
			ITextSelection selection = javaService.getTextSelected(file);
			if(selection.getLength() != -1) {
				selectionText = selection.getText();
				offset = selection.getOffset() + selection.getLength();
			}
		}
		return new CodeGeneratorResponse(file, selectionText, offset);
	}

	public CodeGeneratorResponse getCodeGeneratorResponseWithEndLine() {
		File file = getFile();
		String selectionText = "";
		int endLine = 0;
		if(file != null) {
			ITextSelection selection = javaService.getTextSelected(file);
			if(selection.getLength() != -1) {
				selectionText = selection.getText();
				endLine = selection.getEndLine();
			}
		}
		return new CodeGeneratorResponse(file, selectionText, endLine);
	}

	public CodeGeneratorResponse getCodeGeneratorResponseWithDefaultOffset() {
		File file = getFile();
		String selectionText = "";
		int offset = -1;
		if(file != null) {
			ITextSelection selection = javaService.getTextSelected(file);
			if(selection.getLength() != -1) {
				selectionText = selection.getText();
				offset = selection.getOffset();
			}
		}
		return new CodeGeneratorResponse(file, selectionText, offset);
	}

	//Turns a selection into a field (if possible)
	public Field getTypeAndVariableName(String selection) {
		if(selection != null) {
			String[] selectionSplitted = selection.split(" ");
			if(selectionSplitted.length == 2) {
				return new Field(selectionSplitted[0].trim(), selectionSplitted[1].trim()); 
			}
		}
		return null;
	}

	//Turns a selection into a list of fields (if possible), given a split String
	public List<Field> getTypeAndVariableNameToList(String selection, String splitString) {
		if(selection != null) {
			List<Field> fields = new ArrayList<>();
			String[] selectionSplitted = selection.split(splitString);
			for(String field : selectionSplitted) {
				String[] splittedField = field.split(" ");
				int length = splittedField.length;
				if(length >= 2) {
					fields.add(new Field(splittedField[length - 2].trim(), splittedField[length - 1].trim()));
				}
			}
			return fields;
		}
		return Collections.emptyList();
	}

	//Turns a selection into a SimpleMethod(name and arguments) if possible
	public SimpleMethod getMethodNameAndArguments(String selection) {
		String[] splittedSelection = selection.split("\\(");
		if(splittedSelection.length == 2) {
			String[] arguments = splittedSelection[1].replaceAll("\\);", "").split(",");
			List<Field> argumentsList = new ArrayList<>();
			for(String argumentName : arguments) {
				argumentsList.add(new Field("Object", argumentName.trim()));
			}
			return new SimpleMethod(splittedSelection[0].trim(), argumentsList);
		}
		return null;
	}

	public int getConstructorEndOffset() {
		int offset = constructorEndOffset;
		constructorEndOffset = 0;
		return offset;
	}

	public void setConstructorEndOffset(int constructorEndLine) {
		this.constructorEndOffset = constructorEndLine;
	}

	public int getFieldEndOffset() {
		int offset = fieldEndOffset;
		fieldEndOffset = 0;
		return offset;
	}

	public void setFieldEndOffset(int fieldEndLine) {
		this.fieldEndOffset = fieldEndLine;
	}

	public String getExpressionType() {
		String expression = expressionType;
		expressionType = null;
		return expression;
	}

	public void setExpressionType(String expressionType) {
		this.expressionType = expressionType;
	}

	public int getEndOfFile() {
		return endOfFile;
	}

	public void setEndOfFile(int endOfFile) {
		this.endOfFile = endOfFile;
	}
}
