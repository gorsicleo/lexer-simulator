package hr.fer.oprpp1.hw02.prob1;

/**Enumeration of all token types defined for <code>Lexer</code>
 * @author gorsicleo
 *
 */
public enum TokenType {
	
	/**This is sign for end of file: no more tokens are available!*/
	EOF, 
	
	/**Regular word*/
	WORD, 
	
	/**Number that could be represented as long integer*/
	NUMBER, 
	
	/**Every individual character that is left after eliminating cases for words, numbers and blanks*/
	SYMBOL
}
