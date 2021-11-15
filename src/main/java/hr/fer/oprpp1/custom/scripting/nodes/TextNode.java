package hr.fer.oprpp1.custom.scripting.nodes;

/**A node representing a piece of textual data. It inherits from Node class.
 * @author gorsicleo
 *
 */
public class TextNode extends Node{
	private String text;
	
	/**Constructor.
	 * @param text to set value of text node.
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	/**Returns value of text node.
	 * @return String value of text node.
	 */
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return getText();
	}

}
