package pt.iscte.pidesco.codegenerator.internal;

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
	private String methodType;
	private int endOfFile;

	public CodeGeneratorModel(JavaEditorServices javaService) {
		this.javaService = javaService;
	}

	//Returns open filed
	public File getFile() {
		return javaService.getOpenedFile();
	}

	//Returns a ITextSelection in the opened file
	public ITextSelection getSelection() {
		File file = getFile();
		if(getFile() != null) {
			javaService.getTextSelected(file);
		}
		return null;
	}

	//Returns the offset of the selection in the opened file
	public int getOffset() {
		ITextSelection selection = getSelection();
		if(selection != null) {
			return selection.getOffset();
		}
		return -1;
	}

	//Returns the ITextSelection string from the opened file
	public String getTextSelected() {
		ITextSelection selection = getSelection();
		if(selection != null) {
			return selection.getText();
		}
		return "";
	}

	//Returns the name of the opened file
	public String getFileName() {
		File file = getFile();
		if(file != null) {
			return file.getName();
		}
		return "";
	}

	//Returns the name of the opened file without the extension
	public String getFileNameWithoutExtension() {
		String fileName = getFileName();
		return fileName.substring(0, fileName.lastIndexOf("."));
	}

	//Returns the name of a given file name without the extension
	public String getFileNameWithoutExtension(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf("."));
	}

	//Returns an object containing the opened file, the string of the ITextSelection and the sum of the offset and length 
	//of the ITextSelection
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

	//Returns an object contain the opened file, the string of the ITextSelection and the end line of the ITextSelection
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

	//Returns an object containing the opened file, the string of the ITextSelection and the offset of the ITextSelection
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

	//Returns the ending offset of a constructor and erases the saved offset
	public int getConstructorEndOffset() {
		int offset = constructorEndOffset;
		constructorEndOffset = 0;
		return offset;
	}

	//Sets the ending offset of a constructor
	public void setConstructorEndOffset(int constructorEndLine) {
		this.constructorEndOffset = constructorEndLine;
	}

	//Returns the offset of the last field and erases the saved offset
	public int getFieldEndOffset() {
		int offset = fieldEndOffset;
		fieldEndOffset = 0;
		return offset;
	}

	//Sets the offset of the last field
	public void setFieldEndOffset(int fieldEndLine) {
		this.fieldEndOffset = fieldEndLine;
	}

	//Returns the type of a certain method and erases the saved method type
	public String getMethodType() {
		String method = methodType;
		methodType = null;
		return method;
	}

	//Sets the method type of a certain method
	public void setMethodType(String expressionType) {
		this.methodType = expressionType;
	}

	//Returns the offset of the last line of a certain file
	public int getEndOfFileOffset() {
		return endOfFile;
	}

	//Sets the offset of the last line of a certain file
	public void setEndOfFileOffset(int endOfFile) {
		this.endOfFile = endOfFile;
	}
}
