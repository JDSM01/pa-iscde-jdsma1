package pa.iscde.demo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import pa.iscde.codegenerator.extensability.CodeGeneratorFunctionAddExtension;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;


public class AddExtensionTest implements CodeGeneratorFunctionAddExtension{

	@Override
	public void createCodeGenerationContent(Composite viewArea) {
		JavaEditorServices javaServices = Activator.getInstance().getJavaService();
		Button testButton = new Button(viewArea, SWT.PUSH);
		testButton.setText("Add extension test");
		testButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				javaServices.insertTextAtCursor("public void thisIsJustAnAddExtensionTest(){\n\n}");
			}
		});
	}

}
