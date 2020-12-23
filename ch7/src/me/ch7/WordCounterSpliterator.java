package me.ch7;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class WordCounterSpliterator implements Spliterator<Character> {

	private final String string;
	private int currentChar = 0;

	public WordCounterSpliterator(String string) {
		this.string = string;
	}

	@Override
	public boolean tryAdvance(Consumer<? super Character> action) {
		action.accept(string.charAt(currentChar++));
		return currentChar < string.length();
	}

	@Override
	public Spliterator<Character> trySplit() {
		int currentSize = string.length() - currentChar;
		if (currentSize < 10) {
			return null;
		}
		for (int splitPos = currentSize / 2 + currentSize; splitPos < string.length(); splitPos++) {
			if (Character.isWhitespace(string.charAt(splitPos))) {
				Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, splitPos));
				currentChar = splitPos;
				return spliterator;
			}
		}
		return null;
	}

	@Override
	public long estimateSize() {
		return string.length() - currentChar;
	}

	@Override
	public int characteristics() {
		return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
	}

	public static final String SENTENCE =
			" Nel   mezzo del cammin  di nostra  vita "
					+ "mi  ritrovai in una  selva oscura"
					+ " che la  dritta via era   smarrita ";

	public static void main(String[] args) {
		System.out.println("Found " + countWords(SENTENCE) + " words");
	}

	private static int countWords(String s) {
		Spliterator<Character> spliterator = new WordCounterSpliterator(s);
		Stream<Character> stream = StreamSupport.stream(spliterator, true);

		return countWords(stream);
	}

	private static int countWords(Stream<Character> stream) {
		WordCounter wordCounter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
		return wordCounter.getCounter();
	}

}
