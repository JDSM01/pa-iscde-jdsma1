package pt.iscte.pidesco.codegenerator;

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
