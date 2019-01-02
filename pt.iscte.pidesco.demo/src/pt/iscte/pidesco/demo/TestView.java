package pt.iscte.pidesco.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import pt.iscte.pidesco.codegenerator.extensability.CodeStringGeneratorService;
import pt.iscte.pidesco.codegenerator.extensability.CodeStringGeneratorService.AcessLevel;
import pt.iscte.pidesco.codegenerator.service.CodeGeneratorService;
import pt.iscte.pidesco.codegenerator.wrappers.Field;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class TestView implements PidescoView{

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		// TODO Auto-generated method stub
		viewArea.setLayout(new RowLayout(SWT.HORIZONTAL));
		JavaEditorServices javaServices = Activator.getInstance().getJavaService();
		CodeGeneratorService codeGeneratorService = Activator.getInstance().getCodeGeneratorService();
		CodeStringGeneratorService stringGeneratorService = Activator.getInstance().getStringGeneratorService();
		Label label = new Label(viewArea, SWT.NONE);
		label.setImage(imageMap.get("prettysmile.png"));
		Button stringServiceTest = new Button(viewArea, SWT.PUSH);
		Button codeServiceTest = new Button(viewArea, SWT.PUSH);
		stringServiceTest.setText("String Service");
		codeServiceTest.setText("Code Service");
		stringServiceTest.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				List<Field> fields = new ArrayList<Field>();
				fields.add(new Field("String", "teste"));
				String method = stringGeneratorService.generateMethod(AcessLevel.PRIVATE, false, "String", 
						"stringServiceTest",fields);
				javaServices.insertTextAtCursor(method);
			}
		});
		codeServiceTest.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				List<Field> fields = new ArrayList<Field>();
				fields.add(new Field("String", "teste2"));
				codeGeneratorService.generateMethod(AcessLevel.PRIVATE, false, "String", 
						"codeServiceTest", fields);
			}
		});
	}
}
