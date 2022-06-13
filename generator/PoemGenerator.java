package generator;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that will read and fill a dictionary of sentences for the generation of poems
 * @author demin
 *
 */

public class PoemGenerator {

	private HashMap<String, Rule> dicRules;

	/**
	 * 
	 * @param file File location
	 * @throws IOException The exception is generated in case of problems with the path or file
	 */
	public PoemGenerator(String file) throws IOException {
		dicRules = new HashMap<String, Rule>();
		loadRules(file);
	}

	/**
	 * Method that reads the file line by line, separating the variables with their rules.
	 * @param file File location
	 * @throws IOException The exception is generated in case of problems with the path or file
	 */
	private void loadRules(String file) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

		String RuleLine = "";
		String curLine;
		String[] splitRuleLine;
		while ((curLine = bufferedReader.readLine()) != null) {

			if (curLine.length() == 0) {
				if (RuleLine.contains(":")) {
					splitRuleLine = RuleLine.split(":");
					dicRules.put(splitRuleLine[0], new Rule(splitRuleLine[1]));
					RuleLine = "";
				} else {
					System.out.println("Error en la regla");
				}
			} else {
				if (curLine.charAt(0) == '<') {
					RuleLine += " ";
				}
				RuleLine += curLine;
			}
		}
		bufferedReader.close();
	}

	/**
	 * recursive method that sends list of tokens to receive their final values
	 * @param tokenList List of tokens to extract your information
	 * @return Final Tonken List (str/ter)
	 */
	private ArrayList<Token> genPoemToken(ArrayList<Token> tokenList) {
		ArrayList<Token> trad = new ArrayList<Token>();
		for (Token toke : tokenList) {
			if (toke.getType().equals("str")) {
				trad.add(toke);
			} else if (toke.getType().equals("ter")) {
				if (toke.getVal().equals("END")) {
					trad.add(new Token(" ", "ter"));
				}
				if (toke.getVal().equals("LINEBREAK")) {
					trad.add(new Token("" + '\n', "ter"));
				}
			} else if (toke.getType().equals("var")) {
				//System.out.println("-" + toke.getVal());  //Parameters print
				trad.addAll(genPoemToken((dicRules.get(toke.getVal())).getRandomRule()));
			}
		}
		return trad;
	}

	/**
	 * Method to print in console
	 * @param var initial rule
	 */
	public void printPoem(String var) {
		ArrayList<Token> arrpoem = genPoemToken(dicRules.get(var).getRandomRule());
		String poem = "";
		for (Token token : arrpoem) {
			poem += " " + token.getVal();
		}
		System.out.println("=================");
		System.out.println(poem);
	}

	/**
	 * Method that prints all the rules of the dictionary
	 */
	public void printRules() {
		dicRules.entrySet().forEach(rule -> {
			System.out.println("=================");
			System.out.println("RULE:" + rule.getKey());
			System.out.println("-----");
			rule.getValue().getRules().forEach(rulepice -> {
				rulepice.forEach(rulepiceone -> {
					System.out.println(rulepiceone.getVal() + ":" + rulepiceone.getType());
				});
				System.out.println("-----");
			});
		});
		System.out.println("=================");
	}

	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {		
		
		if (args.length<=0) {
			System.out.println("Error Args");
			return ;
		}
		
		String file = args[0];
		PoemGenerator test = new PoemGenerator(file);
		test.printPoem(args[1]);
		//test.printRules();
		
		//test.printPoem("POEM");
		//test.printPoem("LINE");		
		// NOTAS : EL TXT DEBE TENER AL FINAL 2 ENTERS
	}

}
