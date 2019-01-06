package pa.iscde.codegenerator.searchExtension;

import java.util.Collections;
import java.util.List;


import pa.iscde.codegenerator.internal.CodeGeneratorActivator;
import pa.iscde.codegenerator.internal.CodeGeneratorModel;
import pa.iscde.search.extensibility.SearchProvider;
import pa.iscde.search.model.MatchResult;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class SearchExtension implements SearchProvider{

	@Override
	public List<MatchResult> searchFor(String type, String input) {
		if(type.equals("Variable")) {
			ProjectBrowserServices projectBrowserServices = CodeGeneratorActivator.getInstance().getBrowserService();
			CodeGeneratorModel codeGeneratorModel = new CodeGeneratorModel(CodeGeneratorActivator.getInstance().getJavaEditorServices());
			codeGeneratorModel.searchParse(projectBrowserServices.getRootPackage(), input);
			return codeGeneratorModel.getSearchResults();
		}
		return Collections.emptyList();
	}
}
