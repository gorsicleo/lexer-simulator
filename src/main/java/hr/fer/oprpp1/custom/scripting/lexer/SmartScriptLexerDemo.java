package hr.fer.oprpp1.custom.scripting.lexer;

public class SmartScriptLexerDemo {

	public static void main(String[] args) {
		SmartScriptLexer lexer = new SmartScriptLexer("This is sample text.\r\n" + 
				"{$ FOR i 1 10 1 $}\r\n" + 
				"This is {$= i $}-th time this message is generated.\r\n" + 
				"{$END$}\r\n" + 
				"{$FOR i 0 10 2 $}\r\n" + 
				"sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n" + 
				"{$END$}");
		SmartScriptToken token = lexer.nextToken();
		
		while (token.getType() != SmartScriptTokenType.EOF) {
			
			System.out.println("Token type: "+token.getType().toString()+" value: "+token.getValue().toString());
			token = lexer.nextToken();
		}
		
	}
}
