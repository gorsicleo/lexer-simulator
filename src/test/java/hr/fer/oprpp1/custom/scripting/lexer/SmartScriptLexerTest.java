package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class SmartScriptLexerTest {
	
	
	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}
	
	@Test
	public void testNullInput() {
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
	}
	
	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}
	
	
	
	
	@Test
	public void testCombinedInput() {
		// Lets check for several symbols...
		SmartScriptLexer lexer = new SmartScriptLexer("This is sample text.\r\n" + 
				"{$ FOR i 1 10 1 $}\r\n" + 
				"This is {$= i $}-th time this message is generated.\r\n" + 
				"{$END$}");
		SmartScriptToken currentToken = lexer.nextToken();

		assertEquals(new SmartScriptToken(SmartScriptTokenType.STRING, "This is sample text.\r\n"), currentToken);
		currentToken = lexer.nextToken();
		assertEquals(new SmartScriptToken(SmartScriptTokenType.TAG, "{$"), currentToken);
		currentToken = lexer.nextToken();
		assertEquals(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "FOR"), currentToken);
		currentToken = lexer.nextToken();
		assertEquals(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "I"), currentToken);
		currentToken = lexer.nextToken();
		assertEquals(new SmartScriptToken(SmartScriptTokenType.INTEGER, "1"), currentToken);
		currentToken = lexer.nextToken();
		assertEquals(new SmartScriptToken(SmartScriptTokenType.INTEGER, "10"), currentToken);
		currentToken = lexer.nextToken();
		assertEquals(new SmartScriptToken(SmartScriptTokenType.INTEGER, "1"), currentToken);
		currentToken = lexer.nextToken();
		assertEquals(new SmartScriptToken(SmartScriptTokenType.TAG, "$}"), currentToken);
		currentToken = lexer.nextToken();
		assertEquals(new SmartScriptToken(SmartScriptTokenType.STRING, "This is "), currentToken);
		currentToken = lexer.nextToken();
		assertEquals(new SmartScriptToken(SmartScriptTokenType.TAG, "{$") , currentToken);
		currentToken = lexer.nextToken();
		assertEquals(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "="), currentToken);
		currentToken = lexer.nextToken();
		assertEquals(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "I"), currentToken);
		currentToken = lexer.nextToken();
		assertEquals(new SmartScriptToken(SmartScriptTokenType.TAG, "$}"), currentToken);
		currentToken = lexer.nextToken();
		assertEquals(new SmartScriptToken(SmartScriptTokenType.STRING, "-th time this message is generated.\r\n"), currentToken);
		currentToken = lexer.nextToken();
		assertEquals(new SmartScriptToken(SmartScriptTokenType.TAG, "{$"), currentToken);
		currentToken = lexer.nextToken();
		assertEquals(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "END"), currentToken);
		currentToken = lexer.nextToken();
		assertEquals(new SmartScriptToken(SmartScriptTokenType.TAG, "$}"), currentToken);
		
	}
	
	}
	
	

