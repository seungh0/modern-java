package com.ch19.list;

public class MyLinkedListMain {

	/**
	 * 꼬리가 모두 메모리에 존재하지 않게 할 수 있다. (Supplier<T>를 이용해서)
	 */
	public static void main(String[] args) {
		MyList<Integer> l = new MyLinkedList<>(5, new MyLinkedList<>(10, new Empty<>()));
	}

}
