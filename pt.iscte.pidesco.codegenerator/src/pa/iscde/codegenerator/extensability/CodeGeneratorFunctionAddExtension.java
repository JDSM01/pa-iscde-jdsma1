package pa.iscde.codegenerator.extensability;

import java.util.List;

/**
 * This extension allows whoever extends it to add new functionalities to the component
 * @author D01
 *
 */
public interface CodeGeneratorFunctionAddExtension {

	/**
	 * List of functionalities which contain button name, code to be generated and placement
	 */
	List<Functionality> getCodeGenerationContent();
	
	/*
	 * Different types of placement of the generated code
	 */
	public enum CodePlacementLocation{
		BEGIN_OF_CLASS, END_OF_CLASS, END_OF_FIELDS, END_OF_CONSTRUCTOR, REPLACEMENT_OF_SELECTION, AFTER_SELECTION, BEFORE_SELECTION
		,LINE_AFTER_SELECTION, LINE_BEFORE_SELECTION, CURSOR_POSITION
	}
}
