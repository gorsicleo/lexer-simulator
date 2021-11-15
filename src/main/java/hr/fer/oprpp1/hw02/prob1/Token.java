package hr.fer.oprpp1.hw02.prob1;

/**
 * Class that represents one <i>token</i> or in other words token is a lexical
 * unit that groups one or more consecutive characters from input text.
 * 
 * @author gorsicleo
 */
public class Token {
	
	/**Token type (EOF;WORD;SYMBOL;NUMBER)*/
	private TokenType tokenType;
	
	/**Value that token is holding*/
	private Object tokenValue;

	
	/**Constructor.
	 * @param type type of token
	 * @param value that token is holding
	 */
	public Token(TokenType type, Object value) {
		tokenType = type;
		tokenValue = value;
	}

	
	/**Returns value that token is holding.
	 * @return value that token is holding
	 */
	public Object getValue() {
		return tokenValue;
	}

	/**Returns token type.
	 * @return type of this token.
	 */
	public TokenType getType() {
		return tokenType;
	}
}
