package generator;

import java.util.ArrayList;

/**
 * Class that will store all the rules-sentences of 1 Variable <>
 * @author demin
 *
 */
public class Rule {

	
	private ArrayList<ArrayList<Token>> rules;

	public Rule(String line) {
		rules = new ArrayList<ArrayList<Token>>();
		setRules(line);
	}

	public ArrayList<ArrayList<Token>> getRules() {
		return rules;
	}
	
	/**
	 * Class that will return a random combination of its rules
	 * @return List of token(s)
	 */
	public ArrayList<Token> getRandomRule() {
		ArrayList<Token> RandomRules = new ArrayList<Token>();
		int numRandom = 0;
		for (ArrayList<Token> pieceRule : rules) {
			numRandom = (int) (Math.random() * (pieceRule.size() - 0)) + 0;
			RandomRules.add(pieceRule.get(numRandom));
		}
		return RandomRules;
	}

	/**
	 * Class in charge of cutting the text string into several parts that are dependent on each other
	 * @param line line text string that has the variable after the :
	 */
	private void setRules(String line) {
		if (line.isEmpty()) {
			return;
		}
		String[] splitRulesLine = line.split(" ");
		for (int i = 0; i < splitRulesLine.length; i++) {
			if (splitRulesLine[i].length() > 0) {
				rules.add(getTokens(splitRulesLine[i]));
			}
		}
	}

	/**
	 * Class in charge of generating the tokens for each rule
	 * @param line Trimmed text string with multiple options separated by (|)
	 * @return Optional token list
	 */
	private ArrayList<Token> getTokens(String line) {
		ArrayList<Token> tokenList = new ArrayList<Token>();

		String word = "";
		char let = ' ';
		boolean isvar = false;
		boolean isterminal = false;
		for (int i = 0; i < line.length(); i++) {
			let = line.charAt(i);

			if (let == '<') {
				isvar = true;
				continue;
			}
			if (let == '$') {
				isterminal = true;
				continue;
			}
			if (i == line.length() - 1 && let != '>') {
				word = word + let;
			}

			if (let == '|' || let == '>' || i == line.length() - 1) {
				if (!word.isEmpty()) {
					if (isvar) {
						tokenList.add(new Token(word, "var"));
						word = "";
						isvar = false;
					} else if (isterminal) {
						tokenList.add(new Token(word, "ter"));
						word = "";
						isterminal = false;
					} else {
						tokenList.add(new Token(word, "str"));
						word = "";
					}
				}
			} else {
				word += let;
			}
		}
		return tokenList;
	}
}
