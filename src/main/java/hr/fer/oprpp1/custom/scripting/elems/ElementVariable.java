package hr.fer.oprpp1.custom.scripting.elems;

/**Class that models variable expression in nodes.
 * @author gorsicleo
 *
 */
public class ElementVariable extends Element {
	private String name;
	
	public ElementVariable(String name) {
		this.name = name;
	}
	
	@Override
	public String asText() {
		return name;
	}

}
