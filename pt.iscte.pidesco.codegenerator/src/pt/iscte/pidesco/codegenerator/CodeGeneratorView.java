package pt.iscte.pidesco.codegenerator;

import java.io.File;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.codegenerator.service.CodeGeneratorService.IfType;
import pt.iscte.pidesco.codegenerator.service.CodeGeneratorService.LanguageVariableType;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class CodeGeneratorView implements PidescoView{
	private Button generateVariableNameButton;
	private Button generateIfButton;
	private CodeGeneratorController codeGeneratorController;
	private JavaEditorServices javaService;
	private CodeGeneratorModel model;
	private Button generateIfNotNullButton;
	private Button generateIfNullButton;
	private Button bindVariableButton;
	private Button createConstructorButton;

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		viewArea.setLayout(new RowLayout(SWT.VERTICAL));
		BundleContext context = Activator.getContext();

		ServiceReference<JavaEditorServices> serviceReference = context.getServiceReference(JavaEditorServices.class);
		javaService = context.getService(serviceReference);
		codeGeneratorController = new CodeGeneratorController();
		model = new CodeGeneratorModel(javaService);

		createButtons(viewArea);
		setVariableNameListener();
		setIfListener();
		setIfNotNullListener();
		setIfNullListener();
		setBindListener();
		setCreateConstructorListener();
	}

	private void setCreateConstructorListener() {
		createConstructorButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String constructor = codeGeneratorController.generateConstructor(model.getFileName());
				javaService.insertTextAtCursor(constructor);
			}
		});
	}

	private void setBindListener() {
		bindVariableButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CodeGeneratorResponse response = model.getCodeGeneratorResponseWithEndLine();
				File file = response.getFile();
				if (file != null) {
					String bindedVariable = codeGeneratorController.generateBindedVariable(response.getSelection());
					if(!bindedVariable.equals("")) {
						javaService.insertLine(file, bindedVariable, response.getOffset() + 1);
					}
				}
			}
		});
	}

	private void setIfNullListener() {
		generateIfNullButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				insertIf(IfType.NULL);
			}
		});
	}

	private void setIfNotNullListener() {
		generateIfNotNullButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				insertIf(IfType.NOT_NULL);
			}
		});
	}

	private void setIfListener() {
		generateIfButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				insertIf(IfType.CONDITION);
			}
		});
	}

	private void setVariableNameListener() {
		generateVariableNameButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CodeGeneratorResponse response = model.getCodeGeneratorResponseWithOffset();
				File file = response.getFile();
				if (file != null) {
					String variableName = codeGeneratorController.generateVariableName(response.getSelection(), LanguageVariableType.JAVA);
					javaService.insertText(file, variableName, response.getOffset() , 0);
				}
			}
		});
	}

	private void createButtons(Composite viewArea) {
		generateVariableNameButton = new Button(viewArea, SWT.PUSH);
		generateVariableNameButton.setText("Generate Variable Name");
		generateIfButton = new Button(viewArea, SWT.PUSH);
		generateIfButton.setText("Generate If");
		generateIfNotNullButton = new Button(viewArea, SWT.PUSH);
		generateIfNotNullButton.setText("Generate If not null");
		generateIfNullButton = new Button(viewArea, SWT.PUSH);
		generateIfNullButton.setText("Generate If null");
		bindVariableButton = new Button(viewArea, SWT.PUSH);
		bindVariableButton.setText("Bind Variable");
		createConstructorButton = new Button(viewArea, SWT.PUSH);
		createConstructorButton.setText("Create constructor");
	}

	private void insertIf(IfType type) {
		CodeGeneratorResponse response = model.getCodeGeneratorResponseWithIfOffset();
		File file = response.getFile();
		if (file != null) {
			String selection = response.getSelection();
			String generatedIf = codeGeneratorController.generateIfCondition(selection, type);
			javaService.insertText(file, generatedIf, response.getOffset(), 0);
		}
	}
}
