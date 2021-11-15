package hr.fer.oprpp1.custom.scripting.nodes;

/**A node representing an entire document. It inherits from Node class.
 * @author gorsicleo
 *
 */
public class DocumentNode extends Node {
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DocumentNode) {
			DocumentNode other = (DocumentNode) obj;
			return toString().equals(other.toString()) ;
		} else {
			return false;
		}
	}


}
