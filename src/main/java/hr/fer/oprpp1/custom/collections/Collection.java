package hr.fer.oprpp1.custom.collections;

/**
 * Interface Collection represents some general collection of objects.
 * 
 * @author gorsicleo
 *
 */
public interface Collection {

	/**
	 * Returns size of the collection.
	 * 
	 * @return size of the collection.
	 */
	int size();

	/**
	 * Returns true if collection is empty, false otherwise.
	 * 
	 * @return true if array is empty.
	 */
	default boolean isEmpty() {
		return size()==0;
	}

	/**
	 * Adds object <code>value</code> into collection.
	 * 
	 * @param value object
	 */
	void add(Object value);

	/**
	 * Returns true if collection contains <code>value</code> object.
	 * 
	 * @return true if collection contains value.
	 *
	 */
	public boolean contains(Object value);

	/**
	 * Removes element at <code>index</code>
	 * 
	 * @param value to be removed
	 * @return false
	 */
	public boolean remove(Object value);

	/**
	 * Converts collection to array.
	 * 
	 * @return array version of collection
	 */
	public Object[] toArray();

	/**
	 * Method calls <code>process</code> from <code>processor</code> on each element
	 * in <code>collection</code>.
	 * 
	 * @param processor
	 */
	default void forEach(Processor processor) {
		ElementsGetter elementsGetter = createElementsGetter();
		while (elementsGetter.hasNextElement()) {
			processor.process(elementsGetter.getNextElement());
		}
	}

	/**
	 * Copies all elements from collection <code>other</code> into this collection.
	 * 
	 * @param other
	 */
	default public void addAll(Collection other) {
		class ConcreteProcessor implements Processor {
			public void process(Object value) {
				add(value);
			}
		}
		Processor processor = new ConcreteProcessor();
		other.forEach(processor);
		;
	}

	/**
	 * Clears collection by setting every element to null.
	 *
	 */
	public void clear();

	/** Creates ElementsGetter object on given collection */
	public ElementsGetter createElementsGetter();

	/**
	 * Method gets all elements from given collection <code>col</code> and if
	 * <code>tester</code> accepts it those elements will be added in this
	 * <code>collection</code>. Note: another temporary {@link LinkedListIndexedCollection} is
	 * created to store accepted elemetns!
	 * 
	 * @param col as source of elemetns
	 * @param tester to accept elements
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter elementsGetter = col.createElementsGetter();
		LinkedListIndexedCollection acceptedElements = new LinkedListIndexedCollection();

		while (elementsGetter.hasNextElement()) {
			Object objectToTest = elementsGetter.getNextElement();
			if (tester.test(objectToTest) == true) {
				acceptedElements.add(objectToTest);
			}
		}
		this.addAll(acceptedElements);

	}

}
