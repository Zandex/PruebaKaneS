package generator;

/**
 * Class that will contain-represent each value with its final value type (string or variable)
 * @author demin
 *	
 */

public class Token {
	private String val;
	private String type;

	public Token(String val, String type) {
		this.val = val;
		this.type = type;
	}

	public String getVal() {
		return val;
	}

	public String getType() {
		return type;
	}
}
