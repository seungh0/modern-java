package modern.ch2.policy;

import modern.ch2.AppleFormatterPolicy;
import modern.ch2.FilteringApples;

public class AppleFancyFormatterPolicyImpl implements AppleFormatterPolicy {

	@Override
	public String print(FilteringApples.Apple apple) {
		return "Quiz: " + apple.toString();
	}

}
