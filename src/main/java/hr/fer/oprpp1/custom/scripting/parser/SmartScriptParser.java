package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptToken;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.Node;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

/**
 * Class SmartScriptParser models syntactic analysis for imaginary language
 * SmartScript. This parser generates {@link SmartScriptToken} by following
 * basic rules of this language.
 * 
 * @author gorsicleo
 *
 */
public class SmartScriptParser {

	private static final String NOT_CLOSED_TAG_ERROR = "Tags are not closed properly!";
	private static final String INVALID_TOKEN_ERROR = "Expected string or tag token!";
	private static final String INVALID_ARGUMENTS_TAG = "Tag arguments are not valid!";
	private static final String INVALID_ARGUMENTS_FOR = "For tag arguments are not valid!";
	private static final String INVALID_TAG_NAME = "Invalid tag identifier!";
	private static final String MISSING_TAG_NAME = "Tag identifier is missing!";

	/** Stack is used as storage element for nodes. */
	private ObjectStack nodesStack = new ObjectStack();

	/** Root node on which all other nodes are appended. */
	private DocumentNode documentNode = new DocumentNode();

	/** Last generated node. */
	private Node currentNode;

	/** Last recieved token from lexer. */
	private SmartScriptToken currentToken;

	/** Lexer that is used to generate tokens for processing */
	private SmartScriptLexer lexer;

	/**
	 * Helper method used to easily convert regular object array into array of
	 * Element
	 * 
	 * @param elements object array that needs to be converted into array of
	 *                 elements
	 * @return array of elements converted from array of objects
	 */
	private static Element[] elementArrayConvert(Object[] elements) {
		int size = elements.length;
		Element[] elementArray = new Element[size];

		for (int i = 0; i < size; i++) {
			if (elements[i] instanceof Element) {
				elementArray[i] = (Element) elements[i];
			}
		}

		return elementArray;
	}

	/**
	 * Constructor. Creates new SmartScriptParser and parses given document
	 * 
	 * @param String document for parsing.
	 */
	public SmartScriptParser(String doc) {
		nodesStack.push(documentNode);
		lexer = new SmartScriptLexer(doc);
		currentToken = lexer.nextToken();
		generateDocumentTree();
	}

	/**
	 * Method looks at current token and decides if it is String or Tag. Delegates
	 * job to other functions accordingly.
	 */
	private void generateDocumentTree() {
		while (currentToken.getType() != SmartScriptTokenType.EOF) {
			switch (currentToken.getType()) {

			case STRING:
				handleString(currentToken);
				break;

			case TAG:
				handleTag();
				break;

			default:
				throw new SmartScriptParserException(INVALID_TOKEN_ERROR);

			}
			currentToken = lexer.nextToken();
		}
		if (nodesStack.size() != 1) {
			throw new SmartScriptParserException(NOT_CLOSED_TAG_ERROR);
		}
	}

	/**
	 * In case current token is tag, this method fetches text token that should
	 * contain tag name and delegates job to other function.
	 */
	private void handleTag() {
		currentToken = lexer.nextToken();
		handleTagName(currentToken);

	}

	/**
	 * Method looks at token which contain tag name, decides what kind of tag it
	 * contains, and calls handler methods accordingly.
	 * 
	 * @param token that contains tag name
	 * @throws SmartScriptParserException if token name is illegal or missing
	 */
	private void handleTagName(SmartScriptToken token) {
		if (token.getType() != SmartScriptTokenType.VARIABLE) {
			throw new SmartScriptParserException(MISSING_TAG_NAME);
		}
		if (token.getValue().equals("FOR")) {
			forTag();
		}

		else if (token.getValue().equals("END")) {
			endTag();
		} else if (token.getValue().equals("=")) {
			emptyTag();
		} else {
			throw new SmartScriptParserException(INVALID_TAG_NAME);
		}
	}

	/**
	 * Method gets last object stored in stack and appends last created node as
	 * child.
	 */
	private void addChildToLastPushedNode() {
		Node lastPushedNode = (Node) nodesStack.pop();
		lastPushedNode.addChildNode(currentNode);
		nodesStack.push(lastPushedNode);
	}

	/**
	 * Method creates text node and calls method to push it to stack.
	 * 
	 * @param token that is used to generate text node.
	 */
	private void handleString(SmartScriptToken token) {
		currentNode = new TextNode(token.getValue().toString());
		addChildToLastPushedNode();
	}

	/**
	 * Method creates Echo node by collecting elements and calling appropriate
	 * functions to handle them. After creation method calls function to add node to
	 * stack.
	 */
	private void emptyTag() {
		LinkedListIndexedCollection elements = elementsFactory();
		currentNode = new EchoNode(elementArrayConvert(elements.toArray()));
		addChildToLastPushedNode();
	}

	/**Method fetches tokens until end of tag is found, all those elements are being added to List that is returned.
	 * @return LinkedListIndexedCollection of elements 
	 */
	private LinkedListIndexedCollection elementsFactory() {
		LinkedListIndexedCollection elements = new LinkedListIndexedCollection();
		Element element = null;
		currentToken = lexer.nextToken();

		while (!currentToken.getValue().equals("$}")) {
			String tokenValue = currentToken.getValue().toString();
			element = elementFactory(element, tokenValue);
			elements.add(element);
			currentToken = lexer.nextToken();
		}
		return elements;
	}

	/**Method looks at token type and decides how to create appropriate Element object.
	 * @param element 
	 * @param tokenValue
	 * @return Derivative of Element object by processing given tokenValue
	 */
	private Element elementFactory(Element element, String tokenValue) {
		switch (lexer.getToken().getType()) {

		case DOUBLE:
			element = new ElementConstantDouble(Double.valueOf(tokenValue));
			break;

		case INTEGER:
			element = new ElementConstantInteger(Integer.valueOf(tokenValue));
			break;

		case FUNCTION:
			element = new ElementFunction(tokenValue);
			break;

		case OPERATOR:
			element = new ElementOperator(tokenValue);
			break;

		case VARIABLE:
			element = new ElementVariable(tokenValue);
			break;

		case STRING:
			try {
				element = new ElementConstantDouble(Double.valueOf(tokenValue));
				break;
			} catch (NumberFormatException exception) {
				element = new ElementString(tokenValue);
				break;
			}
		default:
			throw new SmartScriptParserException(INVALID_ARGUMENTS_TAG);
		}
		return element;
	}

	/**
	 * When end tag is found this method pops last pushed object from nodes stack.
	 */
	private void endTag() {
		nodesStack.pop();
		currentToken = lexer.nextToken();
	}

	/**Method collects for tag arguments and creates new ForLoopNode and pushes it to stack.
	 * @throws SmartScriptParserException if arguments are not valid.
	 * 
	 */
	private void forTag() {
		LinkedListIndexedCollection arguments = elementsFactory();
		try {
			currentNode = new ForLoopNode((ElementVariable) arguments.get(0), (Element) arguments.get(1),
					(Element) arguments.get(2), (Element) arguments.get(3));
			addChildToLastPushedNode();
			nodesStack.push(currentNode);
		} catch (ClassCastException exception) {
			throw new SmartScriptParserException(INVALID_ARGUMENTS_FOR);
		}
	}

	@Override
	public String toString() {
		return documentNode.toString();
	}

	/**
	 * Returns root node of document.
	 * 
	 * @return DocumentNode of this parsed document
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
}
