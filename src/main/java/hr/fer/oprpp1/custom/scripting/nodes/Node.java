package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Base class for all graph nodes.
 * 
 * @author User
 *
 */
public class Node {
	private ArrayIndexedCollection childrenCollection;
	private int numberOfChildren = 0;

	/**
	 * Adds child into node collection of children with time complexity O(N). If
	 * internal collection is empty new one will be created.
	 * 
	 * @param child node to be added into collection
	 * 
	 */
	public void addChildNode(Node child) {
		if (childrenCollection == null) {
			childrenCollection = new ArrayIndexedCollection();
		}
		numberOfChildren++;
		childrenCollection.add(child);
	}

	/**
	 * @return number of children nodes that are added into collection.
	 */
	public int numberOfChildren() {
		return numberOfChildren;
	}

	/**
	 * Returns child node at given index.
	 * 
	 * @param index of child to return
	 * @return Node that is at given index
	 * @throws IndexOutOfBoundsException when index is greater of number of children
	 *                                   in collection or smaller than 0
	 */
	public Node getChild(int index) {
		if (index < 0 || index >= numberOfChildren) {
			throw new IndexOutOfBoundsException();
		}

		return (Node) childrenCollection.get(index);

	}

	@Override
	public String toString() {
		String sourceString = new String("");

		for (int i = 0; i < numberOfChildren(); i++) {
			sourceString += getChild(i).toString();
		}

		return sourceString;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Node)) {
			return false;
		}
		Node node = (Node) other;

		return toString().equals(node.toString());
	}
}
