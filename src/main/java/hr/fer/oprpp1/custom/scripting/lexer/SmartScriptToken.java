package hr.fer.oprpp1.custom.scripting.lexer;


/**Models simple token for imaginary language SmartScrpit.
 * @author gorsicleo
 *
 */
public class SmartScriptToken {

	/**Token type (EOF;WORD;SYMBOL;NUMBER)*/
	private SmartScriptTokenType tokenType;
	
	/**Value that token is holding*/
	private Object tokenValue;

	
	/**Constructor.
	 * @param type type of token
	 * @param value that token is holding
	 */
	public SmartScriptToken(SmartScriptTokenType type, Object value) {
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
	public SmartScriptTokenType getType() {
		return tokenType;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof SmartScriptToken)) {
			return false;
		}
		
		SmartScriptToken token =  (SmartScriptToken) other;
		
		return (tokenType.equals(token.getType()) && tokenValue.toString().equals(token.getValue().toString()));
	}
}
