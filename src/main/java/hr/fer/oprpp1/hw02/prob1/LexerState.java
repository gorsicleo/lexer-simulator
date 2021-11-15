package hr.fer.oprpp1.hw02.prob1;

/**States in which lexer can be found.
 * @author gorsicleo
 *
 */
public enum LexerState {

	/**State in which lexer tokenizes until # character is found*/
	BASIC,
	
	/**State in which lexer tokenizes after # character is found*/
	EXTENDED
}
