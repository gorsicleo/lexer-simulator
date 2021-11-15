package hr.fer.oprpp1.custom.scripting.elems;

/**Class that models string expression for nodes
 * @author gorsicleo
 *
 */
public class ElementString extends Element {
	private String value;
	
	public ElementString(String value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return value;
	}

}
