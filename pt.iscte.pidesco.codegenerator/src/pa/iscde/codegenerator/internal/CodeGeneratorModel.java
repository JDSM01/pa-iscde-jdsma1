package pa.iscde.codegenerator.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.text.ITextSelection;

import pa.iscde.codegenerator.wrappers.Field;
import pa.iscde.codegenerator.wrappers.SimpleMethod;
import pa.iscde.search.model.MatchResult;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
/**
 * This class is responsible to give the view the required objects and information.
 * @author D01
 *
 */
public class CodeGeneratorModel {
	private static final String DEFAULT_METHOD_TYPE = "void";
	private final JavaEditorServices javaService;
	private int methodEndLine;
	private int fieldEndLine;
	private String methodType;
	private int endOfFile;
	private int variableOffset;
	private int startLine;
	private List<MatchResult> searchResults;

	public CodeGeneratorModel(JavaEditorServices javaService) {
		this.javaService = javaService;
		this.methodType = DEFAULT_METHOD_TYPE;
	}

	//Returns open filed
	public File getOpenedFile() {
		return javaService.getOpenedFile();
	}

	//Returns a ITextSelection in the opened file
	public ITextSelection getSelection() {
		File file = getOpenedFile();
		if(file != null) {
			return javaService.getTextSelected(file);
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
		File file = getOpenedFile();
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
		File file = getOpenedFile();
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
		File file = getOpenedFile();
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
		File file = getOpenedFile();
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
				String type = selectionSplitted[0].trim();
				String name = selectionSplitted[1].trim().replaceAll(";", "");
				return buildField(type,name);
			}
		}
		return new Field("","");
	}

	private Field buildField(String type, String name) {
		if(name.matches(".*\\d+.*")) { //is number
			if(name.contains(".")) {
				type = "float";
			}else {
				type = "int";
			}
			name = "number";
		}
		else if(name.startsWith("\"")) { //is string
			type = "String";
			name = name.replaceAll("\"", ""); 
		}
		return new Field(type, name);
	}

	//Turns a selection into a list of fields (if possible), given a regex
	public List<Field> getTypeAndVariableNameToList(String selection, String regex) {
		if(selection != null) {
			List<Field> fields = new ArrayList<>();
			String[] selectionSplitted = selection.split(regex);
			for(String field : selectionSplitted) {
				String[] splittedField = field.split(" ");
				int length = splittedField.length;
				if(length >= 2) {
					fields.add(buildField(splittedField[length - 2].trim(), splittedField[length - 1].trim()
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
					argumentsList.add(buildField("Object", argumentName));
				}
			}
			return new SimpleMethod(splittedSelection[0].trim(), argumentsList);
		}
		return new SimpleMethod("", Collections.emptyList());
	}

	//Returns the ending line of a method and erases the saved line
	public int getMethodEndLine() {
		int endLine = methodEndLine;
		methodEndLine = 0;
		return endLine;
	}

	//Sets the ending line of a method
	public void setMethodEndLine(int endLine) {
		this.methodEndLine = endLine;
	}

	//Returns the line of the last field and erases the saved line
	public int getFieldEndLine() {
		int line = fieldEndLine;
		fieldEndLine = 0;
		return line;
	}

	//Sets the line of the last field
	public void setFieldEndLine(int fieldEndLine) {
		this.fieldEndLine = fieldEndLine;
	}

	//Returns the class statement line and erases the saved value
	public int getClassInitLine() {
		int line = startLine;
		startLine = 0;
		return line;
	}

	//Sets the line of the class statement
	public void setClassInitLine(int startLine) {
		this.startLine = startLine;
	}

	//Returns the type of a certain method and erases the saved method type
	public String getMethodType() {
		String method = methodType;
		methodType = DEFAULT_METHOD_TYPE;
		return method;
	}

	//Sets the method type of a certain method
	public void setMethodType(String expressionType) {
		if(methodType.equals(DEFAULT_METHOD_TYPE)) {
			this.methodType = expressionType;
		}
	}

	//Returns the line of the last line of a certain file
	public int getEndOfFileLine() {
		int endLine = endOfFile;
		endOfFile = 0;
		return endLine;
	}

	//Sets the line of the last line of a certain file
	public void setEndOfFileLine(int endOfFile) {
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

	//Gets the function replacement extension of the project
	public IConfigurationElement[] getFunctionReplacementExtension() {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		return reg.getConfigurationElementsFor("pa.iscde.codegenerator.codeGenerationReplacement");
	}

	//Gets the function add extension of the project
	public IConfigurationElement[] getFunctionAddExtension() {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		return reg.getConfigurationElementsFor("pa.iscde.codegenerator.codeGenerationAdd");
	}

	//Returns the correct error value depending on the value of the generatedString and position or null if there's no error
	public String getError(String generatedString, int position) {
		if(position <= 0) {
			return "No class statement found";
		}
		else if(generatedString == null) {
			return "Method not implemented";
		} else if(generatedString.equals("")) {
			return "Selection was not valid";
		}
		return null;
	}

	//Search Integration methods

	public void searchParse(final SourceElement rootPackage, String input) {
		if (rootPackage.isClass()) {
			File file = rootPackage.getFile();
			if(file != null) {
				javaService.parseFile(file, new EditorVisitor(this, input, file));
			}
		} else {
			for (SourceElement c : ((PackageElement) rootPackage).getChildren()) {
				searchParse(c, input);
			}
		}
	}

	//Adds the matchResult to the list of searchResults
	public void addSearchResult(MatchResult matchResult) {
		if(searchResults == null) {
			searchResults = new ArrayList<>();
		}
		searchResults.add(matchResult);
	}

	//Gets the matchResults from the search and resets the list
	public List<MatchResult> getSearchResults(){
		if(searchResults != null) {
			List<MatchResult> matchResults = searchResults;
			searchResults = Collections.emptyList();
			return matchResults;
		}
		return Collections.emptyList();
	}
}
