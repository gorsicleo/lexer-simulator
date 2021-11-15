package hr.fer.oprpp1.hw02.prob1;

/**
 * Class lexer is used to demonstrate lexical analysis.
 * 
 * @author gorsicleo
 *
 */
public class Lexer {
	private static final String NULLERROR = "state cannot be null!";
	private static final String ILLEGAL_CALL_EOF = "Illegal call. Cannot generate next token when EOF is reached!";
	private static final String OVERFLOW_ERROR = "Number is too large!";
	private static final String ESCAPE_ERROR = "Invalid use of escape";
	
	/**Data where is text stored for analysis*/
	private char[] data;
	
	/**Last created token*/
	private Token currentToken;
	
	/**Current index count that is pointing to char[] data of last analysed character*/
	private int currentIndex;
	
	/**State in which lexer is working, can be basic or extended*/
	private LexerState state;

	

	/**
	 * Constructor. Creates new Lexer object and sets initial values.
	 * 
	 * @param text that will be used for lexical analysis
	 */
	public Lexer(String text) {
		this();
		data = text.toCharArray();
	}

	/** Private constructor used for setting initial values. */
	private Lexer() {
		state = LexerState.BASIC;
		currentIndex = 0;
		currentToken = null;
	}

	/**
	 * Method used for manually setting lexer state.
	 * 
	 * @param state in which will lexer operate
	 * @throws NullPointerException if <code>state</code> is null.
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new NullPointerException(NULLERROR);
		}
		this.state = state;
	}

	/**
	 * Groups following characters and creates token.
	 * 
	 * @return Token for following characters.
	 */
	public Token nextToken() {
		generateNextToken();
		return currentToken;
	}

	/**
	 * Returns last created token.
	 * 
	 * @return last created token.
	 */
	public Token getToken() {
		return currentToken;
	}

	/**
	 * Method first calls methods for skipping blanks and depending on current state
	 * is generating appropriate token.
	 */
	private void generateNextToken() {
		checkForEOF();
		removeBlanks();
		if (state.equals(LexerState.BASIC)) {
			generateNextTokenBasic();
		} else {
			generateNextTokenExtended();
		}

	}

	/** Calls methods to identify token. <b>When lexer is in basic state</b> */
	private void generateNextTokenBasic() {
		if (isEndReached() || isNumber() || isWord() || isSymbol())
			return;
	}

	/** Calls methods to identify token. <b>When lexer is in extended state</b> */
	private void generateNextTokenExtended() {
		if (isEndReached() || isSymbolExtended() || isWordExtended())
			return;
	}

	/**
	 * Method creates new token of type symbol with value '#' when it encounters '#'
	 * 
	 * @return true if next character is state switcher ('#')
	 */
	private boolean isSymbolExtended() {
		if (data[currentIndex] == '#') {
			currentToken = new Token(TokenType.SYMBOL, '#');
			currentIndex++;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method checks if following characters can be grouped as one word. Method is
	 * used when lexer is working in extended mode.
	 * 
	 * @return true if following characters are possible to group as one word
	 *         (characters separated by spaces)
	 */
	private boolean isWordExtended() {
		int startOfWordIndex = currentIndex;
		while (currentIndex < data.length && data[currentIndex] != ' ' && data[currentIndex] != '#') {
			currentIndex++;
		}
		if (startOfWordIndex - currentIndex == 0) {
			return false;
		}
		String extractedWord = new String(data, startOfWordIndex, (currentIndex - startOfWordIndex));
		currentToken = new Token(TokenType.WORD, extractedWord);
		return true;
	}

	/**
	 * If token of type symbol with value of '#' has been created this method will
	 * switch state of lexer accordingly
	 */
	private void isStateSwitcher() {
		if (currentToken.equals(new Token(TokenType.SYMBOL, '#'))) {
			if (state.equals(LexerState.BASIC)) {
				state = LexerState.EXTENDED;
			} else {
				state = LexerState.BASIC;
			}
		}
	}

	/**
	 * @return
	 */
	private boolean isSymbol() {
		if (!Character.isLetter(data[currentIndex]) && !Character.isDigit(data[currentIndex])) {
			currentToken = new Token(TokenType.SYMBOL, data[currentIndex]);
			currentIndex++;
			isStateSwitcher();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method checks for escape sequence and returns true if it finds one,
	 * false otherwise.
	 * 
	 * @return true if there is valid escape sequence. False otherwise.
	 * @throws LexerException in case of invalid escape sequence.
	 */
	private boolean isEscape() {
		if (data[currentIndex] != '\\') {
			return false;
		} else {
			if (currentIndex + 1 >= data.length) {
				throw new LexerException(ESCAPE_ERROR);
			}
			if (Character.isDigit(data[currentIndex + 1]) || data[currentIndex + 1] == '\\') {
				return true;
			} else {
				throw new LexerException(ESCAPE_ERROR);
			}
		}

	}

	/**
	 * This method extracts character that is found after valid escape sequence.
	 * <b>Note: this method should only be called after method if isEscape returned
	 * true!</b>
	 * 
	 * @return character extracted after legal escape sequence.
	 */
	private char extractEscape() {
		return data[currentIndex + 1];
	}

	/**
	 * Method checks if following characters can be grouped as word or word with
	 * legal escape sequence. After checking and grouping word token is being
	 * created
	 * 
	 * @return true if following characters can be grouped as word, false otherwise.
	 */
	private boolean isWord() {

		if (Character.isLetter(data[currentIndex]) || isEscape()) {
			String extractedWord = new String("");

			while (currentIndex < data.length && (Character.isLetter(data[currentIndex]) || isEscape())) {

				if (isEscape()) {
					extractedWord += extractEscape();
					currentIndex++;
				} else {
					extractedWord += data[currentIndex];
				}
				currentIndex++;
			}
			currentToken = new Token(TokenType.WORD, extractedWord);
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Method checks if following characters can be grouped as <b>number that can be
	 * written as long type</b> legal escape sequence. After checking and grouping
	 * number token is being created
	 * 
	 * @return true if following characters can be grouped as <b>long number</b>,
	 *         false otherwise.
	 * @throws if number of grouped digits exceeds legal range for long datatype.
	 */
	private boolean isNumber() {

		int startOfNumberIndex = currentIndex;
		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
			currentIndex++;
		}
		int numberOfDigits = currentIndex - startOfNumberIndex;
		if (numberOfDigits < 20 && numberOfDigits > 0) {
			String extractedNumber = new String(data, startOfNumberIndex, numberOfDigits);
			Long value = Long.parseLong(extractedNumber);
			currentToken = new Token(TokenType.NUMBER, value);
			return true;
		} else {
			if (numberOfDigits >= 20) {
				throw new LexerException(OVERFLOW_ERROR);
			} else {
				return false;
			}
		}

	}

	/**
	 * Method checks if current index count reached length of string that is used
	 * for lexical analysis. If true EOF token is being created.
	 * 
	 * @return true if current index count is equal or greater than length of
	 *         current string that is being analysed.
	 */
	private boolean isEndReached() {
		if (currentIndex >= data.length) {
			currentToken = new Token(TokenType.EOF, null);
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Method increases internal index count every time next character is blank, new
	 * line, tabulator.
	 * 
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
	 * When EOF token is generated further calls are not legal! Method raises
	 * exception when user tries to get next token after EOF token is generated.
	 * 
	 * @throws LexerException if user tries to get next token after EOF is
	 *                        generated.
	 * 
	 */
	private void checkForEOF() {
		if (currentToken != null) {
			if (currentToken.getType() == TokenType.EOF) {
				throw new LexerException(ILLEGAL_CALL_EOF);
			}
		}
	}

}
