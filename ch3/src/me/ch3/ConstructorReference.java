package me.ch3;

public class ConstructorReference {

	public static void main(String[] args) {
		TiFunction<Integer, Integer, Integer, Color> colorFactory1 = (a, b, c) -> new Color(a, b, c);

		// 생성자 참조
		TiFunction<Integer, Integer, Integer, Color> colorFactory2 = Color::new;

		Color color = colorFactory2.apply(10, 20, 30);
		System.out.println(color.getA());
	}

	public interface TiFunction<T, U, V, R> {
		R apply(T t, U u, V v);
	}

	public static class Color {
		private final int a;
		private final int b;
		private final int c;

		public Color(int a, int b, int c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}

		public int getA() {
			return a;
		}

		public int getB() {
			return b;
		}

		public int getC() {
			return c;
		}
	}

}
