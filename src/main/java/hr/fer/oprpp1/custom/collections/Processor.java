package hr.fer.oprpp1.custom.collections;

/**The Processor is a
 * model of an object capable of performing some operation on the passed object.
 * @author Leo Goršić
 *
 */
public interface Processor {

	/**Performs some operation on <code>value</code>
	 * @param value
	 */
	public void process(Object value);
}
