package pt.iscte.pidesco.codegenerator;

import java.io.File;
import java.util.Arrays;
import java.util.List;
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

import pt.iscte.pidesco.codegenerator.service.CodeGeneratorService.AcessLevel;
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
	private Button bindFieldVariableButton;
	private Button generateConstructorButton;
	private Button generateSetterButton;
	private Button generateGetterButton;
	private Button bindFieldButton;
	private Button generateMethodButton;
	private Button generateConstructorWithBindingButton;

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		viewArea.setLayout(new RowLayout(SWT.VERTICAL));
		BundleContext context = Activator.getContext();


		ServiceReference<JavaEditorServices> serviceReference = context.getServiceReference(JavaEditorServices.class);
		javaService = context.getService(serviceReference);
		codeGeneratorController = new CodeGeneratorController();
		model = new CodeGeneratorModel(javaService);

		createButtons(viewArea);
		setListeners();
	}

	private void setGetterListener() {
		generateGetterButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CodeGeneratorResponse codeGeneratorResponse = model.getCodeGeneratorResponseWithDefaultOffset();
				File file = codeGeneratorResponse.getFile();
				if(file != null) {
					Field field = model.getTypeAndVariableName(codeGeneratorResponse.getSelection());
					if(field != null) {
						parse(file);
						String setter = codeGeneratorController.generateGetter(field.getType(), field.getName());
						javaService.insertText(file, setter, model.getConstructorEndOffset(), 0);

					}				
				}
			}
		});
	}

	private void setSetterListener() {
		generateSetterButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CodeGeneratorResponse codeGeneratorResponse = model.getCodeGeneratorResponseWithDefaultOffset();
				File file = codeGeneratorResponse.getFile();
				if(file != null) {
					Field field = model.getTypeAndVariableName(codeGeneratorResponse.getSelection());
					if(field != null) {
						parse(file);
						String setter = codeGeneratorController.generateSetter(field.getType(), field.getName());
						javaService.insertText(file, setter, model.getConstructorEndOffset(), 0);

					}				
				}
			}
		});
	}

	private void setCreateConstructorListener() {
		generateConstructorButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CodeGeneratorResponse codeGeneratorResponse = model.getCodeGeneratorResponseWithDefaultOffset();
				File file = codeGeneratorResponse.getFile();
				if(file != null) {
					parse(file);
					String selection = codeGeneratorResponse.getSelection();
					List<Field> fields = model.getTypeAndVariableNameToList(selection, ";");
					String constructor = codeGeneratorController.generateConstructor(file.getName(), fields);
					javaService.insertText(file, constructor, model.getFieldEndOffset(), 0);	
				}
			}
		});
	}
	
	private void setBindFieldListener() {
		bindFieldVariableButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CodeGeneratorResponse response = model.getCodeGeneratorResponseWithEndLine();
				File file = response.getFile();
				if (file != null) {
					String selection = response.getSelection();
					List<Field> fields = model.getTypeAndVariableNameToList(selection,",");
					if(!fields.isEmpty()) {
						parse(file);
						int offset = model.getFieldEndOffset();
						String bindedField = codeGeneratorController.generateField(AcessLevel.PRIVATE, false, true, fields);
						insertBindedVariable(file, selection, response.getOffset(), fields);
						if(offset == 1) {
							javaService.insertLine(file, bindedField, response.getOffset() - 1);
						}
						else {
							javaService.insertText(file, bindedField, offset, 0);
						}
					}
				}
			}
		});
	}

	private void setBindListener() {
		bindFieldButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CodeGeneratorResponse response = model.getCodeGeneratorResponseWithEndLine();
				File file = response.getFile();
				if (file != null) {
					String selection = response.getSelection();
					List<Field> fields = model.getTypeAndVariableNameToList(selection,",");
					insertBindedVariable(file, selection, response.getOffset(), fields);
				}
			}
		});
	}

	private void setConstructorWithBindingListnener() {
		generateConstructorWithBindingButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CodeGeneratorResponse codeGeneratorResponse = model.getCodeGeneratorResponseWithDefaultOffset();
				File file = codeGeneratorResponse.getFile();
				if(file != null) {
					parse(file);
					String selection = codeGeneratorResponse.getSelection();
					List<Field> fields = model.getTypeAndVariableNameToList(selection, ";");
					String constructor = codeGeneratorController.generateConstructorWithBinding(file.getName(), fields);
					javaService.insertText(file, constructor, model.getFieldEndOffset(), 0);	
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
					String variableName = codeGeneratorController.generateVariableName(response.getSelection(), LanguageVariableType.JAVA, false);
					javaService.insertText(file, variableName, response.getOffset() , 0);
				}
			}
		});
	}

	private void setMethodListener() {
		generateMethodButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CodeGeneratorResponse codeGeneratorResponse = model.getCodeGeneratorResponseWithDefaultOffset();
				File file = codeGeneratorResponse.getFile();
				if(file != null) {
					String selection = codeGeneratorResponse.getSelection();
					SimpleMethod method = model.getMethodNameAndArguments(selection);
					if(method != null) {
						parse(file, selection.replaceAll(";", "").replaceAll(" ", ""));
						String methodType = model.getExpressionType();
						String setter = codeGeneratorController.generateMethod(AcessLevel.PRIVATE, false, methodType, method.getName(), method.getArguments());
						javaService.insertText(file, setter, model.getConstructorEndOffset(), 0);

					}				
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
		bindFieldVariableButton = new Button(viewArea, SWT.PUSH);
		bindFieldVariableButton.setText("Bind Variable with Field");
		bindFieldButton = new Button(viewArea, SWT.PUSH);
		bindFieldButton.setText("Bind Variable");
		generateConstructorButton = new Button(viewArea, SWT.PUSH);
		generateConstructorButton.setText("Create constructor");
		generateSetterButton = new Button(viewArea, SWT.PUSH);
		generateSetterButton.setText("Create Setter");
		generateGetterButton = new Button(viewArea, SWT.PUSH);
		generateGetterButton.setText("Create Getter");
		generateMethodButton = new Button(viewArea, SWT.PUSH);
		generateMethodButton.setText("Create Method");
		generateConstructorWithBindingButton = new Button(viewArea, SWT.PUSH);
		generateConstructorWithBindingButton.setText("Create constructor and bind");
	}

	private void setListeners() {
		setVariableNameListener();
		setIfListener();
		setIfNotNullListener();
		setIfNullListener();
		setBindFieldListener();
		setBindListener();
		setCreateConstructorListener();
		setSetterListener();
		setGetterListener();
		setMethodListener();
		setConstructorWithBindingListnener();
	}

	private void insertIf(IfType type) {
		CodeGeneratorResponse response = model.getCodeGeneratorResponseWithDefaultOffset();
		File file = response.getFile();
		if (file != null) {
			String selection = response.getSelection();
			String generatedIf = codeGeneratorController.generateIfCondition(selection, type);
			javaService.insertText(file, generatedIf, response.getOffset(), selection.length());
		}
	}
	
	private void insertBindedVariable(File file, String selection, int offset, List<Field> fields) {
		String bindedVariable = "";
		if(!fields.isEmpty()) {
			bindedVariable = codeGeneratorController.generateBindedVariable(fields);
		}
		else {
			bindedVariable = codeGeneratorController.generateBindedVariable(Arrays.asList(new Field("", selection)));
		}
		javaService.insertLine(file, bindedVariable, offset + 1);
	}
	

	private void parse(File file) {
		javaService.saveFile(file);
		javaService.parseFile(file, new EditorVisitor(model));
	}

	private void parse(File file, String searchExpression) {
		javaService.saveFile(file);
		javaService.parseFile(file, new EditorVisitor(model, searchExpression));
	}
}
