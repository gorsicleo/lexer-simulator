package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class ObjectStackTest {

	@Test
	public void pushTest() {
		ObjectStack stack = new ObjectStack();
		for (int i=0;i<10;i++) {
			stack.push(Integer.valueOf(i));
		}
	}
	
	@Test
	public void popTest() {
		ObjectStack stack = new ObjectStack();
		for (int i=0;i<100;i++) {
			stack.push(Integer.valueOf(i));
		}
		for (int i=0;i<100;i++) {
			assertEquals(Integer.valueOf(99-i), stack.pop());
		}
	}
	
	@Test
	public void popEmptyStackTest() {
		ObjectStack stack = new ObjectStack();
		assertThrows(EmptyStackException.class,()->stack.pop());
	}
	
	@Test
	public void peekEmptyStackTest() {
		ObjectStack stack = new ObjectStack();
		assertThrows(EmptyStackException.class,()->stack.peek());
	}
	
	@Test
	public void peekStackTest() {
		ObjectStack stack = new ObjectStack();
		stack.push(Integer.valueOf(0));
		assertEquals(Integer.valueOf(0), stack.peek());
		assertEquals(Integer.valueOf(0), stack.peek());
	}
}
