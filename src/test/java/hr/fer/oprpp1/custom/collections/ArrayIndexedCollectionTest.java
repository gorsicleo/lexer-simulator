package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {
	
	//constructor tests
	
	
	@Test
	public void testConstructorWhenCollectionIsNull() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(2,null));
	}
	@Test
	public void testConstructorWhenInitialCapacityIsLessThanOne() {
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(-3));
	}
	@Test
	public void testConstructorWhenInitialCapacityIsSmallerThanCollectionCapacity() {
		ArrayIndexedCollection instanceToBeUsedInConstructor = new ArrayIndexedCollection(20);
		ArrayIndexedCollection instance2 = new ArrayIndexedCollection(3,instanceToBeUsedInConstructor);
		assertEquals(20,instance2.size() );
	}
	
	@Test
	public void testConstructorForCopyingFromOtherCollection() {
		ArrayIndexedCollection instanceToBeCopied = new ArrayIndexedCollection(3);
		for (int i=0;i<3;i++) {
		   instanceToBeCopied.add(Integer.valueOf(i));
		}
		ArrayIndexedCollection instanceToBeCopiedInto = new ArrayIndexedCollection(5,instanceToBeCopied);
		for (int i=0;i<5;i++) {
			if (i<3) {
				assertEquals(Integer.valueOf(i), instanceToBeCopiedInto.get(i));
			} else {
				assertEquals(null, instanceToBeCopiedInto.get(i));
				}
		}
	}
	
	//method add tests
	
	@Test
	public void testAddingNullElement() {
		ArrayIndexedCollection instance1 = new ArrayIndexedCollection();
			assertThrows(NullPointerException.class, () -> instance1.add(null));
		}
	
	@Test
	public void testAddingWhenArrayIsFull() {
		ArrayIndexedCollection verySmallCollection = new ArrayIndexedCollection(1);
		verySmallCollection.add(new Object());
		assertEquals(1,verySmallCollection.size());
		verySmallCollection.add(new Object());
		assertEquals(2, verySmallCollection.size());
		verySmallCollection.add(new Object());
		assertEquals(4, verySmallCollection.size());
		
	}
	
	//method get tests
	
	@Test
	public void getWhenInvalidIndex() {
		ArrayIndexedCollection instance = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class,() -> instance.get(-5));
		assertThrows(IndexOutOfBoundsException.class,() -> instance.get(16));
		}
	
	@Test
	public void getWhenValidIndex() {
		ArrayIndexedCollection instance = new ArrayIndexedCollection();
		assertEquals(null, instance.get(0));
		Object testObject = new Object();
		instance.add(testObject);
		assertEquals(testObject, instance.get(0));
	}
	
	//method clear tests
	
	@Test
	public void clearCollectionTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(new Object());
		collection.add(new Object());
		collection.clear();
		assertEquals(null, collection.get(0));
		assertEquals(null, collection.get(1));
	}
	
	//method size test
	@Test
	public void sizeTest() {
		ArrayIndexedCollection defaultCollection = new ArrayIndexedCollection();
		assertEquals(16, defaultCollection.size());
		ArrayIndexedCollection bitBiggerCollection = new ArrayIndexedCollection(24);
		assertEquals(24, bitBiggerCollection.size());
	}
	
	//method insert tests
	
	@Test
	public void insertWhenIndexIsInvalid() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class,()->collection.insert(new Object(), 32));
		assertThrows(IndexOutOfBoundsException.class,()->collection.insert(new Object(), -1));
	}
	
	@Test
	public void insertWhenValueIsNull() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class,()->collection.insert(null, 0));
	}
	
	@Test
	public void insertWhenArgumentsAreValid() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		Object testObject = new Object();
		collection.insert(testObject, 1);
		assertEquals(testObject, collection.get(1));
	}
	
	@Test
	public void insertWhenCollectionIsFull() {
		ArrayIndexedCollection thisCollectionWillBeFull = new ArrayIndexedCollection(10);
		for (int i=0;i<10;i++) {
			thisCollectionWillBeFull.add(Integer.valueOf(i));
		}
		thisCollectionWillBeFull.insert(Integer.valueOf(-100), 3);
		assertEquals(20, thisCollectionWillBeFull.size());
		assertEquals(-100, thisCollectionWillBeFull.get(3));
		assertEquals(3, thisCollectionWillBeFull.get(4));
		assertEquals(2, thisCollectionWillBeFull.get(2));
	}
	
	//method remove tests
	
	@Test
	public void removeWhenInvalidIndex() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, ()-> collection.remove(-5));
		assertThrows(IndexOutOfBoundsException.class, ()-> collection.remove(25));
	}
	
	@Test
	public void removeWhenValidIndex() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(10);
		collection.add(new Object());
		collection.insert(new Object(), 5);
		collection.remove(0);
		collection.remove(5);
		assertEquals(null,collection.get(0));
		assertEquals(null,collection.get(5));
	}
	
	
	

}
