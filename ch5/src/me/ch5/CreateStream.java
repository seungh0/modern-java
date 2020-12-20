package me.ch5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class CreateStream {

	public static void main(String[] args) {
		Stream<String> stream = Stream.of("Modern", "Java", "in");
		stream.map(String::toUpperCase)
				.forEach(System.out::println);

		Stream<String> homeValue = Stream.ofNullable(System.getProperty("home"));

		int[] numbers = {2, 3, 5};
		int sum = Arrays.stream(numbers).sum();

		long uniqueWords = 0;
		try (Stream<String> lines = Files.lines(Paths.get("data.txt"), Charset.defaultCharset())) {
			uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
					.distinct()
					.count();
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
