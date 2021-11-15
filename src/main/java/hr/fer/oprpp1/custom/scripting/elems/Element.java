package hr.fer.oprpp1.custom.scripting.elems;

/**Base class representing expressions for nodes.
 * @author gorsicleo
 *
 */
public class Element {
	
	String asText() {
		return new String("");
	}
	
	@Override
	public String toString() {
		return asText();
	}

}
