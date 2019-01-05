package pt.iscte.pidesco.codegenerator.taskExtension;

import java.io.File;

import pa.iscde.javaTasks.ext.Task;
import pa.iscde.javaTasks.ext.TasksAction;
import pt.iscte.pidesco.codegenerator.extensability.CodeStringGeneratorService;
import pt.iscte.pidesco.codegenerator.internal.CodeGeneratorActivator;
import pt.iscte.pidesco.codegenerator.internal.CodeGeneratorModel;
import pt.iscte.pidesco.codegenerator.service.CodeGeneratorService;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class TaskExtension implements TasksAction{

	@Override
	public String setDescription(Task task) {
		CodeStringGeneratorService codeGeneratorService = CodeGeneratorActivator.getInstance().getCodeGeneratorService();
		return codeGeneratorService.generateVariableName(task.getClass().getName(), CodeGeneratorService.JAVA, false);
	}

	@Override
	public void doubleClick(Task task) {
		JavaEditorServices javaEditorServices = CodeGeneratorActivator.getInstance().getJavaEditorServices();
		CodeGeneratorModel codeGeneratorModel = new CodeGeneratorModel(CodeGeneratorActivator.getInstance().getJavaEditorServices());
		File file = javaEditorServices.getOpenedFile();
		if(file != null) {
			codeGeneratorModel.parse(file);
			int position = codeGeneratorModel.getClassInitLine();
			javaEditorServices.insertLine(file, setDescription(task), position -1);
		}
	}

}
