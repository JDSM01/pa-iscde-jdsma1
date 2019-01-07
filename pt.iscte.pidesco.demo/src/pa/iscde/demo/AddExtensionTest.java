package pa.iscde.demo;

import java.util.ArrayList;
import java.util.List;

import pa.iscde.codegenerator.extensability.CodeGeneratorFunctionAddExtension;
import pa.iscde.codegenerator.extensability.Functionality;


public class AddExtensionTest implements CodeGeneratorFunctionAddExtension{

	@Override
	public List<Functionality> getCodeGenerationContent() {
		List<Functionality> functionalities = new ArrayList<>();
		functionalities.add(new Functionality("addTest1", "public void thisIsJustAnAddExtensionTest(){\n\n}", 
				CodePlacementLocation.END_OF_FIELDS));
		return functionalities;
	}

}
