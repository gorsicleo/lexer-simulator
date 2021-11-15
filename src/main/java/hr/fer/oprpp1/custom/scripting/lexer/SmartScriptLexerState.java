package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * States in which SmartScriptLexer can work. INSIDETAG is when lexer is
 * tokenizing characters that are between {& and &} otherwise it is in
 * OUTSIDETAG state.
 * 
 * @author gorsicleo
 *
 */
public enum SmartScriptLexerState {

	/**When lexer is tokenizing between {$ and $}*/
	INSIDETAG,

	/**When lexer is not tokenizing between {$ and $}*/
	OUTSIDETAG,
}
