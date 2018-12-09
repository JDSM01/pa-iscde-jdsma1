package pt.iscte.pidesco.codegenerator;

import java.io.File;

public class CodeGeneratorResponse {
	private final File file;
	private final String selection;
	private final int offset;

	public CodeGeneratorResponse(File file, String selection, int offset) {
		this.file = file;
		this.selection = selection;
		this.offset = offset;
	}

	public File getFile() {
		return file;
	}

	public String getSelection() {
		return selection;
	}

	public int getOffset() {
		return offset;
	}

}
