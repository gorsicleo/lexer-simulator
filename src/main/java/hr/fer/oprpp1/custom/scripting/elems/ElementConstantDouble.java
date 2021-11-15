package hr.fer.oprpp1.custom.scripting.elems;

/**Class that models double (decimal) number for node.
 * @author gorsicleo
 *
 */
public class ElementConstantDouble extends Element {
	private double value;
	
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return Double.toString(value);
	}
}
