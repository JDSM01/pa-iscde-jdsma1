package pt.iscte.pidesco.codegenerator;

public class Field {

	private final String type, name;

	public Field(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}
}
