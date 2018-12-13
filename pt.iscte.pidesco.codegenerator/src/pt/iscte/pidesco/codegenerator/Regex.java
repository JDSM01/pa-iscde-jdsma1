package pt.iscte.pidesco.codegenerator;

//An object containing the string to be replaced and the string to replace it
public class Regex {

	private String replaceFrom;
	private String replaceTo;

	public Regex(String replaceFrom, String replaceTo) {
		this.replaceFrom = replaceFrom;
		this.replaceTo = replaceTo;
	}

	public String getReplaceFrom() {
		return replaceFrom;
	}

	public String getReplaceTo() {
		return replaceTo;
	}
}
