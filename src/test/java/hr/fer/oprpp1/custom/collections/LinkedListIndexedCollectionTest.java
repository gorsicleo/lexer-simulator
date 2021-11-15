package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {

	// method add test

	@Test
	public void testAddingNullElement() {
		LinkedListIndexedCollection instance = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> instance.add(null));
	}

	@Test
	public void testAddingNonNullElement() {
		LinkedListIndexedCollection instance = new LinkedListIndexedCollection();
		for (int i = 0; i < 10; i++) {
			instance.add(Integer.valueOf(i));
			assertEquals(Integer.valueOf(i), instance.get(i));
		}
	}

	// method get tests

	@Test
	public void getWhenInvalidIndex() {
		LinkedListIndexedCollection instance = new LinkedListIndexedCollection();
		instance.add(new Object());
		instance.add(new Object());
		assertThrows(IndexOutOfBoundsException.class, () -> instance.get(-5));
		assertThrows(IndexOutOfBoundsException.class, () -> instance.get(16));
	}

	@Test
	public void getWhenValidIndex() {
		LinkedListIndexedCollection instance = new LinkedListIndexedCollection();
		Object testObject1 = new Object();
		Object testObject2 = new Object();
		instance.add(testObject1);
		instance.add(testObject2);
		assertEquals(testObject1, instance.get(0));
		assertEquals(testObject2, instance.get(1));
	}

	// method clear tests

	@Test
	public void clearCollectionTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(new Object());
		collection.add(new Object());
		collection.clear();
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(0));
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(1));
	}

	// method size test
	@Test
	public void sizeTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		assertEquals(0, collection.size());
		collection.add(Integer.valueOf(0));
		collection.add(Integer.valueOf(1));
		collection.add(Integer.valueOf(2));
		collection.remove(1);
		assertEquals(2, collection.size());
	}

	// method insert tests

	@Test
	public void insertWhenIndexIsInvalid() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> collection.insert(new Object(), 32));
		assertThrows(IndexOutOfBoundsException.class, () -> collection.insert(new Object(), -1));
	}

	@Test
	public void insertWhenValueIsNull() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> collection.insert(null, 0));
	}

	@Test
	public void insertWhenArgumentsAreValid() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		Object testObject = new Object();
		collection.insert(testObject, 0);
		assertEquals(testObject, collection.get(0));
		for (int i = 0; i < 10; i++) {
			collection.add(Integer.valueOf(i));
		}
		collection.insert(testObject, 5);
		assertEquals(Integer.valueOf(3), collection.get(4));
		assertEquals(testObject, collection.get(5));
		assertEquals(Integer.valueOf(4), collection.get(6));

	}

	// method remove tests

	@Test
	public void removeWhenInvalidIndex() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(-5));
		assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(25));
	}

	@Test
	public void removeWhenValidIndex() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(new Object());
		collection.add(new Object());
		collection.remove(0);
		collection.remove(0);
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(0));
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(1));
		
	}

}
