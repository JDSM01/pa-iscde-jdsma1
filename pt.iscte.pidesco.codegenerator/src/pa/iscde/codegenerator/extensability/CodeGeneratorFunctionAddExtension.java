package pa.iscde.codegenerator.extensability;

import org.eclipse.swt.widgets.Composite;

/**
 * This extension allows whoever extends it to add new functionalities to the component
 * @author D01
 *
 */
public interface CodeGeneratorFunctionAddExtension {

	/**
	 * Place where the extension will create the new functionalities
	 * @param viewArea area where the view components will be added
	 */
	void createCodeGenerationContent(Composite viewArea);
}
