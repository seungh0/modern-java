package com.ch19.list;

import java.util.function.Predicate;

public class Empty<T> implements MyList<T> {

	@Override
	public T head() {
		throw new UnsupportedOperationException();
	}

	@Override
	public MyList<T> tail() {
		throw new UnsupportedOperationException();
	}

	@Override
	public MyList<T> filter(Predicate<T> p) {
		throw new UnsupportedOperationException();
	}

}
