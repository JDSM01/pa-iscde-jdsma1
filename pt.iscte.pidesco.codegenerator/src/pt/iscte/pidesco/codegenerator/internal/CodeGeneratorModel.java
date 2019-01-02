package pt.iscte.pidesco.codegenerator.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.text.ITextSelection;

import pt.iscte.pidesco.codegenerator.wrappers.Field;
import pt.iscte.pidesco.codegenerator.wrappers.SimpleMethod;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
/**
 * This class is responsible to give the view the required objects and information.
 * @author D01
 *
 */
public class CodeGeneratorModel {
	private final JavaEditorServices javaService;
	private int constructorEndOffset;
	private int fieldEndLine;
	private String methodType;
	private int endOfFile;
	private int variableOffset;
	private int startLine;

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
	public CodeGeneratorResponse getCodeGeneratorResponseWithLengthOffset() {
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
				return new Field(selectionSplitted[0].trim(), selectionSplitted[1].trim().replaceAll(";", "")); 
			}
		}
		return new Field("","");
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
					fields.add(new Field(splittedField[length - 2].trim(), splittedField[length - 1].trim()
							.replaceAll(";", "")));
				}
			}
			return fields;
		}
		return Collections.emptyList();
	}

	//Turns a selection into a SimpleMethod(name and arguments) if possible
	public SimpleMethod getMethodNameAndArguments(String selection) {
		if(selection.equals("")) {
			return new SimpleMethod("defaultMethod", Collections.emptyList());
		}
		String[] splittedSelection = selection.split("\\(");
		if(splittedSelection.length == 2) {
			String[] arguments = splittedSelection[1].replaceAll("\\);", "").replaceAll("\\)", "").split(",");
			List<Field> argumentsList = new ArrayList<>();
			for(String argumentName : arguments) {
				if(!argumentName.equals("")) {
					argumentsList.add(new Field("Object", argumentName.trim()));
				}
			}
			return new SimpleMethod(splittedSelection[0].trim(), argumentsList);
		}
		return new SimpleMethod("", Collections.emptyList());
	}

	//Returns the ending offset of a constructor and erases the saved offset
	public int getConstructorEndOffset() {
		int offset = constructorEndOffset;
		constructorEndOffset = 0;
		return offset;
	}

	//Sets the ending offset of a constructor
	public void setConstructorEndOffset(int constructorEndOffset) {
		this.constructorEndOffset = constructorEndOffset;
	}

	//Returns the line of the last field and erases the saved offset
	public int getFieldEndLine() {
		int line = fieldEndLine;
		fieldEndLine = 0;
		return line;
	}

	//Sets the offset of the last field
	public void setFieldEndLine(int fieldEndLine) {
		this.fieldEndLine = fieldEndLine;
	}

	public int getClassInitLine() {
		return startLine;
	}

	public void setClassInitLine(int startLine) {
		this.startLine = startLine;
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
		int endOffset = endOfFile;
		endOfFile = 0;
		return endOffset;
	}

	//Sets the offset of the last line of a certain file
	public void setEndOfFileOffset(int endOfFile) {
		this.endOfFile = endOfFile;
	}

	//Returns the offset of the searched variable
	public int getVariableOffset() {
		int offset = variableOffset;
		variableOffset = 0;
		return offset;
	}

	//Sets the offset of the searched variable
	public void setVariableOffset(int variableOffset) {
		this.variableOffset = variableOffset;
	}

	//Uses an implementation of ASTVisitor to search the specified file and set the necessary values in the model
	public void parse(File file) {
		javaService.saveFile(file);
		javaService.parseFile(file, new EditorVisitor(this));
	}

	//Uses an implementation of ASTVisitor to search for a method and/or variable in the specified file and set the values in the model
	public void parse(File file, String methodSearchExpression, String variableSearchExpression) {
		javaService.saveFile(file);
		javaService.parseFile(file, new EditorVisitor(this, methodSearchExpression, variableSearchExpression));
	}

	//Uses an implementation of ASTVisitor to search for a method and/or variable in the specified file and set the values in the model
	public void parse(File file, String methodSearchExpression, String variableSearchExpression, int line) {
		javaService.saveFile(file);
		javaService.parseFile(file, new EditorVisitor(this, methodSearchExpression, variableSearchExpression, line));
	}

	//Gets the extensions of the project
	public IConfigurationElement[] getExtensions() {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		return reg.getConfigurationElementsFor("pt.iscte.pidesco.codegenerator.codeGeneration");
	}

	//Returns the correct error value depending on the value of the generatedString and position or null if there's no error
	public String getError(String generatedString, int position) {
		if(position == 0) {
			return "No class statement found";
		}
		else if(generatedString == null) {
			return "Method not implemented";
		} else if(generatedString.equals("")) {
			return "Selection was not valid";
		}
		return null;
	}
}
