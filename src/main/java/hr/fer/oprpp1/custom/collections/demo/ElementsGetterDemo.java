package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;
public class ElementsGetterDemo {

	public static void main(String[] args) {
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		col.add("Petar");
		ElementsGetter getter = col.createElementsGetter();
		System.out.println(getter.getNextElement());
		getter.processRemaining(System.out::println);
	}
}
