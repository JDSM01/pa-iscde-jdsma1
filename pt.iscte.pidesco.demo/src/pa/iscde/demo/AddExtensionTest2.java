package pa.iscde.demo;

import java.util.ArrayList;
import java.util.List;

import pa.iscde.codegenerator.extensability.CodeGeneratorFunctionAddExtension;
import pa.iscde.codegenerator.extensability.Functionality;

public class AddExtensionTest2 implements CodeGeneratorFunctionAddExtension{

	@Override
	public List<Functionality> getCodeGenerationContent() {
		List<Functionality> functionalities = new ArrayList<>();
		functionalities.add(new Functionality("addTest2", "public void thisIsJustAnAddExtensionTest2(){\n\n}", 
				CodePlacementLocation.LINE_AFTER_SELECTION));
		functionalities.add(new Functionality("addTest3", "public void thisIsJustAnAddExtensionTest2(){\n\n}",
				10));
		return functionalities;
	}

}
