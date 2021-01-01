package com.ch19;

import java.util.function.DoubleUnaryOperator;

public class Currying {

	public static void main(String[] args) {
		DoubleUnaryOperator convertCtoF = curriedConverter(9.0 / 5, 32);
		DoubleUnaryOperator convertUSDtoGBP = curriedConverter(0.6, 0);
		DoubleUnaryOperator convertKmtoMi = curriedConverter(0.6214, 0);

		double gbp = convertUSDtoGBP.applyAsDouble(1000);
		System.out.println(gbp);
	}

	// 인수의 변환 요소와 기준치를 넣는 일은 귀찮은 일이며 오타도 발생하기 쉬움.
	private static double converter(double x, double f, double b) {
		return x * f + b;
	}

	// 기존 로직을 활용해서 변환기를 특정 상황에 적용할 수 있는 방법 => "커링"
	// 한 개의 인수를 갖는 변환 함수를 생산하는 팩토리를 정의하는 코드
	private static DoubleUnaryOperator curriedConverter(double f, double b) {
		return (double x) -> x * f + b;
	}
}
