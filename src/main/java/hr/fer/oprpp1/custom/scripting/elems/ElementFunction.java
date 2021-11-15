package hr.fer.oprpp1.custom.scripting.elems;

/**Class that models function expression for nodes.
 * @author gorsicleo
 *
 */
public class ElementFunction extends Element {
	private String name;
	
	public ElementFunction(String name) {
		this.name = name;
	}
	
	@Override
	public String asText() {
		return name;
	}
	
	@Override
	public String toString() {
		return "@"+asText();
	}
}
