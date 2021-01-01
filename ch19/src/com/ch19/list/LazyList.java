package com.ch19.list;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class LazyList<T> implements MyList<T> {

	private final T head;
	private final Supplier<MyList<T>> tail;

	public LazyList(T head, Supplier<MyList<T>> tail) {
		this.head = head;
		this.tail = tail;
	}

	@Override
	public T head() {
		return head;
	}

	@Override
	public MyList<T> tail() {
		return tail.get(); // tail에서는 Supplier로 게으른 동작을 만들었다.
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public MyList<T> filter(Predicate<T> p) {
		return this;
	}

}
