package pa.iscde.codegenerator.extensability;

import pa.iscde.codegenerator.extensability.CodeGeneratorFunctionAddExtension.CodePlacementLocation;

public class Functionality {
	
	private final String buttonName;
	private final String generatedCode;
	private final CodePlacementLocation placementLocation;
	private final int line;

	public Functionality(String buttonName, String generatedCode, CodePlacementLocation placementLocation) {
		this.buttonName = buttonName;
		this.generatedCode = generatedCode;
		this.placementLocation = placementLocation;
		this.line = -1;
	}
	
	public Functionality(String buttonName, String generatedCode, int line) {
		this.buttonName = buttonName;
		this.generatedCode = generatedCode;
		this.placementLocation = null;
		this.line = line;
	}

	public String getButtonName() {
		return buttonName;
	}
	
	public String getGeneratedCode() {
		return generatedCode;
	}
	
	public CodePlacementLocation getPlacementLocation() {
		return placementLocation;
	}
	
	public int getLine() {
		return line;
	}
	
	public boolean hasPlacementLocation() {
		return placementLocation != null;
	}
}
