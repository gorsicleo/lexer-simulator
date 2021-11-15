package hr.fer.oprpp1.custom.scripting.elems;

/**Class that models integer number for nodes.
 * @author gorsicleo
 *
 */
public class ElementConstantInteger extends Element {
	private int value;
	
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return Integer.toString(value);
	}
	
}
