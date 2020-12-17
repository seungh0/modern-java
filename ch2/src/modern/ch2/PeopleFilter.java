package modern.ch2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PeopleFilter {

	public static void main(String[] args) {
		List<Person> people = Arrays.asList(
				new Person(23),
				new Person(40),
				new Person(15)
		);
		List<Person> result = filter(people, (Person p) -> p.getAge() <= 25);
		System.out.println(result);

		Thread t = new Thread(() -> System.out.println("Hello world"));
		t.start();
	}

	public interface PredicatePolicy<T> {

		boolean accept(T t);

	}

	public static <T> List<T> filter(List<T> list, PredicatePolicy<T> policy) {
		List<T> result = new ArrayList<>();
		for (T t : list) {
			if (policy.accept(t)) {
				result.add(t);
			}
		}
		return result;
	}

	public static class Person {
		private int age;

		public Person(int age) {
			this.age = age;
		}

		public int getAge() {
			return this.age;
		}

		@Override
		public String toString() {
			return "Person{" +
					"age=" + age +
					'}';
		}
	}

}
