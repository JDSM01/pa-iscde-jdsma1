package pt.iscte.pidesco.codegenerator;

import java.io.File;

import org.eclipse.jface.text.ITextSelection;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class CodeGeneratorModel {
	private final JavaEditorServices javaService;

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
	
	public CodeGeneratorResponse getCodeGeneratorResponseWithIfOffset() {
		File file = getFile();
		String selectionText = "";
		int offset = 0;
		if(file != null) {
			ITextSelection selection = javaService.getTextSelected(file);
			if(selection.getLength() != -1) {
				selectionText = selection.getText();
				offset = selection.getOffset() + selectionText.length() + 2;
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
}
