package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * A node representing a command which generates some textual output
 * dynamically. It inherits from Node class.
 * 
 * @author gorsicleo
 *
 */
public class EchoNode extends Node {
	private Element[] elements;

	/**Constructor.
	 * @param elements to put in echo node.
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**Returns elements of echo node.
	 * @return elements of echo node.
	 */
	public Element[] getElements() {
		return elements;
	}

	@Override
	public String toString() {
		String echoNodeString = new String("{$= ");

		for (Element element : getElements()) {
			echoNodeString += element + " ";
		}

		echoNodeString += "$}";

		return echoNodeString;
	}

}
