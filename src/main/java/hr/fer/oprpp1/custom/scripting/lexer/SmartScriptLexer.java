package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
import hr.fer.oprpp1.hw02.prob1.LexerException;

/**Class {@link SmartScriptLexer} models lexical analysis for imaginary language SmartScript
 * @author gorsicleo
 *
 */
public class SmartScriptLexer {

	/**Data where is text stored for analysis*/
	private char[] data;
	
	/**Last created token*/
	private SmartScriptToken currentToken;
	
	/**Current index count that is pointing to char[] data of last analysed character*/
	private int currentIndex;
	
	/**State in which lexer is working, can be basic or extended*/
	private SmartScriptLexerState state;

	private static final String ILLEGAL_CALL_EOF = "Illegal call. Cannot generate next token when EOF is reached.";
	private static final String FUNCTION_NAME_ERROR = "Invalid function name.";
	private static final String INVALID_ESCAPE_ERROR = "Invalid use of escape.";
	private static final String MISPLACED_TOKEN_ERROR = "Misplaced token.";

	/** Internal (private) constructor to set initial values */
	private SmartScriptLexer() {
		state = SmartScriptLexerState.OUTSIDETAG;
		currentIndex = 0;
		currentToken = null;
	}

	/**
	 * Constructor. Creates new SmartScriptLexer object that will generate tokens
	 * for given String <code>text</code>.
	 * 
	 * @param text that will be used to generate tokens.
	 * 
	 */
	public SmartScriptLexer(String text) {
		this();
		data = text.toCharArray();
	}

	/**
	 * Generates next token.
	 * 
	 * @return {@link SmartScriptToken} next generated token.
	 */
	public SmartScriptToken nextToken() {
		generateNextToken();
		return currentToken;
	}

	/**
	 * Returns last generated token.
	 * 
	 * @return {@link SmartScriptToken} last generated token.
	 */
	public SmartScriptToken getToken() {
		return currentToken;
	}

	/** Generates next token depending of current state. */
	private void generateNextToken() {
		checkForEOF();
		removeBlanks();
		if (state.equals(SmartScriptLexerState.INSIDETAG)) {
			generateNextTokenInsideTag();
		} else {
			generateNextTokenOutsideTag();
		}

	}

	/**
	 * Calls methods to identify token. If none of the called methods accepts token
	 * exception is raised.
	 * @throws LexerException if none of called methods accept following characters.
	 */
	private void generateNextTokenInsideTag() {

		if (isEndReached() || isTag() || isVariable() || isArgument() || isOperator() || isFunction()) {
			return;
		}

		throw new LexerException(MISPLACED_TOKEN_ERROR);
	}

	/**
	 * Method checks if following characters can be grouped as String or number
	 * wrapped in string.
	 * 
	 * @return true if current token is of type string or number that is wrapped in
	 *         string.
	 */
	private boolean isArgument() {
		if (data[currentIndex] == '"') {
			handleStringArgument();
			return true;
		}

		if (data[currentIndex] == '-' && Character.isDigit(data[currentIndex + 1])) {
			handleNegativeNumberArgument();
			return true;
		}

		if (Character.isDigit(data[currentIndex])) {
			handleNumberArgument();
			return true;
		}

		return false;

	}

	/**
	 * Collects number as String and forwards it to numberTokenFactory method to
	 * create token.
	 */
	private void handleNumberArgument() {
		boolean isDecimalValue = false;
		String positiveNumberArgument = new String("");
		while (currentIndex < data.length && (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.')) {
			positiveNumberArgument += data[currentIndex];
			if (data[currentIndex] == '.')
				isDecimalValue = true;
			currentIndex++;
		}

		numberTokenFactory(isDecimalValue, positiveNumberArgument);
	}

	/**
	 * Takes string and creates appropriate number token (double or integer token)
	 * 
	 * @param isDecimalValue true if number is double, false if number is int
	 * @param numberArgument number represented as String
	 */
	private void numberTokenFactory(boolean isDecimalValue, String numberArgument) {
		if (isDecimalValue) {
			currentToken = new SmartScriptToken(SmartScriptTokenType.DOUBLE, numberArgument);
		} else {
			currentToken = new SmartScriptToken(SmartScriptTokenType.INTEGER, numberArgument);
		}
	}

	/**
	 * Collects number as String and forwards it to numberTokenFactory method to
	 * create token.
	 */
	private void handleNegativeNumberArgument() {
		currentIndex++;
		boolean isDecimalValue = false;
		String NegativeNumberArgument = new String("");
		NegativeNumberArgument += '-';
		while (currentIndex < data.length && (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.')) {
			NegativeNumberArgument += data[currentIndex];
			if (data[currentIndex] == '.')
				isDecimalValue = true;
			currentIndex++;
		}

		numberTokenFactory(isDecimalValue, NegativeNumberArgument);
	}

	/**
	 * Collects string and creates token of type string with collected string value.
	 * 
	 * @throws LexerException if escape is not used correctly.
	 */
	private void handleStringArgument() {
		currentIndex++;
		String stringArgument = new String("");
		while (currentIndex < data.length && data[currentIndex] != '"') {
			while (data[currentIndex] == '\\') {
				if (data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '"') {
					stringArgument += data[++currentIndex];
					currentIndex++;
				} else {
					throw new LexerException(INVALID_ESCAPE_ERROR);
				}
			}
			stringArgument += data[currentIndex];
			currentIndex++;
		}
		currentToken = new SmartScriptToken(SmartScriptTokenType.STRING, stringArgument);
		currentIndex++;
	}

	/**
	 * Method checks if following characters can be grouped as operator.
	 * 
	 * @return true if following characters are +, -, *, /, ^
	 */
	private boolean isOperator() {
		if (data[currentIndex] == '+' || data[currentIndex] == '-' || data[currentIndex] == '*'
				|| data[currentIndex] == '/' || data[currentIndex] == '^') {
			currentToken = new SmartScriptToken(SmartScriptTokenType.OPERATOR, data[currentIndex]);
			currentIndex++;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method checks if following characters can be grouped as function.
	 * 
	 * @return true if next character is @ and after it legal function name.
	 * @throws LexerException is function name is not legal
	 */
	private boolean isFunction() {
		if (data[currentIndex] == '@') {
			currentIndex++;
			int startOfFunctionNameIndex = currentIndex;
			while (currentIndex < data.length && (data[currentIndex] == '_' || Character.isDigit(data[currentIndex])
					|| Character.isLetter(data[currentIndex]))) {
				currentIndex++;
			}

			if (currentIndex - startOfFunctionNameIndex != 0) {
				String functionName = new String(data, startOfFunctionNameIndex,
						currentIndex - startOfFunctionNameIndex).toUpperCase();
				currentIndex++;
				currentToken = new SmartScriptToken(SmartScriptTokenType.FUNCTION, functionName);
				return true;
			} else {
				throw new LexerException(FUNCTION_NAME_ERROR);
			}
		}

		return false;
	}

	/**
	 * Method checks if following characters can be grouped as variable or
	 * identifier.
	 * 
	 * @return true if following characters are letters, underscores or numbers.
	 */
	private boolean isVariable() {

		if (Character.isLetter(data[currentIndex])) {
			int startOfVariableNameIndex = currentIndex;
			currentIndex++;
			while (currentIndex < data.length && (data[currentIndex] == '_' || Character.isDigit(data[currentIndex])
					|| Character.isLetter(data[currentIndex]))) {
				currentIndex++;
			}

			if (currentIndex - startOfVariableNameIndex != 0) {
				String variableName = new String(data, startOfVariableNameIndex,
						currentIndex - startOfVariableNameIndex).toUpperCase();
				currentToken = new SmartScriptToken(SmartScriptTokenType.VARIABLE, variableName);
				return true;
			}
		}

		if (data[currentIndex] == '=') {
			currentToken = new SmartScriptToken(SmartScriptTokenType.VARIABLE, "=");
			currentIndex++;
			return true;
		}
		return false;
	}

	/**
	 * When lexer is outside of tags some different rules are used for grouping
	 * characters into tokens. Method calls other methods to see if following
	 * characters are either <code>Tag</code> or <code>Text</code>
	 * 
	 */
	private void generateNextTokenOutsideTag() {
		if (isEndReached() || isTag() || isText())
			return;

		throw new LexerException(MISPLACED_TOKEN_ERROR);
	}

	/**
	 * Method checks if following characters can be grouped as text. <b>Note:
	 * applicable only when outside tags!</b>
	 * 
	 * @return true if following characters are not able to group as tag
	 */
	private boolean isText() {
		String text = new String("");
		while (currentIndex < data.length - 1 && data[currentIndex] != '{' && data[currentIndex + 1] != '$') {
			while (data[currentIndex] == '\\') {
				if (data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '{') {
					text += data[++currentIndex];
					currentIndex++;
				} else {
					throw new LexerException(INVALID_ESCAPE_ERROR);
				}
			}
			text += data[currentIndex];
			currentIndex++;
		}

		if (currentIndex == data.length - 1) {
			text += data[currentIndex++];
		}
		if (text.length() == 0) {
			return false;
		} else {
			currentToken = new SmartScriptToken(SmartScriptTokenType.STRING, text);
			return true;
		}

	}

	/**
	 * Method checks if following characters can be grouped as either start of tag
	 * or end of tag.
	 * 
	 * @return true if following characters can be grouped in format of "{$" or "$}"
	 */
	private boolean isTag() {
		if (isStartOfTag() || isEndOfTag())
			return true;
		return false;
	}

	/** @return true if following characters can be grouped in format of "{$" */
	private boolean isStartOfTag() {
		if (data[currentIndex] == '{' && data[currentIndex + 1] == '$') {
			currentIndex += 2;
			currentToken = new SmartScriptToken(SmartScriptTokenType.TAG, new String("{$"));
			state = SmartScriptLexerState.INSIDETAG;
			return true;
		}
		return false;

	}

	/** @return true if following characters can be grouped in format of "$}" */
	private boolean isEndOfTag() {
		if (data[currentIndex] == '$' && data[currentIndex + 1] == '}') {
			currentIndex += 2;
			currentToken = new SmartScriptToken(SmartScriptTokenType.TAG, new String("$}"));
			state = SmartScriptLexerState.OUTSIDETAG;
			return true;
		}
		return false;
	}

	/**
	 * Method increases index counter when it encounters ' ' or '\n' or '\t' or '\t'
	 */
	private void removeBlanks() {
		while (currentIndex < data.length) {
			char currentCharacter = data[currentIndex];
			if (currentCharacter == ' ' || currentCharacter == '\n' || currentCharacter == '\t'
					|| currentCharacter == '\r') {
				currentIndex++;
			} else {
				break;
			}
		}

	}

	/**
	 * In case when index count reaches length of string that needs to be tokenized
	 * EOF token is going to be generated.
	 * 
	 * @return true if EOF token is generated
	 */
	private boolean isEndReached() {
		if (currentIndex >= data.length) {
			currentToken = new SmartScriptToken(SmartScriptTokenType.EOF, null);
			return true;
		} else {
			return false;
		}

	}

	/**
	 * When EOF token is generated further calls are not legal! Method raises
	 * exception when user tries to get next token after EOF token is generated.
	 * 
	 * @throws SmartScriptLexerException if user tries to get next token after EOF is
	 *                        generated.
	 * 
	 */
	private void checkForEOF() {
		if (currentToken != null) {
			if (currentToken.getType() == SmartScriptTokenType.EOF) {
				throw new SmartScriptParserException(ILLEGAL_CALL_EOF);
			}
		}
	}
}
