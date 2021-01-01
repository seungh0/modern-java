package com.ch19;

public class TreeProcessor {

	public static void main(String[] args) {
	}

	private static int lookup(String k, int defaultVal, Tree t) {
		if (t == null) {
			return defaultVal;
		}
		if (k.equals(t.key)) {
			return t.val;
		}
		return lookup(k, defaultVal, k.compareTo(t.key) < 0 ? t.left : t.right);
	}

	// 기존 트리를 변경함 => 트리에 저장된 맵의 모든 사용자가 변경에 영향을 받는다.
	private static Tree update(String k, int newVal, Tree t) {
		if (t == null) {
			t = new Tree(k, newVal, null, null);
		} else if (k.equals(t.key)) {
			t.val = newVal;
		} else if (k.compareTo(t.key) < 0) {
			t.left = update(k, newVal, t.left);
		} else {
			t.right = update(k, newVal, t.right);
		}
		return t;
	}

	/**
	 * 부작용이 없는 하나의 표현식
	 * 함수형 자료구조 => 영속(지정된 값이 다른 누군가에 의해 영향을 받지 않는 상태)
	 * fupdate가 인수로 전달된 자료구조를 변화시키지 않을 것이라는 사실을 확신할 수 있음.
	 */
	private static Tree fupdate(String k, int newVal, Tree t) {
		return (t == null) ? new Tree(k, newVal, null, null) : k.equals(t.key) ? new Tree(k, newVal, t.left, t.right) : k.compareTo(t.key) < 0 ? new Tree(t.key, t.val, fupdate(k, newVal, t.left), t.right) : new Tree(t.key, t.val, t.left, fupdate(k, newVal, t.right));
	}

	static class Tree {

		private String key;
		private int val;
		private Tree left;
		private Tree right;

		public Tree(String key, int val, Tree left, Tree right) {
			this.key = key;
			this.val = val;
			this.left = left;
			this.right = right;
		}

	}
}
