package com.ch18;

import java.util.stream.LongStream;

public class FactorialMain {

	public static void main(String[] args) {
		System.out.println(factorialIterative(5));
		System.out.println(factorialRecursive(5));
		System.out.println(factorialStreams(5));
		System.out.println(factorialTailRecursive(5));
	}

	/**
	 * 순수 함수형 프로그래밍 언어에서는 반복문을 포함하지 않음. => 이러한 반복문 때문에 변화가 자연스럽게 코드에 스며들 수 있음.
	 */
	private static int factorialIterative(int n) {
		int r = 1;
		for (int i = 1; i <= n; i++) {
			r *= i;
		}
		return r;
	}

	/**
	 * 반복문과는 달리 재귀를 이용하면 변화가 일어나지 않는다.
	 * 재귀를 이용하면 루프 단계마다 갱신되는 반복 변수를 제거할 수 있다.
	 *
	 * 일반적으로 반복코드보다 재귀 코드가 더 비싸다. Why?
	 * factorialRecursive 함수를 호출할 때마다 호출 스택에 각 호출시 생성되는 정보를 저장할 새로운 스택 프레임일 만들어진다.
	 * 즉 재귀 팩토리얼의 입력값에 비례해서 메모리의 사용량이 증가한다.
	 * => 꼬리 호출 최적화로 해결
	 */
	private static long factorialRecursive(long n) {
		return n == 1 ? 1 : n * factorialRecursive(n - 1);
	}

	/**
	 * 중간 결과를 각각의 스택 프레임으로 저장해야 하는 일반 재귀와 달리 꼬리 재귀에서는 컴파일러가 하나의 스택 프레임을 재활용할 가능성이 생김.
	 * 안타깝게 자바는 이와 같은 최적화를 제공하지 않음 => 그러나 고전적인 재귀보다 여러 컴파일러 최적화 여지를 남겨둘 수 있는 꼬리 재귀를 적용하는 것이 좋다.
	 */
	private static long factorialTailRecursive(long n) {
		return factorialHelper(1, n);
	}

	private static long factorialHelper(long acc, long n) {
		return n == 1 ? acc : factorialHelper(acc * n, n - 1);
	}

	/**
	 * 자바 8 에서는 반복을 스트림으로 대체해서 변화를 피할 수 있다. 또한 반복을 재귀로 바꾸면 더 간결하고, 부작용이 없는 알고리즘을 만들 수 있다.
	 */
	private static long factorialStreams(long n) {
		return LongStream.rangeClosed(1, n)
				.reduce(1, (long a, long b) -> a * b);
	}

}
