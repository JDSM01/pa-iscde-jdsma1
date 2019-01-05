package pa.iscde.codegenerator.taskExtension;

import java.io.File;

import pa.iscde.codegenerator.extensability.CodeStringGeneratorService;
import pa.iscde.codegenerator.internal.CodeGeneratorActivator;
import pa.iscde.codegenerator.internal.CodeGeneratorModel;
import pa.iscde.codegenerator.service.CodeGeneratorService;
import pa.iscde.javaTasks.ext.Task;
import pa.iscde.javaTasks.ext.TasksAction;
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
