package hr.fer.oprpp1.custom.scripting.elems;

/**Class that models operator expression for nodes.
 * @author gorsicleo
 *
 */
public class ElementOperator extends Element {
	private String symbol;
	
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	@Override
	public String asText() {
		return symbol;
	}
	
}
