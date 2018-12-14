package pt.iscte.pidesco.codegenerator.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import pt.iscte.pidesco.codegenerator.service.CodeGeneratorService;
import pt.iscte.pidesco.codegenerator.service.CodeGeneratorService.AcessLevel;
import pt.iscte.pidesco.codegenerator.service.CodeGeneratorService.IfType;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;


/**
 * This class is responsible for handling everything related to the view. This includes clicks, selections, creation of visual
 * objects and listeners.
 * @author D01
 *
 */
public class CodeGeneratorView implements PidescoView{
	private final static String ORIGINAL_TAG = "original";
	private final static String NO_FILE_OPENED_ERROR = "There's no open file";
	private final static int INITIAL_UNIQUE_NAME = 1;
	private JavaEditorServices javaService;
	private CodeGeneratorService currentCodeGeneratorService;
	private CodeGeneratorModel model;
	private Button generateSetterButton;
	private Button generateGetterButton;
	private Button generateMethodButton;
	private Button generateConstructorWithBindingButton;
	private Button generateConstructorButton;
	private Button bindFieldButton;
	private Button bindFieldVariableButton;
	private Button generateIfNullButton;
	private Button generateIfNotNullButton;
	private Button generateIfButton;
	private Button generateVariableNameButton;
	private Map<String, CodeGeneratorService> extensionServicesMap;
	private Label label;

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		viewArea.setLayout(new RowLayout(SWT.VERTICAL));
		javaService = CodeGeneratorActivator.getInstance().getJavaEditorServices();
		currentCodeGeneratorService = CodeGeneratorActivator.getInstance().getCodeGeneratorService();

		model = new CodeGeneratorModel(javaService);
		createButtons(viewArea);
		setListeners();
		createExtensions(viewArea);
		createErrorLabel(viewArea);
	}

	private SelectionAdapter setGetterListener() {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CodeGeneratorResponse codeGeneratorResponse = model.getCodeGeneratorResponseWithDefaultOffset();
				File file = codeGeneratorResponse.getFile();
				if(file != null) {
					Field field = model.getTypeAndVariableName(codeGeneratorResponse.getSelection());
					String fileName = model.getFileNameWithoutExtension(file.getName());
					parse(file, fileName, null);
					String setter = currentCodeGeneratorService.generateGetter(field.getType(), field.getName());
					int offset = model.getConstructorEndOffset();
					offset = offset == 0 ? model.getEndOfFileOffset() - 2 : offset + 1;
					insertText(file, setter, offset, 0);
				}
				else {
					setErrorMessage(NO_FILE_OPENED_ERROR);
				}
			}
		};
	}

	private SelectionAdapter setSetterListener() {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CodeGeneratorResponse codeGeneratorResponse = model.getCodeGeneratorResponseWithDefaultOffset();
				File file = codeGeneratorResponse.getFile();
				if(file != null) {
					Field field = model.getTypeAndVariableName(codeGeneratorResponse.getSelection());
					String fileName = model.getFileNameWithoutExtension(file.getName());
					parse(file, fileName, null);
					String setter = currentCodeGeneratorService.generateSetter(field.getType(), field.getName());
					int offset = model.getConstructorEndOffset();
					offset = offset == 0 ? model.getEndOfFileOffset() - 2 : offset + 1;
					insertText(file, setter, offset, 0);
				}
				else {
					setErrorMessage(NO_FILE_OPENED_ERROR);
				}
			}
		};
	}

	private SelectionAdapter setCreateConstructorListener() {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CodeGeneratorResponse codeGeneratorResponse = model.getCodeGeneratorResponseWithDefaultOffset();
				File file = codeGeneratorResponse.getFile();
				if(file != null) {
					parse(file);
					String selection = codeGeneratorResponse.getSelection();
					List<Field> fields = model.getTypeAndVariableNameToList(selection, ";");
					String fileName = model.getFileNameWithoutExtension(file.getName());
					String constructor = currentCodeGeneratorService.generateConstructor(fileName, fields);
					insertText(file, constructor, model.getFieldEndOffset() + 1, 0);	
				}
				else {
					setErrorMessage(NO_FILE_OPENED_ERROR);
				}
			}
		};
	}

	private SelectionAdapter setBindFieldListener() {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CodeGeneratorResponse response = model.getCodeGeneratorResponseWithEndLine();
				File file = response.getFile();
				if (file != null) {
					String selection = response.getSelection();
					List<Field> fields = model.getTypeAndVariableNameToList(selection,",");
					parse(file);
					int offset = model.getFieldEndOffset();
					String bindedField = currentCodeGeneratorService.generateField(AcessLevel.PRIVATE, false, true, fields);
					insertBindedVariable(file, selection, response.getOffset(), fields);
					if(offset == 1) {
						insertLine(file, bindedField, response.getOffset() - 1);
					}
					else {
						insertText(file, bindedField, offset, 0);
					}
				}
				else {
					setErrorMessage(NO_FILE_OPENED_ERROR);
				}
			}
		};
	}

	private SelectionAdapter setBindListener() {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CodeGeneratorResponse response = model.getCodeGeneratorResponseWithEndLine();
				File file = response.getFile();
				if (file != null) {
					String selection = response.getSelection();
					List<Field> fields = model.getTypeAndVariableNameToList(selection,",");
					insertBindedVariable(file, selection, response.getOffset(), fields);
				}
				else {
					setErrorMessage(NO_FILE_OPENED_ERROR);
				}
			}
		};
	}

	private SelectionAdapter setConstructorWithBindingListnener() {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CodeGeneratorResponse codeGeneratorResponse = model.getCodeGeneratorResponseWithDefaultOffset();
				File file = codeGeneratorResponse.getFile();
				if(file != null) {
					parse(file);
					String selection = codeGeneratorResponse.getSelection();
					List<Field> fields = model.getTypeAndVariableNameToList(selection, ";");
					String fileName = model.getFileNameWithoutExtension(file.getName());
					String constructor = currentCodeGeneratorService.generateConstructorWithBinding(fileName, fields);
					insertText(file, constructor, model.getFieldEndOffset() + 1, 0);	
				}
				else {
					setErrorMessage(NO_FILE_OPENED_ERROR);
				}
			}
		};
	}

	private SelectionAdapter setIfNullListener() {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				insertIf(IfType.NULL);
			}
		};
	}

	private SelectionAdapter setIfNotNullListener() {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				insertIf(IfType.NOT_NULL);
			}
		};
	}

	private SelectionAdapter setIfListener() {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				insertIf(IfType.CONDITION);
			}
		};
	}

	private SelectionAdapter setVariableNameListener() {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CodeGeneratorResponse response = model.getCodeGeneratorResponseWithLengthOffset();
				File file = response.getFile();
				if (file != null) {
					String variableName = currentCodeGeneratorService.generateVariableName(response.getSelection(), CodeGeneratorService.JAVA, false);
					insertText(file, variableName, response.getOffset(), 0);
				}
				else {
					setErrorMessage(NO_FILE_OPENED_ERROR);
				}
			}
		};
	}

	private SelectionAdapter setMethodListener() {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CodeGeneratorResponse codeGeneratorResponse = model.getCodeGeneratorResponseWithDefaultOffset();
				File file = codeGeneratorResponse.getFile();
				if(file != null) {
					String selection = codeGeneratorResponse.getSelection();
					SimpleMethod method = model.getMethodNameAndArguments(selection);
					String fileName = model.getFileNameWithoutExtension(file.getName());
					parse(file, fileName, selection.replaceAll(";", "").replaceAll(" ", ""));
					String methodType = model.getMethodType();
					int offset = model.getConstructorEndOffset();
					offset = offset == 0 ? model.getEndOfFileOffset() - 2 : offset;
					String setter = currentCodeGeneratorService.generateMethod(AcessLevel.PRIVATE, false, methodType, method.getName(), method.getArguments());
					insertText(file, setter, offset + 1, 0);
				}
				else {
					setErrorMessage(NO_FILE_OPENED_ERROR);
				}
			}
		};
	}

	private void createExtensions(Composite viewArea) {
		IConfigurationElement[] elements = model.getExtensions();
		if(elements.length > 0) {
			extensionServicesMap = new HashMap<>();	//Create hashmap to handle the different services extensions
			extensionServicesMap.put(ORIGINAL_TAG, currentCodeGeneratorService); //Saves the current service

			//Radio Button creation
			Composite composite = new Composite(viewArea, SWT.NONE);
			composite.setLayout(new RowLayout(SWT.HORIZONTAL));
			new Label(composite, SWT.NONE).setText("Extensions: ");
			createRadioButton(composite, ORIGINAL_TAG, true);

			//To avoid not unique extension names
			List<String> extensionsNames = new ArrayList<>();
			extensionsNames.add(ORIGINAL_TAG);
			for(IConfigurationElement element : elements) {
				String name = element.getAttribute("name");
				String uniqueName = getUniqueName(extensionsNames, name, INITIAL_UNIQUE_NAME);
				extensionsNames.add(uniqueName);
				try {
					CodeGeneratorService codeGeneratorController = (CodeGeneratorService) element.createExecutableExtension("class");
					extensionServicesMap.put(uniqueName, codeGeneratorController);
					createRadioButton(composite, uniqueName, false);
				} catch (CoreException e1) {
					e1.printStackTrace();
				}
			} 
		}
	}

	private String getUniqueName(List<String> extensionsNames, String name, int notUniqueNumber) {
		String uniqueName = name;
		for(String extensionName : extensionsNames) {
			if(extensionName.equals(name)) {
				if(notUniqueNumber != INITIAL_UNIQUE_NAME) {
					name = name.substring(0, name.length() - String.valueOf(notUniqueNumber).length());
				}
				uniqueName = name + notUniqueNumber;
				uniqueName = getUniqueName(extensionsNames, uniqueName, notUniqueNumber + 1);
			}
		}
		return uniqueName;
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

	private void createRadioButton(Composite composite, String name, boolean select) {
		Button radioButton = new Button(composite, SWT.RADIO);
		radioButton.setText(name);
		radioButton.setSelection(select);
		radioButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				currentCodeGeneratorService = extensionServicesMap.get(name);
			}});
	}

	private void setListeners() {
		generateVariableNameButton.addSelectionListener(setVariableNameListener());
		generateIfButton.addSelectionListener(setIfListener());
		generateIfNotNullButton.addSelectionListener(setIfNotNullListener());
		generateIfNullButton.addSelectionListener(setIfNullListener());
		bindFieldVariableButton.addSelectionListener(setBindFieldListener());
		bindFieldButton.addSelectionListener(setBindListener());
		generateConstructorButton.addSelectionListener(setCreateConstructorListener());
		generateSetterButton.addSelectionListener(setSetterListener());
		generateGetterButton.addSelectionListener(setGetterListener());
		generateMethodButton.addSelectionListener(setMethodListener());
		generateConstructorWithBindingButton.addSelectionListener(setConstructorWithBindingListnener());
	}

	private void createErrorLabel(Composite viewArea) {
		Composite composite = new Composite(viewArea, SWT.NONE);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
		label = new Label(composite, SWT.NONE);
		label.setForeground(composite.getDisplay().getSystemColor(SWT.COLOR_RED));
	}

	private void insertIf(IfType type) {
		CodeGeneratorResponse response = model.getCodeGeneratorResponseWithDefaultOffset();
		File file = response.getFile();
		if (file != null) {
			String selection = response.getSelection();
			String generatedIf = currentCodeGeneratorService.generateIfCondition(selection, type);
			insertText(file, generatedIf, response.getOffset(), selection.length());
		}
	}

	private void insertBindedVariable(File file, String selection, int offset, List<Field> fields) {
		String bindedVariable = "";
		if(!fields.isEmpty()) {
			bindedVariable = currentCodeGeneratorService.generateBindedVariable(fields);
		}
		else {
			bindedVariable = currentCodeGeneratorService.generateBindedVariable(Arrays.asList(new Field("", selection)));
		}
		insertLine(file, bindedVariable, offset + 1);
	}

	private void insertLine(File file, String generatedString, int offset) {
		if(getError(generatedString) != null) {
			setErrorMessage(getError(generatedString));
		} else {
			setErrorMessage("");
			javaService.insertLine(file, generatedString, offset);
		}
	}

	private void insertText(File file, String generatedString, int offset, int length) {
		if(getError(generatedString) != null) {
			setErrorMessage(getError(generatedString));
		} else {
			setErrorMessage("");
			javaService.insertText(file, generatedString, offset, length);
		}
	}

	private String getError(String generatedString) {
		System.out.println(generatedString);
		if(generatedString == null) {
			return "Method not implemented";
		} else if(generatedString.equals("")) {
			return "Selection was not valid";
		}
		return null;
	}

	private void parse(File file) {
		javaService.saveFile(file);
		javaService.parseFile(file, new EditorVisitor(model));
	}

	private void parse(File file, String methodSearchExpression, String variableSearchExpression) {
		javaService.saveFile(file);
		javaService.parseFile(file, new EditorVisitor(model, methodSearchExpression, variableSearchExpression));
	}

	private void setErrorMessage(String message) {
		if(!label.getText().equals(message)) {
			label.setText(message);
			label.requestLayout();
		}
	}
}
