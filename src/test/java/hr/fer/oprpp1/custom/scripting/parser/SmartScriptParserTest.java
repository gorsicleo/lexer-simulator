package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;

public class SmartScriptParserTest {
	
	@Test
	public void invalidIdentifierTest() {
		String docBody = "{$INVALID I 1 2 3 $}{&END$}";
		assertThrows(SmartScriptParserException.class, ()->new SmartScriptParser(docBody));
		
	}
	
	@Test
	public void noTagIdentifierTest() {
		String docBody = "{$ I 1 2 3 $}{&END$}";
		assertThrows(SmartScriptParserException.class, ()->new SmartScriptParser(docBody));
	}
	
	@Test
	public void validIdentifiersForNodeTest() {
		String docBody = "{$FOR I 1 2 3 $}{$END$}";
		SmartScriptParser parser = new SmartScriptParser(docBody);
		assertEquals(new ForLoopNode(new ElementVariable("I"), new ElementConstantInteger(1), new ElementConstantInteger(2), new ElementConstantInteger(3)), parser.getDocumentNode().getChild(0));
	}
	
	@Test
	public void validIdentifiersEchoNodeTest() {
		String docBody = "{$=I @sin *$}";
		SmartScriptParser parser = new SmartScriptParser(docBody);
		Element[] elements= {new ElementVariable("I"),new ElementFunction("SIN"),new ElementOperator("*")};
		assertEquals(new EchoNode(elements), parser.getDocumentNode().getChild(0));
	}
	
	@Test
	public void noClosingTagTest() {
		String docBody = "{$FOR I 1 2 3 $}";
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody) );
	}
	
	
	

}
