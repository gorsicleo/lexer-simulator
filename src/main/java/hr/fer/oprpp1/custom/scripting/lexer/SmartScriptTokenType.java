package hr.fer.oprpp1.custom.scripting.lexer;

/**Available types of tokens for imaginary language SmartScript
 * @author gorsicleo
 *
 */
public enum SmartScriptTokenType {

	/**decimal integer*/
	DOUBLE,
	
	/**signed integer*/
	INTEGER,
	
	/**function token*/
	FUNCTION,
	
	/**operator token*/
	OPERATOR,
	
	/**string token*/
	STRING,
	
	/**variable token*/
	VARIABLE,
	
	/**end-of-file token*/
	EOF,
	
	/**tag token*/
	TAG
	
	
}
